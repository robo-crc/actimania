package com.framework.helpers;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/addimage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 2, // 2MB
maxRequestSize = 1024 * 1024 * 10) // 10MB
public class Image extends HttpServlet
{
	private static final long serialVersionUID = -8087776450565155807L;
	
    private static final int MAX_FONT_SIZE = 48;
    //private static final int BOTTOM_MARGIN = 10;
    private static final int TOP_MARGIN = 5;
    private static final int SIDE_MARGIN = 10;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// gets absolute path of the web application
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + Helpers.getClientId().toString();

		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if(!fileSaveDir.exists())
		{
			fileSaveDir.mkdir();
		}

		for(Part part : request.getParts())
		{
			String headerType = part.getContentType();
			if(headerType != null && headerType.equals("image/jpeg"))
			{
				String fileName = extractFileName(part);
				part.write(savePath + File.separator + fileName);
				
				BufferedImage loadImg = ImageIO.read(part.getInputStream());
				loadImg = resize(loadImg, 512, 384);
				
				writeFileCompressed(appPath + File.separator + "CentechNow.jpg", loadImg, 0.75f);
			}
		}

		request.setAttribute("message", "Upload has been done successfully!");
	}

	private String extractFileName(Part part)
	{
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for(String s : items)
		{
			if(s.trim().startsWith("filename"))
			{
				String filename = s.substring(s.indexOf("=") + 2, s.length() - 1);
				if( filename.lastIndexOf("/") != -1 )
				{
					filename = filename.substring(filename.lastIndexOf("/" + 1), filename.length());
				}
				
				return filename;
			}
		}
		return "";
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) 
	{
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
        g.dispose();  
        return dimg;  
    }

	public static void writeFileCompressed(String path, BufferedImage img, float quality) throws IOException
	{
		// get all image writers for JPG format
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

		if (!writers.hasNext())
			throw new IllegalStateException("No writers found");
		
		OutputStream os = new FileOutputStream(path);

		ImageWriter writer = (ImageWriter) writers.next();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();

		// compress to a given quality
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);

		// appends a complete image stream containing a single image and
	    //associated stream and image metadata and thumbnails to the output
		writer.write(null, new IIOImage(img, null, null), param);

		// close all streams
		os.close();
		ios.close();
		writer.dispose();
	}
	
	public static void createMeme(File backgroundFile, String text, File outputFile)
	{
		try 
		{
			BufferedImage image = ImageIO.read(backgroundFile);
			
			drawStringCentered(image, text);
		
			// Make sure the output folder exists
			File dir = new File(outputFile.getParent());
			if(!dir.exists())
			{
				dir.mkdir();
			}
			
			if(outputFile.exists())
			{
				outputFile.delete();
			}
			
			writeFileCompressed(outputFile.getAbsolutePath(), image, 1.f);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException();
		}
		catch (InterruptedException e) 
		{
			throw new RuntimeException();
		}
	}
	
    /**
     * Draws the given string centered, as big as possible, on either the top or
     * bottom 20% of the image given.
     */
    private static void drawStringCentered(BufferedImage image, String text) throws InterruptedException {
        if (text == null)
            text = "";
        
        Graphics graphics = image.getGraphics();

        int height = 0;
        int fontSize = MAX_FONT_SIZE;
        int maxCaptionHeight = image.getHeight() / 5;
        int maxLineWidth = image.getWidth() - SIDE_MARGIN * 2;
        String formattedString = "";

        do 
        {
        	graphics.setFont(new Font("Arial", Font.BOLD, fontSize));

            // first inject newlines into the text to wrap properly
            StringBuilder sb = new StringBuilder();
            int left = 0;
            int right = text.length() - 1;
            while ( left < right ) 
            {
                String substring = text.substring(left, right + 1);
                Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(substring, graphics);
                while ( stringBounds.getWidth() > maxLineWidth ) 
                {
                    if (Thread.currentThread().isInterrupted()) 
                    {
                        throw new InterruptedException();
                    }

                    // look for a space to break the line
                    boolean spaceFound = false;
                    for ( int i = right; i > left; i-- ) 
                    {
                        if ( text.charAt(i) == ' ' ) 
                        {
                            right = i - 1;
                            spaceFound = true;
                            break;
                        }
                    }
                    substring = text.substring(left, right + 1);
                    stringBounds = graphics.getFontMetrics().getStringBounds(substring, graphics);

                    // If we're down to a single word and we are still too wide,
                    // the font is just too big.
                    if ( !spaceFound && stringBounds.getWidth() > maxLineWidth ) 
                    {
                        break;
                    }
                }
                sb.append(substring).append("\n");
                left = right + 2;
                right = text.length() - 1;
            }

            formattedString = sb.toString();

            // now determine if this font size is too big for the allowed height
            height = 0;
            for ( String line : formattedString.split("\n") ) 
            {
                Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(line, graphics);
                height += stringBounds.getHeight();
            }
            fontSize--;
        } while ( height > maxCaptionHeight );

        // draw the string one line at a time
        int y = TOP_MARGIN + graphics.getFontMetrics().getHeight();
        // Bottom formula is 
        // y = image.getHeight() - height - BOTTOM_MARGIN + g.getFontMetrics().getHeight();
        
        for ( String line : formattedString.split("\n") ) 
        {
            // Draw each string twice for a shadow effect
            Rectangle2D stringBounds = graphics.getFontMetrics().getStringBounds(line, graphics);
            graphics.setColor(Color.BLACK);
            graphics.drawString(line, (image.getWidth() - (int) stringBounds.getWidth()) / 2 + 2, y + 2);
            graphics.setColor(Color.WHITE);
            graphics.drawString(line, (image.getWidth() - (int) stringBounds.getWidth()) / 2, y);
            y += graphics.getFontMetrics().getHeight();
        }
    }

}

package com.frontend.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Display a game with all it's events
// http://stackoverflow.com/questions/10878243/sse-and-servlet-3-0

@WebServlet(urlPatterns = { "/gameRefresh" }, asyncSupported = true)
public class GameRefreshController extends HttpServlet 
{
	private static final long serialVersionUID = -6890088129187673292L;

	private static AtomicBoolean refreshNeeded = new AtomicBoolean();

	private final Queue<AsyncContext> ongoingRequests = new ConcurrentLinkedQueue<>();
	private ScheduledExecutorService service;
	
	public static void setRefreshNeeded(boolean value)
	{
		refreshNeeded.set(value);
	}

	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		final Runnable notifier = new Runnable() 
		{
			@Override
			public void run() 
			{
				// Don't refresh if it's not needed.
				if(!refreshNeeded.get())
				{
					return;
				}
				// This var is set by the backend when an event occurs.
				setRefreshNeeded(false);
				
				final Iterator<AsyncContext> iterator = ongoingRequests.iterator();
				// not using for : in to allow removing items while iterating
				while (iterator.hasNext()) 
				{
					AsyncContext asyncContext = iterator.next();
					final ServletResponse servletResponse = asyncContext.getResponse();
					PrintWriter out;
					try 
					{
						out = servletResponse.getWriter();
						String toOutput = "data: refresh\n\n";
						out.write(toOutput);
						out.checkError();
					} 
					catch(IOException exception)
					{
						// iterator is always removed because we refresh the whole page.
					}
					finally
					{
						iterator.remove();
					}
				}
			}
		};
		service = Executors.newScheduledThreadPool(10);
		service.scheduleAtFixedRate(notifier, 1, 1, TimeUnit.SECONDS);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
		final AsyncContext asyncContext = request.startAsync();
		asyncContext.setTimeout(0);
		asyncContext.addListener(new AsyncListener() 
		{
			@Override
			public void onComplete(AsyncEvent event) throws IOException 
			{
				ongoingRequests.remove(asyncContext);
			}

			@Override
			public void onTimeout(AsyncEvent event) throws IOException 
			{
				ongoingRequests.remove(asyncContext);
			}

			@Override
			public void onError(AsyncEvent event) throws IOException 
			{
				ongoingRequests.remove(asyncContext);
			}

			@Override
			public void onStartAsync(AsyncEvent event) throws IOException 
			{
			}
		});
		ongoingRequests.add(asyncContext);
	}
}
namespace ArduinoToServer
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.btnConnect = new System.Windows.Forms.Button();
            this.sendBtn = new System.Windows.Forms.Button();
            this.comPort = new System.Windows.Forms.ComboBox();
            this.baudRate = new System.Windows.Forms.ComboBox();
            this.receiveText = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.sendText = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.shapeContainer1 = new Microsoft.VisualBasic.PowerPacks.ShapeContainer();
            this.Actuator4 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.Actuator1 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape12 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape15 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape14 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape13 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.Actuator6 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape10 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape9 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape8 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape7 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape6 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.Actuator3 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.Actuator5 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.Actuator2 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.lineShape2 = new Microsoft.VisualBasic.PowerPacks.LineShape();
            this.lineShape1 = new Microsoft.VisualBasic.PowerPacks.LineShape();
            this.target1 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.target3 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.target2 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.target6 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.target5 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.target4 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.rectangleShape1 = new Microsoft.VisualBasic.PowerPacks.RectangleShape();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.label16 = new System.Windows.Forms.Label();
            this.blue_score_disp = new System.Windows.Forms.Label();
            this.yellow_score_disp = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.blue_multi_disp = new System.Windows.Forms.Label();
            this.yellow_multi_disp = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.refreshCOM = new System.Windows.Forms.Button();
            this.btn_reset = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // btnConnect
            // 
            this.btnConnect.Location = new System.Drawing.Point(841, 58);
            this.btnConnect.Name = "btnConnect";
            this.btnConnect.Size = new System.Drawing.Size(75, 23);
            this.btnConnect.TabIndex = 0;
            this.btnConnect.Text = "Connect";
            this.btnConnect.UseVisualStyleBackColor = true;
            this.btnConnect.Click += new System.EventHandler(this.btnConnect_Click);
            // 
            // sendBtn
            // 
            this.sendBtn.Location = new System.Drawing.Point(808, 118);
            this.sendBtn.Name = "sendBtn";
            this.sendBtn.Size = new System.Drawing.Size(75, 23);
            this.sendBtn.TabIndex = 1;
            this.sendBtn.Text = "Send";
            this.sendBtn.UseVisualStyleBackColor = true;
            this.sendBtn.Visible = false;
            // 
            // comPort
            // 
            this.comPort.FormattingEnabled = true;
            this.comPort.Location = new System.Drawing.Point(936, 60);
            this.comPort.Name = "comPort";
            this.comPort.Size = new System.Drawing.Size(121, 21);
            this.comPort.TabIndex = 2;
            // 
            // baudRate
            // 
            this.baudRate.FormattingEnabled = true;
            this.baudRate.Location = new System.Drawing.Point(936, 91);
            this.baudRate.Name = "baudRate";
            this.baudRate.Size = new System.Drawing.Size(121, 21);
            this.baudRate.TabIndex = 3;
            // 
            // receiveText
            // 
            this.receiveText.Location = new System.Drawing.Point(808, 166);
            this.receiveText.Name = "receiveText";
            this.receiveText.Size = new System.Drawing.Size(240, 20);
            this.receiveText.TabIndex = 4;
            this.receiveText.TextChanged += new System.EventHandler(this.ChangeTargetColor);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(808, 150);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(47, 13);
            this.label1.TabIndex = 5;
            this.label1.Text = "Receive";
            // 
            // sendText
            // 
            this.sendText.Location = new System.Drawing.Point(808, 214);
            this.sendText.Multiline = true;
            this.sendText.Name = "sendText";
            this.sendText.Size = new System.Drawing.Size(240, 20);
            this.sendText.TabIndex = 6;
            this.sendText.Visible = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(808, 198);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(32, 13);
            this.label2.TabIndex = 7;
            this.label2.Text = "Send";
            this.label2.Visible = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(865, 28);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(108, 13);
            this.label3.TabIndex = 8;
            this.label3.Text = "Serial Communication";
            // 
            // shapeContainer1
            // 
            this.shapeContainer1.Location = new System.Drawing.Point(0, 0);
            this.shapeContainer1.Margin = new System.Windows.Forms.Padding(0);
            this.shapeContainer1.Name = "shapeContainer1";
            this.shapeContainer1.Shapes.AddRange(new Microsoft.VisualBasic.PowerPacks.Shape[] {
            this.Actuator4,
            this.Actuator1,
            this.rectangleShape12,
            this.rectangleShape15,
            this.rectangleShape14,
            this.rectangleShape13,
            this.Actuator6,
            this.rectangleShape10,
            this.rectangleShape9,
            this.rectangleShape8,
            this.rectangleShape7,
            this.rectangleShape6,
            this.Actuator3,
            this.Actuator5,
            this.Actuator2,
            this.lineShape2,
            this.lineShape1,
            this.target1,
            this.target3,
            this.target2,
            this.target6,
            this.target5,
            this.target4,
            this.rectangleShape1});
            this.shapeContainer1.Size = new System.Drawing.Size(1084, 496);
            this.shapeContainer1.TabIndex = 9;
            this.shapeContainer1.TabStop = false;
            // 
            // Actuator4
            // 
            this.Actuator4.BackColor = System.Drawing.Color.Gray;
            this.Actuator4.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.Actuator4.Location = new System.Drawing.Point(85, 267);
            this.Actuator4.Name = "Actuator4";
            this.Actuator4.Size = new System.Drawing.Size(20, 56);
            // 
            // Actuator1
            // 
            this.Actuator1.BackColor = System.Drawing.Color.Gray;
            this.Actuator1.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.Actuator1.Location = new System.Drawing.Point(666, 274);
            this.Actuator1.Name = "Actuator1";
            this.Actuator1.Size = new System.Drawing.Size(20, 56);
            // 
            // rectangleShape12
            // 
            this.rectangleShape12.FillColor = System.Drawing.Color.Gray;
            this.rectangleShape12.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.rectangleShape12.Location = new System.Drawing.Point(533, 417);
            this.rectangleShape12.Name = "rectangleShape12";
            this.rectangleShape12.Size = new System.Drawing.Size(7, 30);
            // 
            // rectangleShape15
            // 
            this.rectangleShape15.FillColor = System.Drawing.Color.Gray;
            this.rectangleShape15.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.rectangleShape15.Location = new System.Drawing.Point(225, 156);
            this.rectangleShape15.Name = "rectangleShape15";
            this.rectangleShape15.Size = new System.Drawing.Size(7, 30);
            // 
            // rectangleShape14
            // 
            this.rectangleShape14.FillColor = System.Drawing.Color.Gray;
            this.rectangleShape14.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.rectangleShape14.Location = new System.Drawing.Point(306, 156);
            this.rectangleShape14.Name = "rectangleShape14";
            this.rectangleShape14.Size = new System.Drawing.Size(7, 30);
            // 
            // rectangleShape13
            // 
            this.rectangleShape13.FillColor = System.Drawing.Color.Gray;
            this.rectangleShape13.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.rectangleShape13.Location = new System.Drawing.Point(450, 416);
            this.rectangleShape13.Name = "rectangleShape13";
            this.rectangleShape13.Size = new System.Drawing.Size(7, 30);
            // 
            // Actuator6
            // 
            this.Actuator6.BackColor = System.Drawing.Color.Gray;
            this.Actuator6.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.Actuator6.Location = new System.Drawing.Point(247, 158);
            this.Actuator6.Name = "Actuator6";
            this.Actuator6.Size = new System.Drawing.Size(43, 26);
            // 
            // rectangleShape10
            // 
            this.rectangleShape10.BackColor = System.Drawing.Color.Gray;
            this.rectangleShape10.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.rectangleShape10.Location = new System.Drawing.Point(232, 165);
            this.rectangleShape10.Name = "rectangleShape10";
            this.rectangleShape10.Size = new System.Drawing.Size(15, 10);
            // 
            // rectangleShape9
            // 
            this.rectangleShape9.BackColor = System.Drawing.Color.Gray;
            this.rectangleShape9.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.rectangleShape9.Location = new System.Drawing.Point(290, 166);
            this.rectangleShape9.Name = "rectangleShape9";
            this.rectangleShape9.Size = new System.Drawing.Size(16, 10);
            // 
            // rectangleShape8
            // 
            this.rectangleShape8.BackColor = System.Drawing.Color.Gray;
            this.rectangleShape8.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.rectangleShape8.Location = new System.Drawing.Point(516, 426);
            this.rectangleShape8.Name = "rectangleShape8";
            this.rectangleShape8.Size = new System.Drawing.Size(16, 10);
            // 
            // rectangleShape7
            // 
            this.rectangleShape7.Location = new System.Drawing.Point(514, 274);
            this.rectangleShape7.Name = "rectangleShape7";
            this.rectangleShape7.Size = new System.Drawing.Size(38, 9);
            // 
            // rectangleShape6
            // 
            this.rectangleShape6.BackColor = System.Drawing.Color.Gray;
            this.rectangleShape6.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.rectangleShape6.Location = new System.Drawing.Point(458, 425);
            this.rectangleShape6.Name = "rectangleShape6";
            this.rectangleShape6.Size = new System.Drawing.Size(15, 10);
            // 
            // Actuator3
            // 
            this.Actuator3.BackColor = System.Drawing.Color.Gray;
            this.Actuator3.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.Actuator3.Location = new System.Drawing.Point(473, 418);
            this.Actuator3.Name = "Actuator3";
            this.Actuator3.Size = new System.Drawing.Size(43, 26);
            // 
            // Actuator5
            // 
            this.Actuator5.BackColor = System.Drawing.Color.Gray;
            this.Actuator5.FillColor = System.Drawing.Color.Gray;
            this.Actuator5.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.Actuator5.Location = new System.Drawing.Point(212, 432);
            this.Actuator5.Name = "Actuator5";
            this.Actuator5.Size = new System.Drawing.Size(103, 12);
            // 
            // Actuator2
            // 
            this.Actuator2.BackColor = System.Drawing.Color.Gray;
            this.Actuator2.FillColor = System.Drawing.Color.Gray;
            this.Actuator2.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.Actuator2.Location = new System.Drawing.Point(447, 156);
            this.Actuator2.Name = "Actuator2";
            this.Actuator2.Size = new System.Drawing.Size(103, 12);
            // 
            // lineShape2
            // 
            this.lineShape2.BorderColor = System.Drawing.Color.Yellow;
            this.lineShape2.Name = "lineShape2";
            this.lineShape2.X1 = 435;
            this.lineShape2.X2 = 435;
            this.lineShape2.Y1 = 152;
            this.lineShape2.Y2 = 448;
            // 
            // lineShape1
            // 
            this.lineShape1.BorderColor = System.Drawing.Color.Yellow;
            this.lineShape1.Name = "lineShape1";
            this.lineShape1.X1 = 326;
            this.lineShape1.X2 = 325;
            this.lineShape1.Y1 = 156;
            this.lineShape1.Y2 = 449;
            // 
            // target1
            // 
            this.target1.BackColor = System.Drawing.Color.Gray;
            this.target1.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target1.BorderColor = System.Drawing.Color.Red;
            this.target1.BorderWidth = 2;
            this.target1.FillColor = System.Drawing.Color.Gray;
            this.target1.Location = new System.Drawing.Point(169, 367);
            this.target1.Name = "target1";
            this.target1.Size = new System.Drawing.Size(32, 30);
            // 
            // target3
            // 
            this.target3.BackColor = System.Drawing.Color.Gray;
            this.target3.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target3.BorderColor = System.Drawing.Color.Red;
            this.target3.BorderWidth = 2;
            this.target3.FillColor = System.Drawing.Color.Gray;
            this.target3.Location = new System.Drawing.Point(169, 203);
            this.target3.Name = "target3";
            this.target3.Size = new System.Drawing.Size(32, 30);
            // 
            // target2
            // 
            this.target2.BackColor = System.Drawing.Color.Gray;
            this.target2.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target2.BorderColor = System.Drawing.Color.Red;
            this.target2.BorderWidth = 2;
            this.target2.FillColor = System.Drawing.Color.Gray;
            this.target2.Location = new System.Drawing.Point(126, 281);
            this.target2.Name = "target2";
            this.target2.Size = new System.Drawing.Size(32, 30);
            // 
            // target6
            // 
            this.target6.BackColor = System.Drawing.Color.Gray;
            this.target6.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target6.BorderColor = System.Drawing.Color.Red;
            this.target6.BorderWidth = 2;
            this.target6.FillColor = System.Drawing.Color.Gray;
            this.target6.Location = new System.Drawing.Point(571, 358);
            this.target6.Name = "target6";
            this.target6.Size = new System.Drawing.Size(32, 30);
            // 
            // target5
            // 
            this.target5.BackColor = System.Drawing.Color.Gray;
            this.target5.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target5.BorderColor = System.Drawing.Color.Red;
            this.target5.BorderWidth = 2;
            this.target5.FillColor = System.Drawing.Color.Gray;
            this.target5.Location = new System.Drawing.Point(613, 286);
            this.target5.Name = "target5";
            this.target5.Size = new System.Drawing.Size(32, 30);
            // 
            // target4
            // 
            this.target4.BackColor = System.Drawing.Color.Gray;
            this.target4.BackStyle = Microsoft.VisualBasic.PowerPacks.BackStyle.Opaque;
            this.target4.BorderColor = System.Drawing.Color.Red;
            this.target4.BorderWidth = 2;
            this.target4.FillColor = System.Drawing.Color.Gray;
            this.target4.Location = new System.Drawing.Point(572, 209);
            this.target4.Name = "target4";
            this.target4.Size = new System.Drawing.Size(32, 30);
            // 
            // rectangleShape1
            // 
            this.rectangleShape1.BorderColor = System.Drawing.Color.Red;
            this.rectangleShape1.BorderWidth = 10;
            this.rectangleShape1.FillStyle = Microsoft.VisualBasic.PowerPacks.FillStyle.Solid;
            this.rectangleShape1.Location = new System.Drawing.Point(80, 151);
            this.rectangleShape1.Name = "rectangleShape1";
            this.rectangleShape1.Size = new System.Drawing.Size(612, 300);
            // 
            // pictureBox2
            // 
            this.pictureBox2.BackColor = System.Drawing.Color.Black;
            this.pictureBox2.BackgroundImage = global::ArduinoToServer.Properties.Resources.CRC;
            this.pictureBox2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox2.Image = global::ArduinoToServer.Properties.Resources.CRC;
            this.pictureBox2.Location = new System.Drawing.Point(207, 261);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(86, 71);
            this.pictureBox2.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox2.TabIndex = 11;
            this.pictureBox2.TabStop = false;
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.Color.Black;
            this.pictureBox1.BackgroundImage = global::ArduinoToServer.Properties.Resources.CRC;
            this.pictureBox1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.pictureBox1.Image = global::ArduinoToServer.Properties.Resources.CRC;
            this.pictureBox1.Location = new System.Drawing.Point(467, 261);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(86, 71);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox1.TabIndex = 10;
            this.pictureBox1.TabStop = false;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.BackColor = System.Drawing.Color.Transparent;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(173, 373);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(18, 20);
            this.label4.TabIndex = 12;
            this.label4.Text = "1";
            this.label4.Visible = false;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.BackColor = System.Drawing.Color.Transparent;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(136, 286);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(18, 20);
            this.label5.TabIndex = 13;
            this.label5.Text = "2";
            this.label5.Visible = false;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.BackColor = System.Drawing.Color.Transparent;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(173, 208);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(18, 20);
            this.label6.TabIndex = 14;
            this.label6.Text = "3";
            this.label6.Visible = false;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.BackColor = System.Drawing.Color.Transparent;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(574, 212);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(18, 20);
            this.label7.TabIndex = 15;
            this.label7.Text = "4";
            this.label7.Visible = false;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.BackColor = System.Drawing.Color.Transparent;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(619, 292);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(18, 20);
            this.label8.TabIndex = 16;
            this.label8.Text = "5";
            this.label8.Visible = false;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.BackColor = System.Drawing.Color.Transparent;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.Location = new System.Drawing.Point(574, 358);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(18, 20);
            this.label9.TabIndex = 17;
            this.label9.Text = "6";
            this.label9.Visible = false;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.BackColor = System.Drawing.Color.Transparent;
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.Location = new System.Drawing.Point(669, 292);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(18, 20);
            this.label10.TabIndex = 18;
            this.label10.Text = "1";
            this.label10.Visible = false;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.BackColor = System.Drawing.Color.Transparent;
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(487, 152);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(18, 20);
            this.label11.TabIndex = 19;
            this.label11.Text = "2";
            this.label11.Visible = false;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.BackColor = System.Drawing.Color.Transparent;
            this.label12.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label12.Location = new System.Drawing.Point(487, 425);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(18, 20);
            this.label12.TabIndex = 20;
            this.label12.Text = "3";
            this.label12.Visible = false;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.BackColor = System.Drawing.Color.Transparent;
            this.label13.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label13.Location = new System.Drawing.Point(88, 286);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(18, 20);
            this.label13.TabIndex = 21;
            this.label13.Text = "4";
            this.label13.Visible = false;
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.BackColor = System.Drawing.Color.Transparent;
            this.label14.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label14.Location = new System.Drawing.Point(258, 429);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(18, 20);
            this.label14.TabIndex = 22;
            this.label14.Text = "5";
            this.label14.Visible = false;
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.BackColor = System.Drawing.Color.Transparent;
            this.label15.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label15.Location = new System.Drawing.Point(258, 162);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(18, 20);
            this.label15.TabIndex = 23;
            this.label15.Text = "6";
            this.label15.Visible = false;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(978, 280);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 24;
            this.button1.Text = "Show";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label16.Location = new System.Drawing.Point(961, 261);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(111, 16);
            this.label16.TabIndex = 25;
            this.label16.Text = "# Target/Actuator";
            // 
            // blue_score_disp
            // 
            this.blue_score_disp.AutoSize = true;
            this.blue_score_disp.BackColor = System.Drawing.Color.Black;
            this.blue_score_disp.Font = new System.Drawing.Font("Microsoft Sans Serif", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.blue_score_disp.ForeColor = System.Drawing.Color.Blue;
            this.blue_score_disp.Location = new System.Drawing.Point(764, 412);
            this.blue_score_disp.Name = "blue_score_disp";
            this.blue_score_disp.Size = new System.Drawing.Size(51, 55);
            this.blue_score_disp.TabIndex = 26;
            this.blue_score_disp.Text = "0";
            // 
            // yellow_score_disp
            // 
            this.yellow_score_disp.AutoSize = true;
            this.yellow_score_disp.BackColor = System.Drawing.Color.Black;
            this.yellow_score_disp.Font = new System.Drawing.Font("Microsoft Sans Serif", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.yellow_score_disp.ForeColor = System.Drawing.Color.Yellow;
            this.yellow_score_disp.Location = new System.Drawing.Point(968, 409);
            this.yellow_score_disp.Name = "yellow_score_disp";
            this.yellow_score_disp.Size = new System.Drawing.Size(51, 55);
            this.yellow_score_disp.TabIndex = 27;
            this.yellow_score_disp.Text = "0";
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label17.Location = new System.Drawing.Point(854, 373);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(79, 25);
            this.label17.TabIndex = 28;
            this.label17.Text = "Scores";
            // 
            // blue_multi_disp
            // 
            this.blue_multi_disp.AutoSize = true;
            this.blue_multi_disp.BackColor = System.Drawing.Color.Black;
            this.blue_multi_disp.Font = new System.Drawing.Font("Microsoft Sans Serif", 27.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.blue_multi_disp.ForeColor = System.Drawing.Color.Blue;
            this.blue_multi_disp.Location = new System.Drawing.Point(41, 70);
            this.blue_multi_disp.Name = "blue_multi_disp";
            this.blue_multi_disp.Size = new System.Drawing.Size(58, 42);
            this.blue_multi_disp.TabIndex = 29;
            this.blue_multi_disp.Text = "x0";
            // 
            // yellow_multi_disp
            // 
            this.yellow_multi_disp.AutoSize = true;
            this.yellow_multi_disp.BackColor = System.Drawing.Color.Black;
            this.yellow_multi_disp.Font = new System.Drawing.Font("Microsoft Sans Serif", 27.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.yellow_multi_disp.ForeColor = System.Drawing.Color.Yellow;
            this.yellow_multi_disp.Location = new System.Drawing.Point(133, 70);
            this.yellow_multi_disp.Name = "yellow_multi_disp";
            this.yellow_multi_disp.Size = new System.Drawing.Size(58, 42);
            this.yellow_multi_disp.TabIndex = 30;
            this.yellow_multi_disp.Text = "x0";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label18.Location = new System.Drawing.Point(62, 28);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(110, 25);
            this.label18.TabIndex = 31;
            this.label18.Text = "Multipliers";
            // 
            // refreshCOM
            // 
            this.refreshCOM.Location = new System.Drawing.Point(765, 58);
            this.refreshCOM.Name = "refreshCOM";
            this.refreshCOM.Size = new System.Drawing.Size(75, 23);
            this.refreshCOM.TabIndex = 32;
            this.refreshCOM.Text = "Refresh";
            this.refreshCOM.UseVisualStyleBackColor = true;
            this.refreshCOM.Click += new System.EventHandler(this.refreshCOM_Click);
            // 
            // btn_reset
            // 
            this.btn_reset.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btn_reset.Location = new System.Drawing.Point(811, 263);
            this.btn_reset.Name = "btn_reset";
            this.btn_reset.Size = new System.Drawing.Size(105, 74);
            this.btn_reset.TabIndex = 33;
            this.btn_reset.Text = "RESET";
            this.btn_reset.UseVisualStyleBackColor = true;
            this.btn_reset.Click += new System.EventHandler(this.button2_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1084, 496);
            this.Controls.Add(this.btn_reset);
            this.Controls.Add(this.refreshCOM);
            this.Controls.Add(this.label18);
            this.Controls.Add(this.yellow_multi_disp);
            this.Controls.Add(this.blue_multi_disp);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.yellow_score_disp);
            this.Controls.Add(this.blue_score_disp);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.pictureBox2);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.sendText);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.receiveText);
            this.Controls.Add(this.baudRate);
            this.Controls.Add(this.comPort);
            this.Controls.Add(this.sendBtn);
            this.Controls.Add(this.btnConnect);
            this.Controls.Add(this.shapeContainer1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form1";
            this.Text = "CRC Field Monitor";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Form1_FormClosed);
            this.Load += new System.EventHandler(this.Form1_Load_1);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnConnect;
        private System.Windows.Forms.Button sendBtn;
        private System.Windows.Forms.ComboBox comPort;
        private System.Windows.Forms.ComboBox baudRate;
        private System.Windows.Forms.TextBox receiveText;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox sendText;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        public Microsoft.VisualBasic.PowerPacks.ShapeContainer shapeContainer1;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target3;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target1;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target2;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target6;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target5;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape target4;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape1;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.PictureBox pictureBox2;
        private Microsoft.VisualBasic.PowerPacks.LineShape lineShape2;
        private Microsoft.VisualBasic.PowerPacks.LineShape lineShape1;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator4;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator1;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape12;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape15;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape14;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape13;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator6;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape10;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape9;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape8;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape7;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape rectangleShape6;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator3;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator5;
        private Microsoft.VisualBasic.PowerPacks.RectangleShape Actuator2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label blue_score_disp;
        private System.Windows.Forms.Label yellow_score_disp;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label blue_multi_disp;
        private System.Windows.Forms.Label yellow_multi_disp;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Button refreshCOM;
        private System.Windows.Forms.Button btn_reset;



        
    }
}


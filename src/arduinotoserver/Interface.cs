using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO.Ports;
using System.Threading;
using System.Net.Http;
using System.Threading.Tasks;

namespace ArduinoToServer
{

    public partial class Interface : Form
    {

        public static System.IO.Ports.SerialPort port;

        delegate void SetTextCallback(string text);

        private BackgroundWorker hardWorker;

        private Thread readThread = null; //Variable de type Thgread pour la lecture du port série

        public string[] value;

        int blue_multi =0;//Variable du multiplicateur bleu
        int yellow_multi =0;//Variable du multiplicateur jaune

        int blue_score = 0;//Variable du score bleu
        int yellow_score = 0;//Variable du score jaune

        string message;//Variable du contenu reçu depuis le Uno


        public Interface()
        {
            InitializeComponent();
            hardWorker = new BackgroundWorker();
            sendBtn.Enabled = false;
            cmbActuatorState.SelectedIndex = 0;
            cmbArduinoNumber.SelectedIndex = 0;
        }

        private void sendBtn_Click(object sender, EventArgs e)//Fonction d'envoie par port série (non utilisé, éléments masqués dans le Form) 
        {
            if (port.IsOpen)
            {
                port.Write(sendText.Text);
            }
        }

        private void SetText(string text)//Fonction d'affichage du message reçu dans la textbox
        {

            if (this.receiveText.InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                this.Invoke(d, new object[] { text });
            }
            else
            {
                //this.receiveText.Text += "Text: ";
                this.receiveText.Text = "";
                this.receiveText.Text += text;
                //this.receiveText.Text += Environment.NewLine;
            }
        }

        public void Read()//Fonction de lecture du port série
        {
            while (port.IsOpen)
            {
                try
                {
                    if (port.BytesToRead > 0)
                    {
                        message = port.ReadLine();
                        this.SetText(message);
                    }
                }
                catch (TimeoutException) { }
            }
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)//Fonction exécuté à la fermeture du programme, termine le thread de lecture et ferme le port série
        {
            try
            {
                if (!(readThread == null))
                    readThread.Abort();
            }
            catch (NullReferenceException)
            {
            }

            try
            {
                port.Close();
            }
            catch (NullReferenceException)
            {
            }
        }

        private void Form1_Load_1(object sender, EventArgs e)//Fonction exécuté au démarrage du programme, détecte les ports série et mets en place les vitesses de connexion
        {
            foreach (string s in SerialPort.GetPortNames())
            {
                comPort.Items.Add(s);
            }
            if (comPort.Items.Count > 0)
                comPort.SelectedIndex = comPort.Items.Count - 1;

            else
            {
                if (comPort.Text != "")
                {
                    btnConnect.Enabled = true;
                    comPort.SelectedIndex = 0;
                }
                else
                    btnConnect.Enabled = false;
                
            }

            baudRate.Items.Add("2400");
            baudRate.Items.Add("4800");
            baudRate.Items.Add("9600");
            baudRate.Items.Add("14400");
            baudRate.Items.Add("19200");
            baudRate.Items.Add("28800");
            baudRate.Items.Add("38400");
            baudRate.Items.Add("57600");
            baudRate.Items.Add("115200");

            baudRate.SelectedIndex = 2;
        }

        private void btnConnect_Click(object sender, EventArgs e)// Fonction pour le bouton Connect, prend le port et la vitesse de trnasfert en paramètre
        {
            System.ComponentModel.IContainer components =
               new System.ComponentModel.Container();
            port = new System.IO.Ports.SerialPort(components);
            port.PortName = comPort.SelectedItem.ToString();
            port.BaudRate = Int32.Parse(baudRate.SelectedItem.ToString());
            port.DtrEnable = true;
            port.ReadTimeout = 5000;
            port.WriteTimeout = 500;
            port.Open();

            readThread = new Thread(new ThreadStart(this.Read));
            readThread.Start();
            this.hardWorker.RunWorkerAsync();

            btnConnect.Text = "<Connected>";

            btnConnect.Enabled = false;
            refreshCOM.Enabled = false;
            comPort.Enabled = false;
            sendBtn.Enabled = true;
        }

        enum SIDE
        {
            BLUE,
            YELLOW
        }

        enum ACTUATOR_STATE
        {
            CLOSED,
            BLUE,
            YELLOW
        }

        enum TARGET
        {
            LOW,
            MID,
            HIGH
        }

        private static TARGET arduinoToTarget(String arduinoNumber)
        {
            switch (arduinoNumber)
            {
                case "1":
                case "4":
                    return TARGET.LOW;

                case "3":
                case "6":
                    return TARGET.MID;

                case "2":
                case "5":
                    return TARGET.HIGH;

                default:
                    throw new Exception("Unknown arduino number");
            }
        }

        private static SIDE arduinoToSide(String arduinoNumber)
        {
            switch (arduinoNumber)
            {
                case "1":
                case "2":
                case "3":
                    return SIDE.BLUE;

                case "4":
                case "5":
                case "6":
                    return SIDE.YELLOW;

                default:
                    throw new Exception("Unknown arduino number");
            }
        }

        private static ACTUATOR_STATE arduinoToState(String state)
        {
            switch(state)
            {
                case "BH":
                    return ACTUATOR_STATE.BLUE;

                case "JH":
                    return ACTUATOR_STATE.YELLOW;

                case "JL":
                case "BL":
                    return ACTUATOR_STATE.CLOSED;

                default:
                    throw new Exception("Unknown arduino state");
            }
        }

        public static String SERVER_ADDRESS = "http://localhost:8080/actimania/arduino";
        //public static String SERVER_ADDRESS = "http://192.227.134.178:8080/actimania/arduino";
        public static String EMAIL = "serverToArduino";
        public static String PASSWORD = "ThisIsATmpPassword";

        private async void sendActuatorChangedToServer(String arduinoNumber, String strState)
        {
            SIDE side = arduinoToSide(arduinoNumber);
            TARGET target = arduinoToTarget(arduinoNumber);
            ACTUATOR_STATE state = arduinoToState(strState);

            using (var client = new HttpClient())
            {
                var values = new List<KeyValuePair<string, string>>();
                values.Add(new KeyValuePair<string, string>("gameEvent", "ACTUATOR_STATE_CHANGED"));
                values.Add(new KeyValuePair<string, string>("email", EMAIL));
                values.Add(new KeyValuePair<string, string>("password", PASSWORD));
                values.Add(new KeyValuePair<string, string>("side", side.ToString()));
                values.Add(new KeyValuePair<string, string>("target", target.ToString()));
                values.Add(new KeyValuePair<string, string>("actuatorState", state.ToString()));

                var content = new FormUrlEncodedContent(values);

                await client.PostAsync(SERVER_ADDRESS, content);
            }
        }

        private async void sendTargetHitToServer(String arduinoNumber)
        {
            using (var client = new HttpClient())
            {
                SIDE side = arduinoToSide(arduinoNumber);
                TARGET target = arduinoToTarget(arduinoNumber);

                var values = new List<KeyValuePair<string, string>>();
                values.Add(new KeyValuePair<string, string>("gameEvent", "TARGET_HIT"));
                values.Add(new KeyValuePair<string, string>("email", EMAIL));
                values.Add(new KeyValuePair<string, string>("password", PASSWORD));
                values.Add(new KeyValuePair<string, string>("side", side.ToString()));
                values.Add(new KeyValuePair<string, string>("target", target.ToString()));

                var content = new FormUrlEncodedContent(values);

                await client.PostAsync(SERVER_ADDRESS, content);
            }
        }

        private void SetColor(string target , string color)//Fonction d'attribution de couleur aux cibles selon les message du Uno
        {
            if (target == "1")
            {
                if (color == "BH")
                {
                    Actuator1.BackColor = System.Drawing.Color.Blue;
                    target1.BackColor = System.Drawing.Color.Blue;
                    blue_multi_mod("+");
                }

                else if (color == "JH")
                {
                    Actuator1.BackColor = System.Drawing.Color.Yellow;
                    target1.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;
                }
                else if (color == "JL" || color== "BL")
                {
                    Actuator1.BackColor = System.Drawing.Color.Gray;
                    target1.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi_mod("-");
                    if (color == "JL") yellow_multi--;
                }

            }

            if (target == "2")
            {

                if (color == "BH")
                {
                    Actuator2.BackColor = System.Drawing.Color.Blue;
                    target2.BackColor = System.Drawing.Color.Blue;
                    blue_multi_mod("+");

                }

                else if (color == "JH")
                {
                    Actuator2.BackColor = System.Drawing.Color.Yellow;
                    target2.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;
                }
                else if (color == "JL" || color == "BL")
                {
                    Actuator2.BackColor = System.Drawing.Color.Gray;
                    target2.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi_mod("-");
                    if (color == "JL") yellow_multi--;

                }
            }

            if (target == "3")
            {

                if (color == "BH")
                {
                    Actuator3.BackColor = System.Drawing.Color.Blue;
                    target3.BackColor = System.Drawing.Color.Blue;
                    blue_multi_mod("+");

                }

                else if (color == "JH")
                {
                    Actuator3.BackColor = System.Drawing.Color.Yellow;
                    target3.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;

                }
                else if (color == "JL" || color == "BL")
                {
                    Actuator3.BackColor = System.Drawing.Color.Gray;
                    target3.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi_mod("-");
                    if (color == "JL") yellow_multi--;

                }
            }

            if (target == "4")
            {

                if (color == "BH")
                {
                    Actuator4.BackColor = System.Drawing.Color.Blue;
                    target4.BackColor = System.Drawing.Color.Blue;
                    blue_multi++;

                }

                else if (color == "JH")
                {
                    Actuator4.BackColor = System.Drawing.Color.Yellow;
                    target4.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;

                }
                else if (color == "JL" || color == "BL")
                {
                    Actuator4.BackColor = System.Drawing.Color.Gray;
                    target4.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi--;
                    if (color == "JL") yellow_multi--;

                }
            }

            if (target == "5")
            {

                if (color == "BH")
                {
                    Actuator5.BackColor = System.Drawing.Color.Blue;
                    target5.BackColor = System.Drawing.Color.Blue;
                    blue_multi++;

                }

                else if (color == "JH")
                {
                    Actuator5.BackColor = System.Drawing.Color.Yellow;
                    target5.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;

                }
                else if (color == "JL" || color == "BL")
                {
                    Actuator5.BackColor = System.Drawing.Color.Gray;
                    target5.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi--;
                    if (color == "JL") yellow_multi--;

                }
            }

            if (target == "6")
            {

                if (color == "BH")
                {
                    Actuator6.BackColor = System.Drawing.Color.Blue;
                    target6.BackColor = System.Drawing.Color.Blue;
                    blue_multi++;

                }

                else if (color == "JH")
                {
                    Actuator6.BackColor = System.Drawing.Color.Yellow;
                    target6.BackColor = System.Drawing.Color.Yellow;
                    yellow_multi++;

                }
                else if (color == "JL" || color == "BL")
                {
                    Actuator6.BackColor = System.Drawing.Color.Gray;
                    target6.BackColor = System.Drawing.Color.Gray;
                    if (color == "BL") blue_multi--;
                    if (color == "JL") yellow_multi--;


                }
            }
            UpdateMultiplier();
        }
        
        private void UpdateMultiplier()//Fonction d'update de l'affichage du multiplicateur
        {
            blue_multi_disp.Text = "x" + blue_multi.ToString();
            yellow_multi_disp.Text = "x" + yellow_multi.ToString();
        
        }
        private void UpdateScore()// Fonction d'update de l'affichage du score
        {
            blue_score_disp.Text = blue_score.ToString();
            yellow_score_disp.Text = yellow_score.ToString();
        
        }

        private void setScore(string C) //Fonction qui update le score selon la détection d'une cible
        {
            if (C == "1")
            {
                if (target1.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (10*blue_multi);
                if (target1.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (10 * yellow_multi);
                UpdateScore();
            }
            if (C == "2")
            {
                if (target2.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (20 * blue_multi);
                if (target2.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (20 * yellow_multi);
                UpdateScore();
            }
            if (C == "3")
            {
                if (target3.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (40 * blue_multi);
                if (target3.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (40 * yellow_multi);
                UpdateScore();
            }
            if (C == "4")
            {
                if (target4.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (10 * blue_multi);
                if (target4.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (10 * yellow_multi);
                UpdateScore();
            }
            if (C == "5")
            {
                if (target5.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (20 * blue_multi);
                if (target5.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (20 * yellow_multi);
                UpdateScore();
            }
            if (C == "6")
            {
                if (target6.BackColor == System.Drawing.Color.Blue) blue_score = blue_score + (40 * blue_multi);
                if (target6.BackColor == System.Drawing.Color.Yellow) yellow_score = yellow_score + (40 * yellow_multi);
                UpdateScore();
            }
            
        }

        private void ChangeTargetColor(object sender, EventArgs e)// Fonction lancée par défaut lors de la réception d'un message venant du Uno
                                                                    //Sert à: updater les couleurs de cibles, ou de détecter quand qqun a marqué 
        {
            value = message.Replace("\r", "").Split('.');

            if (value[0] == "C")
            {
                setScore(value[1]);
                sendTargetHitToServer(value[1]);
            }
            else
            {
                SetColor(value[0], value[1]);
                sendActuatorChangedToServer(value[0], value[1]);
            }
        }


        private void blue_multi_mod(string operation)
        {
            if (operation == "+")
            {
                switch (blue_multi)
                {
                    case 0:
                        blue_multi = 1;
                        break;
                    case 1:
                        blue_multi = 2;
                        break;
                    case 2:
                        blue_multi = 5;
                        break;
                    case 5:
                        blue_multi = 11;
                        break;
                }
            }
       

            if (operation == "-")
            {
                switch (blue_multi)
                {
                    case 1:
                        blue_multi = 0;
                        break;
                    case 2:
                        blue_multi = 1;
                        break;
                    case 5:
                        blue_multi = 2;
                        break;
                    case 11:
                        blue_multi = 5;
                        break;
                }
            }
            operation = null;
        }






        bool toggle_label = true; //Variable pour permmetre le togle des tags des éléments de jeu      
        
        private void button1_Click(object sender, EventArgs e) //Fonction pour afficher les tags pour les éléments du terrain de jeu
        {
            label4.Visible ^= true;
            label5.Visible ^= true;
            label6.Visible ^= true;
            label7.Visible ^= true;
            label8.Visible ^= true;
            label9.Visible ^= true;
            label10.Visible ^= true;
            label11.Visible ^= true;
            label12.Visible ^= true;
            label13.Visible ^= true;
            label14.Visible ^= true;
            label15.Visible ^= true;

            toggle_label ^= true;

            if (toggle_label == true) button1.Text = "Show";
            if (toggle_label == false) button1.Text = "Hide";
        }

        private void refreshCOM_Click(object sender, EventArgs e)
        {
            foreach (string s in SerialPort.GetPortNames())
            {
                comPort.Items.Add(s);
            }
            if (comPort.Items.Count > 0)
                comPort.SelectedIndex = comPort.Items.Count - 1;
            if (comPort.Text == "")
                btnConnect.Enabled = false;
            else
                btnConnect.Enabled = true;
        }

        private void button2_Click(object sender, EventArgs e)//Master Reset
        {
            Actuator1.BackColor = System.Drawing.Color.Gray;
            target1.BackColor = System.Drawing.Color.Gray;
            Actuator2.BackColor = System.Drawing.Color.Gray;
            target2.BackColor = System.Drawing.Color.Gray;
            Actuator3.BackColor = System.Drawing.Color.Gray;
            target3.BackColor = System.Drawing.Color.Gray;
            Actuator4.BackColor = System.Drawing.Color.Gray;
            target4.BackColor = System.Drawing.Color.Gray;
            Actuator5.BackColor = System.Drawing.Color.Gray;
            target5.BackColor = System.Drawing.Color.Gray;
            Actuator6.BackColor = System.Drawing.Color.Gray;
            target6.BackColor = System.Drawing.Color.Gray;
            
            blue_multi = 0;//Variable du multiplicateur bleu
            yellow_multi = 0;//Variable du multiplicateur jaune

            blue_score = 0;//Variable du score bleu
            yellow_score = 0;//Variable du score jaune

            message = null;//Variable du contenu reçu depuis le Uno
            UpdateMultiplier();
            UpdateScore();

        }

        private void btnTargetHit_Click(object sender, EventArgs e)
        {
            sendTargetHitToServer(cmbArduinoNumber.Text);
        }

        private void btnActuatorChanged_Click(object sender, EventArgs e)
        {
            sendActuatorChangedToServer(cmbArduinoNumber.Text, cmbActuatorState.Text);
        }
    }
}






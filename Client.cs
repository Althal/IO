using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab3
{
    public class Client
    {
        private TcpClient client;
        private string ipAddress;
        private int port;
        private bool isConnected = false;
        private NetworkStream stream;
        private int clientNumber { get; set; }



        public Client(string ipAddress, int port)
        {
            this.ipAddress = ipAddress;
            this.port = port;
            client = new TcpClient();
        }

        public string IpAddress { get => ipAddress; set => ipAddress = value; }
        public int Port { get => port; set => port = value; }




        /* Metoda łączy klienta do serwera */
        public void initConnection()
        {
            ThreadPool.QueueUserWorkItem(initConnection);
        }


        public void sendMessage(string message)
        {
            if (!isConnected) return;

            byte[] mes = Encoding.ASCII.GetBytes("Klient " + clientNumber + " wysłał wiadomość: \n" + message);
            stream.Write(mes, 0, mes.Length);
        }






        private void initConnection(Object stateInfo)
        {
            Console.WriteLine("Symulacja klienta");
            Thread.Sleep(500);

            client.Connect(IPAddress.Parse(IpAddress), Port);
            stream = client.GetStream();

            byte[] buffer = new byte[1024];
            int l =stream.Read(buffer, 0, 1024);
            Console.WriteLine(System.Text.Encoding.ASCII.GetString(buffer,0,l));

            buffer = new byte[1024];
            int l1 = stream.Read(buffer, 0, 1024);
            string number = System.Text.Encoding.ASCII.GetString(buffer,0,l1);
            Console.WriteLine("Klient odebrał i przypisał identyfikator: " + number);
            clientNumber = System.Convert.ToInt32(number);

            isConnected = true;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace Lab3
{
    public class Serwer
    {
        private bool isRunning = false;
        private int port;
        private string ipAddress;

        private int clientNumber = 0;
        private TcpListener tcpListener;
        private List<NetworkStream> clients = new List<NetworkStream>();

        public Serwer(string ipAddress, int port)
        {
            this.ipAddress = ipAddress;
            tcpListener = new TcpListener(IPAddress.Parse(ipAddress), port);
        }

        public void start()
        {
            if (!isRunning)
            {
                ThreadPool.QueueUserWorkItem(start);
                isRunning = true;
            }
        }

        private void start(Object stateInfo)
        {
            tcpListener.Start();
            Console.WriteLine("Uruchomiono serwer");
            while (true)
            {
                TcpClient client = tcpListener.AcceptTcpClient();
                clients.Add(client.GetStream());
                ThreadPool.QueueUserWorkItem(acceptClient, new object[]{ clientNumber++});
                Console.WriteLine("Dodano klienta");
            }
        }

        private void acceptClient(Object stateInfo)
        {
            int clientNumber = (int)((object[])stateInfo)[0];

            byte[] message = Encoding.ASCII.GetBytes("Serwer zaakceptował klienta i nadał mu identyfikator: " + clientNumber);
            clients[clientNumber].Write(message, 0, message.Length);

            Thread.Sleep(100);
            message = Encoding.ASCII.GetBytes(System.Convert.ToString(clientNumber));
            clients[clientNumber].Write(message, 0, message.Length);

            /*byte[] buffer = new byte[1024];
            clients[clientNumber].Read(buffer, 0, 1024);
            Console.WriteLine(System.Text.Encoding.ASCII.GetString(buffer));*/

            while (true)
            {
                byte[] buffer = new byte[1024];
                int l = clients[clientNumber].Read(buffer, 0, 1024);
                Console.WriteLine("System otrzymał wiadomość od klienta" + clientNumber + ":\n" + System.Text.Encoding.ASCII.GetString(buffer,0,l));
            }
        }
    }
}

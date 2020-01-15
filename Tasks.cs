using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            Task t = serverTask();
            Task c = clientTask("w");
            Task.WaitAll(new Task[] {t,c});
        }

        static async Task serverTask()
        {
            TcpListener server = new TcpListener(IPAddress.Loopback, 2048);
            server.Start();
            while (true)
            {
                TcpClient client = await server.AcceptTcpClientAsync();
                byte[] buffer = new byte[1024];
                await client.GetStream().ReadAsync(buffer, 0, buffer.Length).ContinueWith(
                    async (t) =>
                    {
                        int i = t.Result;
                        while (true)
                        {
                            client.GetStream().WriteAsync(buffer, 0, i);
                            i = await client.GetStream().ReadAsync(buffer, 0, buffer.Length);
                        }
                    });
            }
        }

        static async Task clientTask(string wiadomosc)
        {
            TcpClient client = new TcpClient();
            client.Connect(IPAddress.Loopback, 2048);
            for (int i=0; i<5; i++)
            {
                client.GetStream().WriteAsync(Encoding.ASCII.GetBytes(wiadomosc), 0, 1024);
                byte[] buffer = new byte[1024];
                int m = await client.GetStream().ReadAsync(buffer, 0, buffer.Length);
                Console.WriteLine(m);
            }
        }
    }
}

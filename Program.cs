using System;
using System.Threading;

namespace Lab3
{
    class Program
    {

        static void Main(string[] args)
        {
            Serwer serwer = new Serwer("127.0.0.1", 10000);
            serwer.start();

            Thread.Sleep(1000);

            Client client1 = new Client("127.0.0.1", 10000);
            client1.initConnection();

            Thread.Sleep(1000);

            Client client2 = new Client("127.0.0.1", 10000);
            client2.initConnection();

            Thread.Sleep(1000);

            Client client3 = new Client("127.0.0.1", 10000);
            client3.initConnection();

            Thread.Sleep(5000);
            client2.sendMessage("qwerty");

            Thread.Sleep(5000);
            client1.sendMessage("asdfgh");

            while (true) ;
        }
    }
}

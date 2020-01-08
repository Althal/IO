using System;

namespace ConsoleApp1
{
    class ProgramImage
    {
        static void Main(string[] args)
        {
            PbmImage img = new PbmImage(80,1);
            img.createChessboard();
            img.saveImg(@"C:\Test\2.txt");

            Console.WriteLine("Press any key to exit.");
            System.Console.ReadKey();
        }

        static void readAndSave()
        {
            PbmImage img = new PbmImage(@"C:\Test\1.txt");
            img.saveImg(@"C:\Test\2.txt");
        }
    }
}

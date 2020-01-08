using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace ConsoleApp1
{
    class PbmImage
    {
        private int width;
        private int height;
        private int maxVal;

        private List<int> img = new List<int>();


        public PbmImage(int size, int maxVal)
        {
            width = size;
            height = size;
            this.maxVal = maxVal;
        }

        public PbmImage(string path)
        {
            string[] lines = System.IO.File.ReadAllLines(@"C:\Test\1.txt");

            for(int i=1; i<lines.Length; i++)
            {
                if(lines[i][0] == '#') continue;
                string[] splited = lines[i].Split(new char[0], StringSplitOptions.RemoveEmptyEntries);

                if (splited.Length == 2)
                {
                    width = int.Parse(splited[0]);
                    height = int.Parse(splited[1]);
                }
                else if(splited.Length == 1)
                {
                    maxVal = int.Parse(splited[0]);
                }
                else
                {
                    foreach(string val in splited) img.Add(int.Parse(val));
                }
            }

            for(int i=0; i<img.Count; i++)
            {
                if (i % width == 0) Console.WriteLine();
                Console.Write(img[i] + " ");
            }

            // Keep the console window open in debug mode.
            Console.WriteLine();
            Console.WriteLine("Stałe: " + width + " " + height + " " + maxVal);
        }

        public void saveImg(string path)
        {
            using (FileStream fs = new FileStream(path, FileMode.Create))
            {
                using (StreamWriter w = new StreamWriter(fs))
                {
                    w.WriteLine("P2");
                    w.WriteLine(width + " " + height);
                    w.Write(maxVal);
                    for(int i=0; i<img.Count; i++)
                    {
                        if (i % width == 0) w.WriteLine();
                        w.Write(img[i] + " ");
                    }
                }
            }
        }

        public void createChessboard()
        {
            img = new List<int>();
            if (width % 8 != 0)
            {
                Console.WriteLine("Zła długość");
                return;
            }

            int lineSize = width / 8;

            for(int i=0; i<width*width; i++)
            {
                int x = (i / width) / lineSize;
                int y = (i % width) / lineSize;

                if(x % 2 == 0)
                {
                    if (y % 2 == 0) img.Add(0);
                    else img.Add(maxVal);
                }
                else
                {
                    if (y % 2 == 0) img.Add(maxVal);
                    else img.Add(0);
                }
            }
        }
    }
}

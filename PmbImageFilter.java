
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


public class PmbImageFilter {
  
    private static String IMAGE_PATH = "D:/Test/1.txt";
    private static String FILTER_PATH = "D:/Test/2.txt";
    private static String OUTPUT_PATH_SEQ = "D:/Test/3.txt";
    private static String OUTPUT_PATH_MTH = "D:/Test/4.txt";
    
    public static void main(String... args) throws Exception {
        
        ArrayList<String> image = getFileContent(IMAGE_PATH);
        int[][] img = getImage(image);

        ArrayList<String> filter = getFileContent(FILTER_PATH);
        BigDecimal[][] fil = getFilter(filter);

        long start = System.nanoTime();
        BigDecimal[][] seq = getFilteredImage(img,fil);
        long stop = System.nanoTime();
        System.out.println("Wykonywanie sekwencyjne: " + (stop - start) + " ns");
        
        saveToFile(seq, false);
        
        long start1 = System.nanoTime();
        BigDecimal[][] mth = getFilteredImageMth(img,fil);
        long stop1 = System.nanoTime();
        System.out.println("Wykonywanie wielowątkowe: " + (stop1 - start1) + " ns");
        
        saveToFile(mth, true);
    }
    
    private static BigDecimal getFilteredValue(int i, int j, int[][] image, BigDecimal[][] filter){
        BigDecimal ret = new BigDecimal(0);
        int filterRadius = filter.length / 2;
        
        for(int x=i-filterRadius; x<=i+filterRadius; x++){
            for(int y = j-filterRadius; y <= j+filterRadius; y++){
                if(x<0 || y<0 || x>= image.length || y>=image[0].length) continue;                
                ret = ret.add(filter[x-i+filterRadius][y-j+filterRadius].multiply(new BigDecimal(image[x][y])));
            }
        }
        
        return ret;
    }
    
    private static BigDecimal[][] getFilteredImage(int[][] image, BigDecimal[][] filter){
        int height = image.length;
        int width = image[0].length;
        BigDecimal[][] ret = new BigDecimal[height][width];
        
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                ret[i][j] = getFilteredValue(i,j,image,filter);
            }
        }
        
        return ret;
    }
    
    private static BigDecimal[][] getFilteredImageMth(int[][] image, BigDecimal[][] filter) throws InterruptedException{
        int height = image.length;
        int width = image[0].length;
        BigDecimal[][] ret = new BigDecimal[height][width];
        
        Thread[] threads = new Thread[height];
        
        for(int i=0; i<height; i++){
            final int i1 = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0; j<width; j++){
                        ret[i1][j] = getFilteredValue(i1,j,image,filter);
                    }
                }
            });
        }
        for(Thread t : threads) t.start();
        for(Thread t : threads) t.join();
        
        return ret;
    }
    
    private static void saveToFile(BigDecimal[][] img, boolean multithreaded) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(multithreaded? OUTPUT_PATH_MTH : OUTPUT_PATH_SEQ));
        for(BigDecimal[] row : img){
            for(BigDecimal val : row){
                writer.write(val.toString());
                writer.write("  ");
            }
            writer.newLine();
        }
        writer.close();
    }
    
    private static ArrayList<String> getFileContent(String path) throws FileNotFoundException, IOException {
        ArrayList<String> ret = new ArrayList<>();

        BufferedReader file = null;
        try {            
            file = new BufferedReader(new FileReader(path));
            String line = file.readLine();
            while (line != null) {
                if(line.charAt(0) != '#') ret.add(line);
                line = file.readLine();
            }
        } finally {
            if (file != null) {
                file.close();
            }
        }
        
        return ret;
    }
    
    private static int[][] getImage(ArrayList<String> lines){
        String[] sizes = lines.get(1).split(" ");
        int length = Integer.parseInt(sizes[0]);
        int height = Integer.parseInt(sizes[1]);
        
        int[][] ret = new int[height][length];
        for(int i=0; i<height; i++){
            String[] values = lines.get(i+3).split("  ");
            for(int j=0; j<length; j++){
                ret[i][j] = Integer.parseInt(values[j]);
            }
        }
        
        return ret;
    }
    
    private static BigDecimal[][] getFilter(ArrayList<String> lines) throws Exception{
        String[] sizes = lines.get(0).split(" ");
        int size = sizes.length;        
        if(lines.size() != size || size % 2 != 1) throw new Exception("Błędne argumenty filtra");
        
        BigDecimal[][] ret = new BigDecimal[size][size];        
        for(int i=0; i<size; i++){
            String[] values = lines.get(i).split(" ");
            for(int j=0; j<size; j++){
                ret[i][j] = new BigDecimal(values[j]);
            } 
        }
        
        return ret;
    }
}

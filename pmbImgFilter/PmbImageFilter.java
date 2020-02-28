
package pmbImgFilter;

import java.io.BufferedWriter;
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

        ImagePmb img = new ImagePmb(IMAGE_PATH);
        FilterPmb fil = new FilterPmb(FILTER_PATH);
        
        FilteringImage exe = new FilteringImage(img,fil);
        
        long start = System.nanoTime();
        FilteredImage f1 = exe.getFilteredImage();
        long stop = System.nanoTime();
        System.out.println("Wykonywanie sekwencyjne: " + (stop - start) + " ns");        
        saveToFile(f1.getData(), false);
        
        long start1 = System.nanoTime();
        FilteredImage f2 = exe.getFilteredImageMth();
        long stop1 = System.nanoTime();
        System.out.println("Wykonywanie wielowątkowe: " + (stop1 - start1) + " ns");
        
        saveToFile(f2.getData(), true);
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
}

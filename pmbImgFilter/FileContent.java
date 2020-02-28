
package pmbImgFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class FileContent {
    
    
    protected final ArrayList<String> lines;

    
    public FileContent(String path) throws FileNotFoundException, IOException {
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
        
        lines = ret;
    }
    
    
    public ArrayList<String> getLines(){
        return lines;
    }
    
}


package pmbImgFilter;

import java.io.FileNotFoundException;
import java.io.IOException;


public class ImagePmb extends FileContent{

    
    private int[][] img;
    
    
    public ImagePmb(String path) throws FileNotFoundException, IOException {
        super(path);
        setImage();
    }

    
    private void setImage(){
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
        img = ret;
    }
    
    
    public int[][] getImg(){
        return img;
    }
}

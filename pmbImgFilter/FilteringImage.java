
package pmbImgFilter;

import java.math.BigDecimal;


public class FilteringImage {
    
    
    private final ImagePmb img;
    private final FilterPmb filter;
    
    
    public FilteringImage(ImagePmb img, FilterPmb filter){
        this.img = img;
        this.filter = filter;
    }
    
    
    public FilteredImage getFilteredImageMth() throws InterruptedException{
        int height = img.getImg().length;
        int width = img.getImg()[0].length;
        BigDecimal[][] ret = new BigDecimal[height][width];
        
        Thread[] threads = new Thread[height];
        
        for(int i=0; i<height; i++){
            final int i1 = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0; j<width; j++){
                        ret[i1][j] = getFilteredValue(i1,j);
                    }
                }
            });
        }
        for(Thread t : threads) t.start();
        for(Thread t : threads) t.join();
        
        return new FilteredImage(ret);
    }
    
    private BigDecimal getFilteredValue(int i, int j){
        BigDecimal ret = new BigDecimal(0);
        int filterRadius = filter.getFilter().length / 2;
        
        for(int x=i-filterRadius; x<=i+filterRadius; x++){
            for(int y = j-filterRadius; y <= j+filterRadius; y++){
                if(x<0 || y<0 || x>= img.getImg().length || y>=img.getImg()[0].length) continue;                
                ret = ret.add(filter.getFilter()[x-i+filterRadius][y-j+filterRadius].multiply(new BigDecimal(img.getImg()[x][y])));
            }
        }
        
        return ret;
    }
    
    public FilteredImage getFilteredImage(){
        int height = img.getImg().length;
        int width = img.getImg()[0].length;
        BigDecimal[][] ret = new BigDecimal[height][width];
        
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                ret[i][j] = getFilteredValue(i,j);
            }
        }
        
        return new FilteredImage(ret);
    }
}

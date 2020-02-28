
package pmbImgFilter;

import java.math.BigDecimal;

public class FilterPmb extends FileContent{
    
    
    private BigDecimal[][] filter;
    
    
    public FilterPmb(String path) throws Exception{
        super(path);
        setFilter();
    }
    
    
    private void setFilter() throws Exception{
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
        
        filter = ret;
    }
    
    
    public BigDecimal[][] getFilter(){
        return filter;
    }
}

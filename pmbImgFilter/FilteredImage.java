
package pmbImgFilter;

import java.math.BigDecimal;

public class FilteredImage {
    
    
    private final BigDecimal[][] data;

    
    public FilteredImage(BigDecimal[][] data) {
        this.data = data;
    }
        
    public BigDecimal[][] getData(){
        return data;
    }
}

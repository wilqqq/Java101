package cooValue;
/**
 * COOValue -immutable
 */
public final class COOValue {
    private final Object value;
    private final int spot;
    
    public COOValue(Object value, int spot){
        this.value = value;
        this.spot = spot;
    }

    public Object getValue() {
        return this.value;
    }

    public int getSpot() {
        return this.spot;
    }

    public String toString() {
        return new String("("+value.toString()+","+spot+")");
    }
}
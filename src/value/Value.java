package value;

/**
 * Value
 */
public abstract class Value {
    public abstract String toString();
    public abstract Value add(Value obj);
    public abstract Value sub(Value obj);
    public abstract Value mul(Value obj);
    public abstract Value div(Value obj);
    public abstract Value pow(Value obj);
    public abstract boolean eq(Value obj);
    // public abstract boolean eq(String s);
    public abstract boolean lte(Value obj);
    public abstract boolean gte(Value obj);
    public abstract boolean neq(Value obj);
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract Value create(String s); 
    // public abstract Class<? extends Object> getValue();
}
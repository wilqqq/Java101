package iValue;

import value.*;

/**
 * DValue
 */
public class IValue extends Value {
    private Integer value;

    public static class Builder {
        private Integer value = 0;

        public Builder setValue(int value) {
            this.value = value;
            return this;
        }

        public Builder setValue(String s) {
            try {
                this.value = Integer.parseInt(s);
            } catch (Exception e) {
                this.value = 0;
            }
            return this;
        }

        public IValue build() {
            return new IValue(this.value);
        }
    }

    public static Builder builder() {
        return new IValue.Builder();
    }

    private Integer parse(Value obj) {
        Integer val;
        try {
            val = Integer.parseInt(obj.toString());
        } catch (Exception e) {
            val = 0;
        }
        return val;
    }

    private IValue(Value obj) {
        this.value = parse(obj);
    }

    private IValue(int value) {
        this.value = value;
    }

    @Override
    public Value add(Value obj) {
        this.value += parse(obj);
        return this;
    }

    public Value add(IValue obj) {
        this.value += obj.getValue();
        return this;
    }

    @Override
    public Value create(String s) {
        return new Builder().setValue(s).build();
    }

    @Override
    public Value div(Value obj) {
        // TODO consider obj.eq("0");
        // if(obj.eq("0"))
        // throw new IllegalArgumentException("Argument 'divisor' is 0");
        // this.value /= parse(obj);
        Integer num = parse(obj);
        if (num == 0.0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        this.value /= num;
        return this;
    }

    public Value div(IValue obj) {
        if (obj.getValue() == 0.0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        this.value /= obj.getValue();
        return this;
    }

    @Override
    public boolean eq(Value obj) {
        return this.value == parse(obj);
    }

    @Override
    public boolean equals(Object other) {
        try {
            return this.value == Integer.parseInt(other.toString());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean gte(Value obj) {
        return this.value >= parse(obj);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean lte(Value obj) {
        return this.value <= parse(obj);
    }

    @Override
    public Value mul(Value obj) {
        this.value *= parse(obj);
        return this;
    }

    @Override
    public boolean neq(Value obj) {
        return !eq(obj);
    }

    @Override
    public Value pow(Value obj) {
        int pow,v=this.value;
        if((pow=parse(obj))<=0){
            this.value = 0;
        }else{
            for(int i=0;i<pow;i++)
                this.value *= v;
        }
        return this;
    }

    @Override
    public Value sub(Value obj) {
        this.value -= parse(obj);
        return this;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // return (DValue)super.clone();
        return new IValue(this.value);
    }

    
}
package dValue;

import value.*;

/**
 * DValue
 */
public class DValue extends Value {
    private Double value;

    public static class Builder{
        private Double value=0.0;
        public Builder setValue(double value){
            this.value = value;
            return this;
        }
        public Builder setValue(String s){
            try {
                this.value = Double.parseDouble(s);
            } catch (Exception e) {
                this.value = 0.0;
            }
            return this;
        }
        public DValue build(){
            return new DValue(this.value);
        }
    }
    public static Builder builder(){
        return new DValue.Builder();
    }

    private Double parse(Value obj){
        double val;
        try {
            val = Double.parseDouble(obj.toString());
        } catch (Exception e) {
            val = 0.0;
        }
        return val;
    }

    private DValue(Value obj){
        this.value = parse(obj);
    }
    private DValue(double value){
        this.value = value;
    }

    @Override 
    public Value add(Value obj) {
        this.value += parse(obj);
        return this;
    }

    public Value add(DValue obj){
        this.value += obj.getValue();
        return this;
    }

    @Override
    public Value create(String s) {
        return new Builder().setValue(s).build();
    }

    @Override
    public Value div(Value obj) {
        //TODO consider obj.eq("0");
        // if(obj.eq("0"))
        //     throw new IllegalArgumentException("Argument 'divisor' is 0");
        // this.value /= parse(obj);
        Double num = parse(obj);
        if(num == 0.0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        this.value /= num;
        return this;
    }

    public Value div(DValue obj) {
        if(obj.getValue() == 0.0)
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
        try{
            return this.value == Double.parseDouble(other.toString());
        }catch(Exception e){
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
        this.value = Math.pow(this.value, parse(obj));
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    
}
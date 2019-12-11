package sValue;

import dValue.DValue;
import iValue.IValue;
import value.*;

/**
 * DValue
 */
public class SValue extends Value {
    private String value;

    public static class Builder {
        private String value = "";

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public SValue build() {
            return new SValue(this.value);
        }
    }

    public static Builder builder() {
        return new SValue.Builder();
    }

    private String parse(Value obj) {
        return obj.toString();
    }

    private SValue(Value obj) {
        this.value = parse(obj);
    }

    private SValue(String value) {
        this.value = value;
    }

    @Override
    public Value add(Value obj) {
        this.value += parse(obj);
        return this;
    }

    public Value add(SValue obj) {
        this.value += obj.getValue();
        return this;
    }

    @Override
    public Value create(String s) {
        return new Builder().setValue(s).build();
    }

    @Override
    public Value div(Value obj) {//derotfl
        byte [] passwd = obj.toString().getBytes();
        byte [] result = this.value.getBytes();
        for(int i=0;i<result.length;i++){
            // System.out.println(result[i] +" "+passwd[i%passwd.length]);
            result[i] -= ('a'-passwd[i%passwd.length]);
        }
        this.value = new String(result);
        return this;
    }

    @Override
    public boolean eq(Value obj) {
        return this.value.equals(obj.toString());
    }

    @Override
    public boolean equals(Object other) {
        // return this.value.equals(other);
        return eq((Value)other);
    }

    @Override
    public boolean gte(Value obj) {
        return this.value.length() >= parse(obj).length();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean lte(Value obj) {
        return this.value.length() <= parse(obj).length();
    }

    @Override
    public Value mul(Value obj) {//rotfl
        byte [] passwd = obj.toString().getBytes();
        byte [] result = this.value.getBytes();
        for(int i=0;i<result.length;i++){
            // System.out.println(result[i] +" "+passwd[i%passwd.length]);
            result[i] += ('a'-passwd[i%passwd.length]);
        }
        this.value = new String(result);
        return this;
    }

    @Override
    public boolean neq(Value obj) {
        return !eq(obj);
    }

    @Override
    public Value pow(Value obj) {
        // if(obj instanceof IValue)
        try{
            if(obj instanceof IValue){
                this.value.repeat(Math.abs(Integer.parseInt(obj.toString())));
            }else if(obj instanceof DValue){
                int w = Math.abs((int)Math.round(Double.parseDouble(obj.toString())));
                this.value.repeat(w);
            }
        }catch(NumberFormatException e){
            this.value.repeat(obj.toString().length());
        }
        return this;
    }

    @Override
    public Value sub(Value obj) {
        String num = parse(obj);
        if (num == "")
            return this;
        this.value.replaceAll(num, "");
        return this;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // return (DValue)super.clone();
        return new SValue(this.value);
    }

    
}
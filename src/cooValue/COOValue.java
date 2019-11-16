package cooValue;

import value.Value;

/**
 * COOValue -immutable
 */
public final class COOValue extends Value {
    private final Value value;
    private final int spot;

    public COOValue(Value value, int spot) {
        this.value = value;
        this.spot = spot;
    }

    public Value getValue() {
        return this.value;
    }

    public int getSpot() {
        return this.spot;
    }

    public String toString() {
        return new String("(" + value.toString() + "," + spot + ")");
    }

    @Override
    public Value add(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return new COOValue((this.value.add(o.value)), spot);
        }
        return this;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new COOValue(value, spot);
    }

    @Override
    public Value create(String s) {
        return this.value.create(s);
    }

    @Override
    public Value div(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return new COOValue((this.value.div(o.value)), spot);
        }
        return this;
    }

    @Override
    public boolean eq(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return this.value.eq(obj);
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return this.value.equals(other);
    }

    @Override
    public boolean gte(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return ((this.value.gte(o.value)));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean lte(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return ((this.value.lte(o.value)));
        }
        return false;
    }

    @Override
    public Value mul(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return new COOValue((this.value.mul(o.value)), spot);
        }
        return this;
    }

    @Override
    public boolean neq(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return this.value.neq(o.value);
        }
        return true;
    }

    @Override
    public Value pow(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return new COOValue((this.value.pow(o.value)), spot);
        }
        return this;
    }

    @Override
    public Value sub(Value obj) {
        if (obj instanceof COOValue) {
            COOValue o = (COOValue) obj;
            if (this.spot == o.spot)
                return new COOValue((this.value.sub(o.value)), spot);
        }
        return this;
    }
}
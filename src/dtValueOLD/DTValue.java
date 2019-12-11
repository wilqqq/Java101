package dtValueOLD;

import value.Value;

/**
 * DTValue
 */
public class DTValue extends Value {
    // days sep dec feb
    static final int[] daysOfMonth = { 30, 31, 30, 31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private String[] value;
    private int[] ivalue;

    public static class Builder {
        private String value[] = new String[] { "2019", "01", "01", "00", "00", "00" };

        public Builder setYear(String value) {
            if (value.matches("^[0-9][0-9][0-9][0-9]$")) {
                this.value[0] = value;
            }
            return this;
        }

        public Builder setMonth(String value) {
            if (value.matches("^[0[0-9]|1[0-2]]$")) {
                this.value[1] = value;
            }
            return this;
        }

        public Builder setDay(String value) {
            if (value.matches("^[30|31]$") && this.value[1].equals("02")) {
                this.value[2] = "28";
                return this;
            }
            if (value.matches("^[[0-2][0-9]|3[0-1]]$"))
                this.value[2] = value;
            return this;
        }

        public Builder setHour(String value) {
            if (value.matches("^[[0-1][0-9]|2[0-3]]$"))
                this.value[3] = value;
            return this;
        }

        public Builder setMinutes(String value) {
            if (value.matches("^[0-5][0-9]$"))
                this.value[4] = value;
            return this;
        }

        public Builder setSeconds(String value) {
            if (value.matches("^[0-5][0-9]$"))
                this.value[5] = value;
            return this;
        }

        public Builder setValue(String s) {
            String[] date = DTValue.parse(s);
            for (int i = 0; i < date.length; i++)
                this.value[i] = date[i];
            return this;// .setSeconds(date[5]).setMinutes(date[4]).setHour(date[3]).setDay(date[2]).setMonth(date[1]).setYear(date[0]);
        }

        public DTValue build() {
            return new DTValue(this.value[0], this.value[1], this.value[2], this.value[3], this.value[4],
                    this.value[5]);
        }
    }

    public static Builder builder() {
        return new DTValue.Builder();
    }

    private static String[] parse(Value obj) {
        return parse(obj.toString());
    }

    private static String[] parse(String obj) {
        String[] result = new String[] { "0000", "00", "00", "00", "00", "00" };
        String[] tmp = obj.split("-");
        if (tmp.length == 2) {
            String[] date = tmp[0].split("/");
            if (date[0].matches("^[0-9]{4}$"))
                result[0] = date[0];
            // for(i=1;i<date.length;i++)
            if (date[1].matches("^0[0-9]|1[0-2]$"))
                result[1] = date[1];
            if (date[2].matches("^[0-2][0-9]|3[0-1]$"))
                result[2] = date[2];
            if (result[1] == "02" && result[2].matches("^30|31$"))
                result[2] = "28";
            String[] time = tmp[1].split(":");
            if (time[0].matches("^[0-1][0-9]|2[0-4]$"))
                result[3] = time[0];
            for (int i = 1; i < time.length; i++)
                if (time[i].matches("^[0-5][0-9]$"))
                    result[i + 3] = time[i];
            // if(result[0].length()>4)
            // result[0]="9999";
            // for(i=1;i<3;i++)
            // if(result[i].length()>2)
            // result[i]="9999";
        } // TODO not full date/time
        return result;
    }

    private DTValue(String v0, String v1, String v2, String v3, String v4, String v5) {
        this.value = new String[] { v0, v1, v2, v3, v4, v5 };
        for (int i = 0; i < 6; i++)
            this.ivalue[i] = Integer.parseInt(this.value[i]);
    }

    private DTValue(String[] v) {
        this(v[0], v[1], v[2], v[3], v[4], v[5]);
    }

    public long toTS() {
        long ret = 0;
        ret += (long) (ivalue[0]) * 365;
        ret += (long) (ivalue[1]);
        ret *= 30;
        ret += (long) (ivalue[2]);
        ret *= 24;
        ret += (long) (ivalue[3]);
        ret *= 60;
        ret += (long) (ivalue[4]);
        ret *= 60;
        ret += (long) (ivalue[5]);
        return ret;
    }

    @Override
    public Value add(Value obj) {
        String[] tmp;
        if (obj instanceof DTValue)
            tmp = ((DTValue) obj).value;
        else
            tmp = parse(obj.toString());

        int addFl = 0, w = 0;
        // minutes and seconds
        for (int i = 5; i >= 4; i--) {// from end to beg with flag
            w = Integer.parseInt(this.value[i]) + Integer.parseInt(tmp[i]) + addFl;
            addFl = w / 60;
            w = w % 60;
            if (w < 10)
                this.value[i] = "0" + w;
            else
                this.value[i] = "" + w;
        }
        // hours
        w = Integer.parseInt(this.value[3]) + Integer.parseInt(tmp[3]) + addFl;
        addFl = w / 24;
        w = w % 24;
        if (w < 10)
            this.value[3] = "0" + w;
        else
            this.value[3] = "" + w;
        // days
        // sep dec feb[5]
        // daysOfMonth={30,31,30,31,31,28,31,30,31,30,31,31,30,31,30,31};
        int[] dom = daysOfMonth.clone();
        if ((w = Integer.parseInt(this.value[1])) >= 3 && w < 9) // doesn't bother february
            w += 4;
        else {
            if (w < 3) {
                w += 4;// jan or feb
                if ((Integer.parseInt(this.value[0]) % 4) == 0) // this year is a leap year
                    dom[5] = 29;
            } else {
                w -= 9;// later than september
                if ((Integer.parseInt(this.value[0]) % 4) == 3) // next year is a leap year
                    dom[5] = 29;
            }
        }
        {
            int i = w;
            w = Integer.parseInt(this.value[2]) + Integer.parseInt(tmp[2]) + addFl;
            for (addFl = 0;; i++, addFl++) {
                if (w <= dom[i])
                    break;
                w -= dom[i];
            }
        }
        if (w < 10)
            this.value[2] = "0" + w;
        else
            this.value[2] = "" + w;
        // months
        w = Integer.parseInt(this.value[1]) + Integer.parseInt(tmp[1]) + addFl;
        addFl = w / 12;
        w = w % 12;
        if (w < 10)
            this.value[1] = "0" + w;
        else
            this.value[1] = "" + w;
        // years
        w = Integer.parseInt(this.value[0]) + Integer.parseInt(tmp[0]) + addFl;
        if (w > 9999)
            this.value[0] = "9999";
        else if (w < 10)
            this.value[0] = "000" + w;
        else if (w < 100)
            this.value[0] = "00" + w;
        else if (w < 1000)
            this.value[0] = "0" + w;
        else
            this.value[0] = "" + w;

        return this;
    }

    @Override
    public Value create(String s) {
        return new DTValue(parse(s));
    }

    @Override
    public Value div(Value obj) {
        return this;
    }

    @Override
    public boolean eq(Value obj) {
        return toString().equals(obj.toString());
    }

    @Override
    public boolean equals(Object other) {
        return this.equals(other);
    }

    @Override
    public boolean gte(Value obj) {
        if (obj instanceof DTValue) {
            DTValue tmp = (DTValue) obj;
            for (int i = 5; i >= 0; i--) {
                if (Integer.parseInt(tmp.value[i]) > Integer.parseInt(this.value[i]))
                    return false;
            }
            return true;
        }
        String[] tmp = parse(obj.toString());
        for (int i = 5; i >= 0; i--) {
            if (Integer.parseInt(tmp[i]) > Integer.parseInt(this.value[i]))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean lte(Value obj) {
        if (obj instanceof DTValue) {
            DTValue tmp = (DTValue) obj;
            for (int i = 5; i >= 0; i--) {
                if (Integer.parseInt(tmp.value[i]) < Integer.parseInt(this.value[i]))
                    return false;
            }
            return true;
        }
        String[] tmp = parse(obj.toString());
        for (int i = 5; i >= 0; i--) {
            if (Integer.parseInt(tmp[i]) < Integer.parseInt(this.value[i]))
                return false;
        }
        return true;
    }

    @Override
    public Value mul(Value obj) {
        return this;
    }

    @Override
    public boolean neq(Value obj) {
        return !eq(obj);
    }

    @Override
    public Value pow(Value obj) {
        return this;
    }

    @Override
    public Value sub(Value obj) {
        // TODO maybe sub
        return this;
    }

    @Override
    public String toString() {
        return this.value[0] + "-" + this.value[1] + "-" + this.value[2] + "/" + this.value[3] + ":" + this.value[4]
                + ":" + this.value[5];
    }

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        String[] nw = this.value.clone();
        return new DTValue(nw);
    }
    
}
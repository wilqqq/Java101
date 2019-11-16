package dtValue;

import java.time.LocalDate;
import java.time.LocalTime;

import dValue.DValue;
import iValue.IValue;
import value.*;

/**
 * DTValue
 */
public class DTValue extends Value {
    private LocalDate date;
    private LocalTime time;

    public static class Builder {
        private int[] value = {0,1,1,0,0,0};

        public Builder setYear(String value) {
            if (value.matches("^[0-9][0-9][0-9][0-9]$")) {
                this.value[0] = Integer.parseInt(value);
            }
            return this;
        }

        public Builder setMonth(String value) {
            if (value.matches("^0[0-9]|1[0-2]$")) {
                this.value[1] = Integer.parseInt(value);
            }
            return this;
        }

        public Builder setDay(String value) {
            if (this.value[1] == 2 && value.matches("^30|31$")) {
                value = "28";
            }
            if (value.matches("^[0-2][0-9]|3[0-1]$"))
                this.value[2] = Integer.parseInt(value);
            return this;
        }

        public Builder setHour(String value) {
            if (value.matches("^[0-1][0-9]|2[0-3]$"))
                this.value[3] = Integer.parseInt(value);
            return this;
        }

        public Builder setMinutes(String value) {
            if (value.matches("^[0-5][0-9]$"))
                this.value[4] = Integer.parseInt(value);
            return this;
        }

        public Builder setSeconds(String value) {
            if (value.matches("^[0-5][0-9]$"))
                this.value[5] = Integer.parseInt(value);
            return this;
        }

        public Builder setValue(String s) {
            String [] tmp=s.split(" ");
            if(tmp.length == 2){
                String [] date = tmp[0].split("-");
                if(date[0].matches("^[0-9]{4}$"))
                    value[0] = Integer.parseInt(date[0]);
                // for(i=1;i<date.length;i++)
                if(date[1].matches("^0[0-9]|1[0-2]$"))
                    value[1] = Integer.parseInt(date[1]);
                if(date[2].matches("^[0-2][0-9]|3[0-1]$"))
                    value[2] = Integer.parseInt(date[2]);
                if(value[1] == 2 && value[2] >= 30)
                    value[2] = 28;
                String [] time = tmp[1].split(":");
                if(time[0].matches("^[0-1][0-9]|2[0-4]$"))
                    value[3] = Integer.parseInt(time[0]);
                for(int i=1;i<time.length;i++)
                    if(time[i].matches("^[0-5][0-9]$"))
                        value[i+3] = Integer.parseInt(time[i]);
            }else if(tmp.length == 1){
                String [] date = s.split("-");
                if(date.length==3){
                    if(date[0].matches("^[0-9]{4}$"))
                        value[0] = Integer.parseInt(date[0]);
                    // for(i=1;i<date.length;i++)
                    if(date[1].matches("^0[0-9]|1[0-2]$"))
                        value[1] = Integer.parseInt(date[1]);
                    if(date[2].matches("^[0-2][0-9]|3[0-1]$"))
                        value[2] = Integer.parseInt(date[2]);
                    if(value[1] == 2 && value[2] >= 30)
                        value[2] = 28;
                    return this;
                }else{
                    String [] time = s.split(":");
                    if(s.length()<3)
                        return this;
                    if(time[0].matches("^[0-1][0-9]|2[0-4]$"))
                        value[3] = Integer.parseInt(time[0]);
                    for(int i=1;i<time.length;i++)
                        if(time[i].matches("^[0-5][0-9]$"))
                            value[i+3] = Integer.parseInt(time[i]);
                }
            }
            return this;
        }

        public DTValue build() {
            return new DTValue(this.value);
        }
    }

    public static final DTValue EPOCH_DT_VALUE = new DTValue(LocalDate.EPOCH,LocalTime.MIN);

    public static Builder builder() {
        return new DTValue.Builder();
    }

    private DTValue(int [] dt) {
        date = LocalDate.of(dt[0],dt[1],dt[2]);
        time = LocalTime.of(dt[3],dt[4],dt[5]);
    }

    private DTValue(LocalDate ld, LocalTime lt){
        date = ld.plusYears(0);
        time = lt.plusHours(0);
    }

    private DTValue(String obj) {
        String [] dt = obj.split(" ");
        if(dt.length == 2){
            date = LocalDate.parse(dt[0]);
            time = LocalTime.parse(dt[1]);
        }else{
            if(obj.split("/").length==3)
                date = LocalDate.parse(obj);
            else if(obj.split(":").length==3)
                time = LocalTime.parse(obj);
            else{
                date = LocalDate.of(0,1,1);
                time = LocalTime.of(0,0,0);
            }
        }
    }

    public long toEpoch(){
        return this.date.toEpochDay()*24*3600+(long)this.time.toSecondOfDay();
    }

    @Override
    public Value add(Value obj) {
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
        int lastHour = this.time.getHour();
        this.time = tmp.time.plusSeconds(this.time.getSecond())//
        .plusMinutes(this.time.getMinute())
        .plusHours(this.time.getHour());
        if(this.time.getHour()<lastHour)
            this.date = this.date.plusDays(1);
        this.date = tmp.date.plusDays(this.date.getDayOfMonth())
        .plusMonths(this.date.getMonthValue())
        .plusYears(this.date.getYear());
        return this;
    }

    @Override
    public Value create(String s) {
        return new DTValue(s);
    }

    @Override
    public Value div(Value obj) {
        if(obj instanceof IValue){
            long tmp = (long)((IValue)obj).getValue();
            return this.div(tmp);
            // if(tmp == 0)
            //     throw new IllegalArgumentException("Argument 'divisor' is 0");
            // this.ofEpoch(this.toEpoch()/tmp);
            // this.ofSec(this.toSec()/tmp);
        }
        return this;
    }
    public Value div(long tmp) {
        if(tmp == 0)
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        // this.date.di
        // this.time = tmp.time.plusSeconds(this.time.getSecond())//
        // .plusMinutes(this.time.getMinute())
        // .plusHours(this.time.getHour());
        // if(this.time.getHour()<lastHour)
        //     this.date = this.date.plusDays(1);
        // this.date = tmp.date.plusDays(this.date.getDayOfMonth())
        // .plusMonths(this.date.getMonthValue())
        // .plusYears(this.date.getYear());
        this.date = LocalDate.ofEpochDay(this.date.toEpochDay()/tmp);
        // this.time = LocalTime.ofSecondOfDay(this.time./tmp);
        return this;
    }

    @Override
    public boolean eq(Value obj) {
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
        return this.date.isEqual(tmp.date) && this.time.equals(tmp.time);
    }

    @Override
    public boolean equals(Object other) {
        return other.equals(this);
    }

    @Override
    public boolean gte(Value obj) {
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
        if(tmp.date.isEqual(this.date))
            return tmp.time.isBefore(this.time);
        return tmp.date.isBefore(this.date);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode()*this.time.hashCode();
    }

    @Override
    public boolean lte(Value obj) {
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
        if(tmp.date.isEqual(this.date))
            return tmp.time.isAfter(this.time);
        return tmp.date.isAfter(this.date);
    }

    @Override
    public Value mul(Value obj) {
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
            
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
        DTValue tmp;
        if(obj instanceof DTValue)
            tmp = (DTValue)obj;
        else
            tmp = new DTValue(obj.toString());
        int lastHour = this.time.getHour();
        this.time = tmp.time.minusHours(lastHour)
        .minusMinutes(this.time.getMinute())
        .minusSeconds(this.time.getSecond());
        this.date = this.date.minusYears(tmp.date.getYear())
        .minusMonths(tmp.date.getMonthValue())
        .minusDays(tmp.date.getDayOfMonth());
        if(this.time.getHour()>lastHour)
            this.date = this.date.minusDays(1);
        return this;
    }

    @Override
    public String toString() {
        return this.date.toString()+" "+this.time.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new DTValue(this.date, this.time);
    }

}
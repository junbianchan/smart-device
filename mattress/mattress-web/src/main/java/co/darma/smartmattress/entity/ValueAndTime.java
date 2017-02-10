package co.darma.smartmattress.entity;

import co.darma.smartmattress.analysis.entity.BodyMotion;
import co.darma.smartmattress.analysis.entity.MeddoValue;

/**
 * Created by frank on 15/10/29.
 */
public class ValueAndTime {

    private Integer value;

    private Long time;


    public ValueAndTime(MeddoValue value) {

        if (value instanceof BodyMotion) {
            BodyMotion obj = (BodyMotion) value;
            this.value = obj.getLevel().getId();
            this.time = obj.getMarkTime();
        }
    }

    public ValueAndTime(Integer value, Long time) {
        this.value = value;
        this.time = time;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[value is :").append(this.value).append(",time:").append(this.time).append("]");
        return sb.toString();
    }
}

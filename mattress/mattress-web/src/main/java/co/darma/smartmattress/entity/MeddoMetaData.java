package co.darma.smartmattress.entity;

import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by frank on 15/11/26.
 */
public class MeddoMetaData {

    private Long time;

    private Integer heart_rate;

    private Integer breath;

    public MeddoMetaData(MeddoHealthData data) {
        if (data != null) {
            this.time = data.getMarkTime();
            this.heart_rate = data.getHeartRate();
            this.breath = data.getBreathValue();
        }
    }

    public MeddoMetaData(){}

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getBreath() {
        return breath;
    }

    public void setBreath(int breath) {
        this.breath = breath;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[time:").append(this.time)
                .append(",heart_rate:").append(this.heart_rate).append(",breath:").append(this.breath).append("]");
        return sb.toString();
    }
}

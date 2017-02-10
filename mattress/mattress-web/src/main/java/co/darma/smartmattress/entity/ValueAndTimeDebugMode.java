package co.darma.smartmattress.entity;

import co.darma.smartmattress.analysis.entity.BodyMotion;
import co.darma.smartmattress.analysis.entity.MeddoValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank on 15/12/17.
 */
public class ValueAndTimeDebugMode extends ValueAndTime {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private String timeConverted;

    public ValueAndTimeDebugMode(MeddoValue value) {
        super(value);
        if (value instanceof BodyMotion) {
            BodyMotion obj = (BodyMotion) value;
            timeConverted = sdf.format(new Date(obj.getMarkTime() * 1000L));
        }
    }

    @JsonProperty("time_converted")
    public String getTimeConverted() {
        return timeConverted;
    }

    public void setTimeConverted(String timeConverted) {
        this.timeConverted = timeConverted;
    }
}

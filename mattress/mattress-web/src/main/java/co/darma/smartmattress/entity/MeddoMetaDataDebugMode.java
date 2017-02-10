package co.darma.smartmattress.entity;

import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank on 15/12/17.
 */
public class MeddoMetaDataDebugMode extends MeddoMetaData {

    private Long lastUpdateTime;

    private String timeConverted;

    private String lutConverted;
    //2015-12-17 14:50:00
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public MeddoMetaDataDebugMode(MeddoHealthData data) {
        super(data);
        if (data != null) {
            lastUpdateTime = data.getLastUpdateTime();
            timeConverted = sdf.format(new Date(data.getMarkTime() * 1000L));
            lutConverted = sdf.format(new Date(lastUpdateTime));
        }
    }

    @JsonProperty("lut")
    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @JsonProperty("time_converted")
    public String getTimeConverted() {
        return timeConverted;
    }

    public void setTimeConverted(String timeConverted) {
        this.timeConverted = timeConverted;
    }

    @JsonProperty("lut_converted")
    public String getLutConverted() {
        return lutConverted;
    }

    public void setLutConverted(String lutConverted) {
        this.lutConverted = lutConverted;
    }
}

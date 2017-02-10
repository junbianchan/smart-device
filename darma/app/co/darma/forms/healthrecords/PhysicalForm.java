package co.darma.forms.healthrecords;

import co.darma.models.data.PhysicalRecord;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/8.
 */
public class PhysicalForm {

    private Long startTime;

    private Integer heartRate;

    private Integer heartRateCount;

    private Integer breath;

    private Integer breathCount;

    private Integer stress;

    private Integer stressCount;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getHeartRateCount() {
        return heartRateCount;
    }

    public void setHeartRateCount(Integer heartRateCount) {
        this.heartRateCount = heartRateCount;
    }

    public Integer getBreath() {
        return breath;
    }

    public void setBreath(Integer breath) {
        this.breath = breath;
    }

    public Integer getBreathCount() {
        return breathCount;
    }

    public void setBreathCount(Integer breathCount) {
        this.breathCount = breathCount;
    }

    public Integer getStress() {
        return stress;
    }

    public void setStress(Integer stress) {
        this.stress = stress;
    }

    public Integer getStressCount() {
        return stressCount;
    }

    public void setStressCount(Integer stressCount) {
        this.stressCount = stressCount;
    }
}

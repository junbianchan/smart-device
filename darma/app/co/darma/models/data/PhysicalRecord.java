package co.darma.models.data;

import co.darma.forms.healthrecords.PhysicalForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/12/4.
 */
public class PhysicalRecord {

    public static final String KEY = "physicalDatas";

    @JsonIgnore
    private Long memberId;

    private Long startTime;

    private Integer heartRate;

    private Integer heartRateWeight;

    private Integer respirationValue;

    private Integer respirationWeight;

    private Integer stress;

    private Integer stressWeight;

    @JsonIgnore
    private Long lastUpdateTime;


    public PhysicalRecord() {

    }

    public PhysicalRecord(Long memberId, PhysicalForm form) {

        this.memberId = memberId;
        this.startTime = form.getStartTime();
        this.heartRate = form.getHeartRate();
        this.heartRateWeight = form.getHeartRateCount();
        this.respirationValue = form.getBreath();
        this.respirationWeight = form.getHeartRateCount();
        this.stress = form.getStress();
        this.stressWeight = form.getStressCount();
    }


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("ts")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("hr")
    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    @JsonProperty("hrc")
    public Integer getHeartRateWeight() {
        return heartRateWeight;
    }

    public void setHeartRateWeight(int heartRateWeight) {
        this.heartRateWeight = heartRateWeight;
    }

    @JsonProperty("br")
    public Integer getRespirationValue() {
        return respirationValue;
    }

    public void setRespirationValue(Integer respirationValue) {
        this.respirationValue = respirationValue;
    }

    @JsonProperty("brc")
    public Integer getRespirationWeight() {
        return respirationWeight;
    }

    public void setRespirationWeight(Integer respirationWeight) {
        this.respirationWeight = respirationWeight;
    }

    @JsonProperty("st")
    public Integer getStress() {
        return stress;
    }

    public void setStress(Integer stress) {
        this.stress = stress;
    }

    @JsonProperty("stc")
    public Integer getStressWeight() {
        return stressWeight;
    }

    public void setStressWeight(Integer stressWeight) {
        this.stressWeight = stressWeight;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public static void refreshLastUpdateTime(List<PhysicalRecord> physicalRecords, Long currentTime) {
        for (PhysicalRecord record : physicalRecords) {
            record.setLastUpdateTime(currentTime);
        }
    }

    public static List<PhysicalRecord> packageToRecordList(Long memberId, List<PhysicalForm> formList) {
        if (CollectionUtils.isNotEmpty(formList)) {
            List<PhysicalRecord> records = new LinkedList<PhysicalRecord>();
            for (PhysicalForm form : formList) {
                records.add(new PhysicalRecord(memberId, form));
            }
            return records;
        }
        return null;
    }
}

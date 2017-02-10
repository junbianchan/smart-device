package co.darma.models.data;

import co.darma.forms.healthrecords.SedentaryForm;
import co.darma.forms.healthrecords.SittingForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 久坐表
 * Created by frank on 15/12/4.
 */
public class SedentaryRecord {

    public static final String KEY = "sedentaryDatas";

    /**
     * 用户ID
     */
    @JsonIgnore
    private Long memberId;


    /**
     * 这段久坐的开始时间
     */
    private Long startTime;

    /**
     * 这段久坐的结束时间
     */
    private Long endTime;

    /**
     * 在本地久坐过程中，会站起来几次，
     * 这几次的站起来时间都在一分钟以内，<br/>
     * 我们会吧这些琐碎时间加起来，存到这个值中。
     */
    private Integer unSitTime;

    /**
     * 最后更新时间
     */
    @JsonIgnore
    private Long lastUpdateTime;

    public SedentaryRecord() {
    }

    public SedentaryRecord(Long memberId, SedentaryForm form) {

        this.memberId = memberId;

        this.startTime = form.getStartTime();
        this.endTime = form.getEndTime();
        this.unSitTime = form.getUnSitTime();

    }

    @JsonProperty("st")
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("et")
    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @JsonProperty("ust")
    public Integer getUnSitTime() {
        return unSitTime;
    }

    public void setUnSitTime(Integer unSitTime) {
        this.unSitTime = unSitTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static void refreshLastUpdateTime(List<SedentaryRecord> sedentaryRecords, Long currentTime) {
        for (SedentaryRecord record : sedentaryRecords) {
            record.setLastUpdateTime(currentTime);
        }
    }

    public static List<SedentaryRecord> packageToRecordList(Long memberId, List<SedentaryForm> formList) {
        if (CollectionUtils.isNotEmpty(formList)) {
            List<SedentaryRecord> recordList = new LinkedList<SedentaryRecord>();
            for (SedentaryForm form : formList) {
                recordList.add(new SedentaryRecord(memberId, form));
            }
            return recordList;
        }
        return null;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();


        sb.append(",memberId:").append(memberId);
        sb.append(",startTime:").append(startTime);
        sb.append(",endTime:").append(endTime);
        sb.append(",unSitTime:").append(unSitTime);
        sb.append(",unSitTime:").append(unSitTime);

        return sb.toString();
    }
}



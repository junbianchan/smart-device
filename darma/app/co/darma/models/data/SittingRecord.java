package co.darma.models.data;

import co.darma.exceptions.InvalidParameterException;
import co.darma.forms.healthrecords.SittingForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 坐姿或其他数据
 * Created by frank on 15/12/8.
 */
public class SittingRecord {

    public static final String KEY = "sittingDatas";

    /**
     * 用户ID
     */
    @JsonIgnore
    private Long memberId;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 久坐姿势
     */
    private SittingChange postureType;

    @JsonIgnore
    private Long lastUpdateTime;

    public SittingRecord() {
    }

    public SittingRecord(Long memberId, SittingForm form) {
        this.memberId = memberId;
        this.startTime = form.getStartTime();
        this.endTime = form.getEndTime();
        this.setPostureType(form.getPostureType());
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    @JsonProperty("st")
    public long getStartTime() {
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

    @JsonProperty("pt")
    public int getPostureType() {
        if (postureType != null) {
            return postureType.val();
        }
        return 0;
    }

    public void setPostureType(Integer postureType) {
        try {
            if (postureType != null) {
                this.postureType = SittingChange.fromDB(postureType);
            }
        } catch (InvalidParameterException e) {
            return;
        }
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static void refreshLastUpdateTime(List<SittingRecord> sittingRecords, Long currentTime) {
        for (SittingRecord record : sittingRecords) {
            record.setLastUpdateTime(currentTime);
        }
    }

    /**
     * 转换成SittingRecord对象
     *
     * @param memberId
     * @param formList
     * @return
     */
    public static List<SittingRecord> packageToRecordList(Long memberId, List<SittingForm> formList) {
        if (CollectionUtils.isNotEmpty(formList)) {
            List<SittingRecord> recordList = new LinkedList<SittingRecord>();
            for (SittingForm form : formList) {
                recordList.add(new SittingRecord(memberId, form));
            }
            return recordList;
        }
        return null;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("memberId:").append(memberId);
        sb.append(",startTime:").append(startTime);
        sb.append(",endTime:").append(endTime);
        sb.append(",postureType:").append(postureType);
        sb.append(",lastUpdateTime:").append(lastUpdateTime);
        return sb.toString();
    }
}

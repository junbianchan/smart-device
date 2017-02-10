package co.darma.forms.healthrecords;

/**
 * Created by frank on 15/12/8.
 */
public class SittingForm {

    private Long startTime;

    private Long endTime;

    private Integer postureType;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getPostureType() {
        return postureType;
    }

    public void setPostureType(int postureType) {
        this.postureType = postureType;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("[startTime:").append(this.startTime)
                .append(",endTime:").append(this.endTime)
                .append(",postureType:").append(this.postureType);

        return sb.toString();
    }
}

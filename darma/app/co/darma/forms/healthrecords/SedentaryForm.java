package co.darma.forms.healthrecords;

/**
 * Created by frank on 15/12/8.
 */
public class SedentaryForm {


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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getUnSitTime() {
        return unSitTime;
    }

    public void setUnSitTime(Integer unSitTime) {
        this.unSitTime = unSitTime;
    }
}

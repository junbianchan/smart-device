package co.darma.models.data;

import co.darma.forms.healthrecords.BehaviorForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 用户行为记录
 * Created by frank on 15/12/4.
 */
public class BehaviorRecord {

    public static final String KEY = "behaviorDatas";

    @JsonIgnore
    private Long memberId;

    /**
     * 日期
     */
    private Long markTime;

    /**
     * 站起来次数
     */
    private Integer standTimes;

    /**
     * 完成拉伸次数
     */
    private Integer finishStretchTimes;

    /**
     * 上次拉伸的时间
     */
    private Long lastStretchTime;

    /**
     * 打开app次数
     */
    private Integer openAppTimes;

    /**
     * 看拉伸的次数
     */
    private Integer sawStretchTimes;

    @JsonIgnore
    private Long lastUpdateTime;

    @JsonIgnore
    private Long connectTime = 0L;

    @JsonIgnore
    private Long remindSitStand = 0L;

    @JsonIgnore
    private Long remindSitNotStand = 0L;

    @JsonIgnore
    private Long sendStretchCount = 0L;

    @JsonIgnore
    private Long tapMain = 0L;

    @JsonIgnore
    private Long tapExercise = 0L;

    @JsonIgnore
    private Long tapDailyReport = 0L;

    @JsonIgnore
    private Long tapVitalSign = 0L;

    @JsonIgnore
    private Long tapSl = 0L;

    @JsonIgnore
    private Long tapHr = 0L;

    @JsonIgnore
    private Long tapBr = 0L;

    @JsonIgnore
    private Long tapSetting = 0L;

    @JsonIgnore
    private Long tapGoal = 0L;


    public BehaviorRecord() {
    }

    public BehaviorRecord(Long memberId, BehaviorForm form) {

        this.memberId = memberId;

        this.markTime = form.getStartTime();
        this.standTimes = form.getStandTimes();
        this.finishStretchTimes = form.getFinishStretchTimes();
        this.lastStretchTime = form.getLastStretchTime();
        this.openAppTimes = form.getOpenAppTimes();
        this.sawStretchTimes = form.getSawStretchTimes();

        this.connectTime = form.getConnectTime() == null ? 0 : form.getConnectTime();
        this.remindSitStand = form.getRemindSitStand() == null ? 0 : form.getRemindSitStand();
        this.remindSitNotStand = form.getRemindSitNotStand() == null ? 0 : form.getRemindSitNotStand();
        this.sendStretchCount = form.getSendStretchCount() == null ? 0 : form.getSendStretchCount();
        this.tapMain = form.getTapMain() == null ? 0 : form.getTapMain();
        this.tapExercise = form.getTapExercise() == null ? 0 : form.getTapExercise();
        this.tapDailyReport = form.getTapDailyReport() == null ? 0 : form.getTapDailyReport();
        this.tapVitalSign = form.getTapVitalSign() == null ? 0 : form.getTapVitalSign();
        this.tapSl = form.getTapSl() == null ? 0 : form.getTapSl();
        this.tapHr = form.getTapHr() == null ? 0 : form.getTapHr();
        this.tapBr = form.getTapHr() == null ? 0 : form.getTapHr();
        this.tapSetting = form.getTapSetting() == null ? 0 : form.getTapSetting();
        this.tapGoal = form.getTapGoal() == null ? 0 : form.getTapGoal();


    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @JsonProperty("st")
    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    @JsonProperty("sts")
    public Integer getStandTimes() {
        return standTimes;
    }

    public void setStandTimes(Integer standTimes) {
        this.standTimes = standTimes;
    }

    @JsonProperty("fsts")
    public Integer getFinishStretchTimes() {
        return finishStretchTimes;
    }

    public void setFinishStretchTimes(Integer finishStretchTimes) {
        this.finishStretchTimes = finishStretchTimes;
    }

    @JsonProperty("lst")
    public Long getLastStretchTime() {
        return lastStretchTime;
    }

    public void setLastStretchTime(Long lastStretchTime) {
        this.lastStretchTime = lastStretchTime;
    }

    @JsonProperty("oppts")
    public Integer getOpenAppTimes() {
        return openAppTimes;
    }

    public void setOpenAppTimes(Integer openAppTimes) {
        this.openAppTimes = openAppTimes;
    }

    @JsonProperty("stts")
    public Integer getSawStretchTimes() {
        return sawStretchTimes;
    }

    public void setSawStretchTimes(Integer sawStretchTimes) {
        this.sawStretchTimes = sawStretchTimes;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static void refreshLastUpdateTime(List<BehaviorRecord> behaviorRecords, Long currentTime) {
        for (BehaviorRecord record : behaviorRecords) {
            record.setLastUpdateTime(currentTime);
        }
    }

    public Long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Long connectTime) {
        if (connectTime != null)
            this.connectTime = connectTime;
    }

    public Long getRemindSitStand() {

        return remindSitStand;
    }

    public void setRemindSitStand(Long remindSitStand) {
        if (remindSitStand != null)
            this.remindSitStand = remindSitStand;
    }

    public Long getRemindSitNotStand() {

        return remindSitNotStand;
    }

    public void setRemindSitNotStand(Long remindSitNotStand) {
        this.remindSitNotStand = remindSitNotStand;
    }

    public Long getSendStretchCount() {
        return sendStretchCount;
    }

    public void setSendStretchCount(Long sendStretchCount) {
        this.sendStretchCount = sendStretchCount;
    }

    public Long getTapMain() {
        return tapMain;
    }

    public void setTapMain(Long tapMain) {
        this.tapMain = tapMain;
    }

    public Long getTapExercise() {
        return tapExercise;
    }

    public void setTapExercise(Long tapExercise) {
        this.tapExercise = tapExercise;
    }

    public Long getTapDailyReport() {
        return tapDailyReport;
    }

    public void setTapDailyReport(Long tapDailyReport) {
        this.tapDailyReport = tapDailyReport;
    }

    public Long getTapVitalSign() {
        return tapVitalSign;
    }

    public void setTapVitalSign(Long tapVitalSign) {
        this.tapVitalSign = tapVitalSign;
    }

    public Long getTapSl() {
        return tapSl;
    }

    public void setTapSl(Long tapSl) {
        this.tapSl = tapSl;
    }

    public Long getTapHr() {
        return tapHr;
    }

    public void setTapHr(Long tapHr) {
        this.tapHr = tapHr;
    }

    public Long getTapBr() {
        return tapBr;
    }

    public void setTapBr(Long tapBr) {
        this.tapBr = tapBr;
    }

    public Long getTapSetting() {
        return tapSetting;
    }

    public void setTapSetting(Long tapSetting) {
        this.tapSetting = tapSetting;
    }

    public Long getTapGoal() {
        return tapGoal;
    }

    public void setTapGoal(Long tapGoal) {
        this.tapGoal = tapGoal;
    }

    public static List<BehaviorRecord> packageToRecordList(Long memberId, List<BehaviorForm> formList) {
        if (CollectionUtils.isNotEmpty(formList)) {
            List<BehaviorRecord> recordList = new LinkedList<BehaviorRecord>();
            for (BehaviorForm form : formList) {
                recordList.add(new BehaviorRecord(memberId, form));
            }
            return recordList;
        }
        return null;
    }

    @Override
    public String toString() {
        return "BehaviorRecord{" +
                "memberId=" + memberId +
                ", markTime=" + markTime +
                ", standTimes=" + standTimes +
                ", finishStretchTimes=" + finishStretchTimes +
                ", lastStretchTime=" + lastStretchTime +
                ", openAppTimes=" + openAppTimes +
                ", sawStretchTimes=" + sawStretchTimes +
                ", lastUpdateTime=" + lastUpdateTime +
                ", connectTime=" + connectTime +
                ", remindSitStand=" + remindSitStand +
                ", remindSitNotStand=" + remindSitNotStand +
                ", sendStretchCount=" + sendStretchCount +
                ", tapMain=" + tapMain +
                ", tapExercise=" + tapExercise +
                ", tapDailyReport=" + tapDailyReport +
                ", tapVitalSign=" + tapVitalSign +
                ", tapSl=" + tapSl +
                ", tapHr=" + tapHr +
                ", tapBr=" + tapBr +
                ", tapSetting=" + tapSetting +
                ", tapGoal=" + tapGoal +
                '}';
    }
}

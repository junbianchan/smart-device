package co.darma.forms.healthrecords;

/**
 * Created by frank on 15/12/8.
 */
public class BehaviorForm {


    private long startTime;

    /**
     * 站起来次数
     */
    private int standTimes;

    /**
     * 完成拉伸次数
     */
    private int finishStretchTimes;

    /**
     * 上次拉伸的时间
     */
    private long lastStretchTime;

    /**
     * 打开app次数
     */
    private int openAppTimes;

    /**
     * 看拉伸的次数
     */
    private int sawStretchTimes;

    private Long connectTime;

    private Long remindSitStand;

    private Long remindSitNotStand;

    private Long sendStretchCount;

    private Long tapMain;

    private Long tapExercise;

    private Long tapDailyReport;

    private Long tapVitalSign;

    private Long tapSl;

    private Long tapHr;

    private Long tapBr;

    private Long tapSetting;

    private Long tapGoal;

    public BehaviorForm() {
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getStandTimes() {
        return standTimes;
    }

    public void setStandTimes(int standTimes) {
        this.standTimes = standTimes;
    }

    public Integer getFinishStretchTimes() {
        return finishStretchTimes;
    }

    public void setFinishStretchTimes(int finishStretchTimes) {
        this.finishStretchTimes = finishStretchTimes;
    }

    public long getLastStretchTime() {
        return lastStretchTime;
    }

    public void setLastStretchTime(long lastStretchTime) {
        this.lastStretchTime = lastStretchTime;
    }

    public int getOpenAppTimes() {
        return openAppTimes;
    }

    public void setOpenAppTimes(int openAppTimes) {
        this.openAppTimes = openAppTimes;
    }

    public int getSawStretchTimes() {
        return sawStretchTimes;
    }

    public void setSawStretchTimes(int sawStretchTimes) {
        this.sawStretchTimes = sawStretchTimes;
    }


    public Long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Long connectTime) {
        this.connectTime = connectTime;
    }

    public Long getRemindSitStand() {
        return remindSitStand;
    }

    public void setRemindSitStand(Long remindSitStand) {
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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("[startTime:").append(this.startTime)
                .append("standTime:").append(this.standTimes)
                .append("finishStretchTimes:").append(this.finishStretchTimes)
                .append("lastStretchTime:").append(this.lastStretchTime)
                .append("openAppTimes:").append(this.openAppTimes)
                .append("sawStretchTimes:").append(this.sawStretchTimes);
        sb.append("]");

        return sb.toString();
    }
}

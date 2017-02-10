package co.darma.smartmattress.analysis.entity;

/**
 * Created by frank on 15/10/12.
 */
public class BodyMotion extends MeddoValue {

    /**
     * 设备ID
     */
    private co.darma.smartmattress.entity.Device device;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 标记时间
     */
    private Long markTime;

    /**
     * 运动级别
     */
    private BodyMotionType level;

    private String algorithmVersion;

    public BodyMotion() {
        super();
    }

    public co.darma.smartmattress.entity.Device getDevice() {
        return device;
    }

    public void setDevice(co.darma.smartmattress.entity.Device device) {
        this.device = device;
        setGroupKey(device.getDeviceNo());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Long markTime) {
        this.markTime = markTime;
    }

    public BodyMotionType getLevelId() {
        return level;
    }

    public void setLevel(BodyMotionType level) {
        this.level = level;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public BodyMotionType getLevel() {
        return level;
    }

    public String getAlgorithmVersion() {
        return algorithmVersion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[userId:").append(this.userId)
                .append(",deviceNo:").append(this.device.getDeviceNo())
                .append(",level:").append(this.getLevel())
                .append(",markTime:").append(this.getMarkTime()).append("]");
        return sb.toString();
    }
}

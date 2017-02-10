package co.darma.smartmattress.analysis.entity;

/**
 * Created by frank on 15/10/12.
 */
public class BodyMotionType {

    /**
     * id
     */
    private Integer id;

    /**
     * 界别
     */
    private String level;

    /**
     * 是否在使用
     */
    private Integer active;

    private static BodyMotionType defaultType = new BodyMotionType(3, "Moving");

    public BodyMotionType() {
    }

    BodyMotionType(int id, String level) {
        this.id = id;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public static BodyMotionType getBodyMotionTypeByValue(int value) {
        return defaultType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("levelId:").append(this.id).append(",level:").append(this.getLevel());
        return sb.toString();
    }
}

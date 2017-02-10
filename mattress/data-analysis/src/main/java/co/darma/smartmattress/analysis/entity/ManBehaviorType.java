package co.darma.smartmattress.analysis.entity;

/**
 * Created by frank on 15/10/12.
 */
public class ManBehaviorType {

    /**
     * id
     */
    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否还使用
     */
    private Integer active;

    public ManBehaviorType() {
    }

    private ManBehaviorType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    private static final ManBehaviorType offline = new ManBehaviorType(2, "Offline");

    private static final ManBehaviorType leave = new ManBehaviorType(3, "Away");

    private static final ManBehaviorType up = new ManBehaviorType(4, "Up");

    private static final ManBehaviorType opticalFault = new ManBehaviorType(5, "OpticalFault");

    private static final ManBehaviorType wifiOffline = new ManBehaviorType(6, "wifiOffline");


    public static ManBehaviorType getTypeById(int type) {
        switch (type) {
            case 2:
                return offline;
            case 3:
                return leave;
            case 4:
                return up;
            case 7:
                return opticalFault;
            case 8:
                return wifiOffline;
            default:
                return null;
        }

    }

    @Override
    public String toString() {
        return "ManBehaviorType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", active=" + active +
                '}';
    }
}

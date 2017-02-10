package co.darma.smartmattress.entity;

/**
 * Created by frank on 15/10/26.
 */
public class Device {

    /*
   *  id
   * */
    private Integer id;

    /**
     * 设备的序列号
     */
    private String deviceNo;

    /**BeanWrapper
     * 设备的名字
     */
    private String deviceName;

    /**
     * 设备的mac地址
     */
    private String deviceMacAddress;

    private Project project;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceNo='" + deviceNo + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceMacAddress='" + deviceMacAddress + '\'' +
                ", project=" + project +
                '}';
    }
}

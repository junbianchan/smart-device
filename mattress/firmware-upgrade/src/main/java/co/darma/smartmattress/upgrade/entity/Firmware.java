package co.darma.smartmattress.upgrade.entity;

/**
 * Created by frank on 15/12/17.
 */
public class Firmware implements Comparable<Firmware> {

    private Long firmwareId;

    private String firmwareName;

    private Double versionNo;

    /**
     * 固件路径
     */
    private String firmwarePath;

    private Long uploadTime;

    private String md5Code;

    private String checkSum;

    public Long getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Long firmwareId) {
        this.firmwareId = firmwareId;
    }

    public String getFirmwareName() {
        return firmwareName;
    }

    public void setFirmwareName(String firmwareName) {

        this.firmwareName = firmwareName;
    }

    public Double getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Double versionNo) {
        this.versionNo = versionNo;
    }

    public String getFirmwarePath() {
        return firmwarePath;
    }

    public void setFirmwarePath(String firmwarePath) {
        this.firmwarePath = firmwarePath;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    @Override
    public int compareTo(Firmware o) {

        if (o == null) {
            return 1;
        }

        if (this.getVersionNo() > o.getVersionNo()) {
            return 1;
        } else if (this.getVersionNo() == o.getVersionNo()) {
            return 0;
        } else {
            return -1;
        }

    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    @Override
    public String toString() {
        return "Firmware{" +
                "firmwareId=" + firmwareId +
                ", firmwareName='" + firmwareName + '\'' +
                ", versionNo=" + versionNo +
                ", firmwarePath='" + firmwarePath + '\'' +
                ", uploadTime=" + uploadTime +
                ", md5Code='" + md5Code + '\'' +
                ", checkSum='" + checkSum + '\'' +
                '}';
    }
}

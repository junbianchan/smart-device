package co.darma.smartmattress.upgrade.entity;

/**
 * 升级历史记录
 */
public class UpgradeRequestRecord extends UpgradeRequest {

    private Long actualUpdateTime;

    private Boolean updateResult;

    private String updateMessage;

    public UpgradeRequestRecord() {
    }

    public UpgradeRequestRecord(UpgradeRequest request) {

    }

    public Long getActualUpdateTime() {
        return actualUpdateTime;
    }

    public void setActualUpdateTime(Long actualUpdateTime) {
        this.actualUpdateTime = actualUpdateTime;
    }

    public Boolean getUpdateResult() {
        return updateResult;
    }

    public void setUpdateResult(Boolean updateResult) {
        this.updateResult = updateResult;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }
}

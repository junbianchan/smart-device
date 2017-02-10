package co.darma.models.view;

/**
 * Created by frank on 15/12/10.
 */
public class HealthDataResponseModel extends ResponseModel {

    private Long lastUpdateTime;

    public HealthDataResponseModel() {
        super();
    }

    public HealthDataResponseModel(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public HealthDataResponseModel(int errorCode, String errorMsg, Long lastUpdateTime) {
        super(errorCode, errorMsg);
        this.lastUpdateTime = lastUpdateTime;
    }

    public HealthDataResponseModel(Long lastUpdateTime) {
        super();
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}

package co.darma.models.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by frank on 15/12/8.
 */
public class UpdatesResult {

    private Long lastUpdateTime;


    private Map healthDatas;


    @JsonProperty("lastUpdateTime")
    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @JsonProperty("healthDatas")
    public Map getHealthDatas() {
        return healthDatas;
    }

    public void setHealthDatas(Map healthDatas) {
        this.healthDatas = healthDatas;
    }
}

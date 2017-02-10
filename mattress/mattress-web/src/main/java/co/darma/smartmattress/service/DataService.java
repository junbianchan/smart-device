package co.darma.smartmattress.service;

import co.darma.smartmattress.ccb.AccessTokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.QueryParam;

/**
 * Created by frank on 15/11/17.
 */
public abstract class DataService {


    public static final String HEART_RATE = "heart_rate";

    public static final String BREATH_VALUE = "breath_value";

    public static final String MOTION = "motion";

    private static final Integer COUNT_LIMIT = 10000;
    /**
     * 用于格式化数据
     */
    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected AccessTokenManager manager;

    protected boolean validateIndexAndCount(@QueryParam("heart_rate_start_index") Long hearRateStartIndex, @QueryParam("heart_rate_count") Integer hearRateCount) {
        //参数校验
        if (hearRateStartIndex == null || hearRateCount == null || hearRateStartIndex < 0 || hearRateCount < 0) {
            return true;
        }
        return false;
    }


}

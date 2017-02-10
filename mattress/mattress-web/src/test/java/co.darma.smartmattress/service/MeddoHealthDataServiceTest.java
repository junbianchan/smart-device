package co.darma.smartmattress.service;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.util.HealthDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * Created by frank on 15/11/25.
 */
public class MeddoHealthDataServiceTest extends BaseTestCase {


    public void testHealthDataGet() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Long startTime = 0L;
        Long endTime = 1000L;
        MeddoHealthDataDao meddoHealthDataDao = (MeddoHealthDataDao) getContext().getBean("meddoHealthDataDao");
        Map<String, Object> helathDataMap = HealthDataUtil.buildHealthDataNew(
                meddoHealthDataDao.queryMeddoHealthDataWithTime(1,startTime, endTime), "", "");
        System.out.println(mapper.writeValueAsString(helathDataMap));

    }

    public void testHealthDatas() {

        BatchQueryHealthDataWithTimeServie service =
                (BatchQueryHealthDataWithTimeServie) getContext().getBean(BatchQueryHealthDataWithTimeServie.class);

        System.out.println(service.bathQueryHealthData("b6871280e57e42f9a7552a39b0da3978", null, 1450254360L, 1450328340L, "false",null));
    }

    public void testBodyMotion() {
        BodyMotionDao dao = getContext().getBean(BodyMotionDao.class);

        System.out.println(dao.queryBodyMotionWithDeviceIdAndTime(20, 1350261065L, 1450261567L));

    }

}

package co.darma.smartmattress.analysis;

import co.darma.smartmattress.BaseTestCase;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.analysis.entity.MeddoHealthData;

/**
 * Created by frank on 15/12/5.
 */
public class MeddoHealthDataDaoTest extends BaseTestCase {

    public void testSaveData() {

        MeddoHealthDataDao meddoHealthDataDao = getContext().getBean(MeddoHealthDataDao.class);


        MeddoHealthData data = new MeddoHealthData();

        Device d = new Device();

        d.setId(4);

        data.setAlgorithmVersion("1.0");
        data.setBreathValue(100);
        data.setBreathValueWeight(100);
        data.setDevice(d);
        data.setHeartRate(-1);
        data.setHeartRateWeight(0);
        data.setMarkTime(1L);
        meddoHealthDataDao.saveMeddoHealthData(data);

    }

    public void testQueryData() {

        MeddoHealthDataDao meddoHealthDataDao = getContext().getBean(MeddoHealthDataDao.class);

        Long startTime = 1448530300L;
        Long endTime = 1448530389L;
        System.out.println(meddoHealthDataDao.queryMeddoHealthDataWithTime(1, startTime, endTime));

    }

    public void testQueryHealth() {

        MeddoHealthDataDao meddoHealthDataDao = getContext().getBean(MeddoHealthDataDao.class);

        Long startTime = 1448530380L;
        Long endTime = 1448530800L;
        System.out.println(meddoHealthDataDao.queryHealthDataByDevice(4, startTime, endTime));
    }
}

package co.darma.smartmattress.analysis.service;

import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.entity.Device;

import java.util.List;

/**
 * Created by frank on 16/1/4.
 */
public interface AnalysisSleepService {

    /**
     * 这个函数会出发系统去计算缓存中的睡眠数据
     * 我们会缓存一个小时左右的数据，然后慧拿这部分数据去计算睡眠数据。
     *
     * @param device  设备
     * @param endTime 结束时间
     * @return 改段时间的数据
     */
    public List<SleepState> analysisSleepData(Device device, Long endTime);

    public List<SleepState> analysisSleepStates(Device device, Long startTime, Long endTime);
}

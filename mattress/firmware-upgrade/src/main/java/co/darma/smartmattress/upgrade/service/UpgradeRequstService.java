package co.darma.smartmattress.upgrade.service;

import co.darma.smartmattress.upgrade.entity.UpgradeRequest;

/**
 * Created by frank on 15/12/29.
 */
public interface UpgradeRequstService {

    /**
     * 用队列中获取升级任务,按照计划更新时间的顺序进行获取,同时将改记录从数据库队列中删除
     *
     * @param projectId
     * @return
     */
    public UpgradeRequest getUpgradeRequest(Integer projectId);

    /**
     * 新增请求
     *
     * @param request
     */
    public void addNewUpgradeRequest(UpgradeRequest request);

    /**
     * 把升级记录放到队列中
     *
     * @param request
     * @param updateResult
     * @param updateMessage
     */
    public void saveUpgradeRequestRecord(UpgradeRequest request, boolean updateResult, String updateMessage);

    /**
     * 删除记录
     *
     * @param request
     */
    public void deleteUpgradeRequst(UpgradeRequest request);


    /**
     * 判断改设备是否已经在队列中的升级任务
     *
     * @param deviceId
     * @return
     */
    public boolean deviceHashUpgradeRequestOrNot(Integer deviceId, Long firmwareId);

}

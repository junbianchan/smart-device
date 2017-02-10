package co.darma.smartmattress.upgrade.dao;

import co.darma.smartmattress.upgrade.entity.UpgradeRequest;
import co.darma.smartmattress.upgrade.entity.UpgradeRequestRecord;

/**
 * Created by frank on 15/12/29.
 */
public interface UpgradeRequestDao {

    /**
     * 获取队列中计划更新时间最早的一个
     *
     * @param projectId
     * @return
     */
    UpgradeRequest getUpgradeRequest(Integer projectId);

    /**
     * 新增升级请求
     *
     * @param request
     */
    void addNewUpgradeRequest(UpgradeRequest request);

    /**
     * 保存升级记录
     *
     * @param requestRecord
     */
    void saveUpgradeRequestRecord(UpgradeRequestRecord requestRecord);

    /**
     * 生气
     * @param id
     */
    void deleteUpgradeRequstById(Long id);

    boolean deviceHashUpgradeRequestOrNot(Integer deviceId, Long firmwareId);

}

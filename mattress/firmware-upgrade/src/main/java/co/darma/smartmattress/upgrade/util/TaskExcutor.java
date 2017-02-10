package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.util.BeanManagementUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by frank on 15/12/30.
 */
public class TaskExcutor {

    /**
     * 每天执行的时间
     */
    private static final long TASK_EXECUTOR_TIME = 9 * 60 * 60 * 1000L;

    /**
     * 执行的周期
     */
    public static final long ONE_DAY_SECOND = 24 * 60 * 60 * 1000L;


    /**
     * 中国时间偏差
     */
    public static final long CHINA_ZONE_MIL_SECOND_OFFSET = 8 * 60 * 60 * 1000L;

    /**
     * 定时器
     */
    private static Timer timer = new Timer(true);


    private static Logger logger = Logger.getLogger(TaskExcutor.class);

    /**
     * 执行一个定时任务
     */
    public static void startFixTimeTask() {

        Long currentTime = System.currentTimeMillis();

        logger.info("currentTime :" + currentTime);

        //距离1970年的天数
        int days = (int) (currentTime / (1000 * 60 * 60 * 24L));

        //距离今天0点的天数
        Long milSecondsToday = (currentTime - days * 24 * 60 * 60 * 1000L) + CHINA_ZONE_MIL_SECOND_OFFSET;

        long delay;
        //如果定时任务执行时间还没到来那么设定一个定时任务
        if (milSecondsToday <= TASK_EXECUTOR_TIME) {
            delay = (TASK_EXECUTOR_TIME - milSecondsToday);
        } else {
            delay = (TASK_EXECUTOR_TIME + ONE_DAY_SECOND - milSecondsToday);
        }

        logger.info("delay :" + delay);
        UpgradeCheckTask fixJob = new UpgradeCheckTask();
        fixJob.setFixJob(true);
        timer.schedule(fixJob, delay, ONE_DAY_SECOND);
        logger.info("Create a new Job.");
    }

    private static List<UpgradeCheckTask> checkerList = new LinkedList<UpgradeCheckTask>();

    /**
     * 新增一个任务
     *
     * @return
     */
    public static boolean addTask() {
        if (checkerList.size() < 10) {
            UpgradeCheckTask checker = BeanManagementUtil.getBeanByType(UpgradeCheckTask.class);
            checkerList.add(checker);
            new Thread(checker).start();
            return true;
        } else {
            return false;
        }
    }


    public static int getTaskSize() {
        return checkerList.size();
    }

    /**
     * 清除未运行
     */
    public static void cleanUnRunningChecker() {
        if (CollectionUtils.isNotEmpty(checkerList)) {
            Iterator<UpgradeCheckTask> iterators = checkerList.iterator();
            while (iterators.hasNext()) {
                UpgradeCheckTask checker = iterators.next();
                if (!checker.isRunning()) {
                    iterators.remove();
                }
            }
        }
    }


    public static List<UpgradeCheckTask> getCheckerList() {
        return checkerList;
    }

}

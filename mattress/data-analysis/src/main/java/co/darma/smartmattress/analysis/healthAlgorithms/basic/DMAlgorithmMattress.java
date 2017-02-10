package co.darma.smartmattress.analysis.healthAlgorithms.basic;

/**
 * Created by frank on 15/10/20.
 */
public class DMAlgorithmMattress {

    /**
     * 普通体动算法，相对比较灵敏，用于计算睡眠使用
     *
     * @param opticData
     * @return
     */
    public native int[] offlineStatusCalulation(final int[] opticData);

    public native int[] offlineBRCalculation(final int[] opticData);

    public native int[] offlineHRCalculation(final int[] opticData);

    public native int[] sleepStageAnalysis(final int[] motionRecord, final int[] heartRate, final int[] breath);

    /**
     * 迈动的算法，对体动不灵敏
     *
     * @param opticData
     * @return
     */
    public native int[] offlineStatusCalForMeddo(final int[] opticData);

    public static String getVersion() {
        return "1.0";
    }

}
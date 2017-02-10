package co.darma.smartmattress.tmp;

import co.darma.smartmattress.QueueUtil;

/**
 * Created by frank on 15/12/7.
 */
public class Main {


    private static int[] calHeartRate(int[] opticData) {

        System.out.println("opticData ");
        for (int i = 0; i < opticData.length; ++i) {
            System.out.print(opticData[i]);
            System.out.print(",");
        }
        System.out.println("end.");

        if (opticData.length > QueueUtil.HISTORY_PACKET_AT_LEAST_NUMBER * 2) {

            int firstHalfLength = (opticData.length) / 2;
            int[] firstHalfArray = new int[firstHalfLength];
            int[] secondHalfArray = new int[opticData.length - firstHalfLength];
            System.arraycopy(opticData, 0, firstHalfArray, 0, firstHalfLength);
            System.arraycopy(opticData, firstHalfLength, secondHalfArray, 0, secondHalfArray.length);


            System.out.println("secondHalfArray ");
            for (int i = 0; i < secondHalfArray.length; ++i) {
                System.out.print(secondHalfArray[i]);
                System.out.print(",");
            }
            System.out.println("secondHalfArray end.");



            int[] firsthHalfResult = offlineHRCalculation(firstHalfArray);
            int[] secondHalfResult = offlineHRCalculation(secondHalfArray);

            System.out.println("first ");
            for (int i = 0; i < firsthHalfResult.length; ++i) {
                System.out.print(firsthHalfResult[i]);
                System.out.print(",");
            }
            System.out.println("end.");




            if (firsthHalfResult[0] > 0 || secondHalfResult[0] > 0) {
                //至少有一段数据是好的才要
                int[] finalResutl = new int[firsthHalfResult.length + secondHalfResult.length];

                System.arraycopy(firsthHalfResult, 0, finalResutl, 0, firsthHalfResult.length);
                System.arraycopy(secondHalfResult, 0, finalResutl, firsthHalfResult.length, secondHalfResult.length);

                return finalResutl;
            }
        }
        return offlineHRCalculation(opticData);
    }

    private static int[] offlineHRCalculation(int[] firstHalfArray) {


        int[] result = new int[firstHalfArray.length];
        for (int i = 0; i < firstHalfArray.length; ++i) {
            result[i] = firstHalfArray[0];
        }
        return result;
    }

    public static void main(String[] args) {

        int[] opticData = new int[60];
        for (int i = 0; i < opticData.length; ++i) {
            opticData[i] = i;
        }
        int[] result = calHeartRate(opticData);
        System.out.println();
        for (int i = 0; i < result.length; ++i) {
            System.out.print(result[i]);
            System.out.print(",");
        }
        System.out.println();


    }


}

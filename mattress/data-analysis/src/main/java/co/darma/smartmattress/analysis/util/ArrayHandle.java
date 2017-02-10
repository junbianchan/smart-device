package co.darma.smartmattress.analysis.util;

/**
 * Created by frank on 16/1/5.
 */
public class ArrayHandle {


    public static String intToString(int startIndex, int[] intarray) {
        if (intarray != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = startIndex; i < intarray.length; ++i) {
                sb.append(intarray[i]).append(",");
            }
            return sb.toString();
        }
        return null;
    }

}

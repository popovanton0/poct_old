package sample.popov.PopovUtilites;

/**
 * Created by popov on 21.03.2016.
 */
public class PopovUtilites  {
    public static String booleanToString(boolean Boolean) {
        String result = null;
        if (Boolean) result = "true";
        else result = "false";
        return result;
    }
    public static String booleanToStringT(boolean Boolean) {
        String result = null;
        if (Boolean) result = "True";
        else result = "False";
        return result;
    }
    public static int doubleToInt(double number){
            int pow = 1;
            double tmp = number * pow;
            return (int) (((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow);
    }

}

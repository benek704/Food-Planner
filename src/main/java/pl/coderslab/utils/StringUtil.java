package pl.coderslab.utils;

public class StringUtil {
    public static boolean isAnyNullOrBlank(String ...strings){
        for(String str : strings){
            if(str == null || str.isBlank()){
                return true;
            }
        }
        return false;
    }
}

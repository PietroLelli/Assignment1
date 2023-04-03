package utils;

public class Logger {
    public static void logPrintString(String s){
        System.out.format("[Print] %s\n",s);
    }

    public static void logPrintStrings(String[] array){
        for (String s: array){
            System.out.format("[Print] %s\n",s);
        }
    }
}

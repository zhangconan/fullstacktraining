package com.zkn.fullstacktraining.second.decorate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by zkn on 2016/12/13.
 */
public class StringDecorateTest {

    private static Map<String,Class<? extends OutputString>> stringMap = new HashMap<String,Class<? extends OutputString>>() {
        {
            put("1",DecorateOutputStringEncrypt.class);
            put("2",DecorateOutputStringReverse.class);
            put("3",DecorateOutputStringSubString.class);
            put("4",DecorateOutputStringToLowerCase.class);
            put("5",DecorateOutputStringToUpperCase.class);
        }
    };

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        OutputString outputString = new ParticularOutputString();
        List<Class<? extends OutputString>> list = new ArrayList<Class<? extends OutputString>>(3);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一段内容：");
        String strNew = scanner.nextLine();
        System.out.println("请输入要进行的操作的数字.");
        System.out.println("1：字符串加密");
        System.out.println("2：字符串反转");
        System.out.println("3：字符串截取");
        System.out.println("4：字符串小写");
        System.out.println("5：字符串大写");
        int i = 0;
        while(scanner.hasNext()){
            String str = scanner.nextLine();
            if("exit".equals(str))
                break;
            switch (str){
                case "1" :
                    i++;
                    list.add(stringMap.get(str));
                    break;
                case "2":
                    i++;
                    list.add(stringMap.get(str));
                    break;
                case "3":
                    i++;
                    list.add(stringMap.get(str));
                    break;
                case "4":
                    i++;
                    list.add(stringMap.get(str));
                    break;
                case "5":
                    i++;
                    list.add(stringMap.get(str));
                    break;
                default:
                    break;
            }
            if(i >= 2)
                break;
        }
        outputString = getOutputStringInstance(list,outputString);
        outputString.processString(strNew);
    }

    private static OutputString getOutputStringInstance(OutputString outputString, String str) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<? extends OutputString> clazz01 = stringMap.get(str);
        try {
            Constructor constructor = clazz01.getConstructor(OutputString.class);
            outputString = (OutputString) constructor.newInstance(outputString);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return outputString;
    }

    private static OutputString getOutputStringInstance(List<Class<? extends OutputString>> list,OutputString outputString){
        if(list == null || list.isEmpty())
            return outputString;
        for(int i= list.size()-1;i>=0;i--){
            try {
                Constructor constructor = list.get(i).getConstructor(OutputString.class);
                outputString = (OutputString) constructor.newInstance(outputString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outputString;
    }
}

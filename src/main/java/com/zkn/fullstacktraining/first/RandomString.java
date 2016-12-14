package com.zkn.fullstacktraining.first;

import java.util.Random;

/**
 * Created by zkn on 2016/12/12.
 */
public class RandomString {

    private static Random random = new Random();

    public static String getRamdomString(int length) {
        //String str = "ABCDEFGHIJKLMNOPQRESTUVWXYZabcdefghijklmnopqrestuvwxyz0123456789";
//        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
//                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
//                'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
//                'x', 'y', 'z', 'o', '1'};
        char[] ch = {'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
                'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z'};
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = ch[random.nextInt(ch.length)];
        }
        return new String(chars);
    }

    public static String getRandomString01(int length) {

        //String str = "ABCDEFGHIJKLMNOPQRESTUVWXYZabcdefghijklmnopqrestuvwxyz0123456789";
        String str = "ABCDEFGHIJKLMNOPQRESTUVWXYZabcdefghijklmnopqrestuvwxyz";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(random.nextInt(str.length())));
        }
        return sb.toString();
    }
}

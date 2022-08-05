package com.hillel.anatoliibondarenko.homework2;

import java.util.Locale;

public class HomeWork2 {

    public static int wordsCount(String string) {
        if (string == null || string.isBlank()) return 0;

        String[] arrayOfWords = string.split("\\s+");
        if (arrayOfWords.length != 0) {
            return (arrayOfWords[0].isEmpty() ? arrayOfWords.length - 1 : arrayOfWords.length);
        } else
            return 0;
    }

    public static boolean isPalindrome(String string) {
        if (string == null || string.isBlank() ) return false;

        String str = string.replace(" ", "").toLowerCase();
        for (int i = 0, j = str.length() - 1; i < str.length() / 2; i++, j--) {
            if (str.charAt(i) != str.charAt(j)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String string = " I love    Java       ";
        String seekPalindrome = "А роза упала на лапу Азора";
        System.out.println(wordsCount(string));
        System.out.println(isPalindrome(seekPalindrome));
    }
}

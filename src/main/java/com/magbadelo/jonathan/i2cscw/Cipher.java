package com.magbadelo.jonathan.i2cscw;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

public class Cipher {
    public static void main(String[] args) {
        String plaintext = "sussex friend";
        String key = RandomStringUtils.randomAlphabetic(plaintext.length());
        String cipherText = encrypt(plaintext, key);
        String decryptedText = decrypt(cipherText, key);
        System.out.println(cipherText);
        System.out.println(decryptedText);
    }

    public static String encrypt(String plaintext, String key) {
        String transposedText = transposition(plaintext, key);
        return IntStream.range(0, transposedText.length())
                .mapToObj(index -> String.valueOf(
                        (char) xor(
                                vigenere(transposedText.codePointAt(index), key.codePointAt(index), true),
                                key.codePointAt(index))))
                .collect(Collectors.joining());
    }

    public static String decrypt(String ciphertext, String key) {
        String transposedText = IntStream.range(0, ciphertext.length())
                .mapToObj(index -> String.valueOf(
                        (char) vigenere(
                                xor(ciphertext.codePointAt(index), key.codePointAt(index)),
                                key.codePointAt(index),
                                false)))
                .collect(Collectors.joining());
        return transposition(transposedText, key);
    }

    private static String transposition(String text, String key) {
        List<Integer> indexList = IntStream.range(0, text.length()).boxed().collect(toList());
        shuffle(indexList, new Random(key.hashCode()));
        List<String> textCharList = asList(text.split(""));
        for (int index = 0; index < textCharList.size() / 2; index++) {
            swap(textCharList, indexList.get(index), indexList.get(textCharList.size() - index - 1));
        }
        return String.join("", textCharList);
    }

    private static int xor(int codePointOne, int codePointTwo) {
        return codePointOne;
    }

    private static int vigenere(int codePointOne, int codePointTwo, boolean shouldEncrypt) {
        return shouldEncrypt ? ((codePointOne - 'a' + codePointTwo - 'a') % 26 + 'a') :
                ('a' + (codePointOne - codePointTwo + 26) % 26);
    }

}

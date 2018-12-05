package com.magbadelo.jonathan.i2cscw;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import static java.util.Collections.swap;
import static java.util.stream.Collectors.toList;

public class Cipher {
    public static void main(String[] args) {
        String plaintext = args[0];
        String key = RandomStringUtils.randomAlphabetic(plaintext.length());

        System.out.println("Running encryption for plaintext: " + plaintext);
        String cipherText = encrypt(plaintext, key);

        System.out.println("The encrypted ciphertext is: " + cipherText);

        String decryptedText = decrypt(cipherText, key);
        System.out.println("The decrypted plaintext from the ciphertext is: " + decryptedText);
    }

    static String encrypt(String plainText, String key) {
        String transposedText = transposition(plainText, key);
        return IntStream.range(0, transposedText.length())
                .mapToObj(index -> String.valueOf(
                        (char) vigenere(transposedText.codePointAt(index), key.codePointAt(index), true)))
                .collect(Collectors.joining());
    }

    static String decrypt(String cipherText, String key) {
        String transposedText = IntStream.range(0, cipherText.length())
                .mapToObj(index -> String.valueOf(
                        (char) vigenere(cipherText.codePointAt(index), key.codePointAt(index), false)))
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

    private static int vigenere(int codePointOne, int codePointTwo, boolean shouldEncrypt) {
        return shouldEncrypt ? ((codePointOne - 'a' + codePointTwo - 'a') % 26 + 'a') :
                ('a' + (codePointOne - codePointTwo + 26) % 26);
    }
}

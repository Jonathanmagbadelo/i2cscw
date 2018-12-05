package com.magbadelo.jonathan.i2cscw;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.magbadelo.jonathan.i2cscw.Cipher.decrypt;
import static com.magbadelo.jonathan.i2cscw.Cipher.encrypt;

@RunWith(Parameterized.class)
public class CipherTest {

    @Parameters
    public static Iterable<? extends Object> data() {
        return IntStream.range(0, 100).mapToObj(num -> RandomStringUtils.randomAlphabetic((new Random().nextInt(100)) + 20)).collect(Collectors.toList());
    }

    @Parameter
    public String plainText;

    @Test
    public void tester() {
        String key = RandomStringUtils.randomAlphabetic(plainText.length());
        plainText = plainText.toLowerCase();

        String cipherText = encrypt(plainText, key);
        String decryptedText = decrypt(cipherText, key);

        System.out.println("Original plainText is: " + plainText);
        System.out.println("Encrypted plainText is: " + cipherText);
        System.out.println("Decrypted cipherText is: " + decryptedText);
        System.out.println();

        Assert.assertEquals(plainText, decryptedText);
    }
}

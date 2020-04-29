/*
 * Copyright (C) 2018 — 2019 Honerfor, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.honerfor.cutils.security;

import com.honerfor.cutils.Serialization;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * <p>
 * This is an implementation of Advanced Encryption Standard,
 * to can encrypt and decrypt Objects of any type.
 * </p>
 *
 * @param <T> Type of value
 * @author B0BAI
 * @since 1.0
 */
public class AES<T> {

    /**
     * <p>Instance of {@link Cipher}</p>
     *
     * @since 1.0
     */
    private final Cipher cipher;

    /**
     * <p>Instance of {@link SecretKeySpec}</p>
     *
     * @since 1.0
     */
    private final SecretKeySpec secretKey;

    /**
     * GCM Length
     */
    private final static int GCM_IV_LENGTH = 12;

    /**
     * Additional Authentication Data for GCM
     *
     * @since 4.0
     */
    private final byte[] additionalAuthenticationData;

    /**
     * @param encryptionKey meta data you want to verify secret message
     * @throws NoSuchPaddingException   when a bad/Wrong encryption key is supplied.
     * @throws NoSuchAlgorithmException This exception is thrown when a cryptographic algorithm not available in the environment.
     * @since 1.0
     */
    private AES(final String encryptionKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] key = this.additionalAuthenticationData = encryptionKey.getBytes(StandardCharsets.UTF_8);
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        key = Arrays.copyOf(messageDigest.digest(key), 16);
        this.secretKey = new SecretKeySpec(key, "AES");
    }

    /**
     * <p>This initiates encryption </p>
     *
     * @param <T> Type of value
     * @return Instance of {@link AES}
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is
     *                                  requested but is not available in the environment.
     * @throws NoSuchPaddingException   This exception is thrown when a particular padding mechanism is
     *                                  requested but is not available in the environment.
     * @since 1.0
     */
    public static <T> AES<T> init() throws NoSuchAlgorithmException, NoSuchPaddingException {
        final String encryptionKey = "This is clearly a default Key: {OYrkhC'I(=fW&yNtP2peBndT5Hz&}. Set yours with: `AES.init(<Your_Key_here>)`";
        return AES.init(encryptionKey);
    }

    /**
     * <p>This initiates encryption</p>
     *
     * @param encryptionKey user encryption Key
     * @param <T>           Type of value
     * @return Instance of {@link AES}
     * @throws NoSuchAlgorithmException This exception is thrown when a particular cryptographic algorithm is
     *                                  requested but is not available in the environment.
     * @throws NoSuchPaddingException   This exception is thrown when a particular padding mechanism is
     *                                  requested but is not available in the environment.
     * @since 1.0
     */
    public static <T> AES<T> init(final String encryptionKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return new AES<>(encryptionKey);
    }

    /**
     * <p>This encrypt item of T type</p>
     *
     * @param itemToEncrypt item to encrypt.
     * @return encrypted string of {@code itemToEncrypt} of T type. Not {@literal null}
     * @throws Exception instance of any exception thrown
     * @since 1.0
     */
    public String encrypt(@Valid final T itemToEncrypt) throws Exception {
        final SecureRandom secureRandom = new SecureRandom();

        final byte[] iv = new byte[GCM_IV_LENGTH]; //NEVER REUSE THIS IV WITH SAME KEY
        secureRandom.nextBytes(iv);

        final GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
        this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, parameterSpec);

        if (Objects.nonNull(this.additionalAuthenticationData)) {
            this.cipher.updateAAD(this.additionalAuthenticationData);
        }

        final byte[] serializeData = Serialization.serialize(itemToEncrypt);
        byte[] cipherText = this.cipher.doFinal(serializeData);

        final ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    /**
     * <p>This method will decrypt the {@code itemToDecrypt}</p>
     *
     * @param itemToDecrypt encrypted string to be decrypted. not {@literal null}
     * @return decrypted Object.
     * @throws Exception instance of {@link InvalidKeyException}, {@link BadPaddingException} or any other exception thrown.
     * @since 1.0
     */
    public T decrypt(@NotNull final String itemToDecrypt) throws Exception {
        final byte[] cipherMessage = Base64.getDecoder().decode(itemToDecrypt);
        final AlgorithmParameterSpec algorithmParameterSpec = new GCMParameterSpec(128, cipherMessage, 0, GCM_IV_LENGTH);
        this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, algorithmParameterSpec);

        if (Objects.nonNull(this.additionalAuthenticationData)) {
            this.cipher.updateAAD(this.additionalAuthenticationData);
        }

        final byte[] plainText = this.cipher.doFinal(cipherMessage, GCM_IV_LENGTH, cipherMessage.length - GCM_IV_LENGTH);

        return Serialization.deserialize(plainText);
    }
}
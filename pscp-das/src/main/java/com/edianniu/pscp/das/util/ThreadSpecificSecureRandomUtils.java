package com.edianniu.pscp.das.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 生成强随机数验证码
 * ClassName: ThreadSpecificSecureRandomUtils
 *
 * @author: tandingbo
 * CreateTime: 2017-10-24 22:18
 */
public class ThreadSpecificSecureRandomUtils {
    /**
     * 该类的唯一实例
     */
    public static final ThreadSpecificSecureRandomUtils INSTANCE = new ThreadSpecificSecureRandomUtils();

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = ThreadLocal.withInitial(() -> {
        SecureRandom srnd;
        try {
            srnd = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            srnd = new SecureRandom();
        }
        return srnd;
    });

    /**
     * 私有构造器
     */
    private ThreadSpecificSecureRandomUtils() {
    }

    /**
     * TODO 使用示例
     * // int verificationCode = ThreadSpecificSecureRandomUtils.INSTANCE.nextInt(999999);
     * // DecimalFormat df = new DecimalFormat("000000");
     * // String txtVerCode = df.format(verificationCode);
     *
     * @param upperBound 最大随机数
     * @return 生成的随机数
     */
    public int nextInt(int upperBound) {
        SecureRandom secureRnd = SECURE_RANDOM.get();
        return secureRnd.nextInt(upperBound);
    }

    public void setSeed(long seed) {
        SecureRandom secureRnd = SECURE_RANDOM.get();
        secureRnd.setSeed(seed);
    }

}

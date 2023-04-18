package com.yqg.utils;

/**
 *
 * 唯一ID生成工具
 * @author KIKO
 */
public class UniqueIdGeneratorUtils {
    private static long lastTimestamp = 0L;
    private static long counter = 0L;

    /**
     * 生成唯一ID（整数）
     * @return
     */
    public static synchronized long IntegerIdGenerate() {
        long now = System.currentTimeMillis();
        if (now == lastTimestamp) {
            counter++;
        } else {
            lastTimestamp = now;
            counter = 0L;
        }
        return (now << 20) + counter;
    }
}

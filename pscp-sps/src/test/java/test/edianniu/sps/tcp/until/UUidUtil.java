package test.edianniu.sps.tcp.until;

import java.util.UUID;

public class UUidUtil {
    public static byte[] getIdentification(UUID uuid) {
        long first = uuid.getMostSignificantBits();
        long second = uuid.getLeastSignificantBits();
        byte[] transaction = new byte[16];
        for (int i = 0; i < 8; i++) {
            transaction[i] = (byte) (first >>> (56 - i * 8));
        }

        for (int i = 8; i < 16; i++) {
            transaction[i] = (byte) (second >>> (56 - i * 8));
        }
        return transaction;
    }

}

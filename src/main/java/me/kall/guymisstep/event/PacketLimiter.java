package me.kall.guymisstep.event;

public final class PacketLimiter {
    private static long lastSendTime = 0L;
    private static final long MIN_INTERVAL_MS = 1000L;

    public static boolean tryAcquire() {
        long now = System.currentTimeMillis();
        if (now - lastSendTime < MIN_INTERVAL_MS) return false;
        lastSendTime = now;
        return true;
    }
}
package me.kall.guymisstep.api;

public interface PacketSender {
    boolean guyMisstep$isSending(long blockPos);
    boolean guyMisstep$setIsSending(long blockPos);
    void guyMisstep$setIsNotSending(long blockPos);
}

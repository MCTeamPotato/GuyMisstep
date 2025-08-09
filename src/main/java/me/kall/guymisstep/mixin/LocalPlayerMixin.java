package me.kall.guymisstep.mixin;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import me.kall.guymisstep.api.PacketSender;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements PacketSender {
    @Unique private final LongSet guyMisstep$packets = new LongOpenHashSet();

    @Override
    public boolean guyMisstep$isSending(long blockPos) {
        return this.guyMisstep$packets.contains(blockPos);
    }

    @Override
    public boolean guyMisstep$setIsSending(long blockPos) {
        return this.guyMisstep$packets.add(blockPos);
    }

    @Override
    public void guyMisstep$setIsNotSending(long blockPos) {
        this.guyMisstep$packets.remove(blockPos);
    }
}

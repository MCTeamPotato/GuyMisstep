package me.kall.guymisstep.mixin;

import me.kall.guymisstep.api.PacketSender;
import me.kall.guymisstep.network.FallingBlockNotifyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin {
    @Inject(method = "animateTick", at = @At("HEAD"))
    private void onSpawnParticle(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        long blockPos = pos.asLong();

        PacketSender sender = (PacketSender) player;

        if (sender.guyMisstep$isSending(blockPos)) return;

        double dx = player.getX() - (pos.getX() + 0.5);
        double dy = player.getY() - (pos.getY() + 0.5);
        double dz = player.getZ() - (pos.getZ() + 0.5);
        double distSqr = dx * dx + dy * dy + dz * dz;

        if (distSqr < 2.25) {
            if (sender.guyMisstep$setIsSending(blockPos)) {
                PacketDistributor.sendToServer(new FallingBlockNotifyPacket(pos));
            }
        }
    }
}

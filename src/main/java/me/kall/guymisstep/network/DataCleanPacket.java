package me.kall.guymisstep.network;

import me.kall.guymisstep.api.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class DataCleanPacket {
    private final BlockPos pos;

    public DataCleanPacket(BlockPos pos) {
        this.pos = pos;
    }

    public DataCleanPacket(@NotNull FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(@NotNull FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public void handle(@NotNull Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> Optional.ofNullable(Minecraft.getInstance().player).ifPresent(player -> ((PacketSender) player).guyMisstep$setIsNotSending(pos.asLong())));
        ctx.get().setPacketHandled(true);
    }
}

package me.kall.guymisstep.network;

import me.kall.guymisstep.GuyMisstep;
import me.kall.guymisstep.api.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record DataCleanPacket(BlockPos pos) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, DataCleanPacket> CODEC = CustomPacketPayload.codec(DataCleanPacket::toBytes, DataCleanPacket::new);
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(GuyMisstep.MOD_ID, "data_clean");
    public static final Type<DataCleanPacket> TYPE = new Type<>(ID);

    public DataCleanPacket(@NotNull FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void toBytes(@NotNull FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(DataCleanPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> Optional.ofNullable(Minecraft.getInstance().player).ifPresent(player -> ((PacketSender) player).guyMisstep$setIsNotSending(packet.pos().asLong())));
    }

    @Override
    public @NotNull Type<DataCleanPacket> type() {
        return TYPE;
    }
}

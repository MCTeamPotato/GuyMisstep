package me.kall.guymisstep.network;

import me.kall.guymisstep.GuyMisstep;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.FallingBlock;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record FallingBlockNotifyPacket(BlockPos pos) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, FallingBlockNotifyPacket> CODEC = CustomPacketPayload.codec(FallingBlockNotifyPacket::toBytes, FallingBlockNotifyPacket::new);
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(GuyMisstep.MOD_ID, "falling_block_notify");
    public static final Type<FallingBlockNotifyPacket> TYPE = new Type<>(ID);

    public FallingBlockNotifyPacket(@NotNull FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    public void toBytes(@NotNull FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(FallingBlockNotifyPacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            if (player == null) return;

            ServerLevel level = player.serverLevel();
            if (!level.isLoaded(packet.pos())) return;

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(packet.pos());

            while (true) {
                if (!level.isLoaded(mutablePos)) break;

                var state = level.getBlockState(mutablePos);
                var block = state.getBlock();
                if (state.isAir()) break;

                if (!(block instanceof FallingBlock)) break;

                level.scheduleTick(mutablePos, block, 1);

                mutablePos.move(0, -1, 0);
            }

            PacketDistributor.sendToPlayer(player, new DataCleanPacket(packet.pos()));
        });
    }

    @Override
    public @NotNull Type<FallingBlockNotifyPacket> type() {
        return TYPE;
    }
}

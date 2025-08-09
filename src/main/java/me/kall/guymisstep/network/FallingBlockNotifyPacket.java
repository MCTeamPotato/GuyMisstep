package me.kall.guymisstep.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FallingBlockNotifyPacket {
    private final BlockPos pos;

    public FallingBlockNotifyPacket(BlockPos pos) {
        this.pos = pos;
    }

    public FallingBlockNotifyPacket(@NotNull FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(@NotNull FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public void handle(@NotNull Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ServerLevel level = player.getLevel();
            if (!level.isLoaded(pos)) return;

            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos().set(pos);

            while (true) {
                if (!level.isLoaded(mutablePos)) break;

                BlockState state = level.getBlockState(mutablePos);
                Block block = state.getBlock();
                if (state.isAir()) break;

                if (!(block instanceof FallingBlock)) break;

                level.getBlockTicks().scheduleTick(mutablePos, block, 1);

                mutablePos.move(0, -1, 0);
            }

            NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new DataCleanPacket(pos));
        });
        ctx.get().setPacketHandled(true);
    }
}

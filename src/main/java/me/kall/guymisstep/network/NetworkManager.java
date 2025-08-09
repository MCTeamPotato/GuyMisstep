package me.kall.guymisstep.network;

import com.google.common.base.Predicates;
import me.kall.guymisstep.GuyMisstep;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class NetworkManager {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocation.tryParse(GuyMisstep.MOD_ID + ":main"), () -> "1", Predicates.alwaysTrue(), Predicates.alwaysTrue());
    private static int id = 0;

    public static void register() {
        INSTANCE.registerMessage(id++, FallingBlockNotifyPacket.class, FallingBlockNotifyPacket::toBytes, FallingBlockNotifyPacket::new, FallingBlockNotifyPacket::handle);
        INSTANCE.registerMessage(id++, DataCleanPacket.class, DataCleanPacket::toBytes, DataCleanPacket::new, DataCleanPacket::handle);
    }
}

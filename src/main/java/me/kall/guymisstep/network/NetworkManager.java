package me.kall.guymisstep.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

public final class NetworkManager {
    public static void register(@NotNull RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(FallingBlockNotifyPacket.TYPE, FallingBlockNotifyPacket.CODEC, FallingBlockNotifyPacket::handle);
        registrar.playToClient(DataCleanPacket.TYPE, DataCleanPacket.CODEC, DataCleanPacket::handle);
    }
}

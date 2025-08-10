package me.kall.guymisstep.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkManager {
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(FallingBlockNotifyPacket.TYPE, FallingBlockNotifyPacket.CODEC, FallingBlockNotifyPacket::handle);
        registrar.playToServer(DataCleanPacket.TYPE, DataCleanPacket.CODEC, DataCleanPacket::handle);
    }
}

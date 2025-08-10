package me.kall.guymisstep.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkManager {
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(FallingBlockNotifyPacket.TYPE, FallingBlockNotifyPacket.CODEC, FallingBlockNotifyPacket::handle);
        registrar.playBidirectional(DataCleanPacket.TYPE, DataCleanPacket.CODEC, DataCleanPacket::handle);
    }
}

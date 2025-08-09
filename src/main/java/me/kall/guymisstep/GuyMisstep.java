package me.kall.guymisstep;

import me.kall.guymisstep.network.NetworkManager;
import net.minecraftforge.fml.common.Mod;

@Mod(GuyMisstep.MOD_ID)
public final class GuyMisstep {
    public static final String MOD_ID = "guymisstep";
    public static final String MOD_NAME = "GuyMisstep";

    public GuyMisstep() {
        NetworkManager.register();
    }
}

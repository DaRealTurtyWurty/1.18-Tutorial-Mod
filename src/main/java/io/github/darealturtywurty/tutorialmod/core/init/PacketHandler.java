package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.core.network.ClientboundUpdateEnergyStorageScreenPacket;
import io.github.darealturtywurty.tutorialmod.core.network.ClientboundUpdateToiletPacket;
import io.github.darealturtywurty.tutorialmod.core.network.ServerboundGetEnergyStoredPacket;
import io.github.darealturtywurty.tutorialmod.core.network.ServerboundToiletUpdatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(TutorialMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals);

    private PacketHandler() {
    }

    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(ServerboundToiletUpdatePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
            .encoder(ServerboundToiletUpdatePacket::encode).decoder(ServerboundToiletUpdatePacket::new)
            .consumer(ServerboundToiletUpdatePacket::handle).add();
        INSTANCE.messageBuilder(ClientboundUpdateToiletPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(ClientboundUpdateToiletPacket::encode).decoder(ClientboundUpdateToiletPacket::new)
            .consumer(ClientboundUpdateToiletPacket::handle).add();
        INSTANCE
            .messageBuilder(ClientboundUpdateEnergyStorageScreenPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(ClientboundUpdateEnergyStorageScreenPacket::encode)
            .decoder(ClientboundUpdateEnergyStorageScreenPacket::new)
            .consumer(ClientboundUpdateEnergyStorageScreenPacket::handle).add();
        INSTANCE.messageBuilder(ServerboundGetEnergyStoredPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
            .encoder(ServerboundGetEnergyStoredPacket::encode).decoder(ServerboundGetEnergyStoredPacket::new)
            .consumer(ServerboundGetEnergyStoredPacket::handle).add();
        TutorialMod.LOGGER.info("Registered {} packets for mod '{}'", index, TutorialMod.MODID);
    }
}

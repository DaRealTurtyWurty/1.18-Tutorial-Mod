package io.github.darealturtywurty.tutorialmod.core.network;

import java.util.function.Supplier;

import io.github.darealturtywurty.tutorialmod.client.ClientAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundUpdateEnergyStorageScreenPacket {
    public final int energy;

    public ClientboundUpdateEnergyStorageScreenPacket(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public ClientboundUpdateEnergyStorageScreenPacket(int energy) {
        this.energy = energy;
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.energy);
    }

    // This is an example of where we can return true instead of setting the packet
    // to be handled.
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
            () -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.updateEnergyStorage(this.energy)));
        return true;
    }
}

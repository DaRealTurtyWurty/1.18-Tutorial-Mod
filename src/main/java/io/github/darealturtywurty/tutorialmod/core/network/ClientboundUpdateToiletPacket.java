package io.github.darealturtywurty.tutorialmod.core.network;

import java.util.function.Supplier;

import io.github.darealturtywurty.tutorialmod.client.ClientAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ClientboundUpdateToiletPacket {
    public final BlockPos toiletPos;

    public ClientboundUpdateToiletPacket(BlockPos pos) {
        this.toiletPos = pos;
    }

    public ClientboundUpdateToiletPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.toiletPos);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(
            () -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAccess.updateToilet(this.toiletPos)));

        ctx.get().setPacketHandled(true);
    }
}

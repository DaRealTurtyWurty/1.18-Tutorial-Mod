package io.github.darealturtywurty.tutorialmod.core.network;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class ServerboundToiletUpdatePacket {
    public final BlockPos toiletPos;

    public ServerboundToiletUpdatePacket(BlockPos pos) {
        this.toiletPos = pos;
    }

    public ServerboundToiletUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.toiletPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            final BlockEntity blockEntity = ctx.get().getSender().level.getBlockEntity(this.toiletPos);
            if (blockEntity instanceof final ToiletBlockEntity toilet) {
                toilet.isShitting = true;
                toilet.fartTicker = 0;
                success.set(true);
            }
        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}

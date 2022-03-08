package io.github.darealturtywurty.tutorialmod.core.network;

import java.util.function.Supplier;

import io.github.darealturtywurty.tutorialmod.core.init.PacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class ServerboundGetEnergyStoredPacket {
    public final BlockPos bePos;
    
    public ServerboundGetEnergyStoredPacket(BlockPos pos) {
        this.bePos = pos;
    }
    
    public ServerboundGetEnergyStoredPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }
    
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.bePos);
    }
    
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final BlockEntity blockEntity = ctx.get().getSender().level.getBlockEntity(this.bePos);
            blockEntity.getCapability(CapabilityEnergy.ENERGY)
                .ifPresent(storage -> PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),
                    new ClientboundUpdateEnergyStorageScreenPacket(storage.getEnergyStored())));
        });
        
        ctx.get().setPacketHandled(true);
    }
}

package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.util.CustomEnergyStorage;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import io.github.darealturtywurty.tutorialmod.core.init.PacketHandler;
import io.github.darealturtywurty.tutorialmod.core.network.ClientboundUpdateEnergyStorageScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.network.PacketDistributor;

public class EnergyStorageBlockEntity extends BlockEntity implements BlockEntityTicker<EnergyStorageBlockEntity> {
    public final CustomEnergyStorage energyStorage;
    private LazyOptional<CustomEnergyStorage> energy;

    private int capacity = 10000, maxExtract = 100, maxReceive = 500;

    public EnergyStorageBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ENERGY_STORAGE.get(), pos, state);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.energyStorage.setEnergy(tag.getInt("Energy"));
        this.setChanged();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    public void tick() {
        // TODO: Extract to all sides
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, EnergyStorageBlockEntity blockEntity) {
        blockEntity.tick();
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Energy", this.energyStorage.getEnergyStored());
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, this.capacity, this.maxReceive, this.maxExtract, 0) {
            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                final int e = super.extractEnergy(maxExtract, simulate);
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),
                    new ClientboundUpdateEnergyStorageScreenPacket(this.energy));
                return e;
            }
            
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                final int e = super.receiveEnergy(maxReceive, simulate);
                PacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(),
                    new ClientboundUpdateEnergyStorageScreenPacket(this.energy));
                TutorialMod.LOGGER.info("Received: {}", e);
                TutorialMod.LOGGER.info("Current: {}", this.energy);
                return e;
            }
        };
    }
}

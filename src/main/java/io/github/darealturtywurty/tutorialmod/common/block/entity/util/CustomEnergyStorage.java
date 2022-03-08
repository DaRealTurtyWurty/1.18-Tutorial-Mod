package io.github.darealturtywurty.tutorialmod.common.block.entity.util;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
    private final BlockEntity blockEntity;

    public CustomEnergyStorage(BlockEntity blockEntity, int capacity) {
        super(capacity);
        this.blockEntity = blockEntity;
    }
    
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.blockEntity = blockEntity;
    }
    
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.blockEntity = blockEntity;
    }
    
    public CustomEnergyStorage(BlockEntity blockEntity, int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
        this.blockEntity = blockEntity;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        this.blockEntity.setChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        this.blockEntity.setChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(energy, this.capacity));
    }
}

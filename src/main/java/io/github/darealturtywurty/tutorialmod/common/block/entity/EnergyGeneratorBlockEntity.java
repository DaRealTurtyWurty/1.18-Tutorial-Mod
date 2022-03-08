package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.util.CustomEnergyStorage;
import io.github.darealturtywurty.tutorialmod.common.block.entity.util.InventoryBlockEntity;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyGeneratorBlockEntity extends InventoryBlockEntity
    implements BlockEntityTicker<EnergyGeneratorBlockEntity> {
    public static final Component TITLE = new TranslatableComponent(
        "container." + TutorialMod.MODID + ".energy_generator");
    
    public final CustomEnergyStorage energyStorage;
    
    private int capacity = 2000, maxExtract = 100;
    private int progress, maxProgress = 0;
    private LazyOptional<CustomEnergyStorage> energy;
    
    public EnergyGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ENERGY_GENERATOR.get(), pos, state, 1);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    public int getEnergy() {
        return this.energyStorage.getEnergyStored();
    }
    
    public int getEnergyForStack(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }
    
    public int getMaxProgress() {
        return this.maxProgress;
    }
    
    public int getProgress() {
        return this.progress;
    }
    
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt("Progress");
        this.energyStorage.setEnergy(tag.getInt("Energy"));
    }
    
    public void outputEnergy() {
        if (this.energyStorage.getEnergyStored() >= this.maxExtract && this.energyStorage.canExtract()) {
            for (final var direction : Direction.values()) {
                final BlockEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
                if (be == null) {
                    continue;
                }
                
                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if (be != this && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                        final int toSend = EnergyGeneratorBlockEntity.this.energyStorage.extractEnergy(this.maxExtract,
                            false);
                        TutorialMod.LOGGER.info("Send: {}", toSend);
                        final int received = storage.receiveEnergy(toSend, false);
                        TutorialMod.LOGGER.info("Final Received: {}", received);

                        EnergyGeneratorBlockEntity.this.energyStorage.setEnergy(
                            EnergyGeneratorBlockEntity.this.energyStorage.getEnergyStored() + toSend - received);
                    }
                });
            }
        }
    }

    @Override
    public void tick() {
        if (this.energyStorage.getEnergyStored() <= this.energyStorage.getMaxEnergyStored() - 100) {
            if (!getItemInSlot(0).isEmpty() && (this.progress <= 0 || this.progress > this.maxProgress)) {
                this.maxProgress = getEnergyForStack(getItemInSlot(0));
                this.inventory.extractItem(0, 1, false);
                this.progress++;
            } else if (this.progress > 0) {
                this.progress++;
                if (this.progress >= this.maxProgress) {
                    this.progress = 0;
                    this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() + this.maxProgress);
                }
            } else {
                this.progress = 0;
                this.maxProgress = 0;
            }
        }
        
        outputEnergy();
        
        super.tick();
    }
    
    @Override
    public void tick(Level level, BlockPos pos, BlockState state, EnergyGeneratorBlockEntity be) {
        be.tick();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Progress", this.progress);
        tag.putInt("Energy", getEnergy());
    }
    
    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(this, this.capacity, 0, this.maxExtract, 0);
    }
}

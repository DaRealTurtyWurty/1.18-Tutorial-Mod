package io.github.darealturtywurty.tutorialmod.common.container.syncdata;

import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyGeneratorBlockEntity;
import net.minecraft.world.inventory.SimpleContainerData;

public class EnergyGeneratorContainerData extends SimpleContainerData {
    private final EnergyGeneratorBlockEntity blockEntity;

    public EnergyGeneratorContainerData(EnergyGeneratorBlockEntity be, int amount) {
        super(amount);
        this.blockEntity = be;
    }

    @Override
    public int get(int key) {
        return switch (key) {
            case 0 -> this.blockEntity.getProgress();
            case 1 -> this.blockEntity.getMaxProgress();
            case 2 -> this.blockEntity.getEnergy();
            case 3 -> this.blockEntity.energyStorage.getMaxEnergyStored();
            default -> throw new UnsupportedOperationException("Unable to get key: '" + key + "' for block entity: '"
                + this.blockEntity + "' at pos: '" + this.blockEntity.getBlockPos() + "'");
        };
    }
}

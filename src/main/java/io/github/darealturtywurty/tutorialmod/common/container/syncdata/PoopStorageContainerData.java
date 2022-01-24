package io.github.darealturtywurty.tutorialmod.common.container.syncdata;

import io.github.darealturtywurty.tutorialmod.common.block.entity.PoopStorageBlockEntity;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class PoopStorageContainerData extends SimpleContainerData {
    private final PoopStorageBlockEntity blockEntity;
    
    public PoopStorageContainerData(PoopStorageBlockEntity be, int amount) {
        super(amount);
        this.blockEntity = be;
    }
    
    @Override
    public int get(int key) {
        return switch (key) {
            case 0 -> this.blockEntity.getItemInSlot(0).getCount();
            default -> throw new UnsupportedOperationException(
                    "There is no value corresponding to key: '" + key + "' in: '" + this.blockEntity + "'");
        };
    }

    @Override
    public void set(int key, int value) {
        switch (key) {
            case 0:
                ItemStack stack = this.blockEntity.getItemInSlot(0);
                if (value > 0 && value < stack.getMaxStackSize()) {
                    stack.setCount(value);
                } else if (value <= 0) {
                    stack = ItemStack.EMPTY;
                } else if (value > stack.getMaxStackSize()) {
                    stack.setCount(stack.getMaxStackSize());
                }

                this.blockEntity.inventory.setStackInSlot(0, stack);
                break;
            default:
                throw new UnsupportedOperationException(
                        "There is no value corresponding to key: '" + key + "' in: '" + this.blockEntity + "'");
        }
    }
}

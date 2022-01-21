package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DrawerBlockEntity extends InventoryBlockEntity {
    private static final int LIMIT = (int) Math.pow(2, 15);
    
    public DrawerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DRAWER.get(), pos, state, 1);
    }
    
    public boolean appendItem(ItemStack stack) {
        final ItemStack current = getItemInSlot(0);
        final int currentCount = current.getCount();
        if (current.getCount() < 0 || current.isEmpty()) {
            current.setCount(0);
        }
        
        if (!current.isEmpty() && !ItemStack.isSame(stack, current))
            return false;
        
        if (stack.getCount() < 0) {
            stack.setCount(0);
            return false;
        }
        
        if (current.getCount() >= LIMIT)
            return false;
        
        boolean increment = false;
        if (current.isEmpty() || current.getCount() == 0) {
            insertItem(0, new ItemStack(stack.getItem(), 1));
            increment = true;
        }
        
        final var copy = new ItemStack(stack.getItem(), getItemInSlot(0).getCount());
        if (current.getCount() + stack.getCount() > LIMIT) {
            final int available = LIMIT - copy.getCount();
            stack.shrink(available);
            copy.setCount(LIMIT);
        } else {
            copy.grow(stack.getCount());
            stack.shrink(stack.getCount());
        }
        
        if (increment || copy.getCount() == 1) {
            copy.shrink(1);
        }
        
        if (currentCount != copy.getCount()) {
            this.inventory.setStackInSlot(0, copy);
            update();
            return true;
        }
        
        return false;
    }
    
    @Override
    public void load(CompoundTag compound) {
        final CompoundTag inventory = compound.getCompound("Inventory");
        this.inventory.setSize(
                inventory.contains("Size", Tag.TAG_INT) ? inventory.getInt("Size") : this.inventory.getSlots());
        
        final ListTag items = inventory.getList("Items", Tag.TAG_COMPOUND);
        for (int index = 0; index < items.size(); index++) {
            final CompoundTag itemTags = items.getCompound(index);
            final int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < this.inventory.getSlots()) {
                final var stack = ItemStack.of(itemTags);
                stack.setCount(itemTags.getInt("RealCount"));
                this.inventory.setStackInSlot(slot, stack);
            }
        }
        
        this.setChanged();
    }

    public boolean prependItem(Player player) {
        final ItemStack current = getItemInSlot(0);
        final ItemStack copy = current.copy();
        
        final int currentCount = current.getCount();
        if (current.getCount() <= 0)
            return false;
        
        getItemInSlot(0).shrink(1);
        
        if (currentCount != current.getCount()) {
            copy.setCount(1);
            final var item = new ItemEntity(this.level, player.getX(), player.getY() + 0.5D, player.getZ(), copy);
            this.level.addFreshEntity(item);
            update();
            return true;
        }
        
        return false;
    }
    
    public boolean prependStack(Player player) {
        final ItemStack current = getItemInSlot(0);
        final ItemStack copy = current.copy();
        
        final int currentCount = current.getCount();
        if (current.getCount() <= 0)
            return false;
        
        int count = 0;
        if (currentCount >= 64) {
            count = 64;
            current.shrink(64);
        } else {
            count = currentCount;
            this.inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        
        if (currentCount != current.getCount() && count != 0) {
            copy.setCount(count);
            final var item = new ItemEntity(this.level, player.getX(), player.getY() + 0.5D, player.getZ(), copy);
            this.level.addFreshEntity(item);
            update();
            return true;
        }
        
        return false;
    }
    
    @Override
    public void saveAdditional(CompoundTag compound) {
        final var items = new ListTag();
        for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
            if (!this.inventory.getStackInSlot(slot).isEmpty()) {
                final var item = new CompoundTag();
                item.putInt("Slot", slot);
                this.inventory.getStackInSlot(slot).save(item);
                item.putInt("RealCount", this.inventory.getStackInSlot(slot).getCount());
                items.add(item);
            }
        }

        final var inventory = new CompoundTag();
        inventory.put("Items", items);
        inventory.putInt("Size", this.inventory.getSlots());
        compound.put("Inventory", inventory);
    }
}

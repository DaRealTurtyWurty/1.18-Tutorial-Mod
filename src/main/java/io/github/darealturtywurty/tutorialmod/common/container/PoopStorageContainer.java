package io.github.darealturtywurty.tutorialmod.common.container;

import io.github.darealturtywurty.tutorialmod.common.block.entity.PoopStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.container.syncdata.PoopStorageContainerData;
import io.github.darealturtywurty.tutorialmod.core.init.BlockInit;
import io.github.darealturtywurty.tutorialmod.core.init.ContainerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PoopStorageContainer extends AbstractContainerMenu {
    private final ContainerLevelAccess containerAccess;
    public final ContainerData data;

    // Client Constructor
    public PoopStorageContainer(int id, Inventory playerInv) {
        this(id, playerInv, new ItemStackHandler(1), BlockPos.ZERO, new SimpleContainerData(1));
    }

    // Server constructor
    public PoopStorageContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos, ContainerData data) {
        super(ContainerInit.POOP_STORAGE.get(), id);
        this.containerAccess = ContainerLevelAccess.create(playerInv.player.level, pos);
        this.data = data;
        
        final int slotSizePlus2 = 18, startX = 8, startY = 86, hotbarY = 144;
        
        addSlot(new SlotItemHandler(slots, 0, 44, 36));
        
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }
        
        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if (index < 1) {
                if (!moveItemStackTo(item, 1, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(item, 0, 1, false))
                return ItemStack.EMPTY;

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return retStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.containerAccess, player, BlockInit.POOP_STORAGE.get());
    }
    
    public static MenuConstructor getServerContainer(PoopStorageBlockEntity be, BlockPos pos) {
        return (id, playerInv, player) -> new PoopStorageContainer(id, playerInv, be.inventory, pos,
                new PoopStorageContainerData(be, 1));
    }
}

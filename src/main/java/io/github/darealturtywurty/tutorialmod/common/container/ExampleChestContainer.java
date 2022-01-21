package io.github.darealturtywurty.tutorialmod.common.container;

import io.github.darealturtywurty.tutorialmod.common.block.entity.ExampleChestBlockEntity;
import io.github.darealturtywurty.tutorialmod.core.init.BlockInit;
import io.github.darealturtywurty.tutorialmod.core.init.ContainerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ExampleChestContainer extends AbstractContainerMenu {
    private final ContainerLevelAccess containerAccess;

    // Client Constructor
    public ExampleChestContainer(int id, Inventory playerInv) {
        this(id, playerInv, new ItemStackHandler(27), BlockPos.ZERO);
    }

    // Server constructor
    public ExampleChestContainer(int id, Inventory playerInv, IItemHandler slots, BlockPos pos) {
        super(ContainerInit.EXAMPLE_CHEST.get(), id);
        this.containerAccess = ContainerLevelAccess.create(playerInv.player.level, pos);

        final int slotSizePlus2 = 18, startX = 8, startY = 86, hotbarY = 144, inventoryY = 18;
        
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 3; row++) {
                addSlot(new SlotItemHandler(slots, row * 9 + column, startX + column * slotSizePlus2,
                        inventoryY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 3; row++) {
                addSlot(new Slot(playerInv, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }

            addSlot(new Slot(playerInv, column, startX + column * slotSizePlus2, hotbarY));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if (index < 27) {
                if (!moveItemStackTo(item, 27, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(item, 0, 27, false))
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
        return stillValid(this.containerAccess, player, BlockInit.EXAMPLE_CHEST.get());
    }
    
    public static MenuConstructor getServerContainer(ExampleChestBlockEntity chest, BlockPos pos) {
        return (id, playerInv, player) -> new ExampleChestContainer(id, playerInv, chest.inventory, pos);
    }
}

package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class PoopStorageBlockEntity extends InventoryBlockEntity {
    public static final Component TITLE = new TranslatableComponent("container." + TutorialMod.MODID + ".poop_storage");
    
    public PoopStorageBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.POOP_STORAGE.get(), pos, state, 1);
    }
}

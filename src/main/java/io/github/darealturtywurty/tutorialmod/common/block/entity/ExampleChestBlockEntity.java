package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.util.InventoryBlockEntity;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleChestBlockEntity extends InventoryBlockEntity {
    public static final Component TITLE = new TranslatableComponent(
            "container." + TutorialMod.MODID + ".example_chest");
    
    public ExampleChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.EXAMPLE_CHEST.get(), pos, state, 27);
    }
}

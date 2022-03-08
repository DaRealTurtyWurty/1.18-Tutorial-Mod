package io.github.darealturtywurty.tutorialmod.common.block.entity;

import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DisplayHandBlockEntity extends BlockEntity {
    public DisplayHandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DISPLAY_HAND.get(), pos, state);
    }
}

package io.github.darealturtywurty.tutorialmod.common.block;

import io.github.darealturtywurty.tutorialmod.common.block.entity.DisplayHandBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DisplayHandBlock extends AbstractGlassBlock implements EntityBlock {
    public DisplayHandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayHandBlockEntity(pos, state);
    }
}

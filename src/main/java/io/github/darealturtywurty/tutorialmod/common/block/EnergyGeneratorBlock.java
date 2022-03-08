package io.github.darealturtywurty.tutorialmod.common.block;

import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyGeneratorBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.container.EnergyGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class EnergyGeneratorBlock extends Block implements EntityBlock {
    public EnergyGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide ? null
            : (level0, pos, state0, blockEntity) -> ((EnergyGeneratorBlockEntity) blockEntity).tick();
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyGeneratorBlockEntity(pos, state);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
        final BlockEntity be = level.getBlockEntity(pos);
        //@formatter:off
        if (!((be instanceof final EnergyGeneratorBlockEntity generator)))
            return;
        //@formatter:on

        for (int slot = 0; slot < generator.inventory.getSlots(); slot++) {
            if (generator.inventory.getStackInSlot(slot).isEmpty())
                return;

            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                generator.inventory.getStackInSlot(slot)));
        }

        super.onRemove(state, level, pos, newState, moving);
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult result) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final EnergyGeneratorBlockEntity generator) {
            final MenuProvider container = new SimpleMenuProvider(
                EnergyGeneratorContainer.getServerContainer(generator, pos), EnergyGeneratorBlockEntity.TITLE);
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }
        
        return InteractionResult.SUCCESS;
    }
}

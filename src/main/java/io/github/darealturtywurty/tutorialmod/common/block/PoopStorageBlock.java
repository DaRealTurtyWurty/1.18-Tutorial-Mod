package io.github.darealturtywurty.tutorialmod.common.block;

import io.github.darealturtywurty.tutorialmod.common.block.entity.PoopStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.container.PoopStorageContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
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

public class PoopStorageBlock extends Block implements EntityBlock {
    public PoopStorageBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((PoopStorageBlockEntity) blockEntity).tick();
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PoopStorageBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult result) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof final PoopStorageBlockEntity be) {
            final MenuProvider container = new SimpleMenuProvider(PoopStorageContainer.getServerContainer(be, pos),
                    PoopStorageBlockEntity.TITLE);
            NetworkHooks.openGui((ServerPlayer) player, container, pos);
        }

        return InteractionResult.SUCCESS;
    }
}

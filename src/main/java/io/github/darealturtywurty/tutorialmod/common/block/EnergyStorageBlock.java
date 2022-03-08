package io.github.darealturtywurty.tutorialmod.common.block;

import io.github.darealturtywurty.tutorialmod.client.ClientAccess;
import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class EnergyStorageBlock extends Block implements EntityBlock {
    public EnergyStorageBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide ? null
            : (level0, pos, state0, blockEntity) -> ((EnergyStorageBlockEntity) blockEntity).tick();
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyStorageBlockEntity(pos, state);
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult result) {
        if (level.isClientSide && level.getBlockEntity(pos) instanceof final EnergyStorageBlockEntity energyStorage) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientAccess.openEnergyStorage(energyStorage));
        }
        
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}

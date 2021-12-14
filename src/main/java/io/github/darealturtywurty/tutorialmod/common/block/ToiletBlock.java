package io.github.darealturtywurty.tutorialmod.common.block;

import java.util.EnumMap;
import java.util.Map;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToiletBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final BooleanProperty IS_USED = BooleanProperty.create("is_used");
    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

    private static final VoxelShape SHAPE = Shapes.join(Block.box(3, 6, 1, 13, 13, 13),
            Block.box(5, 0, 5, 11, 6, 11), BooleanOp.OR);

    public ToiletBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(IS_USED, false));
        runCalculation(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((ToiletBlockEntity) blockEntity).tick();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityInit.TOILET.get().create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult result) {
        final var toilet = (ToiletBlockEntity) level.getBlockEntity(pos);
        if (!level.isClientSide() && !player.isShiftKeyDown()) {
            final boolean success = player.startRiding(toilet.seat);
            if (success) {
                toilet.playerUses.put(player.getUUID(),
                        toilet.playerUses.containsKey(player.getUUID())
                                ? toilet.playerUses.get(player.getUUID()) + 1
                                : 1);
                level.blockUpdated(pos, this);
                toilet.setChanged();
            }
            return success ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        if (player.isShiftKeyDown() && !level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            player.displayClientMessage(
                    new TextComponent(
                            "Player has shitted " + toilet.playerUses.get(player.getUUID()) + " times!"),
                    false);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, IS_USED);
    }

    protected void runCalculation(VoxelShape shape) {
        for (final Direction direction : Direction.values()) {
            SHAPES.put(direction, TutorialMod.calculateShapes(direction, shape));
        }
    }
}

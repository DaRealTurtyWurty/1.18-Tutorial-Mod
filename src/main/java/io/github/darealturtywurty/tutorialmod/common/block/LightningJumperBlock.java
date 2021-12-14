package io.github.darealturtywurty.tutorialmod.common.block;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class LightningJumperBlock extends HorizontalDirectionalBlock {

	@Mod.EventBusSubscriber(modid = TutorialMod.MODID, bus = Bus.FORGE)
	public static class Events {
		@SubscribeEvent
		public static void livingTick(LivingUpdateEvent event) {
			var entity = event.getEntityLiving();
			var foundTag = new StringBuilder();
			entity.getTags().forEach(tag -> {
				if (tag.startsWith("LightningJumper-") && foundTag.isEmpty())
					foundTag.append(tag);
			});

			if (!foundTag.isEmpty()) {
				int ticks = Integer.parseInt(foundTag.toString().split("-")[1]);
				if (ticks < 120) {
					entity.removeTag(foundTag.toString());
					entity.addTag("LightningJumper-" + (ticks + 1));
				} else {
					entity.removeTag(foundTag.toString());
					LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(entity.level);
					lightning.setPos(entity.position());
					entity.level.addFreshEntity(lightning);
				}
			}
		}
	}

	private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);

	private static final Optional<VoxelShape> SHAPE = Stream
			.of(Block.box(7, 1, 3, 9, 7, 7), Block.box(3, 1, 7, 13, 7, 13), Block.box(6, 0, 2, 10, 1, 4),
					Block.box(2, 0, 7, 14, 1, 14), Block.box(3, 0, 6, 13, 1, 7), Block.box(4, 0, 5, 12, 1, 6),
					Block.box(5, 0, 4, 11, 1, 5), Block.box(9, 7, 6, 10, 16, 7), Block.box(6, 7, 6, 7, 16, 7),
					Block.box(7, 7, 3, 9, 9, 13), Block.box(7, 1, 13, 9, 10, 15), Block.box(7, 1, 1, 9, 7, 3),
					Block.box(7, 1, 0, 9, 6, 1), Block.box(7, 7, 2, 9, 8, 3), Block.box(7, 0, 14, 9, 1, 15),
					Block.box(7, 0, 1, 9, 1, 2), Block.box(9, 1, 13, 10, 6, 14), Block.box(6, 1, 13, 7, 6, 14),
					Block.box(11, 5, 6, 12, 6, 7), Block.box(9, 2, 5, 10, 3, 6), Block.box(9, 6, 6, 11, 7, 7),
					Block.box(9, 6, 5, 10, 7, 6), Block.box(10, 4, 6, 11, 5, 7), Block.box(9, 4, 5, 10, 5, 7),
					Block.box(9, 5, 5, 11, 6, 7), Block.box(9, 5, 4, 10, 6, 5), Block.box(9, 2, 6, 11, 3, 7),
					Block.box(11, 1, 6, 12, 2, 7), Block.box(9, 1, 4, 10, 2, 5), Block.box(9, 1, 5, 11, 2, 7),
					Block.box(9, 3, 6, 10, 6, 7), Block.box(4, 5, 6, 5, 6, 7), Block.box(5, 4, 6, 7, 5, 7),
					Block.box(6, 4, 5, 7, 5, 6), Block.box(5, 5, 5, 7, 6, 7), Block.box(6, 5, 4, 7, 6, 5),
					Block.box(6, 6, 5, 7, 7, 7), Block.box(6, 3, 6, 7, 6, 7), Block.box(5, 6, 6, 6, 7, 7),
					Block.box(5, 1, 5, 7, 2, 7), Block.box(4, 1, 6, 5, 2, 7), Block.box(6, 1, 4, 7, 2, 5),
					Block.box(6, 2, 5, 7, 3, 7), Block.box(5, 2, 6, 6, 3, 7))
			.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR));

	public LightningJumperBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
		runCalculation(SHAPE.orElse(Shapes.block()));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	protected void runCalculation(VoxelShape shape) {
		for (Direction direction : Direction.values())
			SHAPES.put(direction, TutorialMod.calculateShapes(direction, shape));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult result) {
		if (!level.isClientSide) {
			if (player.experienceLevel <= 10 && !player.isCreative()) {
				level.playSound(player, pos, SoundEvents.ANVIL_BREAK, SoundSource.PLAYERS, 1.0f, 1.0f);
				return InteractionResult.FAIL;
			}

			if (!player.isCreative())
				player.giveExperienceLevels(-5);

			level.playSound(player, pos, SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundSource.BLOCKS, 1.0f, 1.0f);
			for (int index = 0; index < this.RANDOM.nextInt(10) + 7; index++) {
				Pig pig = EntityType.PIG.create(level);
				pig.setPos(pos.getX() + this.RANDOM.nextInt(10) - 5, pos.getY(),
						pos.getZ() + this.RANDOM.nextInt(10) - 5);
				pig.addTag("LightningJumper-0");
				level.addFreshEntity(pig);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}
}

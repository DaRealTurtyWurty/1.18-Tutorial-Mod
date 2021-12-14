package io.github.darealturtywurty.tutorialmod.common.item;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ClickerItem extends Item {

    public ClickerItem(Properties properties) {
        super(properties);
    }

    public static boolean canInteract(Player player, BlockPos pos) {
        final float speed = player.getDigSpeed(player.level.getBlockState(pos), pos);
        return player.isCreative() || player.mayBuild() && speed > 0 && speed < Float.MAX_VALUE;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        final CompoundTag nbt = stack.getOrCreateTag();
        return nbt.contains(TutorialMod.MODID, Tag.TAG_COMPOUND)
                && nbt.getCompound(TutorialMod.MODID).contains("ContainedBlock", Tag.TAG_COMPOUND);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        final BlockHitResult result = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        final BlockState state = level.getBlockState(result.getBlockPos());
        if (!canInteract(player, result.getBlockPos()) || !state.isAir()
                || !state.canBeReplaced(new BlockPlaceContext(player, hand, stack, result)))
            return InteractionResultHolder.fail(stack);

        if (!stack.getOrCreateTag().contains(TutorialMod.MODID, Tag.TAG_COMPOUND)) {
            stack.getOrCreateTag().put(TutorialMod.MODID, new CompoundTag());
            return InteractionResultHolder.fail(stack);
        }

        final CompoundTag nbt = stack.getOrCreateTag().getCompound(TutorialMod.MODID);
        if (!nbt.contains("ContainedBlock", Tag.TAG_COMPOUND))
            return InteractionResultHolder.fail(stack);

        final BlockState toPlace = NbtUtils.readBlockState(nbt.getCompound("ContainedBlock"));
        level.setBlockAndUpdate(result.getBlockPos(), toPlace);
        nbt.remove("ContainedBlock");
        return InteractionResultHolder.success(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        final ItemStack stack = context.getItemInHand();
        final var player = context.getPlayer();
        final var level = player.level;
        final BlockPos pos = context.getClickedPos();
        final BlockState state = level.getBlockState(pos);
        if (!canInteract(player, pos))
            return InteractionResult.FAIL;

        if (!stack.getOrCreateTag().contains(TutorialMod.MODID, Tag.TAG_COMPOUND)) {
            stack.getOrCreateTag().put(TutorialMod.MODID, new CompoundTag());
        }

        final CompoundTag nbt = stack.getOrCreateTag().getCompound(TutorialMod.MODID);
        if (!nbt.contains("ContainedBlock", Tag.TAG_COMPOUND)) {
            if (!state.isAir()) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                nbt.put("ContainedBlock", NbtUtils.writeBlockState(state));
                return InteractionResult.SUCCESS;
            }
        } else if (state.canBeReplaced(new BlockPlaceContext(context))) {
            level.setBlockAndUpdate(pos, NbtUtils.readBlockState(nbt.getCompound("ContainedBlock")));
            nbt.remove("ContainedBlock");
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}

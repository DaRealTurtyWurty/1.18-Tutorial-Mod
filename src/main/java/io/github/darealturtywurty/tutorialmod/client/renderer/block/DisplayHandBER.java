package io.github.darealturtywurty.tutorialmod.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.darealturtywurty.tutorialmod.common.block.entity.DisplayHandBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.EmptyModelData;

public class DisplayHandBER implements BlockEntityRenderer<DisplayHandBlockEntity> {
    private final BlockEntityRendererProvider.Context context;
    
    public DisplayHandBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }
    
    @SuppressWarnings("resource")
    @Override
    public void render(DisplayHandBlockEntity be, float partialTicks, PoseStack stack, MultiBufferSource buffer,
            int combinedOverlay, int packedLight) {
        final BlockRenderDispatcher dispatcher = this.context.getBlockRenderDispatcher();
        final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        dispatcher.renderSingleBlock(Blocks.GLASS.defaultBlockState(), stack, buffer, combinedOverlay, packedLight,
                EmptyModelData.INSTANCE);

        final LocalPlayer player = Minecraft.getInstance().player;
        final ItemStack heldItem = player.getMainHandItem().isEmpty() ? player.getOffhandItem()
                : player.getMainHandItem();
        stack.pushPose();
        stack.translate(0.5f, 0.5f, 0.5f);
        stack.scale(0.75f, 0.75f, 0.75f);
        itemRenderer.renderStatic(player, heldItem, TransformType.FIXED, false, stack, buffer,
                Minecraft.getInstance().level, combinedOverlay, packedLight, 0);
        stack.popPose();
    }
}

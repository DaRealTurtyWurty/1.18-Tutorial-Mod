package io.github.darealturtywurty.tutorialmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.container.PoopStorageContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PoopStorageScreen extends AbstractContainerScreen<PoopStorageContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MODID,
            "textures/gui/poop_storage.png");

    public PoopStorageScreen(PoopStorageContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 168;
    }
    
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        
        final int count = this.menu.data.get(0);
        final int scaledHeight = (int) mapNumber(count, 0, 64, 0, 62);
        bindTexture();
        blit(stack, this.leftPos + 118, this.topPos + 75 - scaledHeight, 176, 62 - scaledHeight, 30, scaledHeight);

        this.font.draw(stack, this.title, this.leftPos + 20, this.topPos + 5, 0x404040);
        this.font.draw(stack, this.playerInventoryTitle, this.leftPos + 8, this.topPos + 75, 0x404040);
        this.font.draw(stack, count + "", this.leftPos + 105, this.topPos + 12, 0x404040);
    }
    
    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        renderBackground(stack);
        bindTexture();
        blit(stack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
    
    @Override
    protected void renderLabels(PoseStack stack, int mouseX, int mouseY) {
    }
    
    public static void bindTexture() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
    }
    
    public static double mapNumber(double value, double rangeMin, double rangeMax, double resultMin, double resultMax) {
        return (value - rangeMin) / (rangeMax - rangeMin) * (resultMax - resultMin) + resultMin;
    }
}

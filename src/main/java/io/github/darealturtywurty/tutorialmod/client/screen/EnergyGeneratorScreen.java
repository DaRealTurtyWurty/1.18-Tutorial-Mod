package io.github.darealturtywurty.tutorialmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.container.EnergyGeneratorContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MODID,
        "textures/gui/energy_generator.png");
    
    public EnergyGeneratorScreen(EnergyGeneratorContainer container, Inventory playerInv, Component title) {
        super(container, playerInv, title);
        this.leftPos = 0;
        this.topPos = 0;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);

        final int energyStored = this.menu.data.get(2);
        final int maxEnergy = this.menu.data.get(3);
        final int scaledHeight = (int) mapNumber(energyStored, 0, maxEnergy, 0, 62);
        bindTexture();
        blit(stack, this.leftPos + 118, this.topPos + 75 - scaledHeight, 176, 62 - scaledHeight, 30, scaledHeight);

        final int progress = this.menu.data.get(0);
        final int maxProgress = this.menu.data.get(1);
        final int scaledProgress = (int) mapNumber(progress, 0, maxProgress, 0, 22);
        bindTexture();
        blit(stack, this.leftPos + 76, this.topPos + 36, 206, 0, scaledProgress, 15);
        
        this.font.draw(stack, this.title, this.leftPos + 7, this.topPos + 5, 0x404040);
        this.font.draw(stack, this.playerInventoryTitle, this.leftPos + 8, this.topPos + 75, 0x404040);
        drawCenteredString(stack, this.font, energyStored + "", this.leftPos + 133, this.topPos + 4, 0xFFFFFF);
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

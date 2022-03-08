package io.github.darealturtywurty.tutorialmod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.core.init.PacketHandler;
import io.github.darealturtywurty.tutorialmod.core.network.ServerboundGetEnergyStoredPacket;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class EnergyStorageScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MODID,
        "textures/gui/energy_storage.png");
    private static final int IMAGE_WIDTH = 176, IMAGE_HEIGHT = 166;
    private int leftPos = 0, topPos = 0;
    private final EnergyStorageBlockEntity be;
    private int energy;
    
    public EnergyStorageScreen(EnergyStorageBlockEntity be) {
        super(TextComponent.EMPTY);
        this.be = be;
        PacketHandler.INSTANCE.sendToServer(new ServerboundGetEnergyStoredPacket(be.getBlockPos()));
    }

    public int getEnergy() {
        return this.energy;
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        bindTexture();
        blit(stack, this.leftPos, this.topPos, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        
        final int energyStored = this.energy;
        final int maxEnergy = this.be.energyStorage.getMaxEnergyStored();
        final int scaledEnergy = (int) mapNumber(energyStored, 0, maxEnergy, 0, 122);
        bindTexture();
        blit(stack, this.leftPos + 59, this.topPos + 145 - scaledEnergy, 176, 122 - scaledEnergy, 58, scaledEnergy);
        
        this.font.draw(stack, this.title, this.leftPos + 7, this.topPos + 5, 0x404040);
        this.font.draw(stack, this.energy + "RF", this.leftPos + 10, this.topPos + 10, 0x404040);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
    
    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - IMAGE_WIDTH) / 2;
        this.topPos = (this.height - IMAGE_HEIGHT) / 2;
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

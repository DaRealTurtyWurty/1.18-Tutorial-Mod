package io.github.darealturtywurty.tutorialmod.client;

import io.github.darealturtywurty.tutorialmod.client.screen.EnergyStorageScreen;
import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("resource")
public class ClientAccess {
    public static Runnable openEnergyStorage(EnergyStorageBlockEntity be) {
        return () -> Minecraft.getInstance().setScreen(new EnergyStorageScreen(be));
    }
    
    public static void updateEnergyStorage(int energy) {
        final Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof final EnergyStorageScreen energyStorage) {
            energyStorage.setEnergy(energy);
        }
    }

    public static void updateToilet(BlockPos pos) {
        final BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
        if (blockEntity instanceof final ToiletBlockEntity toilet) {
            toilet.isShitting = false;
            toilet.fartTicker = 0;
        }
    }
}

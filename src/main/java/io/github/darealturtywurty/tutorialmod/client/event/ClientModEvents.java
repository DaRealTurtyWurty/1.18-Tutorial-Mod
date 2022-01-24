package io.github.darealturtywurty.tutorialmod.client.event;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.client.KeyInit;
import io.github.darealturtywurty.tutorialmod.client.renderer.ExampleEntityRenderer;
import io.github.darealturtywurty.tutorialmod.client.renderer.SeatRenderer;
import io.github.darealturtywurty.tutorialmod.client.renderer.model.ExampleEntityModel;
import io.github.darealturtywurty.tutorialmod.client.screen.ExampleChestScreen;
import io.github.darealturtywurty.tutorialmod.client.screen.PoopStorageScreen;
import io.github.darealturtywurty.tutorialmod.core.init.BlockInit;
import io.github.darealturtywurty.tutorialmod.core.init.ContainerInit;
import io.github.darealturtywurty.tutorialmod.core.init.EntityInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = TutorialMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.LIGHTNING_JUMPER.get(), RenderType.cutout());
        KeyInit.init();
        MenuScreens.register(ContainerInit.EXAMPLE_CHEST.get(), ExampleChestScreen::new);
        MenuScreens.register(ContainerInit.POOP_STORAGE.get(), PoopStorageScreen::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ExampleEntityModel.LAYER_LOCATION, ExampleEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.EXAMPLE_ENTITY.get(), ExampleEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.SEAT.get(), SeatRenderer::new);
    }
}

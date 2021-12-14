package io.github.darealturtywurty.tutorialmod.client.event;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.client.KeyInit;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.entity.SittableEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TutorialMod.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public final class ClientForgeEvents {
    private ClientForgeEvents() {
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void clientTick(ClientTickEvent event) {
        final var player = Minecraft.getInstance().player;
        if ((KeyInit.exampleKeyMapping.isDown() && player.isPassenger()
                && player.getVehicle() instanceof SittableEntity)
                && (player.level.getBlockEntity(
                        player.getVehicle().blockPosition())instanceof final ToiletBlockEntity toilet)) {
            toilet.setShitting();
        }
    }
}

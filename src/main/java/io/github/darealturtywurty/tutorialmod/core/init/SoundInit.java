package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister
            .create(ForgeRegistries.SOUND_EVENTS, TutorialMod.MODID);

    public static final RegistryObject<SoundEvent> EXAMPLE_ENTITY_AMBIENT = SOUNDS.register(
            "entity.example_entity.ambient",
            () -> new SoundEvent(new ResourceLocation(TutorialMod.MODID, "entity.example_entity.ambient")));

    public static final RegistryObject<SoundEvent> EXAMPLE_ENTITY_HURT = SOUNDS.register(
            "entity.example_entity.hurt",
            () -> new SoundEvent(new ResourceLocation(TutorialMod.MODID, "entity.example_entity.hurt")));

    public static final RegistryObject<SoundEvent> EXAMPLE_ENTITY_DEATH = SOUNDS.register(
            "entity.example_entity.death",
            () -> new SoundEvent(new ResourceLocation(TutorialMod.MODID, "entity.example_entity.death")));

    public static final RegistryObject<SoundEvent> FART = SOUNDS.register("block.toilet.fart",
            () -> new SoundEvent(new ResourceLocation(TutorialMod.MODID, "block.toilet.fart")));

    private SoundInit() {
    }
}

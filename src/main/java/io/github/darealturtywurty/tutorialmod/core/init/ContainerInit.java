package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.container.EnergyGeneratorContainer;
import io.github.darealturtywurty.tutorialmod.common.container.ExampleChestContainer;
import io.github.darealturtywurty.tutorialmod.common.container.PoopStorageContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            TutorialMod.MODID);
    
    public static final RegistryObject<MenuType<ExampleChestContainer>> EXAMPLE_CHEST = CONTAINERS
            .register("example_chest", () -> new MenuType<>(ExampleChestContainer::new));

    public static final RegistryObject<MenuType<PoopStorageContainer>> POOP_STORAGE = CONTAINERS
            .register("poop_storage", () -> new MenuType<>(PoopStorageContainer::new));

    public static final RegistryObject<MenuType<EnergyGeneratorContainer>> ENERGY_GENERATOR = CONTAINERS
            .register("energy_generator", () -> new MenuType<>(EnergyGeneratorContainer::new));

    private ContainerInit() {
    }
}

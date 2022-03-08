package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.DisplayHandBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.DrawerBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyGeneratorBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.EnergyStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ExampleChestBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.PoopStorageBlockEntity;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityInit {
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, TutorialMod.MODID);
    
    public static final RegistryObject<BlockEntityType<ToiletBlockEntity>> TOILET = BLOCK_ENTITIES.register("toilet",
            () -> BlockEntityType.Builder.of(ToiletBlockEntity::new, BlockInit.TOILET.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<DrawerBlockEntity>> DRAWER = BLOCK_ENTITIES.register("drawer",
            () -> BlockEntityType.Builder.of(DrawerBlockEntity::new, BlockInit.DRAWER.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<ExampleChestBlockEntity>> EXAMPLE_CHEST = BLOCK_ENTITIES
            .register("example_chest", () -> BlockEntityType.Builder
                    .of(ExampleChestBlockEntity::new, BlockInit.EXAMPLE_CHEST.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<PoopStorageBlockEntity>> POOP_STORAGE = BLOCK_ENTITIES.register(
            "poop_storage",
            () -> BlockEntityType.Builder.of(PoopStorageBlockEntity::new, BlockInit.POOP_STORAGE.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<DisplayHandBlockEntity>> DISPLAY_HAND = BLOCK_ENTITIES.register(
            "display_hand",
            () -> BlockEntityType.Builder.of(DisplayHandBlockEntity::new, BlockInit.DISPLAY_HAND.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<EnergyStorageBlockEntity>> ENERGY_STORAGE = BLOCK_ENTITIES
            .register("energy_storage", () -> BlockEntityType.Builder
                    .of(EnergyStorageBlockEntity::new, BlockInit.ENERGY_STORAGE.get()).build(null));
    
    public static final RegistryObject<BlockEntityType<EnergyGeneratorBlockEntity>> ENERGY_GENERATOR = BLOCK_ENTITIES
            .register("energy_generator", () -> BlockEntityType.Builder
                    .of(EnergyGeneratorBlockEntity::new, BlockInit.ENERGY_GENERATOR.get()).build(null));
    
    private BlockEntityInit() {
    }
}

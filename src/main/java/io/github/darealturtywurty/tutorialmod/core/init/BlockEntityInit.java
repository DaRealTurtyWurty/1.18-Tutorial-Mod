package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.DrawerBlockEntity;
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

    private BlockEntityInit() {
    }
}

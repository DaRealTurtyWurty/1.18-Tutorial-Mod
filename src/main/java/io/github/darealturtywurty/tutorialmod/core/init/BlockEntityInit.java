package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.entity.ToiletBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, TutorialMod.MODID);

    public static final RegistryObject<BlockEntityType<ToiletBlockEntity>> TOILET = BLOCK_ENTITIES.register(
            "toilet",
            () -> BlockEntityType.Builder.of(ToiletBlockEntity::new, BlockInit.TOILET.get()).build(null));

    private BlockEntityInit() {
    }
}

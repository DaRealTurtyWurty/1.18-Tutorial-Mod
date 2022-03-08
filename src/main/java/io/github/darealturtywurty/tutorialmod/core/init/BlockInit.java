package io.github.darealturtywurty.tutorialmod.core.init;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import io.github.darealturtywurty.tutorialmod.common.block.DisplayHandBlock;
import io.github.darealturtywurty.tutorialmod.common.block.DrawerBlock;
import io.github.darealturtywurty.tutorialmod.common.block.EnergyGeneratorBlock;
import io.github.darealturtywurty.tutorialmod.common.block.EnergyStorageBlock;
import io.github.darealturtywurty.tutorialmod.common.block.ExampleChestBlock;
import io.github.darealturtywurty.tutorialmod.common.block.LightningJumperBlock;
import io.github.darealturtywurty.tutorialmod.common.block.PoopStorageBlock;
import io.github.darealturtywurty.tutorialmod.common.block.ToiletBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            TutorialMod.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE)
                    .strength(2.0f, 15f).requiresCorrectToolForDrops().friction(0.5f)));

    public static final RegistryObject<LightningJumperBlock> LIGHTNING_JUMPER = BLOCKS.register("lightning_jumper",
            () -> new LightningJumperBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.TERRACOTTA_ORANGE)
                    .strength(8.0f, 30f).requiresCorrectToolForDrops().noOcclusion().dynamicShape()));

    public static final RegistryObject<ToiletBlock> TOILET = BLOCKS.register("toilet",
            () -> new ToiletBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).noOcclusion().dynamicShape()));

    public static final RegistryObject<DrawerBlock> DRAWER = BLOCKS.register("drawer",
            () -> new DrawerBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<ExampleChestBlock> EXAMPLE_CHEST = BLOCKS.register("example_chest",
            () -> new ExampleChestBlock(BlockBehaviour.Properties.copy(BlockInit.EXAMPLE_BLOCK.get())));

    public static final RegistryObject<PoopStorageBlock> POOP_STORAGE = BLOCKS.register("poop_storage",
            () -> new PoopStorageBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistryObject<DisplayHandBlock> DISPLAY_HAND = BLOCKS.register("display_hand",
            () -> new DisplayHandBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));
    
    public static final RegistryObject<EnergyStorageBlock> ENERGY_STORAGE = BLOCKS.register("energy_storage",
            () -> new EnergyStorageBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<EnergyGeneratorBlock> ENERGY_GENERATOR = BLOCKS.register("energy_generator",
            () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    private BlockInit() {
    }
}

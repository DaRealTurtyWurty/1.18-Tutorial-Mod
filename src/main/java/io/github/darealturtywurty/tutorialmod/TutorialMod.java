package io.github.darealturtywurty.tutorialmod;

import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.Nonnull;

import io.github.darealturtywurty.tutorialmod.core.init.BlockEntityInit;
import io.github.darealturtywurty.tutorialmod.core.init.BlockInit;
import io.github.darealturtywurty.tutorialmod.core.init.EntityInit;
import io.github.darealturtywurty.tutorialmod.core.init.ItemInit;
import io.github.darealturtywurty.tutorialmod.core.init.SoundInit;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(TutorialMod.MODID)
public class TutorialMod {
    public static final String MODID = "tutorialmod";

    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab(MODID) { // tutorialmod.itemGroup
        @Override
        public ItemStack makeIcon() {
            return ItemInit.EXAMPLE_ENTITY_SPAWN_EGG.get().getDefaultInstance();
        }
    };

    public TutorialMod() {
        final var bus = FMLJavaModLoadingContext.get().getModEventBus();

        SoundInit.SOUNDS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        EntityInit.ENTITIES.register(bus);
    }

    public static VoxelShape calculateShapes(Direction to, VoxelShape shape) {
        final VoxelShape[] buffer = { shape, Shapes.empty() };

        final int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                    Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    @Nonnull
    public Block retreiveBlock(ResourceLocation name) {
        final Optional<Block> block = ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(entry -> entry.getKey().getRegistryName().equals(name)).map(Entry::getValue)
                .findFirst();
        return block.orElse(Blocks.AIR);
    }
}
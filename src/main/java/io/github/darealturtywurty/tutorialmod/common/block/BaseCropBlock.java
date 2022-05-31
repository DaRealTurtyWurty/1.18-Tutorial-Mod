package io.github.darealturtywurty.tutorialmod.common.block;

import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class BaseCropBlock extends CropBlock {
    private final Supplier<Item> seeds;

    public BaseCropBlock(Supplier<Item> seedsItem, Properties properties) {
        super(properties);
        this.seeds = seedsItem;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.seeds.get();
    }
}

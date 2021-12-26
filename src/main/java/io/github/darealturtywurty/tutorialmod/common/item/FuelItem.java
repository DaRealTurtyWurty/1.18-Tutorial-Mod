package io.github.darealturtywurty.tutorialmod.common.item;

import java.util.function.BiFunction;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class FuelItem extends Item {
    private final BiFunction<ItemStack, RecipeType<?>, Integer> burnTime;

    public FuelItem(Properties properties, BiFunction<ItemStack, RecipeType<?>, Integer> burnTime) {
        super(properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
        return this.burnTime.apply(itemStack, recipeType);
    }
}

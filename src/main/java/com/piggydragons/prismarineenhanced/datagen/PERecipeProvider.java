package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.BlockEntityIdFix;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class PERecipeProvider extends RecipeProvider {

    public PERecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.CUT_PRISMARINE.get(), 1, consumer);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_STAIRS.get(), 1, consumer);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_SLAB.get(), 2, consumer);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_WALL.get(), 1, consumer);
        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.CUT_PRISMARINE_STAIRS.get(), 1, consumer);
        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.CUT_PRISMARINE_SLAB.get(), 2, consumer);
        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.CUT_PRISMARINE_WALL.get(), 1, consumer);

//        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PEBlocks.REINFORCED_PRISMARINE.get())
//                        .define('T', Blocks.PRISMARINE_BRICKS).define('D', Blocks.DARK_PRISMARINE)
//                        .pattern("T").pattern("D")
//                        .unlockedBy(getHasName(Items.PRISMARINE_BRICKS), has(Items.PRISMARINE_BRICKS))
        stonecutterRecipe(PEBlocks.REINFORCED_PRISMARINE.get(), PEBlocks.REINFORCED_PRISMARINE_WALL.get(), 1, consumer);

        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.PRISMARINE_PILLAR.get(), 1, consumer);

        waxingRecipes(PEBlocks.COPPER_GILDED_DARK_PRISMARINE, consumer);
        waxingRecipes(PEBlocks.COPPER_LATTICES, consumer);
    }

    private void waxingRecipes(PEBlocks.WeatheringGroup<?, ?> group, Consumer<FinishedRecipe> consumer) {
        for (WeatheringCopper.WeatherState state : WeatheringCopper.WeatherState.values()) {
            Block unwaxed = group.getUnwaxedBlock(state);
            Block waxed = group.getWaxedBlock(state);

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, waxed)
                    .requires(unwaxed)
                    .requires(Items.HONEYCOMB)
                    .unlockedBy(getHasName(unwaxed), has(unwaxed))
                    .save(consumer, blockId(waxed).withSuffix("_from_honeycomb"));
        }
    }

    private void stonecutterRecipe(Block ingredient, Block result, int resultCount, Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, resultCount)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer, blockId(result).withSuffix("_from_" + blockId(ingredient).getPath()));
    }

    private ResourceLocation blockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}

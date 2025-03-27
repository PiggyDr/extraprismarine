package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
    protected void buildRecipes(Consumer<FinishedRecipe> recipes) {
        stonecutterRecipe(Blocks.PRISMARINE_BRICKS, PEBlocks.CUT_PRISMARINE.get(), 1, recipes);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_STAIRS.get(), 1, recipes);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_SLAB.get(), 2, recipes);
        stonecutterRecipe(PEBlocks.CUT_PRISMARINE.get(), PEBlocks.CUT_PRISMARINE_WALL.get(), 1, recipes);
        stonecutterRecipe(Blocks.PRISMARINE_BRICKS, PEBlocks.CUT_PRISMARINE_STAIRS.get(), 1, recipes);
        stonecutterRecipe(Blocks.PRISMARINE_BRICKS, PEBlocks.CUT_PRISMARINE_SLAB.get(), 2, recipes);
        stonecutterRecipe(Blocks.PRISMARINE_BRICKS, PEBlocks.CUT_PRISMARINE_WALL.get(), 1, recipes);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, PEBlocks.REINFORCED_PRISMARINE.get())
                        .define('T', Blocks.PRISMARINE_BRICKS).define('D', Blocks.DARK_PRISMARINE)
                        .pattern("T").pattern("D")
                        .unlockedBy(getHasName(Items.PRISMARINE_BRICKS), has(Items.PRISMARINE_BRICKS))
                        .unlockedBy(getHasName(Items.DARK_PRISMARINE), has(Items.DARK_PRISMARINE))
                        .save(recipes);
        stonecutterRecipe(PEBlocks.REINFORCED_PRISMARINE.get(), PEBlocks.REINFORCED_PRISMARINE_WALL.get(), 1, recipes);

        stonecutterRecipe(Blocks.DARK_PRISMARINE, PEBlocks.DARK_PRISMARINE_WALL.get(), 1, recipes);
        wallShapedRecipe(Blocks.DARK_PRISMARINE, PEBlocks.DARK_PRISMARINE_WALL.get(), 6, recipes);

        stonecutterRecipe(Blocks.PRISMARINE, PEBlocks.PRISMARINE_PILLAR.get(), 1, recipes);

        coralinePrismarine(Items.BRAIN_CORAL_FAN, Items.QUARTZ, PEBlocks.BRAIN_CORALINE_PRISMARINE.get(), recipes);
        coralinePrismarine(Items.BUBBLE_CORAL_FAN, Items.AMETHYST_SHARD, PEBlocks.BUBBLE_CORALINE_PRISMARINE.get(), recipes);
        coralinePrismarine(Items.FIRE_CORAL_FAN, Items.REDSTONE, PEBlocks.FIRE_CORALINE_PRISMARINE.get(), recipes);
        coralinePrismarine(Items.HORN_CORAL_FAN, Items.GOLD_INGOT, PEBlocks.HORN_CORALINE_PRISMARINE.get(), recipes);
        coralinePrismarine(Items.TUBE_CORAL_FAN, Items.LAPIS_LAZULI, PEBlocks.TUBE_CORALINE_PRISMARINE.get(), recipes);

        gildedPrismarine(Items.GOLD_INGOT, PEBlocks.GILDED_DARK_PRISMARINE.get(), recipes);
        gildedPrismarine(Items.COPPER_INGOT, PEBlocks.COPPER_GILDED_DARK_PRISMARINE.getClean(), recipes);

        waxingRecipes(PEBlocks.COPPER_GILDED_DARK_PRISMARINE, recipes);
        waxingRecipes(PEBlocks.COPPER_LATTICES, recipes);

        wallShapedRecipe(Items.COPPER_INGOT, PEBlocks.COPPER_LATTICES.getClean(), 16, recipes);

        wallShapedRecipe(Items.PRISMARINE_BRICKS, PEBlocks.PRISMARINE_TRAPDOOR.get(), 4, recipes);
    }

    private void waxingRecipes(PEBlocks.WeatheringGroup<?, ?> group, Consumer<FinishedRecipe> recipes) {
        String groupId = blockId(group.getClean()).getPath() + "_waxing";
        for (WeatheringCopper.WeatherState state : WeatheringCopper.WeatherState.values()) {
            Block unwaxed = group.getUnwaxedBlock(state);
            Block waxed = group.getWaxedBlock(state);

            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, waxed)
                    .requires(unwaxed)
                    .requires(Items.HONEYCOMB)
                    .group(groupId);

            for (WeatheringCopper.WeatherState unlockState : WeatheringCopper.WeatherState.values()) {
                Block unwaxedReq = group.getUnwaxedBlock(unlockState);
                Block waxedReq = group.getWaxedBlock(unlockState);

                builder.unlockedBy(getHasName(unwaxedReq), has(unwaxedReq))
                        .unlockedBy(getHasName(waxedReq), has(unwaxedReq));
            }

            builder.save(recipes, blockId(waxed).withSuffix("_from_honeycomb"));
        }
    }

    private void stonecutterRecipe(Block ingredient, Block result, int resultCount, Consumer<FinishedRecipe> recipes) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, resultCount)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipes, blockId(result).withSuffix("_from_" + blockId(ingredient).getPath()));
    }

    private void coralinePrismarine(ItemLike coral, ItemLike supplement, Block result, Consumer<FinishedRecipe> recipes) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('P', Blocks.PRISMARINE_BRICKS).define('C', coral).define('S', supplement)
                .pattern(" P ")
                .pattern("SCS")
                .pattern(" P ")
                .unlockedBy("has_coral_fan", has(PEItemTags.CORAL_FANS))
                .unlockedBy(getHasName(Blocks.PRISMARINE_BRICKS), has(Blocks.PRISMARINE_BRICKS))
                .group("coraline_prismarine")
                .save(recipes);
    }

    private void gildedPrismarine(Item ingot, Block result, Consumer<FinishedRecipe> recipes) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('P', Blocks.DARK_PRISMARINE_SLAB).define('I', ingot)
                .pattern("P").pattern("I").pattern("P")
                .unlockedBy(getHasName(Blocks.DARK_PRISMARINE), has(Blocks.DARK_PRISMARINE))
                .unlockedBy(getHasName(Blocks.DARK_PRISMARINE_SLAB), has(Blocks.DARK_PRISMARINE_SLAB))
                .save(recipes);
    }

    private void wallShapedRecipe(ItemLike ingredient, ItemLike result, int resultCount, Consumer<FinishedRecipe> recipes) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', Blocks.DARK_PRISMARINE)
                .pattern("###").pattern("###")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipes);
    }

    private ResourceLocation blockId(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}

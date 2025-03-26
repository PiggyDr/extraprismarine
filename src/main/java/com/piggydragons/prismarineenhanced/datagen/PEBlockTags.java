package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class PEBlockTags extends TagsProvider<Block> {

    protected PEBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper efh) {
        super(output, Registries.BLOCK, provider, PrismarineEnhanced.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagAppender<Block> walls = this.tag(BlockTags.WALLS);
        for (RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            Block block = regBlock.get();
            if (block instanceof WallBlock) walls.add(ResourceKey.create(Registries.BLOCK, regBlock.getId())); // why is this a thing
        }

        TagAppender<Block> pickaxeMineable = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        TagAppender<Block> ironRequired = tag(BlockTags.NEEDS_IRON_TOOL);
        for (RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            ResourceKey<Block> key = key(regBlock.getId());
            pickaxeMineable.add(key);
            ironRequired.add(key);
        }
    }

    private ResourceKey<Block> key(ResourceLocation loc) {
        return ResourceKey.create(Registries.BLOCK, loc);
    }
}

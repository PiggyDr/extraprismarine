package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class PETagProvider extends TagsProvider<Block> {

    protected PETagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Registries.BLOCK, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagAppender<Block> walls = this.tag(BlockTags.WALLS);
        for (RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            Block block = regBlock.get();
            if (block instanceof WallBlock) walls.add(ResourceKey.create(Registries.BLOCK, regBlock.getId())); // why is this a thing
        }
    }
}

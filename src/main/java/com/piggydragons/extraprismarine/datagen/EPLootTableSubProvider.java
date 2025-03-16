package com.piggydragons.extraprismarine.datagen;

import com.piggydragons.extraprismarine.blocks.EPBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.Set;

public class EPLootTableSubProvider extends BlockLootSubProvider {

    protected EPLootTableSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for(RegistryObject<Block> regBlock : EPBlocks.BLOCKS.getEntries()) {
            Block block = regBlock.get();
            if (block instanceof SlabBlock) this.createSlabItemTable(block);
            else this.dropSelf(block);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EPBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}

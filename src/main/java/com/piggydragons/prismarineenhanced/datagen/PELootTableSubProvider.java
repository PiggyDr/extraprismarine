package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.blocks.PEBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class PELootTableSubProvider extends BlockLootSubProvider {

    protected PELootTableSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for(RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            Block block = regBlock.get();
            if (block instanceof SlabBlock) this.createSlabItemTable(block);
            else this.dropSelf(block);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PEBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}

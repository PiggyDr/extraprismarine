package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.blocks.PEBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PEBlockStateProvider extends BlockStateProvider {

    public PEBlockStateProvider(PackOutput output, ExistingFileHelper efh) {
        super(output, PrismarineEnhanced.MOD_ID, efh);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            Block block = regBlock.get();
            ResourceLocation blockId = regBlock.getId();

            if (block instanceof RotatedPillarBlock pBlock) axisBlock(pBlock, model(blockId), model(blockId.withSuffix("_horizontal")));
            else simpleBlock(block, models().getExistingFile(regBlock.getId()));

            simpleBlockItem(block, models().getExistingFile(regBlock.getId()));
        }
    }

    private ModelFile.ExistingModelFile model(ResourceLocation id) {
        return models().getExistingFile(id);
    }
}

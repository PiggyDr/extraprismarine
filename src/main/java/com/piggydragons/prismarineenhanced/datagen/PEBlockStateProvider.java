package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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
            else if (block instanceof SlabBlock sBlock) slabBlock(sBlock, baseBlockTexture(blockId, 5), baseBlockTexture(blockId, 5));
            else if (block instanceof StairBlock sBlock) stairsBlock(sBlock, ForgeRegistries.BLOCKS.getKey(sBlock.stateSupplier.get().getBlock()).withPrefix("block/"));
            else if (block instanceof WallBlock wBlock) wallBlock(wBlock, model(blockId.withSuffix("_post")), model(blockId.withSuffix("_side")), model(blockId.withSuffix("_side_tall")));
            else if (block instanceof TrapDoorBlock tBlock) trapdoorBlockWithRenderType(tBlock, blockId.withPrefix("block/"), true, "cutout_mipped");
            else simpleBlock(block, models().getExistingFile(regBlock.getId()));

            simpleBlockItem(block, models().getExistingFile(regBlock.getId()));
        }
    }

    private static ResourceLocation baseBlockTexture(ResourceLocation blockId, int extensionLength) {
        return baseBlockId(blockId, extensionLength).withPrefix("block/");
    }

    private static ResourceLocation baseBlockId(ResourceLocation blockId, int extensionLength) {
        return blockId.withPath(blockId.getPath().substring(0, blockId.getPath().length() - extensionLength));
    }

    private ModelFile.ExistingModelFile model(ResourceLocation id) {
        return models().getExistingFile(id);
    }
}

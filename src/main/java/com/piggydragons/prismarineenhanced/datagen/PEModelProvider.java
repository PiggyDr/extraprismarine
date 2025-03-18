package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PEModelProvider extends BlockModelProvider {

    public PEModelProvider(PackOutput output, ExistingFileHelper efh) {
        super(output, PrismarineEnhanced.MOD_ID, efh);
    }

    @Override
    protected void registerModels() {
        cubeAll("cut_prismarine", texLoc("cut_prismarine"));
        cubeBottomTop("reinforced_prismarine", texLoc("reinforced_prismarine_side"), texLoc("reinforced_prismarine_bottom"), texLoc("reinforced_prismarine_top"));
        cubeColumn("prismarine_pillar", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));
        cubeColumnHorizontal("prismarine_pillar_horizontal", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));
    }

    private ResourceLocation texLoc(String path) {
        return PrismarineEnhanced.loc("block/" + path);
    }
}

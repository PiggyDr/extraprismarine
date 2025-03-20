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

        cubeColumnConsistentHorizontal("prismarine_pillar", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));
        cubeColumnConsistentHorizontal("prismarine_pillar_horizontal", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));

        coralinePrismarine("brain");
        coralinePrismarine("bubble");
        coralinePrismarine("fire");
        coralinePrismarine("horn");
        coralinePrismarine("tube");
    }

    private void cubeColumnConsistent(String name, ResourceLocation side, ResourceLocation end) {
        withExistingParent(name, texLoc("cube_column_consistent")).texture("side", side).texture("end", end);
    }

    private void cubeColumnConsistentHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        withExistingParent(name, texLoc("cube_column_consistent_horizontal")).texture("side", side).texture("end", end);
    }

    private void coralinePrismarine(String coralType) {
        cubeColumn(coralType + "_coraline_prismarine", texLoc(coralType + "_coraline_prismarine"), texLoc("coraline_prismarine_end"));
    }

    private ResourceLocation texLoc(String path) {
        return PrismarineEnhanced.loc("block/" + path);
    }
}

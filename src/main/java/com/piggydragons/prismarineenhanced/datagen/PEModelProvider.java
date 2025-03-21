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

        wallPost("cut_prismarine_wall_post", texLoc("cut_prismarine"));
        wallSide("cut_prismarine_wall_side", texLoc("cut_prismarine"));
        wallSideTall("cut_prismarine_wall_side_tall", texLoc("cut_prismarine"));
        wallInventory("cut_prismarine_wall", texLoc("cut_prismarine"));

        cubeBottomTop("reinforced_prismarine", texLoc("reinforced_prismarine_side"), texLoc("reinforced_prismarine_bottom"), texLoc("reinforced_prismarine_top"));

        cubeColumnConsistentHorizontal("prismarine_pillar", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));
        cubeColumnConsistentHorizontal("prismarine_pillar_horizontal", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));

        coralinePrismarine("brain");
        coralinePrismarine("bubble");
        coralinePrismarine("fire");
        coralinePrismarine("horn");
        coralinePrismarine("tube");

        engravedPrismarine("sanguine");
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

    private void engravedPrismarine(String engraving) {
        cubeColumn(engraving + "_engraved_prismarine", texLoc(engraving + "_engraved_prismarine"), texLoc("coraline_prismarine_end"));
    }

    public static ResourceLocation texLoc(String path) {
        return PrismarineEnhanced.loc("block/" + path);
    }
}

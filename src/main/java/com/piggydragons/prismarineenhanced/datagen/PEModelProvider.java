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

        wall("cut_prismarine_wall", texLoc("cut_prismarine"));

        cubeBottomTop("reinforced_prismarine", texLoc("reinforced_prismarine_side"), texLoc("reinforced_prismarine_bottom"), texLoc("reinforced_prismarine_top"));

        sbtWall("reinforced_prismarine_wall_post", "template_wall_sbt_post", "reinforced_prismarine_side", "reinforced_prismarine_bottom", "reinforced_prismarine_top");
        sbtWall("reinforced_prismarine_wall_side", "template_wall_sbt_side", "reinforced_prismarine_side", "reinforced_prismarine_bottom", "reinforced_prismarine_top");
        sbtWall("reinforced_prismarine_wall_side_tall", "template_wall_sbt_side_tall", "reinforced_prismarine_side", "reinforced_prismarine_bottom", "reinforced_prismarine_top");
        sbtWall("reinforced_prismarine_wall", "wall_sbt_inventory", "reinforced_prismarine_side", "reinforced_prismarine_bottom", "reinforced_prismarine_top");

        wall("dark_prismarine_wall", mcLoc("block/dark_prismarine"));

        cubeColumnConsistentHorizontal("prismarine_pillar", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));
        cubeColumnConsistentHorizontal("prismarine_pillar_horizontal", texLoc("prismarine_pillar_side"), texLoc("prismarine_pillar_end"));

        coralinePrismarine("brain");
        coralinePrismarine("bubble");
        coralinePrismarine("fire");
        coralinePrismarine("horn");
        coralinePrismarine("tube");

        cubeColumn("gilded_dark_prismarine", texLoc("gilded_dark_prismarine"), texLoc("reinforced_prismarine_bottom"));
        copperPrismarine("");
        copperPrismarine("exposed_");
        copperPrismarine("weathered_");
        copperPrismarine("oxidized_");

        trapdoorBottom("prismarine_trapdoor", texLoc("prismarine_trapdoor"));
        trapdoorTop("prismarine_trapdoor_top", texLoc("prismarine_trapdoor"));
        trapdoorOpen("prismarine_trapdoor_open", texLoc("prismarine_trapdoor"));

        // item models so i dont have to change the blockstate code
        itemLattice("copper_lattice");
        itemLattice("exposed_copper_lattice");
        itemLattice("weathered_copper_lattice");
        itemLattice("oxidized_copper_lattice");

        engravedPrismarine("sanguine");
    }

    private void wall(String name, ResourceLocation texture) {
        wallPost(name + "_post", texture);
        wallSide(name + "_side", texture);
        wallSideTall(name + "_side_tall", texture);
        wallInventory(name, texture);
    }

    // TODO investigate replacing this with cube_column_mirrored
    private void cubeColumnConsistentHorizontal(String name, ResourceLocation side, ResourceLocation end) {
        withExistingParent(name, texLoc("cube_column_consistent_horizontal")).texture("side", side).texture("end", end);
    }

    private void coralinePrismarine(String coralType) {
        cubeColumn(coralType + "_coraline_prismarine", texLoc(coralType + "_coraline_prismarine"), texLoc("coraline_prismarine_end"));
    }

    private void engravedPrismarine(String engraving) {
        cubeColumn(engraving + "_engraved_prismarine", texLoc(engraving + "_engraved_prismarine"), texLoc("coraline_prismarine_end"));
    }

    private void copperPrismarine(String weatherState) {
        cubeColumn(weatherState + "copper_gilded_dark_prismarine", texLoc(weatherState + "copper_gilded_dark_prismarine"), texLoc("reinforced_prismarine_bottom"));
        cubeColumn("waxed_" + weatherState + "copper_gilded_dark_prismarine", texLoc(weatherState + "copper_gilded_dark_prismarine"), texLoc("reinforced_prismarine_bottom"));
    }

    private void itemLattice(String id) {
        withExistingParent(id, "item/generated").texture("layer0", texLoc(id));
        withExistingParent("waxed_" + id, "item/generated").texture("layer0", texLoc(id));
    }

    private void sbtWall(String name, String model, String side, String bottom, String top) {
        withExistingParent(name, texLoc(model)).texture("side", texLoc(side)).texture("bottom", texLoc(bottom)).texture("top", texLoc(top));
    }

    public static ResourceLocation texLoc(String path) {
        return PrismarineEnhanced.loc("block/" + path);
    }
}

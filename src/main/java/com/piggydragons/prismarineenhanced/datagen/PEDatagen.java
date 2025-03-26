package com.piggydragons.prismarineenhanced.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class PEDatagen {

    public static void runDatagen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper efh = event.getExistingFileHelper();

        // client
        generator.addProvider(event.includeClient(), new PEModelProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new PEBlockStateProvider(packOutput, efh));
        generator.addProvider(event.includeClient(), new PELangProvider(packOutput));

        // server
        generator.addProvider(event.includeServer(), new PETagProvider(packOutput, event.getLookupProvider()));
        generator.addProvider(event.includeServer(), new PERecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new LootTableProvider(
                packOutput,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        PELootTableSubProvider::new,
                        LootContextParamSets.BLOCK
                ))
        ));
    }
}

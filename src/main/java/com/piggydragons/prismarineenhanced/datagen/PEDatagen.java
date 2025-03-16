package com.piggydragons.prismarineenhanced.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class PEDatagen {

    public static void runDatagen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(event.includeServer(), new LootTableProvider(
                generator.getPackOutput(),
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        PELootTableSubProvider::new,
                        LootContextParamSets.BLOCK
                ))
        ));
    }
}

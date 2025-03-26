package com.piggydragons.prismarineenhanced;

import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class PEEvents {

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PrismarineEnhanced.MOD_ID)
    public static class ModBus {

        @SubscribeEvent
        public static void addTabItems(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                addItemsBefore(event, Blocks.PRISMARINE, PEBlocks.PRISMARINE_TRAPDOOR.get());

                addItemsBefore(event, Blocks.DARK_PRISMARINE,
                        PEBlocks.CUT_PRISMARINE.get(),
                        PEBlocks.CUT_PRISMARINE_STAIRS.get(),
                        PEBlocks.CUT_PRISMARINE_SLAB.get(),
                        PEBlocks.CUT_PRISMARINE_WALL.get(),
                        PEBlocks.PRISMARINE_PILLAR.get(),
                        PEBlocks.REINFORCED_PRISMARINE.get(),
                        PEBlocks.REINFORCED_PRISMARINE_WALL.get()
                );

                addItemsBefore(event, Blocks.NETHERRACK,
                        Arrays.stream(WeatheringCopper.WeatherState.values())
                                .map(PEBlocks.COPPER_GILDED_DARK_PRISMARINE::getWaxedBlock)
                                .toArray(ItemLike[]::new));
                addItemsBefore(event, Blocks.NETHERRACK,
                        Arrays.stream(WeatheringCopper.WeatherState.values())
                                .map(PEBlocks.COPPER_GILDED_DARK_PRISMARINE::getWaxedBlock)
                                .toArray(ItemLike[]::new));
                addItemsBefore(event, Blocks.NETHERRACK, PEBlocks.GILDED_DARK_PRISMARINE.get());

                addItemsAfter(event, Blocks.CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getClean());
                addItemsAfter(event, Blocks.EXPOSED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getExposed());
                addItemsAfter(event, Blocks.WEATHERED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getWeathered());
                addItemsAfter(event, Blocks.OXIDIZED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getOxidized());
                addItemsAfter(event, Blocks.WAXED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getWaxedClean());
                addItemsAfter(event, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getWaxedExposed());
                addItemsAfter(event, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getWaxedWeathered());
                addItemsAfter(event, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, PEBlocks.COPPER_LATTICES.getWaxedOxidized());
            }
        }

        private static void addItemsAfter(BuildCreativeModeTabContentsEvent event, ItemLike find, ItemLike... addIn) {
            ItemStack findStack = find.asItem().getDefaultInstance();
            List<ItemLike> add = new ArrayList<>(Arrays.stream(addIn).toList());
            Collections.reverse(add);
            add.forEach(i -> event.getEntries().putAfter(findStack, i.asItem().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
        }

        private static void addItemsBefore(BuildCreativeModeTabContentsEvent event, ItemLike find, ItemLike... addIn) {
            ItemStack findStack = find.asItem().getDefaultInstance();
            for (ItemLike item : addIn) {
                event.getEntries().putBefore(findStack, item.asItem().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
        }
    }
}

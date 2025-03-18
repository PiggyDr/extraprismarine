package com.piggydragons.prismarineenhanced;

import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PEEvents {

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PrismarineEnhanced.MOD_ID)
    public static class ModBus {

        @SubscribeEvent
        public static void addTabItems(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
                PEBlocks.BLOCKS.getEntries().forEach(event::accept);
            }
        }
    }
}

package com.piggydragons.prismarineenhanced;

import com.mojang.logging.LogUtils;
import com.piggydragons.prismarineenhanced.datagen.PEDatagen;
import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import com.piggydragons.prismarineenhanced.registries.PEItems;
import com.piggydragons.prismarineenhanced.registries.PETab;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PrismarineEnhanced.MOD_ID)
public class PrismarineEnhanced {

    public static final String MOD_ID = "prismarine_enhanced";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrismarineEnhanced(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        PEBlocks.BLOCKS.register(bus);
        PEItems.ITEMS.register(bus);
        PETab.TABS.register(bus);

        bus.addListener(PEDatagen::runDatagen);
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

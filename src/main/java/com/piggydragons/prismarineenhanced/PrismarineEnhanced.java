package com.piggydragons.prismarineenhanced;

import com.mojang.logging.LogUtils;
import com.piggydragons.prismarineenhanced.blocks.PEBlocks;
import com.piggydragons.prismarineenhanced.blocks.PEItems;
import com.piggydragons.prismarineenhanced.datagen.PEDatagen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PrismarineEnhanced.MOD_ID)
public class PrismarineEnhanced {

    public static final String MOD_ID = "prismarine_enhanced";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PrismarineEnhanced(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        PEBlocks.BLOCKS.register(bus);
        PEItems.ITEMS.register(bus);
        bus.addListener(PEDatagen::runDatagen);
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}

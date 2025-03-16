package com.piggydragons.extraprismarine;

import com.mojang.logging.LogUtils;
import com.piggydragons.extraprismarine.blocks.EPBlocks;
import com.piggydragons.extraprismarine.datagen.EPDatagen;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ExtraPrismarine.MOD_ID)
public class ExtraPrismarine {

    public static final String MOD_ID = "extraprismarine";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExtraPrismarine(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();

        EPBlocks.BLOCKS.register(bus);
        bus.addListener(EPDatagen::runDatagen);
    }
}

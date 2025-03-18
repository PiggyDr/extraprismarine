package com.piggydragons.prismarineenhanced.registries;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PETab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PrismarineEnhanced.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register(PrismarineEnhanced.MOD_ID, () -> CreativeModeTab.builder()
            .displayItems((parameters,builder) ->
                PEBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(builder::accept))
            .icon(PEBlocks.CUT_PRISMARINE.get().asItem()::getDefaultInstance)
            .title(Component.translatable("itemGroup.prismarineEnhanced"))
            .build());
}

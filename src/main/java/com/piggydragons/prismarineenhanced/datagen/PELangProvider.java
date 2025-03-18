package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.registries.PEBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class PELangProvider extends LanguageProvider {

    public PELangProvider(PackOutput output) {
        super(output, PrismarineEnhanced.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.prismarineEnhanced", "Prismarine Enhanced");

        for (RegistryObject<Block> regBlock : PEBlocks.BLOCKS.getEntries()) {
            if (!this.data.containsKey(regBlock.get().getDescriptionId()))
                addBlock(regBlock, nameFromId(regBlock.getId().getPath()));
        }
    }

    private String nameFromId(String id) {
        StringBuilder builder = new StringBuilder();
        for (String segment : id.split("_")) {
            builder.append(segment.substring(0, 1).toUpperCase());
            builder.append(segment.substring(1));
            builder.append(" ");
        }
        builder.deleteCharAt(id.length());
        return builder.toString();
    }
}

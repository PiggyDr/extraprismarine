package com.piggydragons.prismarineenhanced.datagen;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class PEItemTags extends TagsProvider<Item> {

    public static final TagKey<Item> CORAL_FANS = ItemTags.create(PrismarineEnhanced.loc("coral_fans"));

    protected PEItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper efh) {
        super(output, Registries.ITEM, provider, PrismarineEnhanced.MOD_ID, efh);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CORAL_FANS)
                .add(key(Items.BRAIN_CORAL_FAN))
                .add(key(Items.BUBBLE_CORAL_FAN))
                .add(key(Items.FIRE_CORAL_FAN))
                .add(key(Items.HORN_CORAL_FAN))
                .add(key(Items.TUBE_CORAL_FAN));
    }

    private ResourceKey<Item> key(ItemLike item) {
        return key(ForgeRegistries.ITEMS.getKey(item.asItem()));
    }

    private ResourceKey<Item> key(String namespace, String path) {
        return key(new ResourceLocation(namespace, path));
    }

    private ResourceKey<Item> key(ResourceLocation loc) {
        return ResourceKey.create(registryKey, loc);
    }
}

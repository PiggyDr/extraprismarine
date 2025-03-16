package com.piggydragons.extraprismarine.blocks;

import com.piggydragons.extraprismarine.ExtraPrismarine;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class EPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtraPrismarine.MOD_ID);
    private static final BlockBehaviour.Properties PRISMARINE_PROP = BlockBehaviour.Properties.copy(Blocks.PRISMARINE);

    public static final RegistryObject<ConduitFrameBlock> CUT_PRISMARINE = prismarineBlock("cut_prismarine");
    public static final RegistryObject<ConduitFrameBlock> REINFORCED_PRISMARINE = prismarineBlock("reinforced_prismarine");
    public static final RegistryObject<ConduitFrameBlock.Pillar> PRISMARINE_PILLAR = prismarineBlock("prismarine_pillar", ConduitFrameBlock.Pillar::new);

    private static RegistryObject<ConduitFrameBlock> prismarineBlock(String id) {
        return prismarineBlock(id, ConduitFrameBlock::new);
    }

    private static <T extends Block> RegistryObject<T> prismarineBlock(String id, Function<BlockBehaviour.Properties, T> constructor) {
        return blockItem(id, () -> constructor.apply(PRISMARINE_PROP));
    }

    private static <T extends Block> RegistryObject<T> blockItem(String id, Supplier<T> sup) {
        RegistryObject<T> block = BLOCKS.register(id, sup);
        EPItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}

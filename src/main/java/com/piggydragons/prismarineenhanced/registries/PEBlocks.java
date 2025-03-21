package com.piggydragons.prismarineenhanced.registries;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.blocks.ConduitFrameBlock;
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

public class PEBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PrismarineEnhanced.MOD_ID);
    private static final BlockBehaviour.Properties PRISMARINE_PROP = BlockBehaviour.Properties.copy(Blocks.PRISMARINE);

    public static final RegistryObject<ConduitFrameBlock> CUT_PRISMARINE = prismarineBlock("cut_prismarine");
    public static final RegistryObject<ConduitFrameBlock.Slab> CUT_PRISMARINE_SLAB = prismarineBlock("cut_prismarine_slab", ConduitFrameBlock.Slab::new);
    public static final RegistryObject<ConduitFrameBlock.Stairs> CUT_PRISMARINE_STAIRS = prismarineStairs(CUT_PRISMARINE);
    public static final RegistryObject<ConduitFrameBlock.Wall> CUT_PRISMARINE_WALL = prismarineBlock("cut_prismarine_wall", ConduitFrameBlock.Wall::new);

    public static final RegistryObject<ConduitFrameBlock> REINFORCED_PRISMARINE = prismarineBlock("reinforced_prismarine");
    public static final RegistryObject<ConduitFrameBlock.Wall> REINFORCED_PRISMARINE_WALL = prismarineBlock("reinforced_prismarine_wall", ConduitFrameBlock.Wall::new);

    public static final RegistryObject<ConduitFrameBlock.Pillar> PRISMARINE_PILLAR = prismarineBlock("prismarine_pillar", ConduitFrameBlock.Pillar::new);

    public static final RegistryObject<ConduitFrameBlock> BRAIN_CORALINE_PRISMARINE = prismarineBlock("brain_coraline_prismarine");
    public static final RegistryObject<ConduitFrameBlock> BUBBLE_CORALINE_PRISMARINE = prismarineBlock("bubble_coraline_prismarine");
    public static final RegistryObject<ConduitFrameBlock> FIRE_CORALINE_PRISMARINE = prismarineBlock("fire_coraline_prismarine");
    public static final RegistryObject<ConduitFrameBlock> HORN_CORALINE_PRISMARINE = prismarineBlock("horn_coraline_prismarine");
    public static final RegistryObject<ConduitFrameBlock> TUBE_CORALINE_PRISMARINE = prismarineBlock("tube_coraline_prismarine");

    public static final RegistryObject<ConduitFrameBlock> SANGUINE_ENGRAVED_PRISMARINE = prismarineBlock("sanguine_engraved_prismarine");

    private static RegistryObject<ConduitFrameBlock> prismarineBlock(String id) {
        return prismarineBlock(id, ConduitFrameBlock::new);
    }

    private static <T extends Block> RegistryObject<T> prismarineBlock(String id, Function<BlockBehaviour.Properties, T> constructor) {
        return blockItem(id, () -> constructor.apply(PRISMARINE_PROP));
    }

    private static RegistryObject<ConduitFrameBlock.Stairs> prismarineStairs(RegistryObject<? extends Block> original) {
        return blockItem(original.getId().getPath() + "_stairs", () -> new ConduitFrameBlock.Stairs(original.get(), PRISMARINE_PROP));
    }

    private static <T extends Block> RegistryObject<T> blockItem(String id, Supplier<T> sup) {
        RegistryObject<T> block = BLOCKS.register(id, sup);
        PEItems.ITEMS.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }
}

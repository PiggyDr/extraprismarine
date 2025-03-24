package com.piggydragons.prismarineenhanced.registries;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.blocks.ConduitFrameBlock;
import com.piggydragons.prismarineenhanced.blocks.WeatheringPrismarineBlock;
import net.minecraft.core.Holder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
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

    // public static final RegistryObject<ConduitFrameBlock> SANGUINE_ENGRAVED_PRISMARINE = prismarineBlock("sanguine_engraved_prismarine");

    public static final WeatheringGroup COPPER_GILDED_DARK_PRISMARINE = new WeatheringGroup("copper_gilded_dark_prismarine", PRISMARINE_PROP);

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

    public static class WeatheringGroup {

        // i should probably be using optionals here but since the point of these is just to get a nice stable reference object i think it is fine
        private Holder<RegistryObject<WeatheringPrismarineBlock>> clean = Holder.direct(null);
        private Holder<RegistryObject<WeatheringPrismarineBlock>> exposed = Holder.direct(null);
        private Holder<RegistryObject<WeatheringPrismarineBlock>> weathered = Holder.direct(null);
        private Holder<RegistryObject<WeatheringPrismarineBlock>> oxidized = Holder.direct(null);

        private final Map<WeatherState, Holder<RegistryObject<WeatheringPrismarineBlock>>> blockByState = Map.of(
                WeatherState.UNAFFECTED, clean,
                WeatherState.EXPOSED, exposed,
                WeatherState.WEATHERED, weathered,
                WeatherState.OXIDIZED, oxidized
        );

        public WeatheringGroup(String id, BlockBehaviour.Properties p) {
            clean = Holder.direct(blockItem(id, () -> new WeatheringPrismarineBlock(WeatherState.UNAFFECTED, null, exposed.get()::get, p)));
            exposed = Holder.direct(blockItem("exposed_" + id, () -> new WeatheringPrismarineBlock(WeatherState.EXPOSED, clean.get()::get, weathered.get()::get, p)));
            weathered = Holder.direct(blockItem("weathered_" + id, () -> new WeatheringPrismarineBlock(WeatherState.WEATHERED, exposed.get()::get, oxidized.get()::get, p)));
            oxidized = Holder.direct(blockItem("oxidized_" + id, () -> new WeatheringPrismarineBlock(WeatherState.OXIDIZED, weathered.get()::get, null, p)));
        }

        public WeatheringPrismarineBlock getBlock(WeatherState weatherState) {
            return blockByState.get(weatherState).get().get();
        }

        public WeatheringPrismarineBlock getClean() { return clean.get().get(); }
        public WeatheringPrismarineBlock getExposed() { return exposed.get().get(); }
        public WeatheringPrismarineBlock getWeathered() { return weathered.get().get(); }
        public WeatheringPrismarineBlock getOxidized() { return oxidized.get().get(); }
    }
}

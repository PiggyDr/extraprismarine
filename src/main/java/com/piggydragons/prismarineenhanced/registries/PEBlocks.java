package com.piggydragons.prismarineenhanced.registries;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import com.piggydragons.prismarineenhanced.blocks.ConduitFrameBlock;
import com.piggydragons.prismarineenhanced.blocks.ConduitFrameBlock.Weathering;
import com.piggydragons.prismarineenhanced.blocks.CopperLatticeBlock;
import com.piggydragons.prismarineenhanced.blocks.PEWaxedWeatheringCopper;
import com.piggydragons.prismarineenhanced.blocks.PEWeatheringCopper;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    public static final RegistryObject<ConduitFrameBlock> GILDED_DARK_PRISMARINE = prismarineBlock("gilded_dark_prismarine");
    public static final WeatheringGroup<ConduitFrameBlock.Weathering, ConduitFrameBlock.Waxed> COPPER_GILDED_DARK_PRISMARINE =
            new WeatheringGroup<>("copper_gilded_dark_prismarine", PRISMARINE_PROP, Weathering::new, ConduitFrameBlock.Waxed::new);

    public static final WeatheringGroup<CopperLatticeBlock, CopperLatticeBlock.Waxed> COPPER_LATTICES =
            new WeatheringGroup<>("copper_lattice", BlockBehaviour.Properties.of()
                    .strength(0.9F).sound(SoundType.CHAIN).requiresCorrectToolForDrops().noOcclusion(),
                    CopperLatticeBlock::new, CopperLatticeBlock.Waxed::new);

    public static final BlockSetType PRISMARINE_BLOCK_SET = BlockSetType.register(new BlockSetType(
            "prismarine", true, SoundType.METAL,
            SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN,
            SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN,
            SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON
    ));

    public static final RegistryObject<ConduitFrameBlock.Trapdoor> PRISMARINE_TRAPDOOR =
            prismarineBlock("prismarine_trapdoor", p -> new ConduitFrameBlock.Trapdoor(p.noOcclusion(), PRISMARINE_BLOCK_SET));



    // methods

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

    public static class WeatheringGroup<T extends Block, W extends Block> {

        // i should probably be using optionals here but since the point of these is just to get a nice stable reference object i think it is fine
        private Holder<RegistryObject<T>> clean = Holder.direct(null);
        private Holder<RegistryObject<T>> exposed = Holder.direct(null);
        private Holder<RegistryObject<T>> weathered = Holder.direct(null);
        private Holder<RegistryObject<T>> oxidized = Holder.direct(null);

        private Holder<RegistryObject<W>> cleanWaxed = Holder.direct(null);
        private Holder<RegistryObject<W>> exposedWaxed = Holder.direct(null);
        private Holder<RegistryObject<W>> weatheredWaxed = Holder.direct(null);
        private Holder<RegistryObject<W>> oxidizedWaxed = Holder.direct(null);

        private final Map<WeatherState, Holder<RegistryObject<T>>> blockByState;

        private final Map<WeatherState, Holder<RegistryObject<W>>> waxedByState;

        public WeatheringGroup(String id, BlockBehaviour.Properties p, WeatheringBlockConstructor<T> constructor, WaxedWeatheringBlockConstructor<W> waxedConstructor) {

            // most of the blocks referenced in these constructors have not been registered yet so we have to use holders and suppliers and stuff
            clean = Holder.direct(blockItem(id, () -> constructor.create(WeatherState.UNAFFECTED, null, exposed.get()::get, cleanWaxed.get(), p)));
            exposed = Holder.direct(blockItem("exposed_" + id, () -> constructor.create(WeatherState.EXPOSED, clean.get()::get, weathered.get()::get, exposedWaxed.get(), p)));
            weathered = Holder.direct(blockItem("weathered_" + id, () -> constructor.create(WeatherState.WEATHERED, exposed.get()::get, oxidized.get()::get, weatheredWaxed.get(), p)));
            oxidized = Holder.direct(blockItem("oxidized_" + id, () -> constructor.create(WeatherState.OXIDIZED, weathered.get()::get, null, oxidizedWaxed.get(), p)));

            // unwaxed variants have already been registered at this point so there is no need for suppliers
            cleanWaxed = Holder.direct(blockItem("waxed_" + id, () -> waxedConstructor.create(clean.get().get(), p)));
            exposedWaxed = Holder.direct(blockItem("waxed_exposed_" + id, () -> waxedConstructor.create(exposed.get().get(), p)));
            weatheredWaxed = Holder.direct(blockItem("waxed_weathered_" + id, () -> waxedConstructor.create(weathered.get().get(), p)));
            oxidizedWaxed = Holder.direct(blockItem("waxed_oxidized_" + id, () -> waxedConstructor.create(oxidized.get().get(), p)));

            // need to create maps in constructor so they dont reference the null holders
            blockByState = Map.of(
                    WeatherState.UNAFFECTED, clean,
                    WeatherState.EXPOSED, exposed,
                    WeatherState.WEATHERED, weathered,
                    WeatherState.OXIDIZED, oxidized
            );

            waxedByState = Map.of(
                    WeatherState.UNAFFECTED, cleanWaxed,
                    WeatherState.EXPOSED, exposedWaxed,
                    WeatherState.WEATHERED, weatheredWaxed,
                    WeatherState.OXIDIZED, oxidizedWaxed
            );

        }

        public T getUnwaxedBlock(WeatherState weatherState) {
            return blockByState.get(weatherState).get().get();
        }

        public T getClean() { return clean.get().get(); }
        public T getExposed() { return exposed.get().get(); }
        public T getWeathered() { return weathered.get().get(); }
        public T getOxidized() { return oxidized.get().get(); }

        public W getWaxedBlock(WeatherState weatherState) {
            return waxedByState.get(weatherState).get().get();
        }

        public W getWaxedClean() { return cleanWaxed.get().get(); }
        public W getWaxedExposed() { return exposedWaxed.get().get(); }
        public W getWaxedWeathered() { return weatheredWaxed.get().get(); }
        public W getWaxedOxidized() { return oxidizedWaxed.get().get(); }
    }

    public interface WeatheringBlockConstructor<T extends Block> {
        T create(WeatherState weatherState, @Nullable NonNullSupplier<Block> previous, @Nullable NonNullSupplier<Block> next, Supplier<? extends Block> waxed, BlockBehaviour.Properties p);
    }

    public interface WaxedWeatheringBlockConstructor<T extends Block> {
        T create(Block unwaxed, BlockBehaviour.Properties p);
    }
}

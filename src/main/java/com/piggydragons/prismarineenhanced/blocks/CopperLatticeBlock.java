package com.piggydragons.prismarineenhanced.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class CopperLatticeBlock extends IronBarsBlock implements PEWeatheringCopper {

    private final WeatherState weatherState;

    private final LazyOptional<Block> previous;
    private final LazyOptional<Block> next;
    private final Supplier<? extends Block> waxed;

    public CopperLatticeBlock(WeatherState weatherState, @Nullable NonNullSupplier<Block> previous, @Nullable NonNullSupplier<Block> next, Supplier<? extends Block> waxed, Properties p) {
        super(p);
        this.previous = previous == null ? LazyOptional.empty() : LazyOptional.of(previous);
        this.next = next == null ? LazyOptional.empty() : LazyOptional.of(next);
        this.waxed = waxed;
        this.weatherState = weatherState;
    }

    @Override
    public LazyOptional<Block> getNextRaw() {
        return next;
    }

    @Override
    public LazyOptional<Block> getPreviousRaw() {
        return previous;
    }

    @Override
    public Optional<BlockState> getNext(BlockState current) {
        return PEWeatheringCopper.super.getNext(current).map(bs -> copyProperties(current, bs));
    }

    @Override
    public Optional<BlockState> getPrevious(BlockState current) {
        return PEWeatheringCopper.super.getPrevious(current).map(bs -> copyProperties(current, bs));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack item = player.getItemInHand(hand);

        if (item.is(Items.HONEYCOMB)) {

            // important
            level.setBlock(pos, waxedWithSameProperties(blockState), Block.UPDATE_ALL_IMMEDIATE);
            if (!player.getAbilities().instabuild) player.getItemInHand(hand).shrink(1);

            // unimportant
            if (player instanceof ServerPlayer sp)
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, item);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockState));
            level.levelEvent(player, 3003, pos, 0);

            return InteractionResult.SUCCESS;
        } else {
            return super.use(blockState, level, pos, player, hand, hitResult);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState current) {
        return this.getNext(current).isPresent();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        onRandomTick(blockState, level, pos, random);
        if (isSubmerged(level, pos, blockState))
            onRandomTick(blockState, level, pos, random); // oxidize faster underwater
    }

    private boolean isSubmerged(ServerLevel level, BlockPos pos, BlockState blockState) {
        return blockState.getValue(WATERLOGGED) || Arrays.stream(Direction.values())
                .anyMatch(dir -> level.getBlockState(pos.relative(dir)).getFluidState().is(FluidTags.WATER));
    }

    @Override
    public WeatherState getAge() {
        return weatherState;
    }

    private BlockState waxedWithSameProperties(BlockState current) {
        return copyProperties(waxed.get().defaultBlockState(), current);
    }

    // i swear i've figured out some way to do this in the past that doesnt involve janky generics shenanigans or try/catch nonsense but right now the solution eludes me
    private static BlockState copyProperties(BlockState from, BlockState to) {
        for (Property<?> prop : from.getProperties()) {
            if (to.hasProperty(prop))
                to = copyProperty(prop, from, to);
        }
        return to;
    }

    private static  <T extends Comparable<T>> BlockState copyProperty(Property<T> prop, BlockState from, BlockState to) {
        return to.setValue(prop, from.getValue(prop));
    }

    public static class Waxed extends IronBarsBlock implements PEWaxedWeatheringCopper {

        private final Block unwaxed;

        public Waxed(Block unwaxed, Properties p) {
            super(p);
            this.unwaxed = unwaxed;
        }

        @Override
        public BlockState getUnwaxed(BlockState waxed) {
            return copyProperties(waxed, unwaxed.defaultBlockState());
        }
    }
}

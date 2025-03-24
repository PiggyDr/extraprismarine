package com.piggydragons.prismarineenhanced.blocks;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

public class WeatheringPrismarineBlock extends Block implements WeatheringCopper {

    private final WeatherState weatherState;

    private final LazyOptional<Block> previous;
    private final LazyOptional<Block> next;
//    private final @Nullable Block waxed;

    public WeatheringPrismarineBlock(WeatherState weatherState, @Nullable NonNullSupplier<Block> previous, @Nullable NonNullSupplier<Block> next, Properties p) {
        super(p);
        this.previous = previous == null ? LazyOptional.empty() : LazyOptional.of(previous);
        this.next = next == null ? LazyOptional.empty() : LazyOptional.of(next);
        PrismarineEnhanced.LOGGER.debug("previous: {} next: {}", previous, next);
//        this.waxed = waxed;
        this.weatherState = weatherState;
    }

    public Optional<BlockState> getNext(BlockState current) {
        PrismarineEnhanced.LOGGER.debug("getNext: {}", next.resolve().map(Block::defaultBlockState));
        return next.resolve().map(Block::defaultBlockState);
    }

    public Optional<BlockState> getPrevious() {
        return previous.resolve().map(Block::defaultBlockState);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_SCRAPE ? this.getPrevious().orElse(null) : super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Override
    public boolean isRandomlyTicking(BlockState current) {
        return this.getNext(current).isPresent();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        onRandomTick(blockState, level, pos, random);
    }

    @Override
    public WeatherState getAge() {
        return weatherState;
    }
}

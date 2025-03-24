package com.piggydragons.prismarineenhanced.blocks;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.Optional;

public interface PEWeatheringCopper extends WeatheringCopper, IForgeBlock {

    LazyOptional<Block> getNextRaw();
    LazyOptional<Block> getPreviousRaw();

    @Override
    default Optional<BlockState> getNext(BlockState current) {
        return getNextRaw().resolve().map(Block::defaultBlockState);
    }

    default Optional<BlockState> getPrevious() {
        return getPreviousRaw().resolve().map(Block::defaultBlockState);
    }

    @Override
    default @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_SCRAPE ? this.getPrevious().orElse(null) : IForgeBlock.super.getToolModifiedState(state, context, toolAction, simulate);
    }
}

package com.piggydragons.prismarineenhanced.blocks;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

public interface PEWaxedWeatheringCopper extends IForgeBlock {

    @Override
    default @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_WAX_OFF ? getUnwaxed(state) : IForgeBlock.super.getToolModifiedState(state, context, toolAction, simulate);
    }

    BlockState getUnwaxed(BlockState waxed);
}

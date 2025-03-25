package com.piggydragons.prismarineenhanced.blocks;

import com.piggydragons.prismarineenhanced.PrismarineEnhanced;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class ConduitFrameBlock extends Block implements IConduitFrameBlock {

    public ConduitFrameBlock(Properties p) {
        super(p);
    }

    public static class Pillar extends RotatedPillarBlock implements IConduitFrameBlock {
        public Pillar(Properties p) { super(p); }
    }

    public static class Stairs extends StairBlock implements IConduitFrameBlock {
        public Stairs(Block fullBlock, Properties p) { super(fullBlock::defaultBlockState, p); }
    }

    public static class Slab extends SlabBlock implements IConduitFrameBlock {
        public Slab(Properties p) { super(p); }
    }

    public static class Wall extends WallBlock implements IConduitFrameBlock {
        public Wall(Properties p) { super(p); }
    }

    public static class Trapdoor extends TrapDoorBlock implements IConduitFrameBlock {
        public Trapdoor(Properties p, BlockSetType blockSetType) { super(p, blockSetType); }
    }

    public static class Weathering extends Block implements PEWeatheringCopper, IConduitFrameBlock {
        private final WeatherState weatherState;

        private final LazyOptional<Block> previous;
        private final LazyOptional<Block> next;
        private final Supplier<? extends Block> waxed;

        public Weathering(WeatherState weatherState, @Nullable NonNullSupplier<Block> previous, @Nullable NonNullSupplier<Block> next, Supplier<? extends Block> waxed,  Properties p) {
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
        public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
            ItemStack item = player.getItemInHand(hand);

            if (item.is(Items.HONEYCOMB)) {

                // important
                level.setBlock(pos, waxed.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
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
            if (isSubmerged(level, pos))
                onRandomTick(blockState, level, pos, random); // oxidize faster underwater
        }

        private boolean isSubmerged(ServerLevel level, BlockPos pos) {
            return Arrays.stream(Direction.values())
                    .anyMatch(dir -> level.getBlockState(pos.relative(dir)).getFluidState().is(FluidTags.WATER));
        }

        @Override
        public WeatherState getAge() {
            return weatherState;
        }
    }

    public static class Waxed extends Block implements PEWaxedWeatheringCopper, IConduitFrameBlock {

        private final Block unwaxed;

        public Waxed(Block unwaxed, Properties p) {
            super(p);
            this.unwaxed = unwaxed;
        }

        @Override
        public BlockState getUnwaxed(BlockState waxed) {
            return unwaxed.defaultBlockState();
        }
    }
}

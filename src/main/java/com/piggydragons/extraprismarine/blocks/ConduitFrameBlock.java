package com.piggydragons.extraprismarine.blocks;

import net.minecraft.world.level.block.*;

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
}

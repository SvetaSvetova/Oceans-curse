package com.oceanscurse.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Palm sapling — like a normal sapling, but it also takes root on sand (palms grow on beaches and
 * in deserts). Plain saplings only survive on {@code SUPPORTS_VEGETATION} (dirt/grass), which is why
 * the default sapling couldn't be planted on sand.
 */
public class PalmSaplingBlock extends SaplingBlock {
    public PalmSaplingBlock(final TreeGrower treeGrower, final BlockBehaviour.Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.SAND);
    }
}

package com.oceanscurse.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A small cluster of coconuts. Hangs under palm leaves (placed by the palm tree's
 * {@code attached_to_leaves} decorator) and drops coconut food when broken.
 */
public class CoconutsBlock extends Block {
    public static final MapCodec<CoconutsBlock> CODEC = simpleCodec(CoconutsBlock::new);

    private static final VoxelShape SHAPE = Block.box(4.0, 3.0, 4.0, 12.0, 13.0, 12.0);

    public CoconutsBlock(final BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }
}

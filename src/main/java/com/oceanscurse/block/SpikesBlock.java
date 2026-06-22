package com.oceanscurse.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Spikes — a low electrified trap plate (crafted from the ray's electric spike). Anything standing on
 * it takes heavy damage each i-frame window, so it kills quickly: useful for mob farms and dungeon
 * traps. A short collision (you can walk onto it) with taller visual spikes that have no collision.
 */
public class SpikesBlock extends Block {
    public static final MapCodec<SpikesBlock> CODEC = simpleCodec(SpikesBlock::new);

    // Stand-on platform: full footprint, 4px tall (the visual spikes above it are non-colliding).
    private static final VoxelShape SHAPE = Block.column(16.0, 0.0, 4.0);
    private static final float DAMAGE = 6.0F;

    public SpikesBlock(final BlockBehaviour.Properties properties) {
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

    @Override
    protected VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void entityInside(final BlockState state, final Level level, final BlockPos pos, final Entity entity,
                                final InsideBlockEffectApplier effectApplier, final boolean isPrecise) {
        if (entity instanceof LivingEntity && level instanceof ServerLevel serverLevel) {
            entity.hurtServer(serverLevel, level.damageSources().stalagmite(), DAMAGE);
        }
    }
}

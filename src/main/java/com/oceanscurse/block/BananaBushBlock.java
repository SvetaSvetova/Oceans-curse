package com.oceanscurse.block;

import com.mojang.serialization.MapCodec;
import com.oceanscurse.OceansCurse;
import net.minecraft.world.item.Item;

public class BananaBushBlock extends FruitBushBlock {
    public static final MapCodec<BananaBushBlock> CODEC = simpleCodec(BananaBushBlock::new);

    public BananaBushBlock(final Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<BananaBushBlock> codec() {
        return CODEC;
    }

    @Override
    protected Item fruit() {
        return OceansCurse.BANANA.get();
    }
}

package com.oceanscurse.block;

import com.mojang.serialization.MapCodec;
import com.oceanscurse.OceansCurse;
import net.minecraft.world.item.Item;

public class PineappleBushBlock extends FruitBushBlock {
    public static final MapCodec<PineappleBushBlock> CODEC = simpleCodec(PineappleBushBlock::new);

    public PineappleBushBlock(final Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<PineappleBushBlock> codec() {
        return CODEC;
    }

    @Override
    protected Item fruit() {
        return OceansCurse.PINEAPPLE.get();
    }
}

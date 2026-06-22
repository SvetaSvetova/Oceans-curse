package com.oceanscurse.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.level.Level;

/**
 * Sawfish — a sea fish (reuses the vanilla cod's schooling/model with its own texture). Mostly here
 * for its drop: the saw blade, used to craft the Saw tool. (v0.4 fauna; the egg-guarding aggression
 * and a proper model come later.)
 */
public class SawfishEntity extends Cod {
    public SawfishEntity(final EntityType<? extends Cod> type, final Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractFish.createAttributes();
    }
}

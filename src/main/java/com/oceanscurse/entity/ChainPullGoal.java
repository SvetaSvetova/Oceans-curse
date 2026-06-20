package com.oceanscurse.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * Chain-pull: a cursed drowned holding a chain yanks its target toward itself from mid-range.
 *
 * Each pull begins with a short wind-up during which the chain is drawn as a particle line from the
 * mob to the target — so you can see it coming and dodge (move out of range or break line of sight
 * to cancel it). Runs in parallel with the melee goal — it drags you into reach for the others.
 */
public class ChainPullGoal extends Goal {
    private static final double MIN_RANGE = 3.0;
    private static final double MAX_RANGE = 11.0;
    private static final int PULL_INTERVAL = 45; // ticks of rest between pulls
    private static final int WINDUP_TICKS = 12;  // telegraph (~0.6s) before the yank
    private static final double PULL_STRENGTH = 1.15;
    private static final double PULL_LIFT = 0.4;
    private static final float CHAIN_DAMAGE = 2.0F;
    private static final int CHAIN_PARTICLES = 8;

    private final Mob mob;
    private int cooldown;
    private int windup;

    public ChainPullGoal(final Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        final LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive() || !this.mob.getMainHandItem().is(Items.IRON_CHAIN)) {
            return false;
        }
        final double dist = this.mob.distanceTo(target);
        return dist >= MIN_RANGE && dist <= MAX_RANGE && this.mob.getSensing().hasLineOfSight(target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.cooldown = PULL_INTERVAL / 2;
        this.windup = 0;
    }

    @Override
    public void stop() {
        this.cooldown = PULL_INTERVAL;
        this.windup = 0;
    }

    @Override
    public void tick() {
        final LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }
        this.mob.getLookControl().setLookAt(target);

        // Telegraph phase: draw the chain, then yank once it finishes.
        if (this.windup > 0) {
            this.drawChain(target);
            if (--this.windup == 0) {
                this.yank(target);
            }
            return;
        }

        if (this.cooldown-- > 0) {
            return;
        }

        // Begin a new pull: throw the chain (wind-up), so the target gets a moment to react.
        this.windup = WINDUP_TICKS;
        this.mob.level().playSound(null, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
            SoundEvents.CHAIN_PLACE, SoundSource.HOSTILE, 1.0F, 1.3F);
        this.drawChain(target);
    }

    private void yank(final LivingEntity target) {
        final Vec3 toMob = new Vec3(this.mob.getX() - target.getX(), 0.0, this.mob.getZ() - target.getZ()).normalize();
        target.setDeltaMovement(toMob.x * PULL_STRENGTH, PULL_LIFT, toMob.z * PULL_STRENGTH);
        target.hurtMarked = true; // make the server push the new velocity to the client

        if (this.mob.level() instanceof ServerLevel serverLevel) {
            target.hurtServer(serverLevel, this.mob.damageSources().mobAttack(this.mob), CHAIN_DAMAGE);
        }
        this.mob.level().playSound(null, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
            SoundEvents.CHAIN_HIT, SoundSource.HOSTILE, 1.0F, 0.7F);
        this.cooldown = PULL_INTERVAL;
    }

    /** Draws the chain as a line of particles from the mob's hands to the target. */
    private void drawChain(final LivingEntity target) {
        if (!(this.mob.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        final Vec3 from = new Vec3(this.mob.getX(), this.mob.getY() + this.mob.getBbHeight() * 0.7, this.mob.getZ());
        final Vec3 to = new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ());
        for (int i = 1; i <= CHAIN_PARTICLES; i++) {
            final double t = (double) i / (CHAIN_PARTICLES + 1);
            final Vec3 point = from.lerp(to, t);
            serverLevel.sendParticles(ParticleTypes.CRIT, point.x, point.y, point.z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
}

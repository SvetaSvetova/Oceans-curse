package com.oceanscurse.client;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * CLIENT config for the karma HUD indicator. Only ForgeConfigSpec is referenced (no client-only
 * imports), so this is safe to register from the common mod constructor.
 *
 * Lets each player move the indicator if another mod's HUD overlaps the default spot.
 */
public final class ClientHudConfig {
    public static final ForgeConfigSpec SPEC;

    private static final ForgeConfigSpec.BooleanValue ENABLED;
    private static final ForgeConfigSpec.IntValue OFFSET_X;
    private static final ForgeConfigSpec.IntValue OFFSET_Y;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Karma (curse) HUD indicator");
        ENABLED = builder
            .comment("Show the karma indicator above the hotbar")
            .define("hudEnabled", true);
        OFFSET_X = builder
            .comment("Horizontal nudge in pixels from the default position (positive = right)")
            .defineInRange("hudOffsetX", 0, -4096, 4096);
        OFFSET_Y = builder
            .comment("Vertical nudge in pixels from the default position (positive = down)")
            .defineInRange("hudOffsetY", 0, -4096, 4096);
        SPEC = builder.build();
    }

    private ClientHudConfig() {
    }

    public static boolean enabled() {
        return ENABLED.get();
    }

    public static int offsetX() {
        return OFFSET_X.get();
    }

    public static int offsetY() {
        return OFFSET_Y.get();
    }
}

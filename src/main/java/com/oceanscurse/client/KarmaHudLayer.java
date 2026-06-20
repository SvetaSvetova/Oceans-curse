package com.oceanscurse.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;

/**
 * Draws the karma (curse) indicator just above the hotbar: a compact colour-coded number.
 * Colour reads the moral tier at a glance — red when cursed (negative), green when cleansed
 * (positive), grey when neutral. Position is nudgeable via {@link ClientHudConfig}.
 *
 * Client-only: references render classes, so it is only ever loaded on the client.
 */
public final class KarmaHudLayer {
    private static final Identifier LAYER_ID = Identifier.fromNamespaceAndPath("oceanscurse", "karma");

    /** Default anchor: centred horizontally, this many pixels up from the screen bottom. */
    private static final int DEFAULT_BOTTOM_OFFSET = 50;

    private static final int COLOR_CURSED = 0xFFFF5555;
    private static final int COLOR_NEUTRAL = 0xFFAAAAAA;
    private static final int COLOR_CLEANSED = 0xFF55FF55;

    private KarmaHudLayer() {
    }

    public static void onAddLayers(final AddGuiOverlayLayersEvent event) {
        event.getLayeredDraw().add(ForgeLayeredDraw.HOTBAR_AND_DECOS, LAYER_ID, KarmaHudLayer::extract);
    }

    private static void extract(final GuiGraphicsExtractor graphics, final DeltaTracker deltaTracker) {
        final Minecraft minecraft = Minecraft.getInstance();
        // The hotbar layer stack already skips rendering while the HUD is hidden (F1), so we
        // only need to guard for no player and the user's toggle.
        if (minecraft.player == null || !ClientHudConfig.enabled()) {
            return;
        }

        final int karma = ClientKarma.get();
        final Font font = minecraft.font;
        final Component text = Component.translatable("hud.oceanscurse.karma", karma);
        final int x = graphics.guiWidth() / 2 + ClientHudConfig.offsetX();
        final int y = graphics.guiHeight() - DEFAULT_BOTTOM_OFFSET + ClientHudConfig.offsetY();

        graphics.centeredText(font, text, x, y, colorFor(karma));
    }

    private static int colorFor(final int karma) {
        if (karma > 0) {
            return COLOR_CLEANSED;
        }
        if (karma < 0) {
            return COLOR_CURSED;
        }
        return COLOR_NEUTRAL;
    }
}

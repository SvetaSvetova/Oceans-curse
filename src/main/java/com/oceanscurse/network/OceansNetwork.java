package com.oceanscurse.network;

import com.oceanscurse.OceansCurse;
import com.oceanscurse.curse.Karma;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

/**
 * The mod's network channel. Currently just pushes the karma value to each player's client so the
 * HUD can show it (karma itself lives server-side in the player's persistent data).
 */
public final class OceansNetwork {
    private static final int PROTOCOL_VERSION = 1;

    private static SimpleChannel channel;

    private OceansNetwork() {
    }

    public static void init() {
        channel = ChannelBuilder
            .named(Identifier.fromNamespaceAndPath(OceansCurse.MODID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .optional()
            .simpleChannel();

        channel.messageBuilder(KarmaSyncMessage.class)
            .direction(PacketFlow.CLIENTBOUND)
            .encoder(KarmaSyncMessage::encode)
            .decoder(KarmaSyncMessage::decode)
            .consumerMainThread(KarmaSyncMessage::handle)
            .add();
    }

    /** Pushes the player's current karma to their own client. */
    public static void sendKarma(final ServerPlayer player) {
        channel.send(new KarmaSyncMessage(Karma.get(player)), PacketDistributor.PLAYER.with(player));
    }
}

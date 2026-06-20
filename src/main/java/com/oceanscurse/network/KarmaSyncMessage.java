package com.oceanscurse.network;

import com.oceanscurse.client.ClientKarma;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

/**
 * Server → client karma update. The handler only touches {@link ClientKarma} (a plain int holder
 * with no client-only imports), so this stays safe to load on a dedicated server too.
 */
public record KarmaSyncMessage(int karma) {
    public static void encode(final KarmaSyncMessage message, final FriendlyByteBuf buffer) {
        buffer.writeInt(message.karma);
    }

    public static KarmaSyncMessage decode(final FriendlyByteBuf buffer) {
        return new KarmaSyncMessage(buffer.readInt());
    }

    public static void handle(final KarmaSyncMessage message, final CustomPayloadEvent.Context context) {
        // consumerMainThread already runs us on the client main thread.
        ClientKarma.set(message.karma);
        context.setPacketHandled(true);
    }
}

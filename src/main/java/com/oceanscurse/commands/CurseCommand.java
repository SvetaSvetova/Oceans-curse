package com.oceanscurse.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.oceanscurse.curse.Karma;
import com.oceanscurse.network.OceansNetwork;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;

/**
 * {@code /curse karma} — reads your current curse (karma). The op-only {@code set}/{@code add}
 * subcommands exist mainly to test the curse/cleanse milestones (M5/M6) before their in-world
 * triggers are built. Registered via {@code RegisterCommandsEvent.BUS} in {@code OceansCurse}.
 */
public final class CurseCommand {
    private CurseCommand() {
    }

    public static void register(final RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("curse")
            .then(Commands.literal("karma")
                .executes(ctx -> report(ctx.getSource()))
                .then(Commands.literal("set")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .then(Commands.argument("value", IntegerArgumentType.integer(Karma.MIN, Karma.MAX))
                        .executes(ctx -> set(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))))
                .then(Commands.literal("add")
                    .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                    .then(Commands.argument("delta", IntegerArgumentType.integer(-Karma.MAX, Karma.MAX))
                        .executes(ctx -> add(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "delta")))))));
    }

    private static int report(final CommandSourceStack source) throws CommandSyntaxException {
        final ServerPlayer player = source.getPlayerOrException();
        final int karma = Karma.get(player);
        source.sendSuccess(() -> Component.translatable("commands.oceanscurse.karma.query", karma), false);
        return 1;
    }

    private static int set(final CommandSourceStack source, final int value) throws CommandSyntaxException {
        final ServerPlayer player = source.getPlayerOrException();
        Karma.set(player, value);
        OceansNetwork.sendKarma(player);
        source.sendSuccess(() -> Component.translatable("commands.oceanscurse.karma.set", Karma.get(player)), true);
        return 1;
    }

    private static int add(final CommandSourceStack source, final int delta) throws CommandSyntaxException {
        final ServerPlayer player = source.getPlayerOrException();
        final int karma = Karma.add(player, delta);
        OceansNetwork.sendKarma(player);
        source.sendSuccess(() -> Component.translatable("commands.oceanscurse.karma.set", karma), true);
        return 1;
    }
}

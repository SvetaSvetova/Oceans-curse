package com.oceanscurse.block;

import java.util.function.Supplier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

/**
 * A pillar (log / wood) block that an axe can strip into another block, keeping the axis.
 *
 * Vanilla's strippable map ({@code AxeItem.STRIPPABLES}) is immutable, so mods can't add to it;
 * instead we hook Forge's {@code getToolModifiedState} to return the stripped variant on AXE_STRIP.
 */
public class StrippableLogBlock extends RotatedPillarBlock {
    private final Supplier<Block> stripped;

    public StrippableLogBlock(final Supplier<Block> stripped, final Properties properties) {
        super(properties);
        this.stripped = stripped;
    }

    @Override
    public BlockState getToolModifiedState(final BlockState state, final UseOnContext context,
                                           final ToolAction toolAction, final boolean simulate) {
        if (ToolActions.AXE_STRIP.equals(toolAction)) {
            return this.stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}

package com.oceanscurse.items;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

/**
 * The Saw — a crafting tool that turns 1 log into 6 planks (instead of 4). In the recipe it is given
 * back via the crafting remainder, but worn down by 1 each time; once it wears out it's consumed, so
 * you have to hunt more sawfish for fresh blades.
 */
public class SawItem extends Item {
    public SawItem(final Properties properties) {
        super(properties);
    }

    @Override
    public ItemStackTemplate getCraftingRemainder(final ItemStack itemStack) {
        if (!itemStack.isDamageableItem()) {
            return new ItemStackTemplate(itemStack.getItem());
        }
        final int nextDamage = itemStack.getDamageValue() + 1;
        if (nextDamage >= itemStack.getMaxDamage()) {
            return null; // the saw wears out and is used up
        }
        final DataComponentPatch worn = DataComponentPatch.builder()
            .set(DataComponents.DAMAGE, nextDamage)
            .build();
        return new ItemStackTemplate(itemStack.getItem(), worn);
    }
}

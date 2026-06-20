package com.oceanscurse.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * A Global Loot Modifier that appends a fixed stack of an item to a loot roll,
 * but only when every JSON {@code conditions} entry passes (e.g. a loot_table_id
 * plus a random_chance).
 *
 * Used to seed Cursed Doubloons into drowned drops and ocean chests without
 * overwriting — and thus conflicting with — the vanilla loot tables.
 */
public class AddItemModifier extends LootModifier {
    public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
        codecStart(inst).and(inst.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(modifier -> modifier.item),
            Codec.intRange(1, 64).optionalFieldOf("count", 1).forGetter(modifier -> modifier.count)
        )).apply(inst, AddItemModifier::new));

    private final Item item;
    private final int count;

    public AddItemModifier(final LootItemCondition[] conditions, final Item item, final int count) {
        super(conditions);
        this.item = item;
        this.count = count;
    }

    @NotNull
    @Override
    protected ObjectArrayList<ItemStack> doApply(final LootTable table, final ObjectArrayList<ItemStack> generatedLoot, final LootContext context) {
        generatedLoot.add(new ItemStack(item, count));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}

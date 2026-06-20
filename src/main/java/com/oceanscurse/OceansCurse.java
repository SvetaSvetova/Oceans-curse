package com.oceanscurse;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.oceanscurse.client.ClientHudConfig;
import com.oceanscurse.client.OceansClient;
import com.oceanscurse.commands.CurseCommand;
import com.oceanscurse.curse.KarmaEvents;
import com.oceanscurse.curse.NightCurse;
import com.oceanscurse.effects.BleedingMobEffect;
import com.oceanscurse.items.CursedDoubloonItem;
import com.oceanscurse.items.CutlassItem;
import com.oceanscurse.loot.AddItemModifier;
import com.oceanscurse.network.OceansNetwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here must match the modId in META-INF/mods.toml
@Mod(OceansCurse.MODID)
public final class OceansCurse {
    // The mod id, referenced everywhere
    public static final String MODID = "oceanscurse";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Deferred registers hold our content and register it under the "oceanscurse" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
        DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

    // --- Loot modifiers ---
    // Serializer for AddItemModifier; the actual injection points (drowned, ocean chests)
    // live in data/oceanscurse/loot_modifiers/*.json.
    public static final RegistryObject<MapCodec<AddItemModifier>> ADD_ITEM_MODIFIER =
        LOOT_MODIFIERS.register("add_item", () -> AddItemModifier.CODEC);

    // --- Mob effects ---
    // Bleeding — the cutlass's signature: armour-piercing damage over time (dark-red).
    public static final RegistryObject<MobEffect> BLEEDING = MOB_EFFECTS.register("bleeding",
        () -> new BleedingMobEffect(MobEffectCategory.HARMFUL, 0x8B0000));

    // --- Items ---
    // Cursed Doubloon — the cursed gold at the heart of the mod. Right-clicking water "returns it
    // to the sea", cleansing it into a Cleansed Doubloon (see CursedDoubloonItem). The night curse
    // (attracting the drowned) keys off carrying these.
    public static final RegistryObject<Item> CURSED_DOUBLOON = ITEMS.register("cursed_doubloon",
        () -> new CursedDoubloonItem(new Item.Properties().setId(ITEMS.key("cursed_doubloon"))));

    // Cleansed Doubloon — gold the sea has purified; safe everyday currency, no curse.
    public static final RegistryObject<Item> CLEANSED_DOUBLOON = ITEMS.register("cleansed_doubloon",
        () -> new Item(new Item.Properties().setId(ITEMS.key("cleansed_doubloon"))));

    // Abordage Cutlass — iron-tier pirate sword that makes foes bleed (see CutlassItem).
    public static final RegistryObject<Item> ABORDAGE_CUTLASS = ITEMS.register("abordage_cutlass",
        () -> new CutlassItem(new Item.Properties()
            .setId(ITEMS.key("abordage_cutlass"))
            .sword(ToolMaterial.IRON, 3.0F, -2.4F)));

    // Coconut — a simple snack for now; coconut milk and crafting come in a later milestone.
    public static final RegistryObject<Item> COCONUT = ITEMS.register("coconut",
        () -> new Item(new Item.Properties()
            .setId(ITEMS.key("coconut"))
            .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build())));

    // --- Creative tab ---
    // Our own creative tab, shown right before the Combat tab, using the doubloon as its icon.
    public static final RegistryObject<CreativeModeTab> OCEANS_CURSE_TAB = CREATIVE_MODE_TABS.register("oceans_curse_tab",
        () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CURSED_DOUBLOON.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.oceanscurse"))
            .displayItems((params, output) -> {
                output.accept(CURSED_DOUBLOON.get());
                output.accept(CLEANSED_DOUBLOON.get());
                output.accept(ABORDAGE_CUTLASS.get());
                output.accept(COCONUT.get());
            }).build());

    public OceansCurse(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Run commonSetup during mod loading
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // Hook our registers onto the mod event bus so the content actually gets registered
        ITEMS.register(modBusGroup);
        CREATIVE_MODE_TABS.register(modBusGroup);
        MOB_EFFECTS.register(modBusGroup);
        LOOT_MODIFIERS.register(modBusGroup);

        // Network channel (used to push karma to clients for the HUD).
        OceansNetwork.init();

        // Game-bus listeners: carry karma across respawn/dimension change, register /curse,
        // run the night curse each server pulse, and sync karma to clients when they (re)join.
        PlayerEvent.Clone.BUS.addListener(KarmaEvents::onPlayerClone);
        PlayerEvent.PlayerLoggedInEvent.BUS.addListener(KarmaEvents::onLoggedIn);
        PlayerEvent.PlayerRespawnEvent.BUS.addListener(KarmaEvents::onRespawn);
        PlayerEvent.PlayerChangedDimensionEvent.BUS.addListener(KarmaEvents::onChangedDimension);
        RegisterCommandsEvent.BUS.addListener(CurseCommand::register);
        TickEvent.ServerTickEvent.Post.BUS.addListener(NightCurse::onServerTick);

        // Client-only HUD. Guarded so a dedicated server never loads client classes.
        if (FMLEnvironment.dist == Dist.CLIENT) {
            OceansClient.init();
        }

        // Config: CLIENT settings for the personal HUD position.
        context.registerConfig(ModConfig.Type.CLIENT, ClientHudConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Ocean's Curse is rising from the depths...");
    }
}

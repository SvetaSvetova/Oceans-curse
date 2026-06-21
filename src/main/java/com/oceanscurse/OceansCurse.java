package com.oceanscurse;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.oceanscurse.client.ClientHudConfig;
import com.oceanscurse.client.OceansClient;
import com.oceanscurse.commands.CurseCommand;
import com.oceanscurse.curse.KarmaEvents;
import com.oceanscurse.curse.NightCurse;
import com.oceanscurse.block.BananaBushBlock;
import com.oceanscurse.block.PineappleBushBlock;
import com.oceanscurse.block.StrippableLogBlock;
import com.oceanscurse.effects.BleedingMobEffect;
import com.oceanscurse.entity.CursedDrowned;
import com.oceanscurse.entity.CursedSkeleton;
import com.oceanscurse.items.CursedDoubloonItem;
import com.oceanscurse.items.CutlassItem;
import com.oceanscurse.loot.AddItemModifier;
import com.oceanscurse.network.OceansNetwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // --- Blocks (palm wood) ---
    public static final RegistryObject<Block> PALM_LOG = BLOCKS.register("palm_log",
        () -> new StrippableLogBlock(() -> OceansCurse.STRIPPED_PALM_LOG.get(), BlockBehaviour.Properties.of()
            .setId(BLOCKS.key("palm_log"))
            .mapColor(MapColor.DIRT)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()));
    public static final RegistryObject<Block> PALM_PLANKS = BLOCKS.register("palm_planks",
        () -> new Block(BlockBehaviour.Properties.of()
            .setId(BLOCKS.key("palm_planks"))
            .mapColor(MapColor.SAND)
            .instrument(NoteBlockInstrument.BASS)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava()));

    public static final RegistryObject<Block> STRIPPED_PALM_LOG = BLOCKS.register("stripped_palm_log",
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("stripped_palm_log"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_WOOD = BLOCKS.register("palm_wood",
        () -> new StrippableLogBlock(() -> OceansCurse.STRIPPED_PALM_WOOD.get(), BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_wood"))
            .mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> STRIPPED_PALM_WOOD = BLOCKS.register("stripped_palm_wood",
        () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("stripped_palm_wood"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_STAIRS = BLOCKS.register("palm_stairs",
        () -> new StairBlock(PALM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_stairs"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_SLAB = BLOCKS.register("palm_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_slab"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_FENCE = BLOCKS.register("palm_fence",
        () -> new FenceBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_fence"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_FENCE_GATE = BLOCKS.register("palm_fence_gate",
        () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_fence_gate"))
            .mapColor(MapColor.SAND).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_DOOR = BLOCKS.register("palm_door",
        () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_door"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).ignitedByLava()));
    public static final RegistryObject<Block> PALM_TRAPDOOR = BLOCKS.register("palm_trapdoor",
        () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_trapdoor"))
            .mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> PALM_BUTTON = BLOCKS.register("palm_button",
        () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_button"))
            .noCollision().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> PALM_PRESSURE_PLATE = BLOCKS.register("palm_pressure_plate",
        () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().setId(BLOCKS.key("palm_pressure_plate"))
            .mapColor(MapColor.SAND).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollision().strength(0.5F).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).ignitedByLava()));

    // --- Fruit bushes (planted by their fruit item, harvested by hand when grown) ---
    public static final RegistryObject<Block> BANANA_BUSH = BLOCKS.register("banana_bush",
        () -> new BananaBushBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("banana_bush"))
            .mapColor(MapColor.PLANT).randomTicks().noCollision().instabreak().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> PINEAPPLE_BUSH = BLOCKS.register("pineapple_bush",
        () -> new PineappleBushBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("pineapple_bush"))
            .mapColor(MapColor.PLANT).randomTicks().noCollision().instabreak().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY)));

    // Block items (each block needs an item form for the inventory / creative tab).
    public static final RegistryObject<Item> PALM_LOG_ITEM = ITEMS.register("palm_log",
        () -> new BlockItem(PALM_LOG.get(), new Item.Properties().setId(ITEMS.key("palm_log")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_PLANKS_ITEM = ITEMS.register("palm_planks",
        () -> new BlockItem(PALM_PLANKS.get(), new Item.Properties().setId(ITEMS.key("palm_planks")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> STRIPPED_PALM_LOG_ITEM = ITEMS.register("stripped_palm_log",
        () -> new BlockItem(STRIPPED_PALM_LOG.get(), new Item.Properties().setId(ITEMS.key("stripped_palm_log")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_WOOD_ITEM = ITEMS.register("palm_wood",
        () -> new BlockItem(PALM_WOOD.get(), new Item.Properties().setId(ITEMS.key("palm_wood")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> STRIPPED_PALM_WOOD_ITEM = ITEMS.register("stripped_palm_wood",
        () -> new BlockItem(STRIPPED_PALM_WOOD.get(), new Item.Properties().setId(ITEMS.key("stripped_palm_wood")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_STAIRS_ITEM = ITEMS.register("palm_stairs",
        () -> new BlockItem(PALM_STAIRS.get(), new Item.Properties().setId(ITEMS.key("palm_stairs")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_SLAB_ITEM = ITEMS.register("palm_slab",
        () -> new BlockItem(PALM_SLAB.get(), new Item.Properties().setId(ITEMS.key("palm_slab")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_FENCE_ITEM = ITEMS.register("palm_fence",
        () -> new BlockItem(PALM_FENCE.get(), new Item.Properties().setId(ITEMS.key("palm_fence")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_FENCE_GATE_ITEM = ITEMS.register("palm_fence_gate",
        () -> new BlockItem(PALM_FENCE_GATE.get(), new Item.Properties().setId(ITEMS.key("palm_fence_gate")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_DOOR_ITEM = ITEMS.register("palm_door",
        () -> new BlockItem(PALM_DOOR.get(), new Item.Properties().setId(ITEMS.key("palm_door")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_TRAPDOOR_ITEM = ITEMS.register("palm_trapdoor",
        () -> new BlockItem(PALM_TRAPDOOR.get(), new Item.Properties().setId(ITEMS.key("palm_trapdoor")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_BUTTON_ITEM = ITEMS.register("palm_button",
        () -> new BlockItem(PALM_BUTTON.get(), new Item.Properties().setId(ITEMS.key("palm_button")).useBlockDescriptionPrefix()));
    public static final RegistryObject<Item> PALM_PRESSURE_PLATE_ITEM = ITEMS.register("palm_pressure_plate",
        () -> new BlockItem(PALM_PRESSURE_PLATE.get(), new Item.Properties().setId(ITEMS.key("palm_pressure_plate")).useBlockDescriptionPrefix()));

    // --- Fruits (food that also plants its bush, like sweet berries) ---
    private static final FoodProperties BANANA_FOOD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
    private static final FoodProperties PINEAPPLE_FOOD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.3F).build();
    // Pineapple fills you, but its sour curse saps your strength a while.
    private static final Consumable PINEAPPLE_CONSUMABLE = Consumables.defaultFood()
        .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0)))
        .build();

    public static final RegistryObject<Item> BANANA = ITEMS.register("banana",
        () -> new BlockItem(BANANA_BUSH.get(), new Item.Properties().setId(ITEMS.key("banana")).food(BANANA_FOOD)));
    public static final RegistryObject<Item> PINEAPPLE = ITEMS.register("pineapple",
        () -> new BlockItem(PINEAPPLE_BUSH.get(), new Item.Properties().setId(ITEMS.key("pineapple")).food(PINEAPPLE_FOOD, PINEAPPLE_CONSUMABLE)));

    // --- Entities ---
    // Cursed Drowned — the curse's own armed dead (see CursedDrowned); summoned by the night curse.
    public static final RegistryObject<EntityType<CursedDrowned>> CURSED_DROWNED = ENTITY_TYPES.register("cursed_drowned",
        () -> EntityType.Builder.<CursedDrowned>of(CursedDrowned::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .build(ENTITY_TYPES.key("cursed_drowned")));

    // Cursed Skeleton — the curse's archer dead, raised on land (see CursedSkeleton).
    public static final RegistryObject<EntityType<CursedSkeleton>> CURSED_SKELETON = ENTITY_TYPES.register("cursed_skeleton",
        () -> EntityType.Builder.<CursedSkeleton>of(CursedSkeleton::new, MobCategory.MONSTER)
            .sized(0.6F, 1.99F)
            .build(ENTITY_TYPES.key("cursed_skeleton")));

    // --- Spawn eggs (creative-tab convenience) ---
    public static final RegistryObject<Item> CURSED_DROWNED_SPAWN_EGG = ITEMS.register("cursed_drowned_spawn_egg",
        () -> new SpawnEggItem(new Item.Properties().setId(ITEMS.key("cursed_drowned_spawn_egg")).spawnEgg(CURSED_DROWNED.get())));
    public static final RegistryObject<Item> CURSED_SKELETON_SPAWN_EGG = ITEMS.register("cursed_skeleton_spawn_egg",
        () -> new SpawnEggItem(new Item.Properties().setId(ITEMS.key("cursed_skeleton_spawn_egg")).spawnEgg(CURSED_SKELETON.get())));
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
                output.accept(CURSED_DROWNED_SPAWN_EGG.get());
                output.accept(CURSED_SKELETON_SPAWN_EGG.get());
                output.accept(PALM_LOG_ITEM.get());
                output.accept(STRIPPED_PALM_LOG_ITEM.get());
                output.accept(PALM_WOOD_ITEM.get());
                output.accept(STRIPPED_PALM_WOOD_ITEM.get());
                output.accept(PALM_PLANKS_ITEM.get());
                output.accept(PALM_STAIRS_ITEM.get());
                output.accept(PALM_SLAB_ITEM.get());
                output.accept(PALM_FENCE_ITEM.get());
                output.accept(PALM_FENCE_GATE_ITEM.get());
                output.accept(PALM_DOOR_ITEM.get());
                output.accept(PALM_TRAPDOOR_ITEM.get());
                output.accept(PALM_BUTTON_ITEM.get());
                output.accept(PALM_PRESSURE_PLATE_ITEM.get());
                output.accept(BANANA.get());
                output.accept(PINEAPPLE.get());
            }).build());

    public OceansCurse(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Run commonSetup during mod loading
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // Hook our registers onto the mod event bus so the content actually gets registered
        BLOCKS.register(modBusGroup);
        ITEMS.register(modBusGroup);
        CREATIVE_MODE_TABS.register(modBusGroup);
        MOB_EFFECTS.register(modBusGroup);
        LOOT_MODIFIERS.register(modBusGroup);
        ENTITY_TYPES.register(modBusGroup);

        // Give our entities their attributes (max health, damage, etc.).
        EntityAttributeCreationEvent.BUS.addListener(this::onAttributeCreation);

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

    private void onAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(CURSED_DROWNED.get(), CursedDrowned.createAttributes().build());
        event.put(CURSED_SKELETON.get(), CursedSkeleton.createAttributes().build());
    }
}

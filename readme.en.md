[🇷🇺 Читать на русском](readme.md)

# Ocean's Curse

**A large-scale mod for Minecraft 26.2 (Forge 65)**, adding pirate themes, ocean exploration, a ship system, alchemy, bosses, and a storyline with moral choices.

---

## 📖 Table of Contents
- [Concept](#-concept)
- [Current Status and Development Approach](#-current-status-and-development-approach)
- [Key Features](#-key-features)
- [Roadmap (Versions)](#-roadmap-versions)
- [Detailed Version Descriptions](#-detailed-version-descriptions)
  - [V1.0: "Awakening of the Ocean"](#v10-awakening-of-the-ocean)
  - [V2.0: "Lord of the Waves"](#v20-lord-of-the-waves)
  - [V3.0: "Curse of the Abyss"](#v30-curse-of-the-abyss)
- [Systems and Mechanics](#-systems-and-mechanics)
- [Technical Architecture](#-technical-architecture)
- [Installation and Development](#-installation-and-development)

---

## 🌊 Concept

The mod turns the Minecraft ocean into a living, dangerous, and adventure‑filled world. The player starts as a simple sailor, explores shipwrecks, fights pirates and sea monsters, and finally comes face‑to‑face with Davy Jones himself.

**The core mechanic** is **"Cursed Gold" (doubloons)**. This is not just currency: the gold is truly cursed. Keep doubloons on you – at night they attract drowned; the more you hoard, the louder the "call of the sea". You can clear your conscience by returning the gold to the sea. This is **karma**: gold spent on dark altars pulls you toward evil, gold returned to the sea pulls you toward good, and your final choice determines the ending.

**Three pillars of the mod:**
1. **Exploration** – new biomes, structures, fish, and resources.
2. **Progression** – ship system, rum alchemy, weapon upgrades.
3. **Story** – a quest chain, bosses, and a moral‑choice ending.

---

## 📌 Current Status and Development Approach

> This section reflects the **actual** state of the project. Sections below describe the full vision (we are still heading there).

**Platform:** Minecraft **26.2** / Forge **26.2‑65.0.0** / **Java 25**, built with Gradle Wrapper.

**Approach:** The mod is large; we are not doing everything at once. We go step‑by‑step from version to version. The goal is a real release on CurseForge.

**Milestone v0.1 "Core of the Curse" – loop:** find cursed doubloons → fight off monsters that appear because of the curse with a saber → decide: hoard gold (dark karma) or return it to the sea to cleanse (light karma). ✅ Done

**Milestone v0.2 "Development of the Curse – New Hostile Mobs:"** – due to the curse, new hostile mobs begin to appear: the Cursed Drowned Zombie and the Cursed Skeleton. Features: each mob has 3 weapon variants: common – saber; zombie – chain attack; skeleton – bow with effect arrows. ✅ Done

**Milestone v0.3 "New Blocks and Resources – Preparation for Biomes":** palm wood blocks, banana and pineapple bushes, wood crafting recipes. ⏳ In progress

**Milestone v0.4 "New Mobs (Fauna), Drops – Preparation for Biomes":** adding whales, sharks, rays, and sawfish, along with their drops and some crafting ingredients (e.g., for the saber). ⏰ Planned

... and so on (will be expanded).

---

## ⚓ Key Features

### Surface Changes (visible immediately)
- 5 new biomes (Tropical Beach, Cursed Sea, Iceberg, Treasure Island, Deep Ocean).
- 5+ new fish species (whales, sharks, piranhas, sawfish, electric rays).
- 5+ hostile mob types (Bosun, Gunner, Captain, sirens, cursed skeletons and zombies).
- 4 dungeon types (Shipwrecks, Pirate Coves, Sunken Cities, Kraken Grotto).
- 20+ new items (sabers, harpoons, muskets, bombs, flippers, food, resources).
- New blocks (palm wood, black resin, coconut blocks, fishing net).
- 3 boss types (Bluebeard, Kraken, Davy Jones).

### Systemic Mechanics (deeper gameplay)
- **Pilotable Ships** – build with blocks and transform into an entity.
- **Rum Alchemy** – multi‑block brewing of drinks with buffs.
- **Soul Drying** – turn mob essences into runes for enchanting.
- **Karma System** – moral choices affect the world and the ending.
- **The Abyss Dimension (Davy Jones' Locker)** – a separate world for the final battle.
- **Quest Book ("Logbook")** – guides the player through the story.

---

## 🗺️ Roadmap (Versions)

| Version | Title | Main Focus | Status |
|---------|-------|------------|--------|
| **V1.0** | "Awakening of the Ocean" | Content, resources, biomes, structures, basic enemies | 🛠️ In development |
| **V2.0** | "Lord of the Waves" | Ships, alchemy, soul drying, Kraken boss | ❌ Planned |
| **V3.0** | "Curse of the Abyss" | Dimension, Davy Jones, moral choice, finale | ❌ Planned |

---

## 🏝️ V1.0: "Awakening of the Ocean"

### New Biomes
| Biome | Features | Resources |
|-------|----------|-----------|
| **Tropical Beach** | White sand, turquoise water, palm trees | Palm wood, coconuts, **pineapples, bananas**, crabs |
| **Cursed Sea** | Black water, grey sky, bubbles | Black resin (replaces coal) |
| **Iceberg** | Blue ice, cold waters | Frost wood, cryolite |
| **Treasure Island** | Small island with a killer tree | Treasure chest (always) |
| **Deep Ocean** | Depth > 30 blocks, spawns whales and sharks | Whale bone, shark scale |

### Mobs
| Mob | Type | Behaviour | Drops |
|-----|------|-----------|-------|
| **Whale** | Neutral | Swims by, spouts water | Whale bone, whale baleen |
| **Shark** | Aggressive (when HP < 50%) | Attacks when bleeding | Shark scale, fin |
| **Sawfish** | Aggressive | Attacks near eggs | Saw (speeds up plank cutting) |
| **Electric Ray** | Neutral | Deals electric damage when stepped on | Electric spine |
| **Siren** | Hostile | Lures players into water with singing | Siren pearl |
| **Cursed Drowned Zombie & Skeleton** | Hostile | Each mob attacks with one of: 1. Saber attack (both). 2. Chain attack that pulls the player (zombie). 3. Bow with effect arrows (skeleton). | Chain link (zombie), effect arrows (skeleton), doubloons, saber |
| **Bosun** | Hostile | Uses shield, buffs pirates | Saber, doubloons |
| **Gunner** | Hostile | Shoots musket, throws bombs | Musket, gunpowder |

### Structures
- **Corvette** – small stranded ship (1 chest).
- **Galleon** – wrecked ship in the ocean (3 chests, cryolite).
- **Sunken House** – ruins near shore (basement with treasures).
- **Pirate Cove** – land fortress (barrels with resources).
- **Sunken City** – several ruined houses far from shore (chests with resources).

### Weapons and Tools
| Item | Materials | Features |
|------|-----------|----------|
| **Saber** | 2 iron + whale bone | Bleed effect (tick damage) |
| **Harpoon** | Iron + sticks + rope | Throwable, pulls mobs; can pull the player by hooking to blocks |
| **Musket** | Palm wood + iron | Ignores 50% armour, slow reload |
| **Smoke Bomb** | Coal + sand + gunpowder | Creates a smoke screen (hides from mobs) |
| **Flippers** | Palm planks + rope | +40% speed in water, -20% on land |
| **Sea Compass** | Iron + redstone + pearl | Points to the nearest structure |
| **Fishing Net** | Sticks + chain links | Catches mobs/fish/items |

### Food
- **Coconut** – restores 1 hunger / crafts coconut milk.
- **Pineapple** – restores 1 hunger.
- **Banana** – restores 1 hunger.
- **Pirate Biscuit** – restores 1 hunger.
- **Coconut Milk** – removes poison effect.
- **Dried Shark** – restores 3 hunger.

---

## ⛵ V2.0: "Lord of the Waves"

### Ships (main mechanic)

**Architecture:**
- The player builds a ship from ordinary blocks (planks, wool, chests).
- Uses a **"Summoning Scroll"** to turn the structure into an entity.
- In entity mode, the ship can be controlled (WASD), has HP and an inventory.
- Can be reverted to blocks (by using the scroll on the entity).

**Interaction while in entity form:**
- **Chests, doors, levers** work via RayTrace (clicking the model opens the container).
- Access to the hold, sails, figurehead via GUI (click on the helm).
- The ship has **HP (200‑500)** and can be destroyed.

**Upgrades:**
| Slot | Item | Effect |
|------|------|--------|
| Sails (up to 4) | Wool + rope + whale baleen | Increase speed (+20% / +40% / +60%) |
| Figurehead | Mermaid / Dragon figure | Buffs the crew (night vision / strength) |
| Furniture (slots) | Bed, table, barrel | Visually appears on the model |

### Rum Alchemy (multi‑block)
**Construction:** Distillation cube + 4 barrels + hopper on top.

**Ingredients and effects:**
| Brew | Ingredients | Effect |
|------|-------------|--------|
| "Merry Eye" | Pineapple | Night vision (2 min) |
| "Dead Man" | Coconut | Drowning resistance |
| "Storm" | Apple + sugar | Speed + jump boost |
| "Cursed" | Black resin + mushroom | Regeneration + weakness |

### Soul Drying
- A block that turns **"Essence Clumps"** (drops from pirates, sirens) into **"Ocean Runes"**.
- Runes are socketed into weapons via the **"Ocean Forge"**.
- Example runes: "Thirst" (+damage to living), "Depth" (+water breathing), "Abyss" (+damage to aquatic mobs).

### Boss: Kraken
- Summoned by using a **"Kraken Eye"** (found in sunken cities) in the deep ocean.
- **Phases:**
  1. 4 tentacles (50 HP each) – must be destroyed.
  2. Head (150 HP) – attacks with water projectiles and drags the player underwater.
- **Drops:** Kraken Heart, 15‑20 doubloons, Kraken Tentacle, rune "Abyss".

### Quests V2.0
1. "The Sea Calls" – find the Kraken Eye.
2. "Dark Brewing" – brew your first rum.
3. "Soul Energy" – obtain a rune.
4. "Ship of My Dreams" – summon a ship.
5. "Deep Horror" – defeat the Kraken.

---

## 💀 V3.0: "Curse of the Abyss"

### Story Entrance
- The player receives a **"Davy Jones' Amulet"** in a sunken city.
- Use it in the "Cursed Sea" to summon the **Flying Dutchman**.
- Find the **"Map of the Abyss"** aboard the Dutchman.

### The Abyss Dimension (Davy Jones' Locker)
- Entry – **"cheating death"**: when dying in the sea while carrying the Map of the Abyss, actual death is intercepted (no loot dropped) and the player is teleported to the dimension. Lore: those who die at sea end up in Davy Jones' Locker.
- Inside, **you cannot truly die** – you are trapped, not killed.
- Exit – **ritual at sunset ("Green Flash")**: similar to the Locker, you must perform a special action to return to the living world.
- Grey mist, water instead of ground, endless ocean.
- **Spawning:** Drowned Souls (new mobs).
- **Objective:** Find and open **Davy Jones' Chest** by destroying 4 chains (each 100 HP).

### Final Boss: Davy Jones
- **Phase 1 – Artillery Battle:** destroy 2 masts of the Flying Dutchman.
- **Phase 2 – Boarding:** fight Davy Jones (300 HP, teleportation, summoning skeletons).
- **Vulnerability:** only weapons with the "Abyss" rune deal damage.
- **Phase 3 – Heart:** finish the boss with the Kraken Heart.

### Finale (Moral Choice)
| Choice | Consequence |
|--------|-------------|
| **Take the Heart** (evil) | Become the new Davy Jones: control storms, pirates are neutral, eternal storm. |
| **Destroy the Heart** (good) | Lift the curse: pirates disappear, ocean becomes peaceful, storms are rarer. |

### Final Rewards
- **Key of the Abyss** – portal to the dimension.
- **Flag of the Flying Dutchman** – speeds up your ship at night.
- **Trident of Davy Jones** – power, lightning, infinite durability (requires doubloons).

---

## 🧩 Systems and Mechanics

### Karma System (Cursed Gold)
- Doubloons drop from pirates, bosses, and in chests.
- Used to activate altars, upgrade weapons, and trade on the Black Market.
- Affect the ending (good/evil).

### Logbook (Quests)
- A book with tasks that fills up as you progress.
- 15+ quests across all three versions.

### Particles and Sounds
- Water, wind, seagull cries, ship creaks.
- Each structure and boss has unique sounds.

---

## 🛠️ Technical Architecture

### Tools
- **Minecraft 26.2 / Forge 26.2‑65.0.0**
- **Java 25 (JDK 25)** – required, no lower version
- **VSCode** (+ Extension Pack for Java) or **IntelliJ IDEA**
- **Blockbench** (for models)
- **Gradle Wrapper 9.3.1** (`gradlew`) – no separate Gradle installation needed

### Project Structure
```txt
src/main/java/com/oceanscurse/
├── OceansCurse.java   # Main mod class (@Mod, registries, creative tab)
├── Config.java        # Forge config
├── items/             # Items (doubloon, saber, food) – growing over time
├── blocks/            # Blocks (altar, net, alchemy)
├── entities/          # Entities (mobs, ships, bosses)
├── world/             # Biomes, structures, Abyss dimension
└── client/            # Renderers, models, GUI
```

Resources: `src/main/resources/assets/oceanscurse/` (models, textures, lang)

### Key Classes
- `ShipEntity` – ship entity with inventory and HP.
- `ShipScanner` – scans blocks and turns them into NBT structure.
- `RumDistilleryBlockEntity` – logic for the distillation cube.
- `SoulDryerBlockEntity` – soul drying with timer.
- `KrakenEntity` – boss with phases.
- `AbyssDimension` – custom dimension.

---

## 📥 Installation and Development

### For Players (installation)
1. Download **Forge for Minecraft 26.2** and install the client.
2. Download the `.jar` mod file (release on CurseForge).
3. Place it in your `.minecraft/mods/` folder.
4. Launch the game with the Forge profile.

### For Developers (building from source)
1. Install **JDK 25** and set `JAVA_HOME` to it.
2. Open the project folder in **VSCode** (+ Extension Pack for Java) or **IntelliJ IDEA**.
3. Wait for Gradle import – the IDE will automatically pull dependencies.
4. Build: `.\gradlew.bat build` · Run client: `.\gradlew.bat runClient`.

> ForgeGradle 7 generates run configurations automatically – no separate `genIntellijRuns` needed.
> Keep the project **outside OneDrive**: sync locks the `build/` folder and breaks the build.

#### MIT License – you are free to use and modify this mod as long as you give appropriate credit.

package me.matl114.logitech.core;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.matl114.logitech.utils.UtilClass.SpecialItemClass.CustomFireworkStar;
import me.matl114.logitech.utils.UtilClass.SpecialItemClass.CustomHead;
import me.matl114.matlib.algorithms.dataStructures.frames.InitializeProvider;
import me.matl114.matlib.algorithms.dataStructures.frames.InitializeSafeProvider;
import me.matl114.matlib.utils.reflect.wrapper.FieldAccess;
import me.matl114.matlib.utils.version.VersionedRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;

import static me.matl114.logitech.Language.get;
import static me.matl114.logitech.Language.getList;
import static me.matl114.logitech.utils.AddUtils.*;

public class LogiTechSlimefunItemStacks {

    public static void registerItemStack(){
        for (ItemStack it :ADDGLOW){
            addGlow(it);
        }
        LogiTechSlimefunItemStacks.TRACE_ARROW.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
        hideAllFlags(CARGO_CONFIG);
        hideAllFlags(ENTITY_FEAT);
        hideAllFlags(SPACE_CARD);
        hideAllFlags(REPLACE_SF_CARD);
        hideAllFlags(FU_BASE);
        hideAllFlags(DISPLAY_FU_USE_1);
        hideAllFlags(DISPLAY_REMOVE_FU_2);
        setUnbreakable(UNBREAKING_SHIELD,true);
    }

    //Groups
    public static final ItemStack ROOT=new CustomItemStack(Material.BUDDING_AMETHYST,
            get("group.root.name"),getList("group.root.lore"));
    public static final ItemStack INFORMATION=themed(Material.PAPER,Theme.INFO1,
            get("group.information.name"), getList("group.information.lore"));
    public static final ItemStack BASIC_MATERIAL=themed(Material.END_CRYSTAL,Theme.CATEGORY2,
            get("group.basic_material.name"), getList("group.basic_material.lore"));
    public static final ItemStack GITHUB_URL=themed(Material.BOOK,Theme.NONE,
            get("group.github_url.name"), getList("group.github_url.lore"));
    public static final ItemStack STACKABLE_MACHINE=themed(Material.BLAST_FURNACE,Theme.MENU1,
            get("group.stackable_machine.name"), getList("group.stackable_machine.lore"));
    public static final ItemStack ALLRECIPE=themed(Material.KNOWLEDGE_BOOK,Theme.MENU1,
            get("group.recipe_type.name"), getList("group.recipe_type.lore"));
    public static final ItemStack BASIC=themed(Material.FURNACE,Theme.CATEGORY2,
            get("group.basic_machine.name"),getList("group.basic_machine.lore")  );
    public static final ItemStack ALLBIGRECIPES =themed(Material.LODESTONE, Theme.CATEGORY2,
            get("group.big_recipe.name"),getList("group.big_recipe.lore"));
    public static final ItemStack CARGO=themed(Material.BAMBOO_CHEST_RAFT,Theme.CATEGORY2,
            get("group.cargo_machine.name"),getList("group.cargo_machine.lore"));
    public static final ItemStack SINGULARITY=themed(Material.NETHER_STAR,Theme.CATEGORY2,
            get("group.singularity_technology.name"),getList("group.singularity_technology.lore"));
    public static final ItemStack ADVANCED=themed(Material.BEACON,Theme.CATEGORY2,
            get("group.advanced_machine.name"),getList("group.advanced_machine.lore"));
    public static final ItemStack BEYOND=themed(Material.REPEATING_COMMAND_BLOCK,Theme.CATEGORY2,
            get("group.transcendence_realm.name"),getList("group.transcendence_realm.lore"));
    public static final ItemStack VANILLA=themed(Material.OBSERVER,Theme.CATEGORY2,
            get("group.redstone_technology.name"),getList("group.redstone_technology.lore"));
    public static final ItemStack MANUAL=themed(Material.CRAFTING_TABLE,Theme.CATEGORY2,
            get("group.fast_machine.name"),getList("group.fast_machine.lore") );
    public static final ItemStack SPECIAL=themed(Material.SCULK_CATALYST,Theme.CATEGORY2,
            get("group.mysterious_realm.name"),getList("group.mysterious_realm.lore"));
    public static final ItemStack TOOLS=themed(Material.NETHERITE_AXE, Theme.CATEGORY2,
            get("group.tools.name"),getList("group.tools.lore"));
    public static final ItemStack TOOLS_SUBGROUP_1=themed(Material.MUSIC_DISC_RELIC, Theme.CATEGORY2,
            get("group.tools_subgroup_1.name"),getList("group.tools_subgroup_1.lore"));
    public static final ItemStack TOOLS_SUBGROUP_2=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Theme.CATEGORY2,
            get("group.tools_subgroup_2.name"),getList("group.tools_subgroup_2.lore"));
    public static final ItemStack TOOLS_RECIPES=themed("tools_recipes",Material.CRAFTING_TABLE, Theme.CATEGORY2,
            get("group.tools_recipes.name"),getList("group.TOOLS_RECIPES.lore"));
    //public static final ItemStack TEMPLATE=themed()
    public static final ItemStack TOBECONTINUE=themed(Material.STRUCTURE_VOID,Theme.NONE,
            get("group.to_be_continue.name"),getList("group.to_be_continue.lore"));
    public static final ItemStack SPACE =themed(Material.TOTEM_OF_UNDYING, Theme.CATEGORY2,
            get("group.space_technology.name"),getList("group.space_technology.lore"));
    public static final ItemStack GENERATORS=themed(Material.LAVA_BUCKET, Theme.CATEGORY2,
            get("group.resource_generator.name"),getList("group.resource_generator.lore"));
    public static final ItemStack ENERGY=themed(Material.LIGHTNING_ROD, Theme.CATEGORY2,
            get("group.electricity.name"),getList("group.electricity.lore"));
    public static final ItemStack FUNCTIONAL=themed(Material.STRUCTURE_VOID, Theme.CATEGORY2,
            get("group.functional.name"),getList("group.functional.lore"));
    public static final ItemStack UPDATE_LOG=themed(Material.WRITABLE_BOOK,Theme.NONE,
            get("group.update_log.name"), getList("group.update_log.lore"));
    public static final ItemStack NOTICE=themed(Material.WRITABLE_BOOK, Theme.CATEGORY2,
            get("group.notice.name"),getList("group.notice.lore"));

    //Information
    public static final ItemStack WIKI=themed(Material.KNOWLEDGE_BOOK,Theme.NONE,
            get("information.wiki.name"), getList("information.wiki.lore"));
    public static final ItemStack INFO_1=themed(Material.PAPER,Theme.NONE,
            get("information.info_1.name"), getList("information.info_1.lore"));
    public static final ItemStack INFO_2=themed(Material.PAPER,Theme.NONE,
            get("information.info_2.name"), getList("information.info_2.lore"));
    public static final ItemStack INFO_3=themed(Material.PAPER,Theme.NONE,
            get("information.info_3.name"), getList("information.info_3.lore"));
    public static final ItemStack INFO_4=themed(Material.PAPER,Theme.NONE,
            get("information.info_4.name"), getList("information.info_4.lore"));
    public static final ItemStack INFO_5=themed(Material.PAPER,Theme.NONE,
            get("information.info_5.name"), getList("information.info_5.lore"));
    public static final ItemStack INFO_6=themed(Material.PAPER,Theme.NONE,
            get("information.info_6.name"), getList("information.info_6.lore"));

    //Features
    public static final ItemStack FEAT1=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_1.name"),getList("infomation.feature_1.lore"));
    public static final ItemStack FEAT2=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_2.name"),getList("infomation.feature_2.lore"));
    public static final ItemStack FEAT3=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_3.name"),getList("infomation.feature_3.lore"));
    public static final ItemStack FEAT4=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_4.name"),getList("infomation.feature_4.lore"));
    public static final ItemStack FEAT5=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_5.name"),getList("infomation.feature_5.lore"));
    public static final ItemStack FEAT6=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_6.name"),getList("infomation.feature_6.lore"));
    public static final ItemStack FEAT7=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_7.name"),getList("infomation.feature_7.lore"));
    public static final ItemStack FEAT8=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_8.name"),getList("infomation.feature_8.lore"));
    public static final ItemStack FEAT9=themed(Material.BOOK, Theme.NONE,
            get("infomation.feature_9.name"),getList("infomation.feature_9.lore"));


    //Items
    public static final SlimefunItemStack ENTITY_FEAT=themed("ENTITY_FEAT",Material.SPAWNER,Theme.ITEM1,
            get("item.ENTITY_FEAT.name"),getList("item.ENTITY_FEAT.lore"));
    public static final SlimefunItemStack BUG= themed("BUG", Material.BONE_MEAL, Theme.ITEM1,
            get("item.bug.name"),getList("item.bug.lore"));
    public static final SlimefunItemStack MATL114 = themed("MATL114", CustomHead.matl114Head.getItem(), Theme.ITEM1,
            get("item.matl114.name"),getList("item.matl114.lore"));
    public static final SlimefunItemStack CHIP_INGOT=themed("CHIP_INGOT",Material.BAKED_POTATO,Theme.ITEM1,
            get("item.potato_chip.name"),getList("item.potato_chip.lore"));
    public static final SlimefunItemStack TITANIUM_INGOT=themed("TITANIUM_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("item.TITANIUM_INGOT.name"),getList("item.TITANIUM_INGOT.lore"));
    public static final SlimefunItemStack TUNGSTEN_INGOT=themed("TUNGSTEN_INGOT",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("item.TUNGSTEN_INGOT.name"),getList("item.TUNGSTEN_INGOT.lore"));
    public static final SlimefunItemStack LOGIC= themed("LOGIC",Material.STRING,Theme.ITEM1,
            get("item.logic.name"),getList("item.logic.lore"));
    public static final SlimefunItemStack BOOLEAN_TRUE =themed("BOOLEAN_TRUE",Material.MUSIC_DISC_5,Theme.ITEM1,
            get("item.boolean_true.name"),getList("item.boolean_true.lore"));
    public static final SlimefunItemStack BOOLEAN_FALSE =themed("BOOLEAN_FALSE",Material.MUSIC_DISC_5,Theme.ITEM1,
            get("item.boolean_false.name"),getList("item.boolean_false.lore"));
    public static final SlimefunItemStack LOGIC_GATE=themed("LOGIC_GATE",Material.COMPARATOR,Theme.ITEM1,
            get("item.logic_gate.name"),getList("item.logic_gate.lore"));
        //generated items
    public static final SlimefunItemStack EXISTE=themed("EXISTE",Material.SLIME_BALL,Theme.ITEM1,
            get("item.existence.name"),getList("item.existence.lore"));
    public static final SlimefunItemStack UNIQUE=themed("UNIQUE",Material.MAGMA_CREAM,Theme.ITEM1,
            get("item.uniqueness.name"),getList("item.uniqueness.lore"));
    public static final SlimefunItemStack PARADOX=themed("PARADOX",Material.NAUTILUS_SHELL,Theme.ITEM1,
            get("item.paradox.name"),getList("item.paradox.lore"));
    public static final SlimefunItemStack NOLOGIC=themed("NOLOGIC",Material.STRING,Theme.ITEM1,
            get("item.antilogic.name"),getList("item.antilogic.lore"));
    public static final SlimefunItemStack LENGINE=themed("LENGINE",Material.MAGENTA_GLAZED_TERRACOTTA,Theme.ITEM1,
            get("item.logical_engine.name"),getList("item.logical_engine.lore"));
    public static final SlimefunItemStack LFIELD=themed("LFIELD",Material.END_CRYSTAL,Theme.ITEM1,
            get("item.logical_engine_force_field.name"),getList("item.logical_engine_force_field.lore"));
    public static final SlimefunItemStack LSCHEDULER=themed("LSCHEDULER",Material.RECOVERY_COMPASS,Theme.ITEM1,
            get("item.logical_scheduler.name"),getList("item.logical_scheduler.lore"));
    public static final SlimefunItemStack LCRAFT=themed("LCRAFT",Material.CONDUIT,Theme.ITEM1,
            get("item.logical_crafting_unit.name"),getList("item.logical_crafting_unit.lore"));
    public static final SlimefunItemStack LDIGITIZER=themed("LDIGITIZER",Material.TARGET,Theme.ITEM1,
            get("item.digital_core.name"),getList("item.digital_core.lore"));
    public static final SlimefunItemStack LBOOLIZER=themed("LBOOLIZER",Material.LEVER,Theme.ITEM1,
            get("item.boolean_core.name"),getList("item.boolean_core.lore"));
    public static final SlimefunItemStack LIOPORT=themed("LIOPORT",Material.CALIBRATED_SCULK_SENSOR,Theme.ITEM1,
            get("item.io_component.name"),getList("item.io_component.lore"));
    public static final SlimefunItemStack PALLADIUM_INGOT=themed("PALLADIUM_INGOT",Material.COPPER_INGOT,Theme.ITEM1,
            get("item.palladium_ingot.name"),getList("item.palladium_ingot.lore"));
    public static final SlimefunItemStack PLATINUM_INGOT=themed("PLATINUM_INGOT",Material.GOLD_INGOT,Theme.ITEM1,
            get("item.platinum_ingot.name"),getList("item.platinum_ingot.lore"));
    public static final SlimefunItemStack MOLYBDENUM=themed("MOLYBDENUM",Material.GUNPOWDER,Theme.ITEM1,
            get("item.molybdenum.name"),getList("item.molybdenum.lore"));
    public static final SlimefunItemStack CERIUM=themed("CERIUM",Material.GUNPOWDER,Theme.ITEM1,
            get("item.cerium.name"),getList("item.cerium.lore"));
    public static final SlimefunItemStack CADMIUM_INGOT=themed("CADMIUM_INGOT",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("item.cadmium_ingot.name"),getList("item.cadmium_ingot.lore"));
    public static final SlimefunItemStack MENDELEVIUM=themed("MENDELEVIUM",Material.GLOWSTONE_DUST,Theme.ITEM1,
            get("item.mendelevium.name"),getList("item.mendelevium.lore"));
    public static final SlimefunItemStack DYSPROSIUM=themed("DYSPROSIUM",Material.REDSTONE,Theme.ITEM1,
            get("item.dysprosium.name"),getList("item.dysprosium.lore"));
    public static final SlimefunItemStack BISMUTH_INGOT=themed("BISMUTH_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("item.bismuit_ingot.name"),getList("item.bismuit_ingot.lore"));
    public static final SlimefunItemStack ANTIMONY_INGOT=themed("ANTIMONY_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("item.antimony_ingot.name"),getList("item.antimony_ingot.lore"));
    public static final SlimefunItemStack BORON=themed("BORON",Material.CLAY_BALL,Theme.ITEM1,
            get("item.boron.name"),getList("item.boron.lore"));
    public static final SlimefunItemStack THALLIUM=themed("THALLIUM",Material.BRICK,Theme.ITEM1,
            get("item.THALLIUM.name"),getList("item.THALLIUM.lore"));
    public static final SlimefunItemStack HYDRAGYRUM=themed("HYDRAGYRUM",Material.PRISMARINE_CRYSTALS,Theme.ITEM1,
            get("item.HYDRAGYRUM.name"),getList("item.HYDRAGYRUM.lore"));
    public static final SlimefunItemStack HGTLPBBI=themed("HGTLPBBI",CustomHead.HGTLPBBIHead.getItem(), Theme.ITEM1,
            get("item.HGTLPBBI.name"),getList("item.HGTLPBBI.lore"));
    public static final SlimefunItemStack DIMENSIONAL_SHARD=themed("DIMENSIONAL_SHARD",Material.PRISMARINE_SHARD,Theme.ITEM1,
            get("item.dimensional_shard.name"),getList("item.dimensional_shard.lore"));
    public static final SlimefunItemStack STAR_GOLD=themed("STAR_GOLD",Material.NETHER_STAR,Theme.ITEM1,
            get("item.star_gold.name"),getList("item.star_gold.lore"));
    public static final SlimefunItemStack VIRTUAL_SPACE=themed("VIRTUAL_SPACE",CustomHead.virtualSpaceHead.getItem(), Theme.ITEM1,
            get("item.VIRTUAL_SPACE.name"),getList("item.VIRTUAL_SPACE.lore"));
    public static final SlimefunItemStack WORLD_FEAT=themed("WORLD_FEAT",Material.GRASS_BLOCK,Theme.ITEM1,
            get("item.WORLD_FEAT.name"),getList("item.WORLD_FEAT.lore"));
    public static final SlimefunItemStack NETHER_FEAT=themed("NETHER_FEAT",Material.NETHERITE_SCRAP,Theme.ITEM1,
            get("item.NETHER_FEAT.name"),getList("item.NETHER_FEAT.lore"));
    public static final SlimefunItemStack END_FEAT=themed("END_FEAT",Material.CHORUS_PLANT,Theme.ITEM1,
            get("item.END_FEAT.name"),getList("item.END_FEAT.lore"));
    public static final SlimefunItemStack STACKFRAME=themed("STACKFRAME",Material.BEDROCK,Theme.ITEM1,
            get("item.STACKFRAME.name"),getList("item.STACKFRAME.lore"));

    public static final SlimefunItemStack STAR_GOLD_INGOT=themed("STAR_GOLD_INGOT",Material.GOLD_INGOT,Theme.ITEM1,
            get("item.STAR_GOLD_INGOT.name"),getList("item.STAR_GOLD_INGOT.lore"));
    public static final SlimefunItemStack ABSTRACT_INGOT=themed("ABSTRACT_INGOT",Material.IRON_INGOT,Theme.ITEM1,
            get("item.ABSTRACT_INGOT.name"),getList("item.ABSTRACT_INGOT.lore"));
    public static final SlimefunItemStack PDCECDMD=themed("PDCECDMD",CustomHead.PDCECDMDHead.getItem(), Theme.ITEM1,
            get("item.PDCECDMD.name"),getList("item.PDCECDMD.lore"));
    public static final SlimefunItemStack REINFORCED_CHIP_INGOT=themed("REINFORCED_CHIP_INGOT",Material.POISONOUS_POTATO,Theme.ITEM1,
            get("item.REINFORCED_CHIP_INGOT.name"),getList("item.REINFORCED_CHIP_INGOT.lore"));
    public static final SlimefunItemStack ATOM_INGOT=themed("ATOM_INGOT",Material.ECHO_SHARD,Theme.ITEM1,
            get("item.ATOM_INGOT.name"),getList("item.ATOM_INGOT.lore"));

    public static final SlimefunItemStack LMOTOR=themed("LMOTOR",CustomHead.logicMotorHead.getItem(),Theme.ITEM1,
            get("item.LMOTOR.name"),getList("item.LMOTOR.lore"));
    public static final SlimefunItemStack LPLATE=themed("LPLATE",Material.PAPER,Theme.ITEM1,
            get("item.LPLATE.name"),getList("item.LPLATE.lore"));
    public static final SlimefunItemStack METAL_CORE=themed("METAL_CORE",Material.NETHERITE_BLOCK,Theme.ITEM1,
            get("item.METAL_CORE.name"),getList("item.METAL_CORE.lore"));
    public static final SlimefunItemStack SMELERY_CORE=themed("SMELERY_CORE",Material.IRON_BLOCK,Theme.ITEM1,
            get("item.SMELERY_CORE.name"),getList("item.SMELERY_CORE.lore"));
    public static final SlimefunItemStack MASS_CORE=themed("MASS_CORE",Material.COAL_BLOCK,Theme.ITEM1,
            get("item.MASS_CORE.name"),getList("item.MASS_CORE.lore"));
    public static final SlimefunItemStack TECH_CORE=themed("TECH_CORE",Material.BEACON,Theme.ITEM1,
            get("item.TECH_CORE.name"),getList("item.TECH_CORE.lore"));
    public static final SlimefunItemStack SPACE_PLATE=themed("SPACE_PLATE",Material.PAPER,Theme.ITEM1,
            get("item.SPACE_PLATE.name"),getList("item.SPACE_PLATE.lore"));
    public static final SlimefunItemStack LOGIC_CORE=themed("LOGIC_CORE",Material.NETHER_STAR,Theme.ITEM1,
            get("item.LOGIC_CORE.name"),getList("item.LOGIC_CORE.lore"));
    public static final SlimefunItemStack FINAL_FRAME=themed("FINAL_FRAME",Material.BUDDING_AMETHYST,Theme.MULTIBLOCK1,
            get("item.FINAL_FRAME.name"),getList("item.FINAL_FRAME.lore"));
    public static final SlimefunItemStack REDSTONE_ENGINE=themed("REDSTONE_ENGINE",Material.SLIME_BLOCK,Theme.ITEM1,
            get("item.REDSTONE_ENGINE.name"),getList("item.REDSTONE_ENGINE.lore"));
    public static final SlimefunItemStack HYPER_LINK=themed("HYPER_LINK",Material.NETHER_STAR,Theme.ITEM1,
            get("item.HYPER_LINK.name"),getList("item.HYPER_LINK.lore"));

    public static final SlimefunItemStack SAMPLE_HEAD=themed("SAMPLE_HEAD",Material.PLAYER_HEAD,Theme.ITEM1,
            get("item.SAMPLE_HEAD.name"),getList("item.SAMPLE_HEAD.lore"));
    public static final SlimefunItemStack CHIP=themed("CHIP",Material.NAME_TAG,Theme.ITEM1,
            get("item.CHIP.name"),getList("item.CHIP.lore"));
    public static final SlimefunItemStack CHIP_CORE=themed("CHIP_CORE",CustomHead.chipCoreHead.getItem(), Theme.ITEM1,
            get("item.CHIP_CORE.name"),getList("item.CHIP_CORE.lore"));
    public static final SlimefunItemStack LSINGULARITY=themed("LSINGULARITY",Material.FIREWORK_STAR,Theme.ITEM1,
            get("item.LSINGULARITY.name"),getList("item.LSINGULARITY.lore"));
    public static final SlimefunItemStack RADIATION_CLEAR=themed("RADIATION_CLEAR",Material.GLASS_BOTTLE,Theme.ITEM1,
            get("item.RADIATION_CLEAR.name"),getList("item.RADIATION_CLEAR.lore"));
    public static final SlimefunItemStack ANTIMASS_CLEAR=themed("ANTIMASS_CLEAR",Material.GLASS_BOTTLE,Theme.ITEM1,
            get("item.ANTIMASS_CLEAR.name"),getList("item.ANTIMASS_CLEAR.lore"));
    public static final SlimefunItemStack BISILVER=themed("BISILVER",Material.IRON_INGOT,Theme.ITEM1,
            get("item.BISILVER.name"),getList("item.BISILVER.lore"));
    public static final SlimefunItemStack PAGOLD=themed("PAGOLD",Material.GOLD_INGOT,Theme.ITEM1,
            get("item.PAGOLD.name"),getList("item.PAGOLD.lore"));
    public static final SlimefunItemStack LASER=themed("LASER",CustomHead.LASER.getItem(), Theme.ITEM1,
            get("item.LASER.name"),getList("item.LASER.lore"));
    public static final SlimefunItemStack ANTIMASS=themed("ANTIMASS",Material.SCULK,Theme.ITEM1,
            get("item.ANTIMASS.name"),getList("item.ANTIMASS.lore"));
    public static final SlimefunItemStack VIRTUALWORLD=themed("VIRTUALWORLD",CustomHead.END_BLOCK.getItem(), Theme.ITEM1,
            get("item.VIRTUALWORLD.name"),getList("item.VIRTUALWORLD.lore"));
    public static final SlimefunItemStack SAMPLE_SPAWNER=themed("SAMPLE_SPAWNER",Material.SPAWNER,Theme.ITEM1,
            get("item.SAMPLE_SPAWNER.name"),getList("item.SAMPLE_SPAWNER.lore"));
    public static final SlimefunItemStack HOLOGRAM_REMOVER=themed("HOLOGRAM_REMOVER",Material.LIGHT,Theme.ITEM1,
            get("item.HOLOGRAM_REMOVER.name"),getList("item.HOLOGRAM_REMOVER.lore"));
    public static final SlimefunItemStack WITHERPROOF_REDSTONE=themed("WITHERPROOF_REDSTONE",Material.REDSTONE_BLOCK,Theme.ITEM1,
            get("item.WITHERPROOF_REDSTONE.name"),getList("item.WITHERPROOF_REDSTONE.lore"));
    public static final SlimefunItemStack WITHERPROOF_REDS=themed("WITHERPROOF_REDS",Material.REDSTONE,Theme.ITEM1,
            get("item.WITHERPROOF_REDS.name"),getList("item.WITHERPROOF_REDS.lore"));
    public static final SlimefunItemStack BEDROCK_BREAKER=themed("BEDROCK_BREAKER",Material.PISTON,Theme.ITEM1,
            get("item.BEDROCK_BREAKER.name"),getList("item.BEDROCK_BREAKER.lore"));
    public static final SlimefunItemStack LASER_GUN=themed("LASER_GUN",CustomHead.LASER_GUN.getItem(), Theme.TOOL,
            get("item.LASER_GUN.name"),getList("item.LASER_GUN.lore"));
    public static final SlimefunItemStack SUPERSPONGE=themed("SUPERSPONGE",Material.SPONGE,Theme.ITEM1,
            get("item.SUPERSPONGE.name"),getList("item.SUPERSPONGE.lore"));
    public static final SlimefunItemStack SUPERSPONGE_USED=themed("SUPERSPONGE_USED",Material.WET_SPONGE,Theme.ITEM1,
            get("item.SUPERSPONGE_USED.name"),getList("item.SUPERSPONGE_USED.lore"));
    public static final SlimefunItemStack TRACE_ARROW=themed("TRACE_ARROW",Material.CHERRY_SAPLING,Theme.TOOL,
            get("item.TRACE_ARROW.name"),getList("item.TRACE_ARROW.lore"));
    public static final SlimefunItemStack DIMENSIONAL_SINGULARITY=themed("DIMENSIONAL_SINGULARITY",Material.AMETHYST_SHARD,Theme.ITEM1,
            get("item.dimensional_singularity.name"),getList("item.dimensional_singularity.lore"));
    public static final SlimefunItemStack RTP_RUNE=themed("RTP_RUNE", CustomFireworkStar.RTP_RUNE.getItem(),Theme.ITEM1,
            get("item.RTP_RUNE.name"),getList("item.RTP_RUNE.lore"));
    public static final SlimefunItemStack SPACE_CARD=themed("SPACE_CARD",Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.SPACE_CARD.name"),getList("item.SPACE_CARD.lore"));

    public static final SlimefunItemStack UNBREAKING_SHIELD=themed("UNBREAKING_SHIELD",Material.SHIELD, Theme.TOOL,
            get("item.UNBREAKING_SHIELD.name"),getList("item.UNBREAKING_SHIELD.lore"));
    public static final ItemStack MACE_ITEM=new InitializeSafeProvider<>(ItemStack.class,()->{
        Material material= VersionedRegistry.material("MACE");
        return material==null?null: new ItemStack(material);
    }).v ();
    public static final ItemStack SUPER_COBALT_PICKAXE = new InitializeProvider<>(()->{
        ItemStack item = getCopy( resolveItem("COBALT_PICKAXE") );
        item.setType(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(VersionedRegistry.enchantment("efficiency"),10,true );
        meta.setLore(List.of(DEFAULT_COLOR+"超级钴镐"));
        item.setItemMeta(meta);
        return item;
    }).v();
    public static final SlimefunItemStack FAKE_UI=themed("FAKE_UI",Material.LIGHT_GRAY_STAINED_GLASS_PANE,Theme.ITEM1,
            get("item.FAKE_UI.name"),getList("item.FAKE_UI.lore"));
    public static final SlimefunItemStack ANTIGRAVITY=themed("ANTIGRAVITY",Material.NETHERITE_INGOT,Theme.ITEM1,
            get("item.ANTIGRAVITY.name"),getList("item.ANTIGRAVITY.lore"));
    public static final SlimefunItemStack CONFIGURE=themed("CONFIGURE",Material.BLAZE_ROD,Theme.CARGO1,
            get("item.CONFIGURE.name"),getList("item.CONFIGURE.lore"));
    public static final SlimefunItemStack AMPLIFY_BASE=themed("AMPLIFY_BASE",Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.AMPLIFY_BASE.name"),getList("item.AMPLIFY_BASE.lore"));
    public static final SlimefunItemStack SWAMP_SPEED=themed("SWAMP_SPEED",Material.MUSIC_DISC_13,Theme.ITEM1,
            get("item.SWAMP_SPEED.name"),getList("item.SWAMP_SPEED.lore"));
    public static final SlimefunItemStack SWAMP_RANGE=themed("SWAMP_RANGE",Material.MUSIC_DISC_CHIRP,Theme.ITEM1,
            get("item.SWAMP_RANGE.name"),getList("item.SWAMP_RANGE.lore"));
    public static final SlimefunItemStack MULTIBLOCKBUILDER=themed("MULTIBLOCKBUILDER",Material.BOOK,Theme.ITEM1,
            get("item.MULTIBLOCKBUILDER.name"),getList("item.MULTIBLOCKBUILDER.lore"));
    public static final SlimefunItemStack DISPLAY_FU_USE=themed("DISPLAY_FU_USE",Material.SMITHING_TABLE,Theme.TOOL,
            get("item.DISPLAY_FU_USE.name"),getList("item.DISPLAY_FU_USE.lore"));
    public static final ItemStack DISPLAY_FU_USE_1=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Theme.ITEM1,
            get("item.DISPLAY_FU_USE_1.name"),getList("item.DISPLAY_FU_USE_1.lore"));
    public static final ItemStack DISPLAY_FU_USE_2=themed(Material.NETHERITE_SWORD,Theme.ITEM1,
            get("item.DISPLAY_FU_USE_2.name"),getList("item.DISPLAY_FU_USE_2.lore"));
    public static final ItemStack DISPLAY_FU_USE_3=themed(Material.AMETHYST_SHARD,Theme.ITEM1,
            get("item.DISPLAY_FU_USE_3.name"),getList("item.DISPLAY_FU_USE_3.lore"));
    public static final SlimefunItemStack DISPLAY_REMOVE_FU=themed("DISPLAY_REMOVE_FU",Material.GRINDSTONE,Theme.TOOL,
            get("item.DISPLAY_REMOVE_FU.name"),getList("item.DISPLAY_REMOVE_FU.lore"));
    public static final ItemStack DISPLAY_REMOVE_FU_1=themed(Material.NETHERITE_SWORD,Theme.ITEM1,
            get("item.DISPLAY_REMOVE_FU_1.name"),getList("item.DISPLAY_REMOVE_FU_1.lore"));
    public static final ItemStack DISPLAY_REMOVE_FU_2=themed(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.DISPLAY_REMOVE_FU_2.name"),getList("item.DISPLAY_REMOVE_FU_2.lore"));
    public static final SlimefunItemStack FU_BASE=themed("FU_BASE",Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.FU_BASE.name"),getList("item.FU_BASE.lore"));
    public static final SlimefunItemStack DEMO_FU=themed("DEMO_FU",Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE,Theme.FUNIT,
            get("item.DEMO_FU.name"),getList("item.DEMO_FU.lore"));
    public static final SlimefunItemStack PLAYER_IDCARD=themed("PLAYER_IDCARD",Material.PAPER,Theme.ITEM1,
            get("item.PLAYER_IDCARD.name"),getList("item.PLAYER_IDCARD.lore"));
    //nachines
    public static final SlimefunItemStack HEAD_ANALYZER=themed("HEAD_ANALYZER",Material.SOUL_CAMPFIRE, Theme.MACHINE1,
            get("machine.HEAD_ANALYZER.name"),getList("machine.HEAD_ANALYZER.lore"));
    public static final SlimefunItemStack RECIPE_LOGGER=themed("RECIPE_LOGGER",Material.FLETCHING_TABLE, Theme.MACHINE1,
            get("machine.RECIPE_LOGGER.name"),getList("machine.RECIPE_LOGGER.lore"));
    public static final SlimefunItemStack BOOL_GENERATOR=themed("BOOL_GENERATOR",Material.REDSTONE_TORCH,Theme.MACHINE1,
            get("machine.BOOL_GENERATOR.name"),getList("machine.BOOL_GENERATOR.lore"));
    public static final SlimefunItemStack LOGIC_REACTOR=themed("LOGIC_REACTOR",CustomHead.logicReactorHead.getItem(),Theme.MACHINE1,
            get("machine.LOGIC_REACTOR.name"),getList("machine.LOGIC_REACTOR.lore"));
    public static final SlimefunItemStack BUG_CRAFTER=themed("BUG_CRAFTER",CustomHead.bugCrafterHead.getItem(),Theme.MACHINE1,
            get("machine.BUG_CRAFTER.name"),getList("machine.BUG_CRAFTER.lore"));
    public static final SlimefunItemStack ENDFRAME_MACHINE=themed("ENDFRAME_MACHINE",Material.END_PORTAL_FRAME,Theme.MACHINE1,
            get("machine.ENDFRAME_MACHINE.name"),getList("machine.ENDFRAME_MACHINE.lore"));
    public static final SlimefunItemStack LVOID_GENERATOR=themed("LVOID_GENERATOR",Material.SOUL_LANTERN,Theme.MACHINE1,
            get("machine.LVOID_GENERATOR.name"),getList("machine.LVOID_GENERATOR.lore"));
    public static final SlimefunItemStack SPECIAL_CRAFTER=themed("SPECIAL_CRAFTER",Material.LOOM,Theme.MACHINE1,
            get("machine.SPECIAL_CRAFTER.name"),getList("machine.SPECIAL_CRAFTER.lore"));
    public static final SlimefunItemStack STAR_SMELTERY=themed("STAR_SMELTERY",Material.BLAST_FURNACE,Theme.MACHINE1,
            get("machine.STAR_SMELTERY.name"),getList("machine.STAR_SMELTERY.lore"));

    public static final SlimefunItemStack INFINITY_AUTOCRAFT=themed("INFINITY_AUTOCRAFT",Material.CRYING_OBSIDIAN,Theme.MACHINE1,
            get("machine.INFINITY_AUTOCRAFT.name"),getList("machine.INFINITY_AUTOCRAFT.lore"));
    public static final SlimefunItemStack CHIP_MAKER=themed("CHIP_MAKER",Material.CHISELED_BOOKSHELF,Theme.MACHINE1,
            get("machine.CHIP_MAKER.name"),getList("machine.CHIP_MAKER.lore"));
    public static final SlimefunItemStack CHIP_CONSUMER=themed("CHIP_CONSUMER",Material.TORCH,Theme.MACHINE1,
            get("machine.CHIP_CONSUMER.name"),getList("machine.CHIP_CONSUMER.lore"));
    public static final SlimefunItemStack CHIP_BICONSUMER=themed("CHIP_BICONSUMER",Material.LANTERN,Theme.MACHINE1,
            get("machine.CHIP_BICONSUMER.name"),getList("machine.CHIP_BICONSUMER.lore"));
    public static final SlimefunItemStack SEQ_CONSTRUCTOR=themed("SEQ_CONSTRUCTOR",Material.BAMBOO_MOSAIC,Theme.MACHINE1,
            get("machine.SEQ_CONSTRUCTOR.name"),getList("machine.SEQ_CONSTRUCTOR.lore"));
    public static final SlimefunItemStack STACKMACHINE=themed("STACKMACHINE",Material.FURNACE,Theme.MACHINE1,
            get("machine.STACKMACHINE.name"),getList("machine.STACKMACHINE.lore"));
    public static final SlimefunItemStack ENERGY_TRASH=themed("ENERGY_TRASH", SlimefunItems.PORTABLE_DUSTBIN.getItem().getItem().clone()
            ,Theme.MACHINE1, get("machine.ENERGY_TRASH.name"),getList("machine.ENERGY_TRASH.lore"));
    public static final SlimefunItemStack OPPO_GEN=themed("OPPO_GEN",CustomHead.HOT_MACHINE.getItem(), Theme.MACHINE1,
            get("machine.OPPO_GEN.name"),getList("machine.OPPO_GEN.lore"));
    public static final SlimefunItemStack ARC_REACTOR=themed("ARC_REACTOR",CustomHead.REACTOR.getItem(),Theme.MACHINE1,
            get("machine.ARC_REACTOR.name"),getList("machine.ARC_REACTOR.lore"));
    public static final SlimefunItemStack ENERGY_AMPLIFIER=themed("ENERGY_AMPLIFIER",Material.NETHERITE_BLOCK,Theme.MACHINE1,
            get("machine.ENERGY_AMPLIFIER.name"),getList("machine.ENERGY_AMPLIFIER.lore"));
    public static final SlimefunItemStack ADVANCED_CHIP_MAKER=themed("ADVANCED_CHIP_MAKER",Material.CHISELED_BOOKSHELF,Theme.MACHINE1,
            get("machine.ADVANCED_CHIP_MAKER.name"),getList("machine.ADVANCED_CHIP_MAKER.lore"));
    public static final SlimefunItemStack CHIP_REACTOR=themed("CHIP_REACTOR",Material.JUKEBOX,Theme.MACHINE1,
            get("machine.CHIP_REACTOR.name"),getList("machine.CHIP_REACTOR.lore"));
    public static final SlimefunItemStack DUST_EXTRACTOR=themed("DUST_EXTRACTOR",Material.CHISELED_STONE_BRICKS,Theme.MACHINE1,
            get("machine.DUST_EXTRACTOR.name"),getList("machine.DUST_EXTRACTOR.lore"));
    public static final SlimefunItemStack FURNACE_FACTORY=themed("FURNACE_FACTORY",Material.FURNACE,Theme.MACHINE1,
            get("machine.FURNACE_FACTORY.name"),getList("machine.FURNACE_FACTORY.lore"));
    public static final SlimefunItemStack INGOT_FACTORY=themed("INGOT_FACTORY",Material.RED_GLAZED_TERRACOTTA,Theme.MACHINE1,
            get("machine.INGOT_FACTORY.name"),getList("machine.INGOT_FACTORY.lore"));
    public static final SlimefunItemStack FINAL_LASER=themed("FINAL_LASER",Material.DROPPER,Theme.MACHINE1,
            get("machine.FINAL_LASER.name"),getList("machine.FINAL_LASER.lore"));
    public static final SlimefunItemStack FINAL_CONVERTOR=themed("FINAL_CONVERTOR",Material.WARPED_HYPHAE,Theme.MACHINE1,
            get("machine.FINAL_CONVERTOR.name"),getList("machine.FINAL_CONVERTOR.lore"));
    public static final SlimefunItemStack PRESSOR_FACTORY=themed("PRESSOR_FACTORY",Material.PISTON,Theme.MACHINE1,
            get("machine.PRESSOR_FACTORY.name"),getList("machine.PRESSOR_FACTORY.lore"));
    public static final SlimefunItemStack CRAFTER=themed("CRAFTER",Material.CRAFTING_TABLE,Theme.MACHINE1,
            get("machine.CRAFTER.name"),getList("machine.CRAFTER.lore"));
    public static final SlimefunItemStack EASYSTACKMACHINE=themed("EASYSTACKMACHINE",Material.FURNACE,Theme.MACHINE1,
            get("machine.EASYSTACKMACHINE.name"),getList("machine.EASYSTACKMACHINE.lore"));
    public static final SlimefunItemStack CONVERTOR=themed("CONVERTOR",Material.SEA_LANTERN,Theme.MACHINE1,
            get("machine.CONVERTOR.name"),getList("machine.CONVERTOR.lore"));
    public static final SlimefunItemStack VIRTUAL_KILLER=themed("VIRTUAL_KILLER",Material.STONECUTTER,Theme.MACHINE1,
            get("machine.VIRTUAL_KILLER.name"),getList("machine.VIRTUAL_KILLER.lore"));
    public static final SlimefunItemStack INF_MOBSIMULATION=themed("INF_MOBSIMULATION",Material.GILDED_BLACKSTONE,Theme.MACHINE1,
            get("machine.INF_MOBSIMULATION.name"),getList("machine.INF_MOBSIMULATION.lore"));
    public static final SlimefunItemStack INF_GEOQUARRY=themed("INF_GEOQUARRY",Material.CHISELED_QUARTZ_BLOCK,Theme.MACHINE1,
            get("machine.INF_GEOQUARRY.name"),getList("machine.INF_GEOQUARRY.lore"));
    public static final SlimefunItemStack RAND_EDITOR=themed("RAND_EDITOR",Material.ENCHANTING_TABLE,Theme.MACHINE1,
            get("machine.RAND_EDITOR.name"),getList("machine.RAND_EDITOR.lore"));
    public static final SlimefunItemStack ATTR_OP=themed("ATTR_OP",Material.ENCHANTING_TABLE,Theme.MACHINE1,
            get("machine.ATTR_OP.name"),getList("machine.ATTR_OP.lore"));
    public static final SlimefunItemStack CUSTOM_CHARGER=themed("CUSTOM_CHARGER",Material.LODESTONE,Theme.MACHINE1,
            get("machine.CUSTOM_CHARGER.name"),getList("machine.CUSTOM_CHARGER.lore"));
    public static final SlimefunItemStack GRIND_FACTORY=themed("GRIND_FACTORY",Material.GRINDSTONE,Theme.MACHINE1,
            get("machine.GRIND_FACTORY.name"),getList("machine.GRIND_FACTORY.lore"));
    public static final SlimefunItemStack TNT_GEN=themed("TNT_GEN",Material.NOTE_BLOCK,Theme.MACHINE1,
            get("machine.TNT_GEN.name"),getList("machine.TNT_GEN.lore"));
    public static final SlimefunItemStack ADVANCE_BREWER=themed("ADVANCE_BREWER",Material.SMOKER,Theme.MACHINE1,
            get("machine.ADVANCE_BREWER.name"),getList("machine.ADVANCE_BREWER.lore"));
    public static final SlimefunItemStack SIMU_LVOID=themed("SIMU_LVOID",Material.SOUL_TORCH,Theme.MACHINE1,
            get("machine.SIMU_LVOID.name"),getList("machine.SIMU_LVOID.lore"));
    public static final SlimefunItemStack SPACETOWER =themed("SPACETOWER",Material.SHROOMLIGHT,Theme.MACHINE1,
            get("machine.SPACETOWER.name"),getList("machine.SPACETOWER.lore"));
    public static final SlimefunItemStack SPACETOWER_FRAME=themed("SPACETOWER_FRAME",Material.AMETHYST_BLOCK,Theme.MACHINE1,
            get("machine.SPACETOWER_FRAME.name"),getList("machine.SPACETOWER_FRAME.lore"));
    public static final SlimefunItemStack ITEM_OP=themed("ITEM_OP",Material.SMITHING_TABLE,Theme.MACHINE1,
            get("machine.ITEM_OP.name"),getList("machine.ITEM_OP.lore"));
    public static final SlimefunItemStack CHUNK_CHARGER=themed("CHUNK_CHARGER",Material.SCULK_SHRIEKER,Theme.MACHINE1,
            get("machine.CHUNK_CHARGER.name"),getList("machine.CHUNK_CHARGER.lore"));
    public static final SlimefunItemStack INGOT_CONVERTOR=themed("INGOT_CONVERTOR",Material.PINK_GLAZED_TERRACOTTA,Theme.MACHINE1,
            get("machine.INGOT_CONVERTOR.name"),getList("machine.INGOT_CONVERTOR.lore"));
    public static final SlimefunItemStack LINE_CHARGER=themed("LINE_CHARGER",Material.DEEPSLATE_TILE_WALL,Theme.MACHINE1,
            get("machine.LINE_CHARGER.name"),getList("machine.LINE_CHARGER.lore"));
    public static final SlimefunItemStack LINE_CHARGER_PLUS=themed("LINE_CHARGER_PLUS",Material.DEEPSLATE_TILE_WALL,Theme.MACHINE1,
            get("machine.LINE_CHARGER_PLUS.name"),getList("machine.LINE_CHARGER_PLUS.lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR=themed("ADJ_COLLECTOR",Material.RED_NETHER_BRICKS,Theme.MACHINE1,
            get("machine.ADJ_COLLECTOR.name"),getList("machine.ADJ_COLLECTOR.lore"));
    public static final SlimefunItemStack ADJ_COLLECTOR_PLUS=themed("ADJ_COLLECTOR_PLUS",Material.RED_NETHER_BRICKS,Theme.MACHINE1,
            get("machine.ADJ_COLLECTOR_PLUS.name"),getList("machine.ADJ_COLLECTOR_PLUS.lore"));
    public static final SlimefunItemStack LINE_COLLECTOR=themed("LINE_COLLECTOR",Material.RED_NETHER_BRICK_WALL,Theme.MACHINE1,
            get("machine.LINE_COLLECTOR.name"),getList("machine.LINE_COLLECTOR.lore"));
    public static final SlimefunItemStack LINE_COLLECTOR_PLUS=themed("LINE_COLLECTOR_PLUS",Material.RED_NETHER_BRICK_WALL,Theme.MACHINE1,
            get("machine.LINE_COLLECTOR_PLUS.name"),getList("machine.LINE_COLLECTOR_PLUS.lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_NONE=themed("ENERGY_STORAGE_NONE",Material.CRACKED_DEEPSLATE_TILES,Theme.MACHINE1,
            get("machine.ENERGY_STORAGE_NONE.name"),getList("machine.ENERGY_STORAGE_NONE.lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IN=themed("ENERGY_STORAGE_IN",Material.CHISELED_DEEPSLATE,Theme.MACHINE1,
            get("machine.ENERGY_STORAGE_IN.name"),getList("machine.ENERGY_STORAGE_IN.lore"));
    public static final SlimefunItemStack ENERGY_STORAGE_IO=themed("ENERGY_STORAGE_IO",Material.CHISELED_POLISHED_BLACKSTONE,Theme.MACHINE1,
            get("machine.ENERGY_STORAGE_IO.name"),getList("machine.ENERGY_STORAGE_IO.lore"));
    public static final SlimefunItemStack ADJ_CHARGER=themed("ADJ_CHARGER",Material.DEEPSLATE_BRICKS,Theme.MACHINE1,
            get("machine.ADJ_CHARGER.name"),getList("machine.ADJ_CHARGER.lore"));
    public static final SlimefunItemStack ADJ_CHARGER_PLUS=themed("ADJ_CHARGER_PLUS",Material.DEEPSLATE_BRICKS,Theme.MACHINE1,
            get("machine.ADJ_CHARGER_PLUS.name"),getList("machine.ADJ_CHARGER_PLUS.lore"));
    public static final SlimefunItemStack ENERGY_PIPE=themed("ENERGY_PIPE",Material.LIGHTNING_ROD,Theme.MACHINE1,
            get("machine.ENERGY_PIPE.name"),getList("machine.ENERGY_PIPE.lore"));
    public static final SlimefunItemStack ENERGY_PIPE_PLUS=themed("ENERGY_PIPE_PLUS",Material.END_ROD,Theme.MACHINE1,
            get("machine.ENERGY_PIPE_PLUS.name"),getList("machine.ENERGY_PIPE_PLUS.lore"));
    public static final SlimefunItemStack FINAL_CRAFT=themed("FINAL_CRAFT",Material.BEACON,Theme.MACHINE1,
            get("machine.FINAL_CRAFT.name"),getList("machine.FINAL_CRAFT.lore"));
    public static final SlimefunItemStack VIRTUAL_EXPLORER=themed("VIRTUAL_EXPLORER",Material.DECORATED_POT,Theme.MACHINE1,
            get("machine.VIRTUAL_EXPLORER.name"),getList("machine.VIRTUAL_EXPLORER.lore"));
    public static final SlimefunItemStack TIMER_BLOCKENTITY=themed("TIMER_BLOCKENTITY",Material.REDSTONE_TORCH,Theme.MACHINE1,
            get("machine.TIMER_BLOCKENTITY.name"),getList("machine.TIMER_BLOCKENTITY.lore"));
    public static final SlimefunItemStack TIMER_RD=themed("TIMER_RD",Material.TORCH,Theme.MACHINE1,
            get("machine.TIMER_RD.name"),getList("machine.TIMER_RD.lore"));
    public static final SlimefunItemStack TIMER_SF=new InitializeSafeProvider<>(SlimefunItemStack.class,()->themed("TIMER_SF_入机",Material.SOUL_TORCH,Theme.MACHINE1,
            get("machine.TIMER_SF.name"),getList("machine.TIMER_SF.lore"))).v();

//    public static final SlimefunItemStack TIMER_SF_SEQ=themed("TIMER_SF_SEQ",Material.SOUL_TORCH,Theme.MACHINE1,
//            get("machine.TIMER_SF_SEQ.name"),getList("machine.TIMER_SF_SEQ.lore"));
    //manuals
    public static final SlimefunItemStack MANUAL_CORE=themed("MANUAL_CORE",Material.AMETHYST_SHARD,Theme.ITEM1,
            get("manuals.MANUAL_CORE.name"),getList("manuals.MANUAL_CORE.lore"));
    public static final SlimefunItemStack CRAFT_MANUAL=themed("CRAFT_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("manuals.CRAFT_MANUAL.name"),getList("manuals.CRAFT_MANUAL.lore"));
    public static final SlimefunItemStack FURNACE_MANUAL=themed("FURNACE_MANUAL",Material.FURNACE,Theme.MANUAL1,
            get("manuals.FURNACE_MANUAL.name"),getList("manuals.FURNACE_MANUAL.lore"));
    public static final SlimefunItemStack ENHANCED_CRAFT_MANUAL=themed("ENHANCED_CRAFT_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("manuals.ENHANCED_CRAFT_MANUAL.name"),getList("manuals.ENHANCED_CRAFT_MANUAL.lore"));
    public static final SlimefunItemStack GRIND_MANUAL=themed("GRIND_MANUAL",Material.DISPENSER,Theme.MANUAL1,
            get("manuals.GRIND_MANUAL.name"),getList("manuals.GRIND_MANUAL.lore"));
    public static final SlimefunItemStack ARMOR_FORGE_MANUAL=themed("ARMOR_FORGE_MANUAL",Material.IRON_BLOCK,Theme.MANUAL1,
            get("manuals.ARMOR_FORGE_MANUAL.name"),getList("manuals.ARMOR_FORGE_MANUAL.lore"));
    public static final SlimefunItemStack ORE_CRUSHER_MANUAL=themed("ORE_CRUSHER_MANUAL",Material.DROPPER,Theme.MANUAL1,
            get("manuals.ORE_CRUSHER_MANUAL.name"),getList("manuals.ORE_CRUSHER_MANUAL.lore"));
    public static final SlimefunItemStack COMPRESSOR_MANUAL=themed("COMPRESSOR_MANUAL",Material.PISTON,Theme.MANUAL1,
            get("manuals.COMPRESSOR_MANUAL.name"),getList("manuals.COMPRESSOR_MANUAL.lore"));
    public static final SlimefunItemStack PRESSURE_MANUAL=themed("PRESSURE_MANUAL",Material.GLASS,Theme.MANUAL1,
            get("manuals.PRESSURE_MANUAL.name"),getList("manuals.PRESSURE_MANUAL.lore"));
    public static final SlimefunItemStack MAGIC_WORKBENCH_MANUAL=themed("MAGIC_WORKBENCH_MANUAL",Material.BOOKSHELF,Theme.MANUAL1,
            get("manuals.MAGIC_WORKBENCH_MANUAL.name"),getList("manuals.MAGIC_WORKBENCH_MANUAL.lore"));
    public static final SlimefunItemStack ORE_WASHER_MANUAL=themed("ORE_WASHER_MANUAL",Material.BLUE_STAINED_GLASS,Theme.MANUAL1,
            get("manuals.ORE_WASHER_MANUAL.name"),getList("manuals.ORE_WASHER_MANUAL.lore"));
    public static final SlimefunItemStack GOLD_PAN_MANUAL=themed("GOLD_PAN_MANUAL",Material.BROWN_TERRACOTTA,Theme.MANUAL1,
            get("manuals.GOLD_PAN_MANUAL.name"),getList("manuals.GOLD_PAN_MANUAL.lore"));
    public static final SlimefunItemStack ANCIENT_ALTAR_MANUAL=themed("ANCIENT_ALTAR_MANUAL",Material.ENCHANTING_TABLE,Theme.MANUAL1,
            get("manuals.ANCIENT_ALTAR_MANUAL.name"),getList("manuals.ANCIENT_ALTAR_MANUAL.lore"));
    public static final SlimefunItemStack SMELTERY_MANUAL=themed("SMELTERY_MANUAL",Material.BLAST_FURNACE,Theme.MANUAL1,
            get("manuals.SMELTERY_MANUAL.name"),getList("manuals.SMELTERY_MANUAL.lore"));
    public static final SlimefunItemStack CRUCIBLE_MANUAL=themed("CRUCIBLE_MANUAL",Material.RED_TERRACOTTA,Theme.MANUAL1,
            get("manuals.CRUCIBLE_MANUAL.name"),getList("manuals.CRUCIBLE_MANUAL.lore"));
    public static final SlimefunItemStack PULVERIZER_MANUAL=themed("PULVERIZER_MANUAL",Material.GRINDSTONE,Theme.MANUAL1,
            get("manuals.PULVERIZER_MANUAL.name"),getList("manuals.PULVERIZER_MANUAL.lore"));
    public static final SlimefunItemStack MULTICRAFTTABLE_MANUAL=themed("MULTICRAFTTABLE_MANUAL",Material.CRAFTING_TABLE,Theme.MANUAL1,
            get("manuals.MULTICRAFTTABLE_MANUAL.name"),getList("manuals.MULTICRAFTTABLE_MANUAL.lore"));
    public static final SlimefunItemStack TABLESAW_MANUAL=themed("TABLESAW_MANUAL",Material.STONECUTTER,Theme.MANUAL1,
            get("manuals.TABLESAW_MANUAL.name"),getList("manuals.TABLESAW_MANUAL.lore"));
    public static final SlimefunItemStack COMPOSTER=themed("COMPOSTER",Material.CAULDRON,Theme.MANUAL1,
            get("manuals.COMPOSTER.name"),getList("manuals.COMPOSTER.lore"));
    public static final SlimefunItemStack MULTIMACHINE_MANUAL=themed("MULTIMACHINE_MANUAL",Material.GRAY_STAINED_GLASS,Theme.MANUAL1,
            get("manuals.MULTIMACHINE_MANUAL.name"),getList("manuals.MULTIMACHINE_MANUAL.lore"));
    public static final SlimefunItemStack MOBDATA_MANUAL=themed("MOBDATA_MANUAL",Material.LODESTONE,Theme.MANUAL1,
            get("manuals.MOBDATA_MANUAL.name"),getList("manuals.MOBDATA_MANUAL.lore"));
    public static final SlimefunItemStack INFINITY_MANUAL=themed("INFINITY_MANUAL",Material.RESPAWN_ANCHOR,Theme.MANUAL1,
            get("manuals.INFINITY_MANUAL.name"),getList("manuals.INFINITY_MANUAL.lore"));
    public static final SlimefunItemStack NTWWORKBENCH_MANUAL=themed("NTWWORKBENCH_MANUAL",Material.BAMBOO_BLOCK,Theme.MANUAL1,
            get("manuals.NTWWORKBENCH_MANUAL.name"),getList("manuals.NTWWORKBENCH_MANUAL.lore"));
    public static final SlimefunItemStack MULTIBLOCK_MANUAL=themed("MULTIBLOCK_MANUAL",Material.BRICKS,Theme.MANUAL1,
            get("manuals.MULTIBLOCK_MANUAL.name"),getList("manuals.MULTIBLOCK_MANUAL.lore"));
    public static final SlimefunItemStack FINAL_MANUAL=themed("FINAL_MANUAL",Material.REINFORCED_DEEPSLATE,Theme.MANUAL1,
            get("manuals.FINAL_MANUAL.name"),getList("manuals.FINAL_MANUAL.lore"));
    public static final SlimefunItemStack REPLACE_CARD=themed("REPLACE_CARD",Material.PRIZE_POTTERY_SHERD,Theme.ITEM1,
            get("item.REPLACE_CARD.name"),getList("item.REPLACE_CARD.lore"));
    public static final SlimefunItemStack REPLACE_SF_CARD=themed("REPLACE_SF_CARD",Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.REPLACE_SF_CARD.name"),getList("item.REPLACE_SF_CARD.lore"));
    public static final SlimefunItemStack CARD_MAKER=themed("CARD_MAKER",Material.FLETCHING_TABLE,Theme.MACHINE1,
            get("manuals.CARD_MAKER.name"),getList("manuals.CARD_MAKER.lore"));
    public static final SlimefunItemStack ADV_MANUAL=themed("ADV_MANUAL",Material.LECTERN,Theme.MANUAL1,
            get("manuals.ADV_MANUAL.name"),getList("manuals.ADV_MANUAL.lore"));
    public static final SlimefunItemStack PORTABLE_MANUAL=new InitializeProvider<>(()->{
        SlimefunItemStack item= themed("PORTABLE_MANUAL",CustomHead.CRAFTER.getItem(),Theme.MANUAL1,
                get("manuals.PORTABLE_MANUAL.name"),getList("manuals.PORTABLE_MANUAL.lore"));
        try{
            int portableCrafter = Slimefun.getItemTextureService().getModelData("PORTABLE_CRAFTER");
            if(portableCrafter!=0){
                String itemId = item.getItemId();
                FieldAccess.AccessWithObject<Config> configAccess = (FieldAccess.ofName("config").ofAccess( Slimefun.getItemTextureService()));
                configAccess.get((config)->{
                    config.setValue(itemId,portableCrafter);
                });
                item = new SlimefunItemStack(itemId,item);
            }
        }catch (Throwable ignored){        }
        return item;
    }).v();
    //generators
    public static final SlimefunItemStack MAGIC_STONE=themed("MAGIC_STONE",Material.COBBLESTONE,Theme.MACHINE2,
            get("generator.MAGIC_STONE.name"),getList("generator.MAGIC_STONE.lore"));
    public static final SlimefunItemStack BOOL_MG=themed("BOOL_MG",Material.REDSTONE_TORCH,Theme.MACHINE2,
            get("generator.BOOL_MG.name"),getList("generator.BOOL_MG.lore"));
    public static final SlimefunItemStack OVERWORLD_MINER=themed("OVERWORLD_MINER",Material.SMOOTH_STONE,Theme.MACHINE2,
            get("generator.OVERWORLD_MINER.name"),getList("generator.OVERWORLD_MINER.lore"));
    public static final SlimefunItemStack NETHER_MINER=themed("NETHER_MINER",Material.CRIMSON_NYLIUM,Theme.MACHINE2,
            get("generator.NETHER_MINER.name"),getList("generator.NETHER_MINER.lore"));
    public static final SlimefunItemStack END_MINER =themed("END_MINER",Material.END_STONE_BRICKS,Theme.MACHINE2,
            get("generator.END_MINER.name"),getList("generator.END_MINER.lore"));
    public static final SlimefunItemStack DIMENSION_MINER=themed("DIMENSION_MINER",Material.CRYING_OBSIDIAN,Theme.MACHINE2,
            get("generator.DIMENSION_MINER.name"),getList("generator.DIMENSION_MINER.lore"));
    public static final SlimefunItemStack REDSTONE_MG=themed("REDSTONE_MG",Material.OBSERVER,Theme.MACHINE2,
            get("generator.REDSTONE_MG.name"),getList("generator.REDSTONE_MG.lore"));
    public static final SlimefunItemStack DUPE_MG=themed("DUPE_MG",Material.STICKY_PISTON,Theme.MACHINE2,
            get("generator.DUPE_MG.name"),getList("generator.DUPE_MG.lore"));
    public static final SlimefunItemStack ENDDUPE_MG=themed("ENDDUPE_MG",Material.END_PORTAL_FRAME,Theme.MACHINE2,
            get("generator.ENDDUPE_MG.name"),getList("generator.ENDDUPE_MG.lore"));
    public static final SlimefunItemStack BNOISE_MAKER = themed("BNOISE_MAKER", Material.JUKEBOX, Theme.MACHINE2,
            get("generator.BNOISE_MAKER.name"), getList("generator.BNOISE_MAKER.lore"));
    public static final SlimefunItemStack BNOISE_HEAD = themed("BNOISE_HEAD", CustomHead.BNOISE_HEAD.getItem(), Theme.ITEM1,
            get("item.BNOISE_HEAD.name"), getList("item.BNOISE_HEAD.lore"));
    public static final SlimefunItemStack STACKMGENERATOR=themed("STACKMGENERATOR",Material.SMOOTH_STONE,Theme.MACHINE2,
            get("generator.STACKMGENERATOR.name"),getList("generator.STACKMGENERATOR.lore"));
    public static final SlimefunItemStack REVERSE_GENERATOR=themed("REVERSE_GENERATOR",CustomHead.reverseGeneratorHead.getItem(), Theme.MACHINE2,
            get("generator.REVERSE_GENERATOR.name"),getList("generator.REVERSE_GENERATOR.lore"));
    public static final SlimefunItemStack VIRTUAL_MINER=themed("VIRTUAL_MINER",Material.CHERRY_WOOD,Theme.MACHINE2,
            get("generator.VIRTUAL_MINER.name"),getList("generator.VIRTUAL_MINER.lore"));
    public static final SlimefunItemStack VIRTUAL_PLANT=themed("VIRTUAL_PLANT",Material.STRIPPED_CHERRY_WOOD,Theme.MACHINE2,
            get("generator.VIRTUAL_PLANT.name"),getList("generator.VIRTUAL_PLANT.lore"));
    public static final SlimefunItemStack MAGIC_PLANT=themed("MAGIC_PLANT",Material.DIRT,Theme.MACHINE2,
            get("generator.MAGIC_PLANT.name"),getList("generator.MAGIC_PLANT.lore"));
    public static final SlimefunItemStack OVERWORLD_PLANT=themed("OVERWORLD_PLANT",Material.PODZOL,Theme.MACHINE2,
            get("generator.OVERWORLD_PLANT.name"),getList("generator.OVERWORLD_PLANT.lore"));
    public static final SlimefunItemStack NETHER_PLANT=themed("NETHER_PLANT",Material.WARPED_NYLIUM,Theme.MACHINE2,
            get("generator.NETHER_PLANT.name"),getList("generator.NETHER_PLANT.lore"));
    public static final SlimefunItemStack END_PLANT=themed("END_PLANT",Material.END_STONE,Theme.MACHINE2,
            get("generator.END_PLANT.name"),getList("generator.END_PLANT.lore"));
    public static final SlimefunItemStack SMELTRY=themed("SMELTRY",Material.FURNACE,Theme.MACHINE1,
            get("machine.SMELTRY.name"),getList("machine.SMELTRY.lore"));
    public static final SlimefunItemStack STONE_FACTORY=themed("STONE_FACTORY",Material.STONE_BRICKS,Theme.MACHINE2,
            get("generator.STONE_FACTORY.name"),getList("generator.STONE_FACTORY.lore"));
    public static final SlimefunItemStack TNT_MG=themed("TNT_MG",Material.ANCIENT_DEBRIS,Theme.MACHINE2,
            get("generator.TNT_MG.name"),getList("generator.TNT_MG.lore"));
    //cargos
    public static final SlimefunItemStack CARGO_PART=themed("CARGO_PART",Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.CARGO_PART.name"),getList("item.CARGO_PART.lore"));
    public static final SlimefunItemStack CARGO_CONFIG=themed("CARGO_CONFIG",Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,Theme.ITEM1,
            get("item.CARGO_CONFIG.name"),getList("item.CARGO_CONFIG.lore"));
    public static final SlimefunItemStack CARGO_CONFIGURATOR=themed("CARGO_CONFIGURATOR",Material.JUKEBOX,Theme.CARGO1,
            get("item.CARGO_CONFIGURATOR.name"),getList("item.CARGO_CONFIGURATOR.lore"));
    public static final SlimefunItemStack SIMPLE_CARGO=themed("SIMPLE_CARGO",Material.TARGET,Theme.CARGO1,
            get("cargo.SIMPLE_CARGO.name"),getList("cargo.SIMPLE_CARGO.lore"));
    public static final SlimefunItemStack REMOTE_CARGO=themed("REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,Theme.CARGO1,
            get("cargo.REMOTE_CARGO.name"),getList("cargo.REMOTE_CARGO.lore"));
    public static final SlimefunItemStack LINE_CARGO=themed("LINE_CARGO",Material.OBSERVER,Theme.CARGO1,
            get("cargo.LINE_CARGO.name"),getList("cargo.LINE_CARGO.lore"));
    public static final SlimefunItemStack BISORTER=themed("BISORTER",Material.VERDANT_FROGLIGHT,Theme.CARGO1,
            get("cargo.BISORTER.name"),getList("cargo.BISORTER.lore"));
    public static final SlimefunItemStack QUARSORTER=themed("QUARSORTER",Material.PEARLESCENT_FROGLIGHT,Theme.CARGO1,
            get("cargo.QUARSORTER.name"),getList("cargo.QUARSORTER.lore"));
    public static final SlimefunItemStack OCTASORTER=themed("OCTASORTER",Material.OCHRE_FROGLIGHT,Theme.CARGO1,
            get("cargo.OCTASORTER.name"),getList("cargo.OCTASORTER.lore"));
    public static final SlimefunItemStack ADV_TRASH=themed("ADV_TRASH",CustomHead.FIRE_GENERATOR.getItem(), Theme.CARGO1,
            get("cargo.ADV_TRASH.name"),getList("cargo.ADV_TRASH.lore"));
    public static final SlimefunItemStack QUANTUM_TRASH=themed("QUANTUM_TRASH",CustomHead.TRASHCAN_RECYCLE.getItem(), Theme.CARGO1,
            get("cargo.QUANTUM_TRASH.name"),getList("cargo.QUANTUM_TRASH.lore"));
    public static final SlimefunItemStack STORAGE_OPERATOR=themed("STORAGE_OPERATOR",Material.CARTOGRAPHY_TABLE,Theme.CARGO1,
            get("cargo.STORAGE_OPERATOR.name"),getList("cargo.STORAGE_OPERATOR.lore"));
    public static final SlimefunItemStack ADV_ADJACENT_CARGO=themed("ADV_ADJACENT_CARGO",Material.TARGET,Theme.CARGO1,
            get("cargo.ADV_ADJACENT_CARGO.name"),getList("cargo.ADV_ADJACENT_CARGO.lore"));
    public static final SlimefunItemStack ADV_REMOTE_CARGO=themed("ADV_REMOTE_CARGO",Material.CALIBRATED_SCULK_SENSOR,Theme.CARGO1,
            get("cargo.ADV_REMOTE_CARGO.name"),getList("cargo.ADV_REMOTE_CARGO.lore"));
    public static final SlimefunItemStack ADV_LINE_CARGO=themed("ADV_LINE_CARGO",Material.OBSERVER,Theme.CARGO1,
            get("cargo.ADV_LINE_CARGO.name"),getList("cargo.ADV_LINE_CARGO.lore"));
    public static final SlimefunItemStack REDSTONE_ADJACENT_CARGO=themed("REDSTONE_ADJACENT_CARGO",Material.REDSTONE_LAMP,Theme.CARGO1,
            get("cargo.REDSTONE_ADJACENT_CARGO.name"),getList("cargo.REDSTONE_ADJACENT_CARGO.lore"));
    public static final SlimefunItemStack CHIP_ADJ_CARGO=themed("CHIP_ADJ_CARGO",Material.SHROOMLIGHT,Theme.CARGO1,
            get("cargo.CHIP_ADJ_CARGO.name"),getList("cargo.CHIP_ADJ_CARGO.lore"));
    public static final SlimefunItemStack RESETTER=themed("RESETTER",Material.FLETCHING_TABLE,Theme.CARGO1,
            get("cargo.RESETTER.name"),getList("cargo.RESETTER.lore"));
    public static final SlimefunItemStack STORAGE_SINGULARITY= themed("STORAGE_SINGULARITY",Material.NETHER_STAR,Theme.ITEM1,
            get("cargo.STORAGE_SINGULARITY.name"),getList("cargo.STORAGE_SINGULARITY.lore"));
    public static final SlimefunItemStack QUANTUM_LINK=themed("QUANTUM_LINK",Material.NETHER_STAR,Theme.ITEM1,
            get("cargo.QUANTUM_LINK.name"),getList("cargo.QUANTUM_LINK.lore"));
    public static final SlimefunItemStack INPORT=themed("INPORT",Material.END_STONE,Theme.MACHINE1,
            get("cargo.INPORT.name"),getList("cargo.INPORT.lore"));
    public static final SlimefunItemStack OUTPORT=themed("OUTPORT",Material.END_STONE,Theme.MACHINE1,
            get("cargo.OUTPORT.name"),getList("cargo.OUTPORT.lore"));
    public static final SlimefunItemStack IOPORT=themed("IOPORT",Material.PURPUR_PILLAR,Theme.MACHINE1,
            get("cargo.IOPORT.name"),getList("cargo.IOPORT.lore"));
    public static final SlimefunItemStack STORAGE=themed("STORAGE",Material.LIGHT_GRAY_STAINED_GLASS,Theme.CARGO1,
            get("cargo.STORAGE.name"),getList("cargo.STORAGE.lore"));
    public static final SlimefunItemStack STORAGE_INPUT=themed("STORAGE_INPUT",Material.BLUE_STAINED_GLASS,Theme.CARGO1,
            get("cargo.STORAGE_INPUT.name"),getList("cargo.STORAGE_INPUT.lore"));
    public static final SlimefunItemStack STORAGE_OUTPUT=themed("STORAGE_OUTPUT",Material.RED_STAINED_GLASS,Theme.CARGO1,
            get("cargo.STORAGE_OUTPUT.name"),getList("cargo.STORAGE_OUTPUT.lore"));
    public static final SlimefunItemStack BIFILTER=themed("BIFILTER",Material.PRISMARINE,Theme.CARGO1,
            get("cargo.BIFILTER.name"),getList("cargo.BIFILTER.lore"));
    public static final SlimefunItemStack QUARFILTER=themed("QUARFILTER",Material.PRISMARINE_BRICKS,Theme.CARGO1,
            get("cargo.QUARFILTER.name"),getList("cargo.QUARFILTER.lore"));
    public static final SlimefunItemStack OCTAFILTER=themed("OCTAFILTER",Material.DARK_PRISMARINE,Theme.CARGO1,
            get("cargo.OCTAFILTER.name"),getList("cargo.OCTAFILTER.lore"));
    public static final SlimefunItemStack CARGO_PIP=themed("CARGO_PIP",Material.END_ROD,Theme.CARGO1,
            get("cargo.CARGO_PIP.name"),getList("cargo.CARGO_PIP.lore"));
    //multiblock
    public static final SlimefunItemStack PORTAL_CORE=themed("PORTAL_CORE",Material.CRYING_OBSIDIAN,Theme.MULTIBLOCK1,
            get("multiblock.PORTAL_CORE.name"),getList("multiblock.PORTAL_CORE.lore"));
    public static final SlimefunItemStack PORTAL_FRAME=themed("PORTAL_FRAME",Material.IRON_BLOCK,Theme.MULTIBLOCK2,
            get("multiblock.PORTAL_FRAME.name"),getList("multiblock.PORTAL_FRAME.lore"));
    public static final SlimefunItemStack SOLAR_REACTOR=themed("SOLAR_REACTOR",Material.LODESTONE,Theme.MULTIBLOCK1,
            get("multiblock.SOLAR_REACTOR.name"),getList("multiblock.SOLAR_REACTOR.lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_FRAME=themed("SOLAR_REACTOR_FRAME",Material.CHISELED_QUARTZ_BLOCK,Theme.MULTIBLOCK2,
            get("multiblock.SOLAR_REACTOR_FRAME.name"),getList("multiblock.SOLAR_REACTOR_FRAME.lore"));
    public static final SlimefunItemStack SOLAR_REACTOR_GLASS=themed("SOLAR_REACTOR_GLASS",Material.TINTED_GLASS,Theme.MULTIBLOCK2,
            get("multiblock.SOLAR_REACTOR_GLASS.name"),getList("multiblock.SOLAR_REACTOR_GLASS.lore"));
    public static final SlimefunItemStack SOLAR_INPUT=themed("SOLAR_INPUT",Material.WAXED_OXIDIZED_COPPER,Theme.MULTIBLOCK2,
            get("multiblock.SOLAR_INPUT.name"),getList("multiblock.SOLAR_INPUT.lore"));
    public static final SlimefunItemStack SOLAR_OUTPUT=themed("SOLAR_OUTPUT",Material.WAXED_COPPER_BLOCK,Theme.MULTIBLOCK2,
            get("multiblock.SOLAR_OUTPUT.name"),getList("multiblock.SOLAR_OUTPUT.lore"));
    public static final SlimefunItemStack TRANSMUTATOR_FRAME=themed("TRANSMUTATOR_FRAME",Material.SMOOTH_STONE, Theme.MULTIBLOCK2,
            get("multiblock.TRANSMUTATOR_FRAME.name"),getList("multiblock.TRANSMUTATOR_FRAME.lore"));
    public static final SlimefunItemStack TRANSMUTATOR_GLASS=themed("TRANSMUTATOR_GLASS",Material.LIGHT_GRAY_STAINED_GLASS,Theme.MULTIBLOCK2,
            get("multiblock.TRANSMUTATOR_GLASS.name"),getList("multiblock.TRANSMUTATOR_GLASS.lore"));
    public static final SlimefunItemStack TRANSMUTATOR_ROD=themed("TRANSMUTATOR_ROD",Material.REINFORCED_DEEPSLATE,Theme.MULTIBLOCK2,
            get("multiblock.TRANSMUTATOR_ROD.name"),getList("multiblock.TRANSMUTATOR_ROD.lore"));
    public static final SlimefunItemStack TRANSMUTATOR=themed("TRANSMUTATOR",Material.FURNACE,Theme.MULTIBLOCK1,
            get("multiblock.TRANSMUTATOR.name"),getList("multiblock.TRANSMUTATOR.lore"));
    public static final SlimefunItemStack FINAL_BASE=themed("FINAL_BASE",Material.POLISHED_DEEPSLATE,Theme.MULTIBLOCK1,
            get("multiblock.FINAL_BASE.name"),getList("multiblock.FINAL_BASE.lore"));
    public static final SlimefunItemStack FINAL_ALTAR=themed("FINAL_ALTAR",Material.CHISELED_DEEPSLATE,Theme.MULTIBLOCK2,
            get("multiblock.FINAL_ALTAR.name"),getList("multiblock.FINAL_ALTAR.lore"));
    public static final SlimefunItemStack SMITH_WORKSHOP=themed("SMITH_WORKSHOP",Material.RESPAWN_ANCHOR,Theme.MACHINE1,
            get("multiblock.SMITH_WORKSHOP.name"),getList("multiblock.SMITH_WORKSHOP.lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_NONE=themed("SMITH_INTERFACE_NONE",Material.SEA_LANTERN,Theme.MACHINE1,
            get("multiblock.SMITH_INTERFACE_NONE.name"),getList("multiblock.SMITH_INTERFACE_NONE.lore"));
    public static final SlimefunItemStack SMITH_INTERFACE_CRAFT=themed("SMITH_INTERFACE_CRAFT",Material.TARGET,Theme.MACHINE1,
            get("multiblock.SMITH_INTERFACE_CRAFT.name"),getList("multiblock.SMITH_INTERFACE_CRAFT.lore"));
    //feat
    public static final SlimefunItemStack CUSTOM1=
            themed("CUSTOM1",new ItemStack(Material.COMMAND_BLOCK),Theme.ITEM1,"测试物件1","只是一个简单的测试");
    public static final SlimefunItemStack MACHINE1=
            themed("MACHINE1",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机器1","tnnd对照组");
    public static final SlimefunItemStack MACHINE2=
            themed("MACHINE2",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机2","tnnd实验组");
    public static final SlimefunItemStack MACHINE3=
            themed("MACHINE3",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机3","tnnd测试组 AbstractProcessor");
    public static final SlimefunItemStack MACHINE4=
            themed("MACHINE4",new ItemStack(Material.FURNACE),Theme.MACHINE1,"测试机4","tnnd测试组 AbstractAdvancedProcessor");
    public static final SlimefunItemStack SMG1=
            themed("SMG1",new ItemStack(Material.DIAMOND_BLOCK),Theme.MACHINE2,"测试生成器1","测测我的");
    public static final SlimefunItemStack MMG1=
            themed("MMG1",new ItemStack(Material.EMERALD_BLOCK),Theme.MACHINE2,"定向生成器1","测测我的");
    public static final SlimefunItemStack MANUAL1=
            themed("MANUAL1",new ItemStack(Material.CRAFTING_TABLE),Theme.MANUAL1,"测试快捷机器","强化工作台");
    public static final SlimefunItemStack MANUAL_MULTI=
            themed("MANUAL_MULTI",new ItemStack(Material.CRAFTING_TABLE),Theme.MANUAL1,"测试快捷机器","多方块机器");
    public static final SlimefunItemStack MANUAL_KILL=
            themed("MANUAL_KILL",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试快捷机器","击杀掉落");
    public static final SlimefunItemStack MANUAL_INF=
            themed("MANUAL_INF",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试快捷机器","无尽工作台");
    public static final SlimefunItemStack MANUAL_MOB=
            themed("MANUAL_MOB",new ItemStack(Material.LODESTONE),Theme.MANUAL1,"测试快捷机器","无尽芯片注入");
    public static final SlimefunItemStack MANUAL_NTWBENCH=
            themed("MANUAL_NTWBENCH",new ItemStack(Material.DRIED_KELP_BLOCK),Theme.MANUAL1,"测试快捷机器","网络工作台");
    public static final SlimefunItemStack AUTOSMELTING1=
            themed("AUTOCRAFT_SMELT",new ItemStack(Material.FURNACE),Theme.MANUAL1,"测试AutoCraft","冶炼炉");
    public static final SlimefunItemStack AUTO_INF=
            themed("AUTOCRAFT_INF",new ItemStack(Material.RESPAWN_ANCHOR),Theme.MANUAL1,"测试定向合成机","无尽工作台");

//    public static final SlimefunItemStack INPORT=
//            themed("INPORT",new ItemStack(Material.END_STONE),Theme.CARGO1,"存入接口","较快的将物品存入奇点...");
//    public static final SlimefunItemStack OUTPORT=
//            themed("OUTPORT",new ItemStack(Material.END_STONE),Theme.CARGO1,"取出接口","较快的将物品取出奇点...");
    public static final SlimefunItemStack TESTUNIT1=
            themed("TESTUNIT1",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元","啥用都没");
    public static final SlimefunItemStack TESTUNIT2=
            themed("TESTUNIT2",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元2","啥用都没");
    public static final SlimefunItemStack TESTUNIT3=
            themed("TESTUNIT3",new ItemStack(Material.GLASS),Theme.CARGO1,"测试存储单元3","啥用都没");
    public static final SlimefunItemStack AUTO_SPECIAL=
            themed("AUTOCRAFT_SPECIAL",new ItemStack(Material.LOOM),Theme.MACHINE2,"测试特殊合成机","测试测试");
    public static final SlimefunItemStack AUTO_MULTIBLOCK=
            themed("AUTOCRAFT_MULTIBLOCK",new ItemStack(Material.BRICKS),Theme.MANUAL1,"测试快捷多方块","测试测试");
    public static final SlimefunItemStack WORKBENCH1=
            themed("WORKBENCH1",new ItemStack(Material.ENCHANTING_TABLE),Theme.BENCH1,"测试工作站","测试测试");
    //final
    public static final SlimefunItemStack FINAL_SEQUENTIAL=themed("FINAL_SEQUENTIAL",Material.STRIPPED_BAMBOO_BLOCK,Theme.MACHINE1,
            get("machine.FINAL_SEQUENTIAL.name"),getList("machine.FINAL_SEQUENTIAL.lore"));

    public static final SlimefunItemStack FINAL_STACKMACHINE=themed("FINAL_STACKMACHINE",Material.BLAST_FURNACE,Theme.MACHINE1,
            get("machine.FINAL_STACKMACHINE.name"),getList("machine.FINAL_STACKMACHINE.lore"));
    public static final SlimefunItemStack FINAL_STACKMGENERATOR=themed("FINAL_STACKMGENERATOR",Material.POLISHED_ANDESITE,Theme.MACHINE2,
            get("generator.FINAL_STACKMGENERATOR.name"),getList("generator.FINAL_STACKMGENERATOR.lore"));
    public static final SlimefunItemStack FINAL_STONE_MG=themed("FINAL_STONE_MG",Material.DEEPSLATE_TILES,Theme.MACHINE2,
            get("generator.FINAL_STONE_MG.name"),getList("generator.FINAL_STONE_MG.lore"));

    //tmp占位符
    public static final SlimefunItemStack TMP1= new SlimefunItemStack("TMP1",Material.STONE,"&b占位符","&7暂未开发");
    public static final SlimefunItemStack RESOLVE_FAILED=themed("RESOLVE_FAILED0",Material.STRUCTURE_VOID,Theme.NONE,
            get("item.RESOLVE_FAILED.name"),getList("item.RESOLVE_FAILED.lore"));
    public static final SlimefunItemStack SHELL=themed("调试终端",Material.BOOK,Theme.ITEM1,
            get("item.SHELL.name"),getList("item.SHELL.lore"));
    public static final HashSet<ItemStack> ADDGLOW=new HashSet<>(){{
        add(RESOLVE_FAILED);
        add(BUG);
        add(INFORMATION);
        add(BEYOND);
        add(BOOLEAN_TRUE);
        add(CHIP_INGOT);
        add(PARADOX);
        add(NOLOGIC);
        add(DIMENSIONAL_SHARD);
        add(WORLD_FEAT);
        add(NETHER_FEAT);
        add(END_FEAT);
        add(REINFORCED_CHIP_INGOT);
        add(ABSTRACT_INGOT);
        add(STAR_GOLD_INGOT);
        add(METAL_CORE);
        add(TECH_CORE);
        add(SMELERY_CORE);
        add(MASS_CORE);
        add(PORTAL_FRAME);
        add(LSINGULARITY);
        add(ATOM_INGOT);
        add(PAGOLD);
        add(BISILVER);
        add(STACKFRAME);
        add(MULTIBLOCK_MANUAL);
        add(ADVANCED_CHIP_MAKER);
        add(ADV_ADJACENT_CARGO);
        add(ADV_REMOTE_CARGO);
        add(ADV_LINE_CARGO);
        add(ENERGY_AMPLIFIER);
        add(NOTICE);
        add(INF_MOBSIMULATION);
        add(ENTITY_FEAT);
        add(WITHERPROOF_REDSTONE);
        add(WITHERPROOF_REDS);
        add(BEDROCK_BREAKER);
        add(DIMENSIONAL_SINGULARITY);
        add(SPACETOWER_FRAME);
        add(ADJ_COLLECTOR_PLUS);
        add(ADJ_CHARGER_PLUS);
        add(LINE_CHARGER_PLUS);
        add(LINE_COLLECTOR_PLUS);
        add(ENERGY_PIPE_PLUS);
        add(CONFIGURE);
        add(TOOLS);
        add(MULTIBLOCKBUILDER);
    }};
}

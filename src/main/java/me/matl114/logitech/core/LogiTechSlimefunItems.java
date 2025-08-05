package me.matl114.logitech.core;

import com.google.common.base.Preconditions;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemDropHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.Pair;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.matl114.logitech.Language;
import me.matl114.logitech.MyAddon;
import me.matl114.logitech.core.Blocks.*;
import me.matl114.logitech.core.Blocks.MultiBlock.*;
import me.matl114.logitech.core.Blocks.MultiBlock.SmithWorkShop.*;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiPart;
import me.matl114.logitech.core.Cargo.CargoMachine.*;
import me.matl114.logitech.core.Cargo.Config.ChipCard;
import me.matl114.logitech.core.Cargo.Config.ChipCardCode;
import me.matl114.logitech.core.Cargo.Config.ConfigCard;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceStorageCard;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceTower;
import me.matl114.logitech.core.Cargo.SpaceStorage.SpaceTowerFrame;
import me.matl114.logitech.core.Cargo.StorageMachines.IOPort;
import me.matl114.logitech.core.Cargo.StorageMachines.InputPort;
import me.matl114.logitech.core.Cargo.StorageMachines.OutputPort;
import me.matl114.logitech.core.Cargo.StorageMachines.Storage;
import me.matl114.logitech.core.Cargo.TestStorageUnit;
import me.matl114.logitech.core.Cargo.Transportation.*;
import me.matl114.logitech.core.Cargo.WorkBench.CargoConfigurator;
import me.matl114.logitech.core.Cargo.WorkBench.ChipBiConsumer;
import me.matl114.logitech.core.Cargo.WorkBench.ChipConsumer;
import me.matl114.logitech.core.Cargo.WorkBench.ChipCopier;
import me.matl114.logitech.core.Items.Abstracts.*;
import me.matl114.logitech.core.Items.Equipments.DisplayUseTrimmerLogicLate;
import me.matl114.logitech.core.Items.Equipments.EquipmentFUItem;
import me.matl114.logitech.core.Items.Equipments.LaserGun;
import me.matl114.logitech.core.Items.Equipments.TrackingArrowLauncher;
import me.matl114.logitech.core.Items.SpecialItems.*;
import me.matl114.logitech.core.Machines.AutoMachines.*;
import me.matl114.logitech.core.Machines.Electrics.*;
import me.matl114.logitech.core.Machines.ManualMachines.*;
import me.matl114.logitech.core.Machines.SpecialMachines.*;
import me.matl114.logitech.core.Machines.WorkBenchs.BugCrafter;
import me.matl114.logitech.core.Machines.WorkBenchs.EWorkBench;
import me.matl114.logitech.core.Registries.*;
import me.matl114.logitech.manager.EquipmentFUManager;
import me.matl114.logitech.manager.RadiationRegionManager;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.Algorithms.PairList;
import me.matl114.logitech.utils.UtilClass.CommandClass.CommandShell;
import me.matl114.logitech.utils.UtilClass.EquipClass.EquipmentFU;
import me.matl114.logitech.utils.UtilClass.FunctionalClass.AsyncResultRunnable;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemCounter;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemGreedyConsumer;
import me.matl114.logitech.utils.UtilClass.MenuClass.MenuFactory;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.utils.UtilClass.RecipeClass.MultiCraftingOperation;
import me.matl114.matlib.algorithms.dataStructures.frames.InitializeSafeProvider;
import me.matl114.matlib.algorithms.dataStructures.struct.Triplet;
import me.matl114.matlib.implement.slimefun.core.CustomRecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType.*;
import static me.matl114.logitech.core.LogiTechItemGroups.*;
import static me.matl114.logitech.utils.AddUtils.*;

/**
 * register main
 */
@SuppressWarnings("all")
public class LogiTechSlimefunItems {
    public static void registerSlimefunItems() {
        Debug.logger("注册附属物品...");
        Debug.logger("注册附属机器...");
        CRAFTTYPE_MANUAL_RECIPETYPE.put(CRAFT_MANUAL,BukkitUtils.VANILLA_CRAFTTABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ENHANCED_CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(MAGIC_WORKBENCH_MANUAL,MAGIC_WORKBENCH);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ANCIENT_ALTAR_MANUAL,ANCIENT_ALTAR);
        CRAFTTYPE_MANUAL_RECIPETYPE.put(ARMOR_FORGE_MANUAL,ARMOR_FORGE);
        if(TYPE){
            FINAL_STACKMACHINE.setRecipe(LAZY_STACKMACHINE);
            FINAL_STACKMACHINE.setRecipeType(ENHANCED_CRAFTING_TABLE);
            FINAL_STACKMGENERATOR.setRecipe(LAZY_STACKMGENERATOR);
            FINAL_STACKMGENERATOR.setRecipeType(ENHANCED_CRAFTING_TABLE);
        }
    }
    public static final ItemStack[] LAZY_RECIPE=new ItemStack[]{null,null,null,null,new ItemStack(Material.DIRT),null,null,null,null};
    public static final ItemStack[] LAZY_STACKMACHINE=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_FURNACE_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final ItemStack[] LAZY_STACKMGENERATOR=recipe("ADVANCED_CIRCUIT_BOARD","ENERGIZED_CAPACITOR","ADVANCED_CIRCUIT_BOARD","ELECTRIC_MOTOR","ELECTRIC_ORE_GRINDER_3","ELECTRIC_MOTOR",
            "HEATING_COIL","ELECTRIC_MOTOR","HEATING_COIL");
    public static final SlimefunAddon INSTANCE = MyAddon.getInstance();
    public static SlimefunItem register(SlimefunItem item){
        item.register(INSTANCE);
        return item;
    }
    protected static boolean TYPE=false;
    protected static Object mkP(Object v1,Object v2){
        return new Pair(v1,v2);
    }
    protected static Object[] mkl(Object ... v){
        return Arrays.stream(v).toArray();
    }
    protected static int[] CHOOSEN_SLOT=new int[]{
       
    };
    public static ItemStack[] recipe(Object ... v){
        if(!TYPE||v.length<=9)
            return Arrays.stream(v).map(AddUtils::resolveItem).toArray(ItemStack[]::new);
        else{
            int len=v.length;
            ItemStack[] res = new ItemStack[9];
            int index=0;
            int delta=len/9;
            for(int i=0;i<9;++i){
                if(index>=len)res[i]=null;
                else{
                    res[i]=resolveItem(v[index]);
                }
                index+=delta-1+i%2;
            }
            return res;
        }
    }
    protected static RecipeType COMMON_TYPE=TYPE? ENHANCED_CRAFTING_TABLE: BugCrafter.TYPE;
    protected static <T extends Object> List<T> list(T ... input){
        return Arrays.asList(input);
    }
    protected static <T extends Object,Z extends Object> Pair<T,Z> pair(T v1,Z v2){
        return new Pair(v1,v2);
    }
    public static ItemStack setC(ItemStack it,int amount){
        return new CustomItemStack(it,amount);
    }
    protected static ItemStack[] nullRecipe(){
        return NULL_RECIPE.clone();
    }
//    private static HashMap<Object,Integer> mrecipe(Object ... v){
//        int len=v.length;
//        return new PairList<>((len+1)/2){{
//            for(int i=0;i<len;i+=2){
//                Object v1=v[i];
//                Integer v2=(Integer)v[i+1];
//                put(v1,v2);
//            }
//        }};
//    }
    private static PairList<Object,Integer> mkMp(Object ... v){
        int len=v.length;
        return new PairList<>(){{
            for(int i=0;i<len;i+=2){
                Object v1=v[i];
                Integer v2=(Integer)v[i+1];
                put(v1,v2);
            }
        }};
    }
    //items
    public static final CustomRecipeType STARSMELTERY=new CustomRecipeType(
            getNameKey("star_smeltery"),
            new CustomItemStack(LogiTechSlimefunItemStacks.STAR_SMELTERY, LogiTechSlimefunItemStacks.STAR_SMELTERY.getDisplayName(),
                    "", "&c在%s中锻造!".formatted(Language.get("machine.STAR_SMELTERY.name")))
    );
    public static HashMap<SlimefunItem,RecipeType> CRAFTTYPE_MANUAL_RECIPETYPE=new HashMap<>();
    public static final SlimefunItem MATL114=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL, LogiTechSlimefunItemStacks.MATL114,
            AddDepends.INFINITYWORKBENCH_TYPE!=null?AddDepends.INFINITYWORKBENCH_TYPE:COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG)
            ).register();
    public static final SlimefunItem BUG= new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BUG,NULL,
            nullRecipe(),list(getInfoShow("&f获取方式",
    "&7不要害怕,这只是一个基础材料...",
            "&a它会出现在一些隐蔽的地方...",
            "&7当你出现疑问,为什么这个物品找不到配方时",
            "&7你可能需要多看看&e\"逻辑工艺 版本与说明\"&7分类(物理意义)")))
            .register();

    public static final SlimefunItem TRUE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.BOOL_GENERATOR,Language.get("machine.BOOL_GENERATOR.name")))
            .register();
    public static final SlimefunItem FALSE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.BOOL_GENERATOR,Language.get("machine.BOOL_GENERATOR.name")))
            .register();
    public static final SlimefunItem LOGIGATE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LOGIC_GATE,ENHANCED_CRAFTING_TABLE,
            recipe("SILVER_INGOT","REDSTONE_TORCH","SILVER_INGOT",
                        "REDSTONE",null,"REDSTONE",
                    "SILVER_INGOT","REDSTONE_TORCH","SILVER_INGOT"))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LOGIC_GATE,3));
    public static final SlimefunItem LOGIC=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LOGIC,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.LOGIC_REACTOR,Language.get("machine.LOGIC_REACTOR.name")))
            .register();
    public static final SlimefunItem NOLOGIC=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.NOLOGIC,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.LOGIC_REACTOR,Language.get("machine.LOGIC_REACTOR.name")))
            .register();



        //generated
    public static final SlimefunItem EXISTE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.EXISTE,NULL,
                formatInfoRecipe(LogiTechSlimefunItemStacks.LOGIC_REACTOR,Language.get("machine.LOGIC_REACTOR.name")),null)
            .register();
    public static final SlimefunItem UNIQUE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.UNIQUE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.LOGIC_REACTOR,Language.get("machine.LOGIC_REACTOR.name")),null)
            .register();
    public static final SlimefunItem PARADOX=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.PARADOX,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.LVOID_GENERATOR,Language.get("machine.LVOID_GENERATOR.name")),null)
            .register();

    public static final SlimefunItem LENGINE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LENGINE,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LOGIC,"ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LOGIC,"ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    "REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT","REINFORCED_ALLOY_INGOT"),null)
            .register().setOutput(LogiTechSlimefunItemStacks.LENGINE);


    public static final SlimefunItem LFIELD=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LFIELD,COMMON_TYPE,
            recipe("SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT",
                    null,LogiTechSlimefunItemStacks.EXISTE,null,LogiTechSlimefunItemStacks.EXISTE,null,LogiTechSlimefunItemStacks.EXISTE,
                    LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,
                    LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,
                    null,LogiTechSlimefunItemStacks.UNIQUE,null,LogiTechSlimefunItemStacks.UNIQUE,null,LogiTechSlimefunItemStacks.UNIQUE,
                    "SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT","SOLDER_INGOT"),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LFIELD,17));



    public static final SlimefunItem LSCHEDULER=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LSCHEDULER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS", LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE","PROGRAMMABLE_ANDROID",setC(LogiTechSlimefunItemStacks.LENGINE,1),"ANDROID_MEMORY_CORE",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ANDROID_MEMORY_CORE",setC(LogiTechSlimefunItemStacks.LENGINE,1),"PROGRAMMABLE_ANDROID","ANDROID_MEMORY_CORE",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LFIELD,"ANDROID_INTERFACE_ITEMS","ANDROID_MEMORY_CORE","ANDROID_MEMORY_CORE","ANDROID_INTERFACE_ITEMS",LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD),null)
            .register();
    public static final SlimefunItem LCRAFT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LCRAFT,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",LogiTechSlimefunItemStacks.LFIELD, LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"CRAFTING_MOTOR","CRAFTING_MOTOR", LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,
                    "CARGO_MOTOR","CRAFTING_MOTOR","ENHANCED_AUTO_CRAFTER",setC(LogiTechSlimefunItemStacks.LENGINE,1),"CRAFTING_MOTOR","CARGO_MOTOR",
                    "CARGO_MOTOR","CRAFTING_MOTOR", setC(LogiTechSlimefunItemStacks.LENGINE,1),"ENHANCED_AUTO_CRAFTER","CRAFTING_MOTOR","CARGO_MOTOR",
                    LogiTechSlimefunItemStacks.LFIELD, LogiTechSlimefunItemStacks.LFIELD,"CRAFTING_MOTOR","CRAFTING_MOTOR",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"CARGO_MOTOR","CARGO_MOTOR",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LCRAFT,8));
    public static final SlimefunItem LBOOLIZER=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LBOOLIZER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,
                    LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,
                    LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,
                    LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE, LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,
                    LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,
                    LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE),null)
            .register();
    public static final SlimefunItem LMOTOR=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LMOTOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"ELECTRIC_MOTOR","ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,"ELECTRO_MAGNET",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"ELECTRO_MAGNET",LogiTechSlimefunItemStacks.LFIELD,
                    "ELECTRIC_MOTOR",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.LENGINE,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"ELECTRIC_MOTOR",
                    "ELECTRIC_MOTOR",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LENGINE,"ELECTRIC_MOTOR",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"ELECTRIC_MOTOR",
                    LogiTechSlimefunItemStacks.LFIELD,"ELECTRO_MAGNET",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"ELECTRO_MAGNET",LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"ELECTRIC_MOTOR","ELECTRIC_MOTOR",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LMOTOR,4));
    public static final SlimefunItem LDIGITIZER=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LDIGITIZER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.UNIQUE
                    ,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1)
                    ,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC_GATE,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX
                    ,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.PARADOX
                    ,LogiTechSlimefunItemStacks.PARADOX,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1)
                    ,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.UNIQUE
                    ,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.UNIQUE),null)
            .register();
    public static final SlimefunItem LIOPORT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LIOPORT,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"HOPPER","HOPPER",LogiTechSlimefunItemStacks.LFIELD, LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,"GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE", "GPS_TRANSMITTER",LogiTechSlimefunItemStacks.LFIELD,
                    "HOPPER","CARGO_NODE_INPUT","CARGO_NODE_OUTPUT", "CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER",
                    "HOPPER","CARGO_NODE_INPUT", LogiTechSlimefunItemStacks.LENGINE,"CARGO_NODE_OUTPUT_ADVANCED","CARGO_NODE_INPUT","HOPPER",
                    LogiTechSlimefunItemStacks.LFIELD, "GPS_TRANSMITTER","CARGO_NODE","CARGO_NODE","GPS_TRANSMITTER",LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"HOPPER","HOPPER",LogiTechSlimefunItemStacks.LFIELD, LogiTechSlimefunItemStacks.LFIELD),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LIOPORT,2));
    public static final SlimefunItem LPLATE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LPLATE,SMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,2),"BATTERY","POTATO",LogiTechSlimefunItemStacks.CHIP_INGOT),null)
            .register();
    //TODO 修改输出数量
    public static final SlimefunItem DIMENSIONAL_SHARD=new AbstractGeoResource(LogiTechItemGroups.BASIC_MATERIAL, LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
            recipe(null,LogiTechSlimefunItemStacks.END_MINER,null,null,getInfoShow("&f获取方式","&7在末地大部分群系","或风袭沙丘或蘑菇岛开采","&7或者在本附属的矿机中获取")),
            1, new HashMap<>(){{
        put(Biome.END_BARRENS,1);
        put(Biome.END_HIGHLANDS,1);
        put(Biome.THE_END,1);
        put(Biome.SMALL_END_ISLANDS,1);
        put(Biome.WINDSWEPT_GRAVELLY_HILLS,1);
        put(Biome.MUSHROOM_FIELDS,1);
    }}) .registerGeo();
    public static final SlimefunItem DIMENSIONAL_SINGULARITY=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),null)
            .register();
    public static final SlimefunItem STAR_GOLD=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.STAR_GOLD,NULL    ,
            recipe(null,LogiTechSlimefunItemStacks.END_MINER,null,null,getInfoShow("&f获取方式","&7在本附属的矿机中获取")),null)
            .register();
    public static final SlimefunItem STAR_GOLD_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,SMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.STAR_GOLD,5),setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,11)),null)
            .register();

    public static final SlimefunItem WORLD_FEAT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.WORLD_FEAT,COMMON_TYPE,
            recipe(null,"STONE","PODZOL","PODZOL","GRASS_BLOCK",null,
                    "STONE","PODZOL","STONE","GRASS_BLOCK","PODZOL","GRASS_BLOCK",
                    "PODZOL","STONE","GRASS_BLOCK","STONE","GRASS_BLOCK","PODZOL",
                    "PODZOL","GRASS_BLOCK","STONE","GRASS_BLOCK","STONE","PODZOL",
                    "GRASS_BLOCK","PODZOL","GRASS_BLOCK","STONE","PODZOL","STONE",
                    null,"GRASS_BLOCK","PODZOL","PODZOL","STONE",null),null)
            .register();
    public static final SlimefunItem NETHER_FEAT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.NETHER_FEAT,COMMON_TYPE,
            recipe(null,"MAGMA_BLOCK","CRIMSON_NYLIUM","CRIMSON_NYLIUM","OBSIDIAN",null,
                    "MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN",
                    "CRIMSON_NYLIUM","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","CRIMSON_NYLIUM",
                    "CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM",
                    "OBSIDIAN","CRIMSON_NYLIUM","OBSIDIAN","MAGMA_BLOCK","CRIMSON_NYLIUM","MAGMA_BLOCK",
                    null,"OBSIDIAN","CRIMSON_NYLIUM","CRIMSON_NYLIUM","MAGMA_BLOCK",null),null)
            .register();
    public static final SlimefunItem END_FEAT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.END_FEAT,COMMON_TYPE,
            recipe(null,"CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FRUIT","END_STONE",null,
                    "CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FLOWER","END_STONE","CHORUS_FRUIT","END_STONE",
                    "CHORUS_FRUIT","CHORUS_FLOWER","END_STONE","CHORUS_FLOWER","END_STONE","CHORUS_FRUIT",
                    "CHORUS_FRUIT","END_STONE","CHORUS_FLOWER","END_STONE","CHORUS_FLOWER","CHORUS_FRUIT",
                    "END_STONE","CHORUS_FRUIT","END_STONE","CHORUS_FLOWER","CHORUS_FRUIT","CHORUS_FLOWER",
                    null,"END_STONE","CHORUS_FRUIT","CHORUS_FRUIT","CHORUS_FLOWER",null),null)
            .register();
    public static final SlimefunItem ENTITY_FEATURE=new EntityFeat(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.ENTITY_FEAT,NULL,
            recipe(null,addGlow( new ItemStack(Material.IRON_PICKAXE)),null,null,getInfoShow("&f获取方式","&7当 挖掘任意刷怪笼方块时 ","&750%额外掉落随机种类的生物特征"),null,
                    null,new ItemStack(Material.SPAWNER),null))
            .register();
    public static final SlimefunItem HYPER_LINK=new HypLink(SPACE,LogiTechSlimefunItemStacks.HYPER_LINK,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ABSTRACT_INGOT))
            .register();


    public static final SlimefunItem METAL_CORE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.METAL_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),null)
            .register();
    public static final SlimefunItem SMELERY_CORE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.SMELERY_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),null)
            .register();
    public static final SlimefunItem MASS_CORE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.MASS_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),null)
            .register();
    public static final SlimefunItem TECH_CORE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.TECH_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),null)
            .register();
    public static final SlimefunItem LSINGULARITY=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LSINGULARITY,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();
    public static final SlimefunItem ATOM_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.ATOM_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();


    //alloy
    public static final SlimefunItem CHIP_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.CHIP_INGOT,SMELTERY,
            recipe("2SILVER_INGOT","2REINFORCED_ALLOY_INGOT","4IRON_INGOT",
                    setC(SlimefunItems.COPPER_INGOT,4),"2SILICON","4ALUMINUM_INGOT"))
            .register();
    public static final SlimefunItem ABSTRACT_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,SMELTERY,
            recipe(LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,null,null,null))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,4));
    public static final SlimefunItem PALLADIUM_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.PALLADIUM_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();
    public static final SlimefunItem PLATINUM_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.PLATINUM_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();
    public static final SlimefunItem MOLYBDENUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.MOLYBDENUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem CERIUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.CERIUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem CADMIUM_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.CADMIUM_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();
    public static final SlimefunItem MENDELEVIUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.MENDELEVIUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem DYSPROSIUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.DYSPROSIUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem BISMUTH_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BISMUTH_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SOLAR_REACTOR,Language.get("multiblock.SOLAR_REACTOR.name")),null)
            .register();
    public static final SlimefunItem ANTIMONY_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.ANTIMONY_INGOT,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem THALLIUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.THALLIUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem HYDRAGYRUM=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.HYDRAGYRUM,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem BORON=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BORON,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TRANSMUTATOR,Language.get("multiblock.TRANSMUTATOR.name")),null)
            .register();
    public static final SlimefunItem BISILVER=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.BISILVER,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.BISMUTH_INGOT,4),setC(LogiTechSlimefunItemStacks.PARADOX,20),"4SILVER_INGOT",
                    "4BILLON_INGOT"),null)
            .register();
    public static final SlimefunItem PAGOLD=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.PAGOLD,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,2),setC(LogiTechSlimefunItemStacks.PARADOX,20),
                    setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,2),"7GOLD_22K"
                    ),null)
            .register();

    public static final SlimefunItem PDCECDMD=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItems.STARSMELTERY,
            recipe("32PLUTONIUM",setC(LogiTechSlimefunItemStacks.CERIUM,64),setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,64),
                    setC(LogiTechSlimefunItemStacks.MENDELEVIUM,48),setC(LogiTechSlimefunItemStacks.LSINGULARITY,1)
                    ),null)
            .register();
    public static final SlimefunItem HGTLPBBI=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItems.STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.HYDRAGYRUM,64),setC(LogiTechSlimefunItemStacks.THALLIUM,64),
                    "64LEAD_INGOT",setC(LogiTechSlimefunItemStacks.BISILVER,12),setC(LogiTechSlimefunItemStacks.LSINGULARITY,1)),null)
            .register();
    public static final SlimefunItem REINFORCED_CHIP_INGOT=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT,LogiTechSlimefunItems.STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,40),setC(LogiTechSlimefunItemStacks.CHIP_INGOT,16),
                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,32),setC(LogiTechSlimefunItemStacks.PAGOLD,4),
                    setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,8),setC(LogiTechSlimefunItemStacks.BISILVER,2)
                    ),null)
            .register();



    public static final SlimefunItem SPACE_PLATE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.SPACE_PLATE,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,48),setC(LogiTechSlimefunItemStacks.PARADOX,24),
                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,64),setC(LogiTechSlimefunItemStacks.LFIELD,32),LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT
                    ),null)
            .register();
    public static final SlimefunItem VIRTUAL_SPACE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.SPACE_PLATE,2),setC(LogiTechSlimefunItemStacks.BISILVER,2),LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,setC(LogiTechSlimefunItemStacks.BISILVER,2),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,2),
                    setC(LogiTechSlimefunItemStacks.BISILVER,2),LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.PAGOLD,2),LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.BISILVER,2),
                    "4GPS_TRANSMITTER_4",setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,6),setC(LogiTechSlimefunItemStacks.LSINGULARITY,6),setC(LogiTechSlimefunItemStacks.PAGOLD,2),"4GPS_TRANSMITTER_4",
                    LogiTechSlimefunItemStacks.LIOPORT,setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.PAGOLD,2),LogiTechSlimefunItemStacks.LIOPORT,
                    setC(LogiTechSlimefunItemStacks.BISILVER,2),LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.PAGOLD,2),LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.BISILVER,2),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,2),setC(LogiTechSlimefunItemStacks.BISILVER,2),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),setC(LogiTechSlimefunItemStacks.BISILVER,2),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,2)),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,4));

    public static final SlimefunItem REDSTONE_ENGINE=new MaterialItem(VANILLA,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,ENHANCED_CRAFTING_TABLE,
            recipe("TNT","SLIME_BLOCK","ANVIL",
                    "OBSERVER","STICKY_PISTON","STICKY_PISTON",
                    "REDSTONE_TORCH",LogiTechSlimefunItemStacks.LOGIC_GATE,"REPEATER"),null)
            .register();

    public static final SlimefunItem SAMPLE_HEAD=new AbstractBlock(SPECIAL,LogiTechSlimefunItemStacks.SAMPLE_HEAD,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.HEAD_ANALYZER,Language.get("machine.HEAD_ANALYZER.name")))
            .register();
    public static final SlimefunItem PLAYER_IDCARD=new PlayerIdCard(SPECIAL,LogiTechSlimefunItemStacks.PLAYER_IDCARD,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,null,LogiTechSlimefunItemStacks.LPLATE,"PLAYER_HEAD",LogiTechSlimefunItemStacks.HYPER_LINK,"PLAYER_HEAD",
                    LogiTechSlimefunItemStacks.LPLATE,null,LogiTechSlimefunItemStacks.LPLATE),null)
            .register();
    public static final SlimefunItem SAMPLE_SPAWNER=new AbstractSpawner(FUNCTIONAL,LogiTechSlimefunItemStacks.SAMPLE_SPAWNER,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENTITY_FEAT,Language.get("item.ENTITY_FEAT.name")))
            .register();
    public static final SlimefunItem CHIP=new ChipCard(ADVANCED,LogiTechSlimefunItemStacks.CHIP,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.CHIP_MAKER,Language.get("machine.CHIP_MAKER.name")))
            .register();
    public static final SlimefunItem CHIP_CORE=new MaterialItem(ADVANCED,LogiTechSlimefunItemStacks.CHIP_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.CHIP_MAKER,Language.get("machine.CHIP_MAKER.name")),null)
            .register();

    public static final SlimefunItem LOGIC_CORE=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.LOGIC_CORE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.FINAL_SEQUENTIAL,Language.get("machine.FINAL_SEQUENTIAL.name")),null)
            .register();
    public static final SlimefunItem FINAL_FRAME=new MultiPart(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.FINAL_FRAME,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.FINAL_SEQUENTIAL,Language.get("machine.FINAL_SEQUENTIAL.name")),"final.frame"){
            public boolean redirectMenu(){
                return false;
            }
    }
            .register();

    public static final SlimefunItem STACKFRAME=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.STACKFRAME,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LIOPORT,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),"4GPS_TRANSMITTER_4","4GPS_TRANSMITTER_4",setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),LogiTechSlimefunItemStacks.LMOTOR,
                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),
                    LogiTechSlimefunItemStacks.PAGOLD,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),setC(LogiTechSlimefunItemStacks.LSINGULARITY,16),LogiTechSlimefunItemStacks.LSCHEDULER,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.PAGOLD,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),LogiTechSlimefunItemStacks.PAGOLD,
                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,6),LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),
                    LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),LogiTechSlimefunItemStacks.LCRAFT),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.STACKFRAME,64));
    public static final SlimefunItem LASER=new MaterialItem(ADVANCED,LogiTechSlimefunItemStacks.LASER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.HYDRAGYRUM,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.HYDRAGYRUM,
                    LogiTechSlimefunItemStacks.ANTIMONY_INGOT,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.ANTIMONY_INGOT,
                    LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.BORON,
                    LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.BORON,
                    LogiTechSlimefunItemStacks.ANTIMONY_INGOT,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.ANTIMONY_INGOT,
                    LogiTechSlimefunItemStacks.HYDRAGYRUM,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.BORON,LogiTechSlimefunItemStacks.MOLYBDENUM,LogiTechSlimefunItemStacks.HYDRAGYRUM),null)
            .register();
    public static final SlimefunItem VIRTUALWORLD=new MaterialItem(LogiTechItemGroups.BASIC_MATERIAL,LogiTechSlimefunItemStacks.VIRTUALWORLD,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.FINAL_CONVERTOR,Language.get("machine.FINAL_CONVERTOR.name")),null)
            .register();


    public static final SlimefunItem WITHERPROOF_REDSTONE=new WitherProofBlock(VANILLA,LogiTechSlimefunItemStacks.WITHERPROOF_REDSTONE,ENHANCED_CRAFTING_TABLE,
            recipe("REDSTONE_BLOCK","LEAD_INGOT","REDSTONE_BLOCK","LEAD_INGOT","WITHER_PROOF_OBSIDIAN","LEAD_INGOT",
                    "REDSTONE_BLOCK","LEAD_INGOT","REDSTONE_BLOCK"))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.WITHERPROOF_REDSTONE,4));
    public static final SlimefunItem WITHERPROOF_REDS=new WitherProofBlock(VANILLA,LogiTechSlimefunItemStacks.WITHERPROOF_REDS,ENHANCED_CRAFTING_TABLE,
            recipe("REDSTONE","LEAD_INGOT","REDSTONE","LEAD_INGOT","WITHER_PROOF_OBSIDIAN","LEAD_INGOT",
                    "REDSTONE","LEAD_INGOT","REDSTONE"))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.WITHERPROOF_REDS,4));
    public static final MyVanillaItem MACE_ITEM=new InitializeSafeProvider <>(MyVanillaItem.class, ()->{
        if(LogiTechSlimefunItemStacks.MACE_ITEM!=null){
            var re= new MyVanillaItem(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.MACE_ITEM,"LOGITECH_MACE_RECIPE",BukkitUtils.VANILLA_CRAFTTABLE,
                    recipe(LogiTechSlimefunItemStacks.METAL_CORE,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.METAL_CORE,
                            null,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,null,
                            null,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,null));
            //todo we will add this to TOOLS_FUNCTIONAL later after we reconstruct TOOLS_FUNCTIONAL!
            return re.register();
        }
        return null;
    }).v();
    public static final SlimefunItem UNBREAKING_SHIELD=new MaterialItem(TOOLS_FUNCTIONAL,LogiTechSlimefunItemStacks.UNBREAKING_SHIELD,BukkitUtils.VANILLA_CRAFTTABLE,
            recipe("IRON_BLOCK","DAMASCUS_STEEL_INGOT","IRON_BLOCK","IRON_BLOCK","OBSIDIAN","IRON_BLOCK",
            null,"IRON_BLOCK",null)).register();
    public static final MyVanillaItem SUPER_COBALT_PICKAXE = new MyVanillaItem(TOOLS_FUNCTIONAL,LogiTechSlimefunItemStacks.SUPER_COBALT_PICKAXE,"SUPER_COBALT_PICKAXE", SmithInterfaceProcessor.INTERFACED_SMITH_UPDATE,recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"COBALT_PICKAXE","3DIAMOND",null,null,null,null,null,null))
            .register();



    //machines
    public static final SlimefunItem BOOL_GENERATOR=new BoolGenerator(BASIC,LogiTechSlimefunItemStacks.BOOL_GENERATOR,ENHANCED_CRAFTING_TABLE,
            recipe("OBSERVER","REDSTONE","OBSERVER",
                    "REDSTONE_TORCH","SILICON","REDSTONE_TORCH",
                    "FERROSILICON","ENERGY_CONNECTOR","FERROSILICON"),Material.RECOVERY_COMPASS,6)
            .register();
    public static final SlimefunItem LOGIC_REACTOR=new LogicReactor(BASIC,LogiTechSlimefunItemStacks.LOGIC_REACTOR,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LOGIC_GATE,"COMPARATOR",LogiTechSlimefunItemStacks.LOGIC_GATE,
                    "HEATING_COIL" , null,"HEATING_COIL",
                    "IRON_INGOT","SMALL_CAPACITOR","IRON_INGOT"
                    ),Material.COMPARATOR,3)
            .register();
    public static final SlimefunItem BUG_CRAFTER=new BugCrafter(BASIC,LogiTechSlimefunItemStacks.BUG_CRAFTER,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.CHIP_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT),10_000,1_00,7)
            .register();
    public static final  SlimefunItem FURNACE_FACTORY=new MTMachine(BASIC, LogiTechSlimefunItemStacks.FURNACE_FACTORY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_FURNACE","ELECTRIC_FURNACE",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT),
            addGlow( new ItemStack(Material.FLINT_AND_STEEL)),60,6_400,
            ()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp= RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(BukkitUtils.VANILLA_FURNACE);
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            })
            .register();
    public static final  SlimefunItem PRESSOR_FACTORY=new MTMachine(BASIC, LogiTechSlimefunItemStacks.PRESSOR_FACTORY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_PRESS","ELECTRIC_PRESS",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_PRESS","ELECTRIC_PRESS",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), addGlow( new ItemStack(Material.PISTON))
            ,120,64_00,()->{
                HashSet<SlimefunItem> hasInCompressor=new HashSet<>();
                List<MachineRecipe> recipes=new ArrayList<>();
                List<MachineRecipe> elecpress=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(RecipeType.COMPRESSOR);
                for (MachineRecipe recipe:elecpress){
                    if(recipe.getOutput()[0].getType()!=Material.COBBLESTONE)
                    recipes.add(MachineRecipeUtils.stackFrom(3,recipe.getInput(),recipe.getOutput()));
                }
                elecpress=RecipeSupporter.PROVIDED_UNSHAPED_RECIPES.get(RecipeType.PRESSURE_CHAMBER);
                for (MachineRecipe recipe:elecpress){
                    recipes.add(MachineRecipeUtils.stackFrom(4,recipe.getInput(),recipe.getOutput()));
                }
                List<MachineRecipe> compressorRecipes=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_PRESS"),new ArrayList<>());
                for(MachineRecipe recipe:compressorRecipes){
                    ItemStack stack=recipe.getOutput()[0];
                    SlimefunItem stackItem=SlimefunItem.getByItem(stack);
                    if(stackItem!=null&&stackItem.getRecipeType()== RecipeType.COMPRESSOR){
                        continue;
                    }
                    recipes.add(recipe);
                }
                return recipes;
            })
            .register();
    public static final  SlimefunItem GRIND_FACTORY=new MTMachine(BASIC, LogiTechSlimefunItemStacks.GRIND_FACTORY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_ORE_GRINDER_3","ELECTRIC_ORE_GRINDER_3",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_ORE_GRINDER_3","ELECTRIC_ORE_GRINDER_3",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), addGlow( new ItemStack(Material.DIAMOND_CHESTPLATE))
            ,125,64_00,()->{
        List<MachineRecipe> recipelist=new ArrayList<>();
        List<MachineRecipe> rp=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_ORE_GRINDER_3"),new ArrayList<>());
        for (MachineRecipe rps:rp){
            recipelist.add(MachineRecipeUtils.stackFromMachine(rps));
        }
        return recipelist;
    })
            .register();
    public static final  SlimefunItem SMELTRY=new AEMachine(BASIC, LogiTechSlimefunItemStacks.SMELTRY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks. ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks. ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks. ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks. ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT),
            Material.STONE,100,12_800,
            ()->{

                List<MachineRecipe> recipelist=new ArrayList<>();
                List<MachineRecipe> rp=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_SMELTERY"),new ArrayList<>());
                for (MachineRecipe rps:rp){
                    recipelist.add(MachineRecipeUtils.stackFrom(0,rps.getInput(),rps.getOutput()));
                }
                return recipelist;
            }){
        {
            this.USE_HISTORY=false;
        }
    }
            .register();


    public static final  SlimefunItem DUST_EXTRACTOR=new MTMachine(BASIC, LogiTechSlimefunItemStacks.DUST_EXTRACTOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.TECH_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.MASS_CORE,1),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LENGINE,"4ELECTRIC_ORE_GRINDER_3","4ELECTRIC_ORE_GRINDER_3",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LIOPORT,"4ELECTRIC_GOLD_PAN_3","4ELECTRIC_GOLD_PAN_3",LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LMOTOR,"4ELECTRIC_DUST_WASHER_3","4ELECTRIC_DUST_WASHER_3",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LPLATE,
                    setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.TECH_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.MASS_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1)),
            new ItemStack(Material.LANTERN),1800,86400,
                mkMp(
                        mkP(mkl("64COBBLESTONE"),mkl(randItemStackFactory(
                                new PairList<>(){{
                                    put("64IRON_DUST",1);
                                    put("64GOLD_DUST",1);
                                    put("64COPPER_DUST",1);
                                    put("64TIN_DUST",1);
                                    put("64ZINC_DUST",1);
                                    put("64ALUMINUM_DUST",1);
                                    put("64SILVER_DUST",1);
                                    put("64MAGNESIUM_DUST",1);
                                    put("64LEAD_DUST",1);
                                }}
                        ))),0
                )
            )
            .register();
    public static final  SlimefunItem INGOT_FACTORY=new MTMachine(BASIC, LogiTechSlimefunItemStacks.INGOT_FACTORY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.METAL_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LENGINE,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LIOPORT,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LMOTOR,"4ELECTRIC_INGOT_FACTORY_3","4ELECTRIC_INGOT_FACTORY_3",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LPLATE,
                    setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.METAL_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1)),
            new ItemStack(Material.LANTERN),2400,129600,
//            mkMp(
//                    mkP(mkl("64IRON_DUST"),mkl("64IRON_INGOT")),0,
//                    mkP(mkl("64GOLD_DUST"),mkl("64GOLD_INGOT")),0,
//                    mkP(mkl("64COPPER_DUST"),mkl("64COPPER_INGOT")),0,
//                    mkP(mkl("64TIN_DUST"),mkl("64TIN_INGOT")),0,
//                    mkP(mkl("64ZINC_DUST"),mkl("64ZINC_INGOT")),0,
//                    mkP(mkl("64ALUMINUM_DUST"),mkl("64ALUMINUM_INGOT")),0,
//                    mkP(mkl("64SILVER_DUST"),mkl("64SILVER_INGOT")),0,
//                    mkP(mkl("64MAGNESIUM_DUST"),mkl("64MAGNESIUM_INGOT")),0,
//                    mkP(mkl("64LEAD_DUST"),mkl("64LEAD_INGOT")),0
//
//
//            )
            ()->{
                List<MachineRecipe> recipes=new ArrayList<>();
                List<MachineRecipe> recipes2=RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItem.getById("ELECTRIC_INGOT_FACTORY"),new ArrayList<>());
                for(MachineRecipe recipe : recipes2) {
                    ItemStack[] input=new ItemStack[recipe.getInput().length];
                    for (int i=0;i<input.length;++i){
                        input[i]=recipe.getInput()[i].clone();
                        input[i].setAmount(input[i].getAmount()*64);
                    }
                    ItemStack[] output =new ItemStack[recipe.getOutput().length];
                    for(int i=0;i<output.length;++i){
                        output[i]=recipe.getOutput()[i].clone();
                        if(CraftUtils.parseSfId(output[i])=="GOLD_4K"){
                            output[i]=new ItemStack(Material.GOLD_INGOT);
                        }
                        output[i].setAmount(output[i].getAmount()*64);
                    }
                    recipes.add(MachineRecipeUtils.stackFrom(0,input,output));
                }
                return recipes;
            }
    )
            .register();
    public static final  SlimefunItem INGOT_CONVERTOR=new MTMachine(BASIC, LogiTechSlimefunItemStacks.INGOT_CONVERTOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.MASS_CORE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.CHIP_CORE,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LENGINE,"4ENHANCED_AUTO_CRAFTER","4ENHANCED_AUTO_CRAFTER",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LIOPORT,"4REINFORCED_FURNACE","4REINFORCED_FURNACE",LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LMOTOR,"4ELECTRIC_INGOT_FACTORY","4ELECTRIC_INGOT_FACTORY",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.MASS_CORE,LogiTechSlimefunItemStacks.PAGOLD),new ItemStack(Material.BEACON),2400,129600,
            mkMp(
                    mkP(mkl("64GOLD_INGOT"),mkl("64GOLD_4K")),0,
                    mkP(mkl("64GOLD_4K"),mkl("64GOLD_INGOT")),0,
                    mkP(mkl("16COPPER_INGOT"),mkl(new ItemStack(Material.COPPER_INGOT,16))),0,
                    mkP(mkl(new ItemStack(Material.COPPER_INGOT,16)),mkl("16COPPER_INGOT")),0,
                    mkP(mkl("9GOLD_DUST"),mkl("GOLD_24K")),0,
                    mkP(mkl("8STEEL_INGOT"),mkl("4DAMASCUS_STEEL_INGOT")),0,
                    mkP(mkl("4DAMASCUS_STEEL_INGOT"),mkl("8STEEL_INGOT")),0,
                    mkP(mkl("4CARBONADO"),mkl("8CARBON_CHUNK")),0,
                    mkP(mkl("8CARBON_CHUNK"),mkl("4CARBONADO")),0,
                    mkP(mkl("8QUARTZ"),mkl("2SILICON")),0
            ))
            .register();
    public static final  SlimefunItem CRAFTER=new SpecialCrafter(BASIC, LogiTechSlimefunItemStacks.CRAFTER, ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT, LogiTechSlimefunItemStacks.NOLOGIC, LogiTechSlimefunItemStacks.ABSTRACT_INGOT, LogiTechSlimefunItemStacks.LBOOLIZER, LogiTechSlimefunItemStacks.LCRAFT, LogiTechSlimefunItemStacks.LBOOLIZER,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT, LogiTechSlimefunItemStacks.NOLOGIC, LogiTechSlimefunItemStacks.ABSTRACT_INGOT), Material.BOOK, 0, 180, 7200) {
        @Override
        public HashMap<SlimefunItem, RecipeType> getRecipeTypeMap() {
            return CRAFTTYPE_MANUAL_RECIPETYPE;
        }
        public boolean advanced(){
            return true;
        }
    }
            .register();
    public static final  SlimefunItem CONVERTOR=new EMachine(BASIC, LogiTechSlimefunItemStacks.CONVERTOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT),
            Material.STONE,500,24_000,
            mkMp(mkP(mkl("STONE"),mkl("DIRT")),3,
                    mkP(mkl("DIRT"),mkl("GRASS_BLOCK")),3,
                    mkP(mkl("GRASS_BLOCK"),mkl("PODZOL")),3,
                    mkP(mkl("PODZOL"),mkl("MYCELIUM")),3,
                    mkP(mkl("MYCELIUM"),mkl("ROOTED_DIRT")),3,
                    mkP(mkl("ROOTED_DIRT"),mkl("STONE")),3,
                    mkP(mkl("OBSIDIAN"),mkl("NETHERRACK")),3,
                    mkP(mkl("NETHERRACK"),mkl("CRIMSON_NYLIUM")),3,
                    mkP(mkl("CRIMSON_NYLIUM"),mkl("WARPED_NYLIUM")),3,
                    mkP(mkl("WARPED_NYLIUM"),mkl("MAGMA_BLOCK")),3,
                    mkP(mkl("MAGMA_BLOCK"),mkl("BASALT")),3,
                    mkP(mkl("BASALT"),mkl("OBSIDIAN")),3,
                    mkP(mkl("END_STONE"),mkl("ENDER_EYE")),3,
                    mkP(mkl("ENDER_EYE"),mkl("CHORUS_FRUIT")),3,
                    mkP(mkl("CHORUS_FRUIT"),mkl("CHORUS_FLOWER")),3,
                    mkP(mkl("CHORUS_FLOWER"),mkl("CHORUS_PLANT")),3,
                    mkP(mkl("CHORUS_PLANT"),mkl("ENDER_PEARL")),3,
                    mkP(mkl("ENDER_PEARL"),mkl("END_STONE")),3
                    ))
            .register();

    public static final  SlimefunItem VIRTUAL_KILLER=new VirtualKiller(BASIC, LogiTechSlimefunItemStacks.VIRTUAL_KILLER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_BUTCHER","2PROGRAMMABLE_ANDROID_3_BUTCHER",null,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_FISHERMAN","2PROGRAMMABLE_ANDROID_3_FISHERMAN",null,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_BUTCHER","2PROGRAMMABLE_ANDROID_3_BUTCHER",null,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,null,"2PROGRAMMABLE_ANDROID_3_FISHERMAN","2PROGRAMMABLE_ANDROID_3_FISHERMAN",null,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER), 300_000,30_000,
            1)
            .register();
    public static final  SlimefunItem LVOID_GENERATOR=new TestGenerator(ENERGY, LogiTechSlimefunItemStacks.LVOID_GENERATOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LPLATE,null,null,null,null,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LPLATE,null,null,null,null,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LFIELD),2333,180,
            1000,1145)
            .register();
    public static final  SlimefunItem SIMU_LVOID=new SimulateTestGenerator(ENERGY, LogiTechSlimefunItemStacks.SIMU_LVOID,COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.LVOID_GENERATOR,LogiTechSlimefunItemStacks.LVOID_GENERATOR,LogiTechSlimefunItemStacks.LVOID_GENERATOR,LogiTechSlimefunItemStacks.LVOID_GENERATOR,null,
                    LogiTechSlimefunItemStacks.LVOID_GENERATOR,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LVOID_GENERATOR,
                    LogiTechSlimefunItemStacks.LVOID_GENERATOR,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LVOID_GENERATOR,
                    LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.HGTLPBBI,
                    LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.PDCECDMD,
                    setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2),setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,2))
            , 2333,180,1000,1145)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.SIMU_LVOID,6));
    public static final  SlimefunItem ENERGY_TRASH=new EnergyTrash(ENERGY, LogiTechSlimefunItemStacks.ENERGY_TRASH,ANCIENT_ALTAR,
            recipe(LogiTechSlimefunItemStacks.BUG,"ENERGY_REGULATOR",LogiTechSlimefunItemStacks.BUG,"ENERGY_CONNECTOR","TRASH_CAN_BLOCK","ENERGY_CONNECTOR",
                    LogiTechSlimefunItemStacks.BUG,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.BUG), 100_000_000)
            .register();
    public static final  SlimefunItem OPPO_GEN=new BiReactor(ENERGY, LogiTechSlimefunItemStacks.OPPO_GEN,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), 50_000,2_500,10_000)
            .register();
    public static final  SlimefunItem ARC_REACTOR=new EGenerator(ENERGY, LogiTechSlimefunItemStacks.ARC_REACTOR,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.PAGOLD,null,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SMELERY_CORE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.MASS_CORE,LogiTechSlimefunItemStacks.METAL_CORE,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.BISILVER), Material.BEACON,2_500_000,333_333,
            mkMp(
                mkP(    mkl(LogiTechSlimefunItemStacks.PAGOLD),mkl("GOLD_INGOT")) ,6000,
                    mkP(    mkl(LogiTechSlimefunItemStacks.BISILVER),mkl(LogiTechSlimefunItemStacks.ABSTRACT_INGOT)) ,4800
            ))
            .register();

    public static final  SlimefunItem CHIP_REACTOR=new ChipReactor(ENERGY, LogiTechSlimefunItemStacks.CHIP_REACTOR,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,3),setC(LogiTechSlimefunItemStacks.HGTLPBBI,2),setC(LogiTechSlimefunItemStacks.HGTLPBBI,2),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,3),null,
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,3),setC(LogiTechSlimefunItemStacks.PDCECDMD,2),setC(LogiTechSlimefunItemStacks.LASER,3),setC(LogiTechSlimefunItemStacks.LASER,3),setC(LogiTechSlimefunItemStacks.PDCECDMD,2),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,3),
                    setC(LogiTechSlimefunItemStacks.TECH_CORE,8),LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.ADVANCED_CHIP_MAKER,LogiTechSlimefunItemStacks.ADVANCED_CHIP_MAKER,LogiTechSlimefunItemStacks.CHIP_CORE,setC(LogiTechSlimefunItemStacks.TECH_CORE,8),
                    setC(LogiTechSlimefunItemStacks.TECH_CORE,8),setC(LogiTechSlimefunItemStacks.HGTLPBBI,2),setC(LogiTechSlimefunItemStacks.LASER,3),setC(LogiTechSlimefunItemStacks.LASER,3),setC(LogiTechSlimefunItemStacks.HGTLPBBI,2),setC(LogiTechSlimefunItemStacks.TECH_CORE,8),
                    setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,6)), 200_000_000,0.1,300)
            .register();
    public static final SlimefunItem ENERGY_AMPLIFIER=new EnergyAmplifier(ENERGY,LogiTechSlimefunItemStacks.ENERGY_AMPLIFIER,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),1_000_000_000,2.0)
            .register();
    public static final  SlimefunItem ENERGY_PIPE=new EnergyPipe(ENERGY, LogiTechSlimefunItemStacks.ENERGY_PIPE,ENHANCED_CRAFTING_TABLE,
            recipe("COPPER_INGOT","SILVER_INGOT","COPPER_INGOT","COPPER_INGOT","SILVER_INGOT","COPPER_INGOT",
                    "COPPER_INGOT","SILVER_INGOT","COPPER_INGOT"), 100_000,0.10f)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ENERGY_PIPE,8));
    public static final  SlimefunItem ENERGY_PIPE_PLUS=new EnergyPipe(ENERGY, LogiTechSlimefunItemStacks.ENERGY_PIPE_PLUS,COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ENERGY_PIPE,null,
                    null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.ENERGY_PIPE,null,
                    null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ENERGY_PIPE,null,
                    null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ENERGY_PIPE,null,
                    null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.ENERGY_PIPE,null,
                    null,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ENERGY_PIPE,null),2_000_000_000,0.0f)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ENERGY_PIPE_PLUS,16));
    public static final  SlimefunItem ENERGY_STORAGE_NONE=new EnergyStorage(ENERGY, LogiTechSlimefunItemStacks.ENERGY_STORAGE_NONE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,"ENERGY_CONNECTOR",LogiTechSlimefunItemStacks.NOLOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), 2_000_000_000, EnergyNetComponentType.NONE)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ENERGY_STORAGE_NONE,8));
    public static final  SlimefunItem ENERGY_STORAGE_IN=new EnergyStorage(ENERGY, LogiTechSlimefunItemStacks.ENERGY_STORAGE_IN,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,"MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), 1_000_000_000,EnergyNetComponentType.CONSUMER)
            .register();
    public static final  SlimefunItem ENERGY_STORAGE_IO=new EnergyIOStorage(ENERGY, LogiTechSlimefunItemStacks.ENERGY_STORAGE_IO,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ENERGY_STORAGE_NONE,LogiTechSlimefunItemStacks.NOLOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT),2_000_000)
            .register();

    public static final  SlimefunItem ADJ_COLLECTOR=new AdjacentEnergyCollector(ENERGY, LogiTechSlimefunItemStacks.ADJ_COLLECTOR,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC,"ENERGY_REGULATOR",LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ENERGY_CONNECTOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT), 1_000_000)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ADJ_COLLECTOR,6));
    public static final  SlimefunItem ADJ_COLLECTOR_PLUS=new AdjacentEnergyCollector(ENERGY, LogiTechSlimefunItemStacks.ADJ_COLLECTOR_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.ADJ_COLLECTOR,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ADJ_COLLECTOR),1_000_000_000)

            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.ADJ_COLLECTOR_PLUS,2));
    public static final  SlimefunItem LINE_COLLECTOR=new LineEnergyCollector(ENERGY, LogiTechSlimefunItemStacks.LINE_COLLECTOR,COMMON_TYPE,
            recipe("ENERGY_REGULATOR","SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"SMALL_CAPACITOR","ENERGY_REGULATOR",
                    "SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.LENGINE,"SMALL_CAPACITOR",
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,
                    "SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.ADJ_COLLECTOR,LogiTechSlimefunItemStacks.LENGINE,"SMALL_CAPACITOR",
                    "ENERGY_REGULATOR","SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"SMALL_CAPACITOR","ENERGY_REGULATOR"), 16_000_000)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.LINE_COLLECTOR,4));
    public static final  SlimefunItem LINE_COLLECTOR_PLUS=new LineEnergyCollector(ENERGY, LogiTechSlimefunItemStacks.LINE_COLLECTOR_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.LINE_COLLECTOR,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LINE_COLLECTOR),2_000_000_000)

            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.LINE_COLLECTOR_PLUS,2));
    public static final  SlimefunItem ADJ_CHARGER=new AdjacentEnergyCharger(ENERGY, LogiTechSlimefunItemStacks.ADJ_CHARGER,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,"CHARGING_BENCH",LogiTechSlimefunItemStacks.NOLOGIC,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ENERGY_PIPE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), 1_000_000)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.ADJ_CHARGER,6));
    public static final  SlimefunItem ADJ_CHARGER_PLUS=new AdjacentEnergyCharger(ENERGY, LogiTechSlimefunItemStacks.ADJ_CHARGER_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ADJ_CHARGER,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ADJ_CHARGER,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.ADJ_CHARGER,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ADJ_CHARGER),1_000_000_000)

            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.ADJ_CHARGER_PLUS,2));
    public static final  SlimefunItem LINE_CHARGER=new LineEnergyCharger(ENERGY, LogiTechSlimefunItemStacks.LINE_CHARGER,COMMON_TYPE,
            recipe("ENERGY_REGULATOR","SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"SMALL_CAPACITOR","ENERGY_REGULATOR",
                    "SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.LDIGITIZER,"SMALL_CAPACITOR",
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LPLATE,
                    "SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.ADJ_CHARGER,LogiTechSlimefunItemStacks.LENGINE,"SMALL_CAPACITOR",
                    "ENERGY_REGULATOR","SMALL_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"SMALL_CAPACITOR","ENERGY_REGULATOR"),1_000_000)
            .register()
            .setOutput(setC( LogiTechSlimefunItemStacks.LINE_CHARGER,4));

    public static final  SlimefunItem LINE_CHARGER_PLUS=new LineEnergyCharger(ENERGY, LogiTechSlimefunItemStacks.LINE_CHARGER_PLUS,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LINE_CHARGER,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LINE_CHARGER,"ENERGIZED_CAPACITOR",null,"ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.LINE_CHARGER,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LINE_CHARGER),2_000_000_000)

            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.LINE_CHARGER_PLUS,2));
    public static final  SlimefunItem CHUNK_CHARGER=new ChunkEnergyCharger(ENERGY, LogiTechSlimefunItemStacks.CHUNK_CHARGER,COMMON_TYPE,
            recipe("4ENERGY_REGULATOR","2MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"2MEDIUM_CAPACITOR","4ENERGY_REGULATOR",
                    "2MEDIUM_CAPACITOR",setC(LogiTechSlimefunItemStacks.LSINGULARITY,4),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,2),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,4),"2MEDIUM_CAPACITOR",
                    LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,2),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,2),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LPLATE,
                    "2MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.LDIGITIZER,"2MEDIUM_CAPACITOR",
                    "4ENERGY_REGULATOR","2MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"2MEDIUM_CAPACITOR","4ENERGY_REGULATOR"),2_000_000_000)
            .register();
    public static final  SlimefunItem SPECIAL_CRAFTER=new SpecialTypeCrafter(BASIC, LogiTechSlimefunItemStacks.SPECIAL_CRAFTER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT)
            , Material.NETHER_STAR,0,1200,20_000,new HashSet<>(){{
                if(AddDepends.INFINITYWORKBENCH_TYPE!=null)
                    add(AddDepends.INFINITYWORKBENCH_TYPE);
                if(AddDepends.VOIDHARVEST!=null)
                    add(AddDepends.VOIDHARVEST);
            }})
            .register();
    public static final  SlimefunItem STAR_SMELTERY=new AEMachine(ADVANCED, LogiTechSlimefunItemStacks.STAR_SMELTERY,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,setC(LogiTechSlimefunItemStacks.LPLATE,1),"ELECTRIC_INGOT_FACTORY_3","ELECTRIC_INGOT_FACTORY_3",setC(LogiTechSlimefunItemStacks.LPLATE,1),LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LMOTOR,"CARBONADO_EDGED_FURNACE","CARBONADO_EDGED_FURNACE",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,setC(LogiTechSlimefunItemStacks.LPLATE,1),"ELECTRIC_SMELTERY_2","ELECTRIC_SMELTERY_2",setC(LogiTechSlimefunItemStacks.LPLATE,1),LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LFIELD), Material.BEACON,18_000,180_000, (List<Pair<Object, Integer>>) null ){
        int recipetime=120;
        {
            STARSMELTERY.relatedTo((input,out)->{
                this.machineRecipes.add(MachineRecipeUtils.stackFrom(recipetime,input,new ItemStack[]{out}));
            },(input,out)->{
                this.machineRecipes.removeIf(recipe->recipe.getOutput().length>=1&&CraftUtils.matchItemStack(recipe.getOutput()[0],out,true));
            });
        }
        public int[] getSlotsAccessedByItemTransportPlus(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item){
            if(flow==ItemTransportFlow.WITHDRAW){
                return this.getOutputSlots();
            }else {
                if(item==null||item.getType().isAir()||item.getType().getMaxStackSize()==1){
                    return this.getInputSlots();
                }
                int[] inputSlot=getInputSlots();
                ItemCounter push=CraftUtils.getCounter(item);
                ItemStack itemInSlot;
                for(int i =0;i<inputSlot.length;++i){
                    itemInSlot=menu.getItemInSlot(inputSlot[i]);
                    if(itemInSlot==null||itemInSlot.getType()!=item.getType()){
                        continue;
                    }
                    if(CraftUtils.matchItemStack(itemInSlot,push,false)){
                        return new int[]{inputSlot[i]};
                    }
                }
                return inputSlot;
            }
        }
    }
            .register();
    public static final  SlimefunItem SEQ_CONSTRUCTOR=new SequenceConstructor(BASIC, LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.STAR_GOLD,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.STAR_GOLD,1),
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LBOOLIZER,null,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,null,LogiTechSlimefunItemStacks.LBOOLIZER,
                    LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LBOOLIZER,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    setC(LogiTechSlimefunItemStacks.STAR_GOLD,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.STAR_GOLD,1)),new ItemStack(Material.NETHERITE_BLOCK) ,1280,8848,
            mkMp(
                    //重金核心铜铁金 下界  重金核心-------
                    //镁铅锡锌 冶炼核心-------
                    //银铝 红石 钻石 科技核心-------
                    //煤炭 青金石 石英 绿宝石 物质核心-------
                    mkP(mkl("512COPPER_INGOT","512IRON_INGOT","512GOLD_INGOT","512NETHERITE_INGOT"),mkl(LogiTechSlimefunItemStacks.METAL_CORE)),3,
                    mkP(mkl("512MAGNESIUM_INGOT","512LEAD_INGOT","512TIN_INGOT","512ZINC_INGOT"),mkl(LogiTechSlimefunItemStacks.SMELERY_CORE)),3,
                    mkP(mkl("512SILVER_INGOT","512ALUMINUM_INGOT","512DIAMOND","512REDSTONE"),mkl(LogiTechSlimefunItemStacks.TECH_CORE)),3,
                    mkP(mkl("512COAL","512LAPIS_LAZULI","512QUARTZ","512EMERALD"),mkl(LogiTechSlimefunItemStacks.MASS_CORE)),3,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,1024)),mkl(LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY)),3,
                    mkP(mkl(LogiTechSlimefunItemStacks.METAL_CORE,LogiTechSlimefunItemStacks.SMELERY_CORE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.MASS_CORE),mkl(LogiTechSlimefunItemStacks.EASYSTACKMACHINE)),3
            ))
            .register();




    public static final  SlimefunItem CHIP_MAKER=new EMachine(ADVANCED, LogiTechSlimefunItemStacks.CHIP_MAKER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    null,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,null,
                    LogiTechSlimefunItemStacks.LPLATE,null,LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.NOLOGIC,null,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE), Material.POTATO,0,0,
            mkMp(
                mkP(mkl(setC(LogiTechSlimefunItemStacks.CHIP_INGOT,6),LogiTechSlimefunItemStacks.EXISTE),mkl( ChipCardCode.CHIP_0)),4,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.CHIP_INGOT,6),LogiTechSlimefunItemStacks.UNIQUE),mkl( ChipCardCode.CHIP_1)),4,
                    mkP(mkl(LogiTechSlimefunItemStacks.LSCHEDULER,ChipCardCode.CHIP_FINAL),mkl(setC(LogiTechSlimefunItemStacks.CHIP_CORE,2))),4
            )).register();

    public static final SlimefunItem CHIP_CONSUMER=new ChipConsumer(ADVANCED,LogiTechSlimefunItemStacks.CHIP_CONSUMER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.CHIP_INGOT,
                    LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LDIGITIZER,setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.CHIP_INGOT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE),4_000,500)
            .register();

    public static final  SlimefunItem CHIP_BICONSUMER=new ChipBiConsumer(ADVANCED, LogiTechSlimefunItemStacks.CHIP_BICONSUMER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LDIGITIZER,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LDIGITIZER,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),setC(LogiTechSlimefunItemStacks.UNIQUE,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.EXISTE,1),setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LDIGITIZER,1),LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.LDIGITIZER,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE),8_000,1_000)
            .register();
    public static final SlimefunItem EASYSTACKMACHINE=new StackMachine(BASIC,LogiTechSlimefunItemStacks.EASYSTACKMACHINE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,Language.get("machine.SEQ_CONSTRUCTOR.name")),Material.IRON_PICKAXE,
            20_000,4_000_000,0.5)
            .register();
    public static final SlimefunItem STACKMACHINE=new StackMachine(ADVANCED,LogiTechSlimefunItemStacks.STACKMACHINE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),Material.IRON_PICKAXE,
            2_000,40_000_000,2.0)
            .register();
    //
    public static final  SlimefunItem ADVANCED_CHIP_MAKER=new ChipCopier(ADVANCED, LogiTechSlimefunItemStacks.ADVANCED_CHIP_MAKER,COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_MAKER,LogiTechSlimefunItemStacks.CHIP_MAKER,LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT,null,
                    null,LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT,LogiTechSlimefunItemStacks.CHIP_MAKER,LogiTechSlimefunItemStacks.CHIP_MAKER,LogiTechSlimefunItemStacks.REINFORCED_CHIP_INGOT,null,
                    LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.LASER,2),LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.HGTLPBBI,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.LSINGULARITY,8),setC(LogiTechSlimefunItemStacks.LSINGULARITY,8),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.LSINGULARITY,8),setC(LogiTechSlimefunItemStacks.LSINGULARITY,8),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.LMOTOR,4),setC(LogiTechSlimefunItemStacks.LMOTOR,4),LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.PDCECDMD))
            .register();

    public static final  SlimefunItem ITEM_OP=new ItemOperator(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.ITEM_OP,ENHANCED_CRAFTING_TABLE,
            recipe("NAME_TAG","CRYING_OBSIDIAN","NAME_TAG","AUTO_DISENCHANTER_2","AUTO_ANVIL_2","AUTO_ENCHANTER_2",
                    "ENCHANTING_TABLE","ANVIL","SMITHING_TABLE"), 0,0)
            .register();

    public static final  SlimefunItem TNT_GEN=new TntGenerator(VANILLA, LogiTechSlimefunItemStacks.TNT_GEN,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LOGIC_GATE,"OBSERVER",LogiTechSlimefunItemStacks.LOGIC_GATE,"STICKY_PISTON",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"STICKY_PISTON",
                    LogiTechSlimefunItemStacks.LOGIC_GATE,"NOTE_BLOCK",LogiTechSlimefunItemStacks.LOGIC_GATE))
            .register();
    public static final SlimefunItem BEDROCK_BREAKER=new CustomProps(VANILLA, LogiTechSlimefunItemStacks.BEDROCK_BREAKER, ENHANCED_CRAFTING_TABLE,
            recipe("TNT", "LEVER", "TNT", "PISTON", LogiTechSlimefunItemStacks.LOGIC_GATE, "PISTON",
                    LogiTechSlimefunItemStacks.NOLOGIC, LogiTechSlimefunItemStacks.BUG, LogiTechSlimefunItemStacks.NOLOGIC), null) {
        protected ItemConsumer CONSUMED=CraftUtils.getConsumer(new ItemStack(Material.PISTON));
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            Optional<Block> b=event.getClickedBlock();
            if(b.isPresent()&&event.getPlayer()!=null){
                Block block=b.get();
                Location loc=block.getLocation();
                    //不是sf方块
                if(WorldUtils.hasPermission(event.getPlayer(),loc,Interaction.BREAK_BLOCK,Interaction.PLACE_BLOCK)&&canUse(event.getPlayer(),false)){
                    SlimefunItem item;
                    if((item=DataCache.getSfItem(loc))==null||item==LOGIC_CORE||item==ENDFRAME_MACHINE||item==ENDDUPE_MG){
                        if(WorldUtils.consumeItem(event.getPlayer(),CONSUMED)){
                            if(WorldUtils.breakVanillaBlockByPlayer(block,event.getPlayer(),true,false)){
                                if(item!=null){
                                    WorldUtils.removeSlimefunBlock(loc,event.getPlayer(),false);
                                }
                                sendMessage(event.getPlayer(),"&a已成功破坏方块");
                            }else{
                                sendMessage(event.getPlayer(),"&c抱歉,您没有在这里破坏方块的权限");
                            }
                        }else{

                            sendMessage(event.getPlayer(),"&c所需物品不足!");

                        }
                    }else{
                        sendMessage(event.getPlayer(),"&c该方块是粘液方块!不能被该道具破坏");
                    }
                }else{
                    sendMessage(event.getPlayer(),"&c抱歉,您没有在这里使用该物品的权限");
                }

            }
            event.cancel();
        }
    }
            .register();
    public static final SlimefunItem SUPERSPONGE=new CustomProps(VANILLA,LogiTechSlimefunItemStacks.SUPERSPONGE,ENHANCED_CRAFTING_TABLE,
            recipe("SPONGE","PISTON","SPONGE","REDSTONE",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"REDSTONE",
                    "SPONGE","PISTON","SPONGE")){
        protected final int SEARCH_RANGE=10;
        protected final Set<Player> COOL_DOWN=ConcurrentHashMap.newKeySet();
        public void onClickAction(PlayerRightClickEvent event){
            Player p=event.getPlayer();
            if(p!=null){
                if(COOL_DOWN.contains(p)){
                    sendMessage(p,"&c物品冷却中");
                }else{

                    Location loc=p.getLocation();
                    if(WorldUtils.hasPermission(p,loc,Interaction.INTERACT_BLOCK,Interaction.PLACE_BLOCK)&&canUse(p,true)){
                        ItemStack stack= event.getItem();
                        stack.setAmount(stack.getAmount()-1);
                        forceGive(p,LogiTechSlimefunItemStacks.SUPERSPONGE_USED,1);
                        final HashSet<Block> liquids=new HashSet<>();
                        final HashSet<Block> blockInLiquids=new HashSet<>();
                        final HashSet<Block> forceLiquids=new HashSet<>();
                        Schedules.launchSchedules(()->{
                            COOL_DOWN.add(p);
                            try{
                                int dx=loc.getBlockX();
                                int dy=loc.getBlockY();
                                int dz=loc.getBlockZ();
                                sendMessage(p,"&a开始搜索");
                                for(int i=-SEARCH_RANGE;i<=SEARCH_RANGE;i++){
                                    for(int j=-SEARCH_RANGE;j<=SEARCH_RANGE;j++){
                                        for(int k=-SEARCH_RANGE;k<=SEARCH_RANGE;k++){
                                            Block checkBlock=loc.getWorld().getBlockAt(dx+i,dy+j,dz+k);
                                            if(checkBlock!=null){
                                                if(WorldUtils.isLiquid( checkBlock)){
                                                    liquids.add(checkBlock);
                                                }
                                                else if(WorldUtils.isWaterLogged(checkBlock)){
                                                    blockInLiquids.add(checkBlock);
                                                }else if(WorldUtils.BLOCK_MUST_WATERLOGGED.contains(checkBlock.getType())){
                                                    forceLiquids.add(checkBlock);
                                                }
                                            }

                                        }
                                    }
                                }
                                if(!liquids.isEmpty()||!blockInLiquids.isEmpty()||!forceLiquids.isEmpty()){
                                    sendMessage(p,"&a搜索完成,正在吸取液体");
                                    BukkitUtils.executeSync(()->{
                                        List<BlockState> blocksToBeChanged=new ArrayList<>(liquids.size()+blockInLiquids.size()+2);
                                        for(Block b:liquids){
                                            blocksToBeChanged.add(b.getState(false));
                                        }
                                        for(Block b:blockInLiquids){
                                            blocksToBeChanged.add(b.getState(false));
                                        }
                                        SpongeAbsorbEvent spongeAbsorbEvent=new SpongeAbsorbEvent(loc.getBlock(),blocksToBeChanged);
                                        Bukkit.getPluginManager().callEvent(spongeAbsorbEvent);
                                        if(spongeAbsorbEvent.isCancelled()){
                                            sendMessage(p,"&c抱歉,你没有在这里吸取液体的权限");
                                        }else {
                                            for(Block b:liquids){
                                                b.setType(Material.AIR);
                                            }
                                            for(Block b:blockInLiquids){
                                                BlockData data=b.getBlockData();
                                                if(data instanceof Waterlogged wl){
                                                    wl.setWaterlogged(false);
                                                    b.setBlockData(data,true);
                                                }
                                            }

                                            sendMessage(p,"&a成功移除液体!");
                                        }
                                        if(!forceLiquids.isEmpty()){
                                            for(Block b:forceLiquids){
                                                if(WorldUtils.testVanillaBlockBreakPermission(b,p,false)){
                                                    b.setType(Material.AIR);
                                                }
                                            }
                                            sendMessage(p,"&a成功移除海草和海带!");
                                        }
                                    });
                                }else{
                                    sendMessage(p,"&c附近没有剩余的液体");
                                }
                            }finally {
                                COOL_DOWN.remove(p);
                            }
                        },0,false,0);
                    }
                    else{
                        sendMessage(p,"&c抱歉,你没有在这里吸水的权限");
                    }

                }
            }
            event.cancel();
        }
    }
            .register().setOutput(setC(LogiTechSlimefunItemStacks.SUPERSPONGE,8));
    public static final SlimefunItem SUPERSPONGE_USED=new MaterialItem(FUNCTIONAL,LogiTechSlimefunItemStacks.SUPERSPONGE_USED,NULL,NULL_RECIPE.clone())
            .register();
    public static final  SlimefunItem VIRTUAL_EXPLORER=new VirtualExplorer(VANILLA, LogiTechSlimefunItemStacks.VIRTUAL_EXPLORER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,"NETHERITE_INGOT","NETHERITE_INGOT","NETHERITE_INGOT","NETHERITE_INGOT",null,
                    "NETHERITE_INGOT","ELYTRA",LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LIOPORT,"ELYTRA","NETHERITE_INGOT",
                    "NETHERITE_INGOT","COBALT_PICKAXE",LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.LSINGULARITY,"COBALT_PICKAXE","NETHERITE_INGOT",
                    "NETHERITE_INGOT","SLIME_LEGGINGS",LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LPLATE,"SLIME_LEGGINGS","NETHERITE_INGOT",
                    "NETHERITE_INGOT","SLIME_BOOTS","STEEL_THRUSTER","STEEL_THRUSTER","SLIME_BOOTS","NETHERITE_INGOT"), 12_500,1250)
            .register();

    //Material Generators
    public static final SlimefunItem MAGIC_STONE=new SMGenerator(GENERATORS, LogiTechSlimefunItemStacks.MAGIC_STONE,ENHANCED_CRAFTING_TABLE,
            recipe("IRON_PICKAXE","LAVA_BUCKET","IRON_PICKAXE",
                    "PISTON",LogiTechSlimefunItemStacks.LOGIC_GATE,"PISTON",
                    "COBALT_PICKAXE","WATER_BUCKET","COBALT_PICKAXE"),16,500,33,
            randItemStackFactory(
                    mkMp("24COBBLESTONE",75,
                            "2COAL",5,
                            "2REDSTONE",5,
                            "IRON_INGOT",5,
                            "4LAPIS_LAZULI",4,
                            "GOLD_INGOT",3,
                            "DIAMOND",2,
                            "EMERALD",1
                    )
            ))
            .register();
    public static final SlimefunItem BOOL_MG = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.BOOL_MG, COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,null,
                    LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.LOGIC_GATE,1),setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,
                    null,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,null), 6, 1919, 256,
            new PairList<>(){{
                put(mkl(LogiTechSlimefunItemStacks.BOOLEAN_TRUE),mkl(setC(LogiTechSlimefunItemStacks.BOOLEAN_TRUE,114514)));
                put(mkl(LogiTechSlimefunItemStacks.BOOLEAN_FALSE),mkl(setC(LogiTechSlimefunItemStacks.BOOLEAN_FALSE,1919810)));
                put(mkl(LogiTechSlimefunItemStacks.LBOOLIZER),mkl(setC(LogiTechSlimefunItemStacks.LBOOLIZER,1)));
            }})
            .register();

    public static final SlimefunItem OVERWORLD_MINER=new SMGenerator(GENERATORS, LogiTechSlimefunItemStacks.OVERWORLD_MINER,ENHANCED_CRAFTING_TABLE,
            recipe("REINFORCED_PLATE",LogiTechSlimefunItemStacks.MAGIC_STONE,"REINFORCED_PLATE",
                    LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.EXISTE
                    ,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.MAGIC_STONE,LogiTechSlimefunItemStacks.WORLD_FEAT
            ),12,2_500,400,
            randItemStackFactory(
                    mkMp("32COBBLESTONE",40,
                            "3COAL",9,
                            "3REDSTONE",9,
                            "3IRON_INGOT",9,
                            "6LAPIS_LAZULI",9,
                            "3GOLD_INGOT",9,
                            "3DIAMOND",8,
                            "3EMERALD",7
                    )
            ))
            .register();
    public static final SlimefunItem NETHER_MINER=new SMGenerator(GENERATORS, LogiTechSlimefunItemStacks.NETHER_MINER,COMMON_TYPE,
            recipe(null,setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),null,
                    null,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.OVERWORLD_MINER,LogiTechSlimefunItemStacks.OVERWORLD_MINER,LogiTechSlimefunItemStacks.LPLATE,null,
                    null,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LPLATE,null,
                    null,LogiTechSlimefunItemStacks.NETHER_FEAT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.NETHER_FEAT,null
                  ),10,3_000,600,
            randItemStackFactory(
                    mkMp(
                            "32NETHERRACK",30,
                            "6QUARTZ",40,
                            "16MAGMA_BLOCK",7,
                            "16BLACKSTONE",7,
                            "16BASALT",7,
                            "8NETHER_WART",7,
                            "4ANCIENT_DEBRIS",2
                    )
            ))
            .register();
    public static final SlimefunItem END_MINER=new SMGenerator(GENERATORS, LogiTechSlimefunItemStacks.END_MINER,COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LMOTOR,null,
                    null,LogiTechSlimefunItemStacks.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,null,
                    null,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.NETHER_MINER,LogiTechSlimefunItemStacks.NETHER_MINER,LogiTechSlimefunItemStacks.LPLATE,null,
                    null,LogiTechSlimefunItemStacks.LPLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.LPLATE,null,
                    LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.END_FEAT
            ),8,6_000,1000,
            randItemStackFactory(
                    mkMp(
                            "32END_STONE",150,
                            "10OBSIDIAN",100,
                            "2CHORUS_FLOWER",30,
                            "SHULKER_SHELL",50,
                            "DRAGON_BREATH",40,
                            LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,20,
                            LogiTechSlimefunItemStacks.STAR_GOLD,10
                    )
            ))
            .register();
    public static final SlimefunItem DIMENSION_MINER=new SMGenerator(GENERATORS, LogiTechSlimefunItemStacks.DIMENSION_MINER,COMMON_TYPE  ,
            recipe("GPS_TRANSMITTER_2",LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD,"GPS_TRANSMITTER_2",
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
                    LogiTechSlimefunItemStacks.LFIELD,null,LogiTechSlimefunItemStacks.NETHER_MINER,LogiTechSlimefunItemStacks.END_MINER,null,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,null,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,null,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
                    "GPS_TRANSMITTER_2",LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD,"GPS_TRANSMITTER_2"),6,18_000,1_800,
            randItemStackFactory(
                    mkMp(
                            "64COBBLESTONE",1,
                            "64END_STONE",1,
                            "64NETHERRACK",1
                    )
            ),

            randItemStackFactory(
                    mkMp(
                            LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,3,
                            LogiTechSlimefunItemStacks.STAR_GOLD,1
                    )
            ),
            randItemStackFactory(
                    mkMp(
                            "12COAL",8,
                            "16REDSTONE",8,
                            "12IRON_INGOT",8,
                            "16LAPIS_LAZULI",8,
                            "12GOLD_INGOT",8,
                            "12DIAMOND",8,
                            "12EMERALD",8,
                            "16QUARTZ",8,
                            "4ANCIENT_DEBRIS",8
                    )
            ))
            .register();
    //TODO 完成这块

    public static final SlimefunItem MAGIC_PLANT = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.MAGIC_PLANT, ENHANCED_CRAFTING_TABLE,
            recipe(Material.DIAMOND_HOE,"WATER_BUCKET",Material.DIAMOND_HOE,
                    "OBSERVER",LogiTechSlimefunItemStacks.LOGIC_GATE,"OBSERVER",
                    Material.BONE_BLOCK,"PISTON",Material.BONE_BLOCK), 45, 1_000, 33,
            new PairList<>(){{
                put(mkl("WHEAT_SEEDS"),mkl("WHEAT","WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("2CARROT"));
                put(mkl("POTATO"),mkl("2POTATO"));
                put(mkl("PUMPKIN_SEEDS"),mkl("PUMPKIN","PUMPKIN_SEEDS"));
                put(mkl("SUGAR_CANE"),mkl("2SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("OAK_SAPLING","2OAK_LOG","OAK_LEAVES"));
            }})
            .register();
    public static final SlimefunItem OVERWORLD_PLANT = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.OVERWORLD_PLANT, ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.MAGIC_PLANT,LogiTechSlimefunItemStacks.LENGINE,
                    "TREE_GROWTH_ACCELERATOR","CROP_GROWTH_ACCELERATOR","TREE_GROWTH_ACCELERATOR"
                    ,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.MAGIC_PLANT,LogiTechSlimefunItemStacks.WORLD_FEAT
            ), 9, 2_500,400,
            new PairList<>(){{
                put(mkl("COCOA_BEANS"),mkl("6COCOA_BEANS"));
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("8BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("8WHEAT","6WHEAT_SEEDS"));
                put(mkl("APPLE"),mkl("9APPLE"));
                put(mkl("BROWN_MUSHROOM"),mkl("6BROWN_MUSHROOM"));
                put(mkl("RED_MUSHROOM"),mkl("6RED_MUSHROOM"));
                put(mkl("DEAD_BUSH"),mkl("6DEAD_BUSH","2STICK"));
                put(mkl("CARROT"),mkl("8CARROT"));
                put(mkl("POTATO"),mkl("8POTATO"));
                put(mkl("SWEET_BERRIES"),mkl("6SWEET_BERRIES"));
                put(mkl("GLOW_BERRIES"),mkl("6GLOW_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("6SUGAR_CANE"));
                put(mkl("BAMBOO"),mkl("6BAMBOO"));
                put(mkl("CACTUS"),mkl("6CACTUS"));
                put(mkl("DANDELION"),mkl("6DANDELION"));
                put(mkl("POPPY"),mkl("3POPPY"));
                put(mkl("BLUE_ORCHID"),mkl("9BLUE_ORCHID"));
                put(mkl("ALLIUM"),mkl("9ALLIUM"));
                put(mkl("AZURE_BLUET"),mkl("9AZURE_BLUET"));
                put(mkl("RED_TULIP"),mkl("9RED_TULIP"));
                put(mkl("ORANGE_TULIP"),mkl("9ORANGE_TULIP"));
                put(mkl("WHITE_TULIP"),mkl("9WHITE_TULIP"));
                put(mkl("PINK_TULIP"),mkl("9PINK_TULIP"));
                put(mkl("OXEYE_DAISY"),mkl("9OXEYE_DAISY"));
                put(mkl("CORNFLOWER"),mkl("9CORNFLOWER"));
                put(mkl("LILY_OF_THE_VALLEY"),mkl("9LILY_OF_THE_VALLEY"));
                put(mkl("WITHER_ROSE"),mkl("6WITHER_ROSE"));
                put(mkl("SUNFLOWER"),mkl("6SUNFLOWER"));
                put(mkl("LILAC"),mkl("6LILAC"));
                put(mkl("ROSE_BUSH"),mkl("6ROSE_BUSH"));
                put(mkl("PEONY"),mkl("6PEONY"));
                put(mkl("MOSS_BLOCK"),mkl("4MOSS_BLOCK"));
                put(mkl("OAK_SAPLING"),mkl("12OAK_LOG","6APPLE","6OAK_LEAVES","OAK_SAPLING","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("12BIRCH_LOG","6APPLE","6BIRCH_LEAVES","BIRCH_SAPLING","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("12SPRUCE_LOG","6APPLE","6SPRUCE_LEAVES","SPRUCE_SAPLING","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("12DARK_OAK_LOG","6APPLE","6DARK_OAK_LEAVES","DARK_OAK_SAPLING","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("12JUNGLE_LOG","6APPLE","6JUNGLE_LEAVES","JUNGLE_SAPLING","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("12ACACIA_LOG","6APPLE","6ACACIA_LEAVES","ACACIA_SAPLING","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("12MANGROVE_LOG","6MANGROVE_LEAVES","MANGROVE_PROPAGULE"));
                put(mkl(Material.CHERRY_SAPLING),mkl("12CHERRY_LOG","6CHERRY_LEAVES",new ItemStack(Material.CHERRY_SAPLING,1)));
            }})
            .register();
    public static final SlimefunItem NETHER_PLANT = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.NETHER_PLANT, COMMON_TYPE,
            recipe(null,setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.OVERWORLD_PLANT,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LOGIC_GATE,"TREE_GROWTH_ACCELERATOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,LogiTechSlimefunItemStacks.NETHER_FEAT,"MEDIUM_CAPACITOR","MEDIUM_CAPACITOR",LogiTechSlimefunItemStacks.NETHER_FEAT,null
            ), 9,3_000,600,
            new PairList<>(){{
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("WEEPING_VINES"),mkl("12WEEPING_VINES"));
                put(mkl("TWISTING_VINES"),mkl("12TWISTING_VINES"));
                put(mkl("CRIMSON_ROOTS"),mkl("12CRIMSON_ROOTS"));
                put(mkl("WARPED_ROOTS"),mkl("12WARPED_ROOTS"));
                put(mkl("NETHER_SPROUTS"),mkl("12NETHER_SPROUTS"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));

            }})
            .register();
    public static final SlimefunItem END_PLANT = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.END_PLANT, COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.PARADOX,1),setC(LogiTechSlimefunItemStacks.PARADOX,1),LogiTechSlimefunItemStacks.LFIELD,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CROP_GROWTH_ACCELERATOR_2","TREE_GROWTH_ACCELERATOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.OVERWORLD_PLANT,LogiTechSlimefunItemStacks.NETHER_PLANT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.END_FEAT
            ), 9,6_000,1000,
            new PairList<>(){{
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("OAK_SAPLING"),mkl("9OAK_SAPLING","18OAK_LOG","6APPLE","9OAK_LEAVES","6STICK"));
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));
                put(mkl("CHORUS_FLOWER"),mkl("6CHORUS_FLOWER","24CHORUS_FRUIT"));
                put(mkl("KELP"),mkl("9KELP"));
                put(mkl("LILY_PAD"),mkl("9LILY_PAD"));
                put(mkl("SEA_PICKLE"),mkl("9SEA_PICKLE"));
                put(mkl("TUBE_CORAL_BLOCK"),mkl("6TUBE_CORAL_BLOCK"));
                put(mkl("BRAIN_CORAL_BLOCK"),mkl("6BRAIN_CORAL_BLOCK"));
                put(mkl("BUBBLE_CORAL_BLOCK"),mkl("6BUBBLE_CORAL_BLOCK"));
                put(mkl("FIRE_CORAL_BLOCK"),mkl("6FIRE_CORAL_BLOCK"));
                put(mkl("HORN_CORAL_BLOCK"),mkl("6HORN_CORAL_BLOCK"));
                put(mkl("TUBE_CORAL"),mkl("6TUBE_CORAL"));
                put(mkl("BRAIN_CORAL"),mkl("6BRAIN_CORAL"));
                put(mkl("BUBBLE_CORAL"),mkl("6BUBBLE_CORAL"));
                put(mkl("FIRE_CORAL"),mkl("6FIRE_CORAL"));
                put(mkl("HORN_CORAL"),mkl("6HORN_CORAL"));
                put(mkl("TUBE_CORAL_FAN"),mkl("6TUBE_CORAL_FAN"));
                put(mkl("BRAIN_CORAL_FAN"),mkl("6BRAIN_CORAL_FAN"));
                put(mkl("BUBBLE_CORAL_FAN"),mkl("6BUBBLE_CORAL_FAN"));
                put(mkl("FIRE_CORAL_FAN"),mkl("6FIRE_CORAL_FAN"));
                put(mkl("HORN_CORAL_FAN"),mkl("6HORN_CORAL_FAN"));
            }})
            .register();
    public static final SlimefunItem STONE_FACTORY = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.STONE_FACTORY, COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.METAL_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.ATOM_INGOT,4),LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LENGINE,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LIOPORT,setC(LogiTechSlimefunItemStacks.OVERWORLD_MINER,4),setC(LogiTechSlimefunItemStacks.OVERWORLD_MINER,4),LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.LMOTOR,"4PROGRAMMABLE_ANDROID_3_BUTCHER","4PROGRAMMABLE_ANDROID_3_BUTCHER",LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LPLATE,
                    setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.METAL_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),setC(LogiTechSlimefunItemStacks.PAGOLD,1)), 1, 129_600, 2400,
            new PairList<>(){{
                put(mkl("COBBLESTONE"),mkl(   randAmountItemFactory(new ItemStack(Material.COBBLESTONE),514,1919)));
                put(mkl("NETHERRACK"),mkl(randAmountItemFactory(new ItemStack(Material.NETHERRACK),514,1919)));
                put(mkl("END_STONE"),mkl(randAmountItemFactory(new ItemStack(Material.END_STONE),514,1919)));
                put(mkl("GRANITE"),mkl(randAmountItemFactory(new ItemStack(Material.GRANITE),114,514)));
                put(mkl("DIORITE"),mkl(randAmountItemFactory(new ItemStack(Material.DIORITE),114,514)));
                put(mkl("ANDESITE"),mkl(randAmountItemFactory(new ItemStack(Material.ANDESITE),114,514)));
                put(mkl("STONE"),mkl(randAmountItemFactory(new ItemStack(Material.STONE),114,514)));
                put(mkl("TUFF"),mkl(randAmountItemFactory(new ItemStack(Material.TUFF),114,514)));
            }})
            .register();
    public static final SlimefunItem REDSTONE_MG=new SMGenerator(VANILLA, LogiTechSlimefunItemStacks.REDSTONE_MG,COMMON_TYPE,
            recipe(null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null,
                    "REDSTONE_BLOCK",setC(LogiTechSlimefunItemStacks.NOLOGIC,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.NOLOGIC,1),"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.REDSTONE_ENGINE,1),setC(LogiTechSlimefunItemStacks.REDSTONE_ENGINE,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.REDSTONE_ENGINE,1),setC(LogiTechSlimefunItemStacks.REDSTONE_ENGINE,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"REDSTONE_BLOCK",
                    "REDSTONE_BLOCK",setC(LogiTechSlimefunItemStacks.NOLOGIC,1),LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,setC(LogiTechSlimefunItemStacks.NOLOGIC,1),"REDSTONE_BLOCK",
                    null,"REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK","REDSTONE_BLOCK",null),1,10000,250,
            randItemStackFactory(
                    mkMp(
                            randItemStackFactory(mkMp("PISTON",1,"STICKY_PISTON",1,"OBSERVER",1,"HOPPER",1,"CHISELED_BOOKSHELF",1,"LIGHT_WEIGHTED_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("REDSTONE_TORCH",1,"REPEATER",1,"COMPARATOR",1,"LEVER",1,"OAK_PRESSURE_PLATE",1,"STONE_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("NOTE_BLOCK",1, "REDSTONE_LAMP",1, "TARGET",1, "REDSTONE_BLOCK",1, "TRIPWIRE_HOOK",1,"JUKEBOX",1 )),1,
                            randItemStackFactory(mkMp("DISPENSER",1,"DROPPER",1, "DAYLIGHT_DETECTOR",1, "LECTERN",1, "LIGHTNING_ROD",1,"HEAVY_WEIGHTED_PRESSURE_PLATE",1)),1,
                            randItemStackFactory(mkMp("SLIME_BALL",1,"HONEY_BLOCK",1,"TNT",1,"REDSTONE",1,"OAK_BUTTON",1,"STONE_BUTTON",1,"BELL",1,"TRAPPED_CHEST",1,Material.COMPOSTER,1)),1)
            ))
            .register();
    public static final SlimefunItem TNT_MG=new SMGenerator(VANILLA, LogiTechSlimefunItemStacks.TNT_MG,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.LOGIC_GATE,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"STICKY_PISTON",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,
                    LogiTechSlimefunItemStacks.LENGINE,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.LENGINE),4,3600,233,
            "TNT")
            .register();
    public static final SlimefunItem DUPE_MG=new MMGenerator(VANILLA, LogiTechSlimefunItemStacks.DUPE_MG,COMMON_TYPE,
            recipe("TRIPWIRE_HOOK",LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",null,null,null,null,"TRIPWIRE_HOOK",
                    "TRIPWIRE_HOOK",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,null,null,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"TRIPWIRE_HOOK",
                    "WHITE_CARPET",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,null,null,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"WHITE_CARPET",
                    "WHITE_CARPET","STICKY_PISTON","OBSERVER","OBSERVER","STICKY_PISTON","WHITE_CARPET",
                    null,"WHITE_CARPET","WHITE_CARPET","WHITE_CARPET","WHITE_CARPET",null),1,1000,116,
            new PairList<>(){{
                put(mkl("STRING"),mkl("16STRING"));
                put(mkl("RAIL"),mkl("16RAIL"));
                put(mkl("POWERED_RAIL"),mkl("16POWERED_RAIL"));
                put(mkl("DETECTOR_RAIL"),mkl("16DETECTOR_RAIL"));
                put(mkl("ACTIVATOR_RAIL"),mkl("16ACTIVATOR_RAIL"));
                put(mkl(Material.BLACK_CARPET),mkl("32BLACK_CARPET"));
                put(mkl(Material.RED_CARPET),mkl("32RED_CARPET"));
                put(mkl(Material.ORANGE_CARPET),mkl("32ORANGE_CARPET"));
                put(mkl(Material.YELLOW_CARPET),mkl("32YELLOW_CARPET"));
                put(mkl(Material.LIME_CARPET),mkl("32LIME_CARPET"));
                put(mkl(Material.WHITE_CARPET),mkl("32WHITE_CARPET"));
                put(mkl(Material.CYAN_CARPET),mkl("32CYAN_CARPET"));
                put(mkl(Material.BLUE_CARPET),mkl("32BLUE_CARPET"));
                put(mkl(Material.GRAY_CARPET),mkl("32GRAY_CARPET"));
                put(mkl(Material.BROWN_CARPET),mkl("32BROWN_CARPET"));
                put(mkl(Material.GREEN_CARPET),mkl("32GREEN_CARPET"));
                put(mkl(Material.LIGHT_BLUE_CARPET),mkl("32LIGHT_BLUE_CARPET"));
                put(mkl(Material.MAGENTA_CARPET),mkl("32MAGENTA_CARPET"));
                put(mkl(Material.PINK_CARPET),mkl("32PINK_CARPET"));
                put(mkl(Material.PURPLE_CARPET),mkl("32PURPLE_CARPET"));
                put(mkl(Material.GRAY_CARPET),mkl("32GRAY_CARPET"));
                put(mkl(Material.LIGHT_GRAY_CARPET),mkl("32LIGHT_GRAY_CARPET"));
                put(mkl(Material.MOSS_CARPET),mkl("32MOSS_CARPET"));

            }})
            .register();
    public static final SlimefunItem ENDDUPE_MG=new MMGenerator(VANILLA, LogiTechSlimefunItemStacks.ENDDUPE_MG,COMMON_TYPE,
            recipe(null,"SAND","ANVIL","ANVIL","SAND",null,
                    null,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,null,
                    "OBSERVER",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"END_PORTAL_FRAME","END_PORTAL_FRAME",LogiTechSlimefunItemStacks.REDSTONE_ENGINE,"OBSERVER",
                    "OBSERVER",LogiTechSlimefunItemStacks.DUPE_MG,"END_PORTAL_FRAME","END_PORTAL_FRAME",LogiTechSlimefunItemStacks.DUPE_MG,"OBSERVER",
                    "END_STONE",LogiTechSlimefunItemStacks.LFIELD,setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,2),setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,2),LogiTechSlimefunItemStacks.LFIELD,"END_STONE",
                    null,"END_STONE",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,"END_STONE",null),3,1000,116,
            new PairList<>(){{
                put(mkl("SAND"),mkl("8SAND"));
                put(mkl("RED_SAND"),mkl("8RED_SAND"));
                put(mkl("ANVIL"),mkl("ANVIL"));
                put(mkl("CHIPPED_ANVIL"),mkl("CHIPPED_ANVIL"));
                put(mkl("DAMAGED_ANVIL"),mkl("DAMAGED_ANVIL"));
                put(mkl("GRAVEL"),mkl("8GRAVEL"));
                put(mkl("SNOW"),mkl("4SNOW"));
                put(mkl("POINTED_DRIPSTONE"),mkl("4POINTED_DRIPSTONE"));
                put(mkl(Material.BLUE_CONCRETE_POWDER),mkl("32BLUE_CONCRETE_POWDER"));
                put(mkl(Material.RED_CONCRETE_POWDER),mkl("32RED_CONCRETE_POWDER"));
                put(mkl(Material.YELLOW_CONCRETE_POWDER),mkl("32YELLOW_CONCRETE_POWDER"));
                put(mkl(Material.WHITE_CONCRETE_POWDER),mkl("32WHITE_CONCRETE_POWDER"));
                put(mkl(Material.BROWN_CONCRETE_POWDER),mkl("32BROWN_CONCRETE_POWDER"));
                put(mkl(Material.LIME_CONCRETE_POWDER),mkl("32LIME_CONCRETE_POWDER"));
                put(mkl(Material.GREEN_CONCRETE_POWDER),mkl("32GREEN_CONCRETE_POWDER"));
                put(mkl(Material.LIGHT_BLUE_CONCRETE_POWDER),mkl("32LIGHT_BLUE_CONCRETE_POWDER"));
                put(mkl(Material.MAGENTA_CONCRETE_POWDER),mkl("32MAGENTA_CONCRETE_POWDER"));
                put(mkl(Material.GRAY_CONCRETE_POWDER),mkl("32GRAY_CONCRETE_POWDER"));
                put(mkl(Material.BLACK_CONCRETE_POWDER),mkl("32BLACK_CONCRETE_POWDER"));
                put(mkl(Material.PURPLE_CONCRETE_POWDER),mkl("32PURPLE_CONCRETE_POWDER"));
                put(mkl(Material.ORANGE_CONCRETE_POWDER),mkl("32ORANGE_CONCRETE_POWDER"));
                put(mkl(Material.LIGHT_GRAY_CONCRETE_POWDER),mkl("32LIGHT_GRAY_CONCRETE_POWDER"));
                put(mkl(Material.PINK_CONCRETE_POWDER),mkl("32PINK_CONCRETE_POWDER"));
                put(mkl(Material.CYAN_CONCRETE_POWDER),mkl("32CYAN_CONCRETE_POWDER"));


            }})
            .register();
    public static final SlimefunItem BNOISE_MAKER = new BNoiseMaker(VANILLA, LogiTechSlimefunItemStacks.BNOISE_MAKER, ENHANCED_CRAFTING_TABLE,
            recipe(
                    "NOTE_BLOCK",  LogiTechSlimefunItemStacks.BUG, LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,  LogiTechSlimefunItemStacks.BUG, "NOTE_BLOCK",
                    LogiTechSlimefunItemStacks.BUG, "NOTE_BLOCK",  LogiTechSlimefunItemStacks.BUG
            )).register();
    public static final SlimefunItem BNOISE_HEAD = new BNoiseHead(VANILLA, LogiTechSlimefunItemStacks.BNOISE_HEAD, RecipeType.NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.BNOISE_MAKER,Language.get("generator.BNOISE_MAKER.name"))
            ).register();
    public static final SlimefunItem REVERSE_GENERATOR = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.REVERSE_GENERATOR, COMMON_TYPE,
            recipe(null,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.SPACE_PLATE,null,
                    null,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.LOGIC_REACTOR,LogiTechSlimefunItemStacks.LOGIC_REACTOR,LogiTechSlimefunItemStacks.SPACE_PLATE,null,
                    null,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.PAGOLD,null,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.PAGOLD,
                    null,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.PAGOLD,null), 1, 1_000_000, 24_000,
            new PairList<>(){{
                put(mkl(LogiTechSlimefunItemStacks.LOGIC_GATE),mkl(setC(LogiTechSlimefunItemStacks.LOGIC_GATE,16)));
                put(mkl(LogiTechSlimefunItemStacks.LOGIC),mkl(setC(LogiTechSlimefunItemStacks.NOLOGIC,32)));
                put(mkl(LogiTechSlimefunItemStacks.NOLOGIC),mkl(setC(LogiTechSlimefunItemStacks.LOGIC,32)));
                put(mkl(LogiTechSlimefunItemStacks.EXISTE),mkl(setC(LogiTechSlimefunItemStacks.UNIQUE,32)));
                put(mkl(LogiTechSlimefunItemStacks.UNIQUE),mkl(setC(LogiTechSlimefunItemStacks.EXISTE,32)));
                put(mkl(LogiTechSlimefunItemStacks.BUG),mkl(setC(LogiTechSlimefunItemStacks.BUG,8)));
                put(mkl("IRON_INGOT"),mkl(setC(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,16)));
                put(mkl(LogiTechSlimefunItemStacks.ABSTRACT_INGOT),mkl("1024IRON_INGOT"));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_MINER = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.VIRTUAL_MINER, COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.TECH_CORE,1),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.BISILVER,null,null,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LDIGITIZER,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.DIMENSION_MINER,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.DIMENSION_MINER,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.BISILVER,null,null,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LIOPORT,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.MASS_CORE,1),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.METAL_CORE,1),LogiTechSlimefunItemStacks.BISILVER), 1, 129_600, 5400,
            new PairList<>(){{
                put(mkl(LogiTechSlimefunItemStacks.WORLD_FEAT),mkl(randItemStackFactory(
                        mkMp("128COAL",8,
                                "128REDSTONE",8,
                                "128IRON_INGOT",8,
                                "256LAPIS_LAZULI",8,
                                "128DIAMOND",8,
                                "128EMERALD",8)
                )));
                put(mkl(LogiTechSlimefunItemStacks.NETHER_FEAT),mkl(randItemStackFactory(
                        mkMp("48NETHERITE_INGOT",1,
                                "192QUARTZ",1,
                                "64MAGMA_BLOCK",1,
                                "64OBSIDIAN",1,
                                "32ANCIENT_DEBRIS",1,
                                "6NETHER_ICE",1)
                )));
                put(mkl(LogiTechSlimefunItemStacks.END_FEAT),mkl(randItemStackFactory(
                        mkMp("8DRAGON_BREATH",2,
                       // "4CHORUS_FLOWER",2,
                                "16ENDER_EYE",2,
                                "16ENDER_PEARL",2,
                                "6BUCKET_OF_OIL",2,
                                setC(LogiTechSlimefunItemStacks.STAR_GOLD,16),2,
                                setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,32),2
                        )
                )));
            }})
            .register();
    public static final SlimefunItem VIRTUAL_PLANT = new MMGenerator(GENERATORS, LogiTechSlimefunItemStacks.VIRTUAL_PLANT, COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.TECH_CORE,1),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,1),LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.LDIGITIZER,LogiTechSlimefunItemStacks.BISILVER,null,null,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LDIGITIZER,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.LMOTOR,1),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),LogiTechSlimefunItemStacks.END_PLANT,setC(LogiTechSlimefunItemStacks.LMOTOR,1),LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.LENGINE,1),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,1),setC(LogiTechSlimefunItemStacks.CHIP_CORE,1),setC(LogiTechSlimefunItemStacks.LENGINE,1),LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.BISILVER,null,null,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LIOPORT,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.MASS_CORE,1),LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.METAL_CORE,1),LogiTechSlimefunItemStacks.BISILVER), 1, 129_600, 5400,
            new PairList<>(){{
                put(mkl("COCOA_BEANS"),mkl("9COCOA_BEANS"));
                put(mkl("MELON_SEEDS"),mkl("3MELON","3MELON_SEEDS"));
                put(mkl("PUMPKIN_SEEDS"),mkl("3PUMPKIN","3PUMPKIN_SEEDS"));
                put(mkl("BEETROOT_SEEDS"),mkl("9BEETROOT","6BEETROOT_SEEDS"));
                put(mkl("WHEAT_SEEDS"),mkl("9WHEAT","6WHEAT_SEEDS"));
                put(mkl("BROWN_MUSHROOM"),mkl("9BROWN_MUSHROOM"));
                put(mkl("RED_MUSHROOM"),mkl("9RED_MUSHROOM"));
                put(mkl("DEAD_BUSH"),mkl("9DEAD_BUSH","6STICK"));
                put(mkl("CARROT"),mkl("9CARROT"));
                put(mkl("POTATO"),mkl("9POTATO"));
                put(mkl("DEAD_BUSH"),mkl("9DEAD_BUSH","6STICK"));
                put(mkl("SWEET_BERRIES"),mkl("9SWEET_BERRIES"));
                put(mkl("GLOW_BERRIES"),mkl("9GLOW_BERRIES"));
                put(mkl("SUGAR_CANE"),mkl("9SUGAR_CANE"));
                put(mkl("CACTUS"),mkl("9CACTUS"));
                put(mkl("DANDELION"),mkl("9DANDELION"));
                put(mkl("POPPY"),mkl("3POPPY"));
                put(mkl("BLUE_ORCHID"),mkl("9BLUE_ORCHID"));
                put(mkl("ALLIUM"),mkl("9ALLIUM"));
                put(mkl("AZURE_BLUET"),mkl("9AZURE_BLUET"));
                put(mkl("RED_TULIP"),mkl("9RED_TULIP"));
                put(mkl("ORANGE_TULIP"),mkl("9ORANGE_TULIP"));
                put(mkl("WHITE_TULIP"),mkl("9WHITE_TULIP"));
                put(mkl("PINK_TULIP"),mkl("9PINK_TULIP"));
                put(mkl("OXEYE_DAISY"),mkl("9OXEYE_DAISY"));
                put(mkl("CORNFLOWER"),mkl("9CORNFLOWER"));
                put(mkl("LILY_OF_THE_VALLEY"),mkl("9LILY_OF_THE_VALLEY"));
                put(mkl("WITHER_ROSE"),mkl("6WITHER_ROSE"));
                put(mkl("SUNFLOWER"),mkl("6SUNFLOWER"));
                put(mkl("LILAC"),mkl("6LILAC"));
                put(mkl("ROSE_BUSH"),mkl("6ROSE_BUSH"));
                put(mkl("PEONY"),mkl("6PEONY"));
                put(mkl("OAK_SAPLING"),mkl("18OAK_LOG","6APPLE","9OAK_LEAVES","9OAK_SAPLING","6STICK"));
                put(mkl("BIRCH_SAPLING"),mkl("18BIRCH_LOG","6APPLE","9BIRCH_LEAVES","9BIRCH_SAPLING","6STICK"));
                put(mkl("SPRUCE_SAPLING"),mkl("18SPRUCE_LOG","6APPLE","9SPRUCE_LEAVES","9SPRUCE_SAPLING","6STICK"));
                put(mkl("DARK_OAK_SAPLING"),mkl("18DARK_OAK_LOG","6APPLE","9DARK_OAK_LEAVES","9DARK_OAK_SAPLING","6STICK"));
                put(mkl("JUNGLE_SAPLING"),mkl("18JUNGLE_LOG","6APPLE","9JUNGLE_LEAVES","9JUNGLE_SAPLING","6STICK"));
                put(mkl("ACACIA_SAPLING"),mkl("18ACACIA_LOG","6APPLE","9ACACIA_LEAVES","9ACACIA_SAPLING","6STICK"));
                put(mkl("MANGROVE_PROPAGULE"),mkl("18MANGROVE_LOG","9MANGROVE_LEAVES","9MANGROVE_PROPAGULE"));
                put(mkl(Material.CHERRY_SAPLING),mkl("18CHERRY_LOG","9CHERRY_LEAVES",new ItemStack(Material.CHERRY_SAPLING,9)));
                put(mkl("NETHER_WART"),mkl("12NETHER_WART"));
                put(mkl("NETHER_SPROUTS"),mkl("12NETHER_SPROUTS"));
                put(mkl("CRIMSON_FUNGUS"),mkl("6CRIMSON_FUNGUS","18CRIMSON_STEM","6SHROOMLIGHT","12NETHER_WART_BLOCK"));
                put(mkl("WARPED_FUNGUS"),mkl("6WARPED_FUNGUS","18WARPED_STEM","6SHROOMLIGHT","12WARPED_WART_BLOCK"));
                put(mkl("CHORUS_FLOWER"),mkl("6CHORUS_FLOWER","24CHORUS_FRUIT"));
                put(mkl("LILY_PAD"),mkl("9LILY_PAD"));
                put(mkl("KELP"),mkl("9KELP"));
            }})
            .register();
    public static final SlimefunItem STACKMGENERATOR=new StackMGenerator(GENERATORS, LogiTechSlimefunItemStacks.STACKMGENERATOR,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),1,40_000_000,2_000,2)
            .register();


    //TODO register recips
    //TODO 平衡性调整
    //TODO 虚拟世界 等生成器 有了
    //发电机 123
    //更多的智能货运
    //垃圾提 ok
    //终极路线
    //高级芯片之类的? 高级芯片机 ok
    //补充说明 堆叠机器 ok
    //正反发电机 ok
    //方舟反应堆 ok
    //百度
    //吃掉实体
    //红石控制的货运
    //芯片控制的货运
    //红石tnt生成器
    //终极机器 物质重构器
    //终极机器 逆机器
    //,物质重构机，小型多方块结构 射线 出逻辑核心
    //最终发展路线 。快捷->圆石生成器->逻辑核心->概念同化体->终极堆叠,逆机器

    //multiblock
    public static final SlimefunItem PORTAL_CORE=new PortalCore(SPACE,LogiTechSlimefunItemStacks.PORTAL_CORE,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD,"GPS_TRANSMITTER","GPS_TRANSMITTER",LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,"GPS_TRANSMITTER",LogiTechSlimefunItemStacks.HYPER_LINK,LogiTechSlimefunItemStacks.HYPER_LINK,"GPS_TRANSMITTER",LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,"GPS_TRANSMITTER",LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,"GPS_TRANSMITTER",LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.STAR_GOLD,"GPS_TRANSMITTER","GPS_TRANSMITTER",LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD),"portal.core",
            MultiBlockTypes.PORTAL_TYPE)
            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.PORTAL_CORE,2));
    public static final SlimefunItem PORTAL_FRAME=new MultiPart(SPACE,LogiTechSlimefunItemStacks.PORTAL_FRAME,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"portal.part")
            .register();
    public static final SlimefunItem SOLAR_REACTOR=new SolarReactorCore(SPACE,LogiTechSlimefunItemStacks.SOLAR_REACTOR,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.LMOTOR,LogiTechSlimefunItemStacks.MASS_CORE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,"GPS_TRANSMITTER_4",LogiTechSlimefunItemStacks.SMELERY_CORE,LogiTechSlimefunItemStacks.METAL_CORE,"GPS_TRANSMITTER_4",LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,"GPS_TRANSMITTER_4",LogiTechSlimefunItemStacks.MASS_CORE,LogiTechSlimefunItemStacks.TECH_CORE,"GPS_TRANSMITTER_4",LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.METAL_CORE,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.SMELERY_CORE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LPLATE)
            ,"solar.core",MultiBlockTypes.SOLAR_TYPE,80_000,2_000_000,
            mkMp(mkP(   mkl(LogiTechSlimefunItemStacks.METAL_CORE)  ,
                            mkl(
                                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,18),
                                    probItemStackFactory( LogiTechSlimefunItemStacks.LSINGULARITY,73),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.STAR_GOLD,39,87),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ATOM_INGOT,63,99),
                                    randItemStackFactory(
                                            Utils.list(setC(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,4),setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.BISMUTH_INGOT,4)),
                                            Utils.list(37,29,13,21)
                                    )
                            )
                    ),75,
                    mkP(   mkl(LogiTechSlimefunItemStacks.SMELERY_CORE)  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,64),
                                    probItemStackFactory( LogiTechSlimefunItemStacks.LSINGULARITY,12),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,99,127),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.STAR_GOLD,49,83),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ATOM_INGOT,37,51),
                                    randItemStackFactory(
                                            Utils.list(setC(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,4),setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.BISMUTH_INGOT,4)),
                                            Utils.list(2,59,13,24)
                                    )
                            )
                    ),75,
                    mkP(   mkl(LogiTechSlimefunItemStacks.MASS_CORE)  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.BUG,32),
                                    probItemStackFactory( LogiTechSlimefunItemStacks.LSINGULARITY,97),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,73,127),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.STAR_GOLD,39,63),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ATOM_INGOT,12,39),

                                    randItemStackFactory(
                                            Utils.list(setC(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,4),setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,4),setC(LogiTechSlimefunItemStacks.BISMUTH_INGOT,4)),
                                            Utils.list(27,22,15,45)
                                    )
                            )

                    ),75,
                    mkP(   mkl(LogiTechSlimefunItemStacks.TECH_CORE)  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.LPLATE,64),
                                    setC( LogiTechSlimefunItemStacks.LSINGULARITY,2),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,23,40),
                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ATOM_INGOT,92,127)
                            )

                    ),75
            )).setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建超新星外壳方可运行",
                                    "&7搭建成功后需要保证外壳内部(除机器顶部)不包含任何非空气方块",
                                    "&7才可以成功启动多方块机器",
                                    "&7在此你可以使用[%s]等远程访问工具打开内部的界面".formatted(Language.get("item.HYPER_LINK.name")),
                                    "&7或者开启自动构建模式让机器自行启动"),null,
                            getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:为了防止意外发生,当重启/使用不安全的远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            getInfoShow("&f机制",
                                    "&7机器自动关闭且&e进程未结束时&c会发生爆炸",
                                    "&7机器爆炸会在四周生成爆炸强度为80的爆炸,并伴有多方块结构1/3损坏",
                                    "&7机器会在以下条件自动关闭",
                                    "&7- 电力不足",
                                    "&7- 人为的破坏多方块框架(挖掘,或者数据清除)",
                                    "&7- 手动关机而多方块不处于\"自动构建\"模式",
                                    "&7- 超新星特效丢失或者距离源位置超过1格(&a不会发起爆炸)",
                                    "&7服务器重启时多方块会安全关闭,并在重启后自动恢复"
                            ),null
                    )
            )
            .register();
    public static final SlimefunItem SOLAR_REACTOR_FRAME=new MultiPart(SPACE,LogiTechSlimefunItemStacks.SOLAR_REACTOR_FRAME,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"solar.frame")
            .register();
    public static final SlimefunItem SOLAR_REACTOR_GLASS=new MultiPart(SPACE,LogiTechSlimefunItemStacks.SOLAR_REACTOR_GLASS,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"solar.glass")
            .register();
    public static final SlimefunItem SOLAR_INPUT=new MultiIOPort(SPACE,LogiTechSlimefunItemStacks.SOLAR_INPUT,ANCIENT_ALTAR,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.HYPER_LINK,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT),"solar.frame",true,true)
            .register();
    public static final SlimefunItem SOLAR_OUTPUT=new MultiIOPort(SPACE,LogiTechSlimefunItemStacks.SOLAR_OUTPUT,ANCIENT_ALTAR,
            recipe(LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.HYPER_LINK,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX),"solar.frame",false,false)
            .register() ;
    public static final SlimefunItem TRANSMUTATOR_FRAME=new MultiPart(ADVANCED,LogiTechSlimefunItemStacks.TRANSMUTATOR_FRAME,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"nuclear.frame")
            .register();
    public static final SlimefunItem TRANSMUTATOR_GLASS=new MultiPart(ADVANCED,LogiTechSlimefunItemStacks.TRANSMUTATOR_GLASS,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"nuclear.glass")
            .register();
    public static final SlimefunItem TRANSMUTATOR_ROD=new MultiPart(ADVANCED,LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),"nuclear.rod")
            .register();
    public static final  SlimefunItem TRANSMUTATOR=new Transmutator(ADVANCED, LogiTechSlimefunItemStacks.TRANSMUTATOR,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.TECH_CORE,64),LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,setC(LogiTechSlimefunItemStacks.MASS_CORE,64),
                    LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.LSINGULARITY,4),setC(LogiTechSlimefunItemStacks.LSINGULARITY,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,
                    LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(LogiTechSlimefunItemStacks.PAGOLD,4),LogiTechSlimefunItemStacks.SPACE_PLATE,
                    LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.PAGOLD,4),"16NUCLEAR_REACTOR","16NUCLEAR_REACTOR",setC(LogiTechSlimefunItemStacks.PAGOLD,4),LogiTechSlimefunItemStacks.SPACE_PLATE,
                    LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,
                    setC(LogiTechSlimefunItemStacks.METAL_CORE,64),LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,64)), "nuclear.core",
            MultiBlockTypes.NUCLEAR_REACTOR,750_000,20_000_000,
            mkMp(mkP(   mkl(setC(LogiTechSlimefunItemStacks.ATOM_INGOT,64) )  ,
                        mkl(
                                setC(LogiTechSlimefunItemStacks.ATOM_INGOT,128),
                                randItemStackFactory(
                                        Utils.list(
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,37,53),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.PLATINUM_INGOT,27,50),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.CADMIUM_INGOT,34,64),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.BISMUTH_INGOT,40,54)
                                        ),
                                        Utils.list(1,1,1,1)
                                ),
                                randItemStackFactory(
                                        Utils.list(
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.CERIUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.MENDELEVIUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.DYSPROSIUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.ANTIMONY_INGOT,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.THALLIUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.HYDRAGYRUM,1,4),
                                                randAmountItemFactory(LogiTechSlimefunItemStacks.BORON,1,4)
                                        ),
                                        Utils.list(1,1,1,1,1,1,1,1,1)
                                )
                        )

                    ),8000,
                    mkP(   mkl(setC(LogiTechSlimefunItemStacks.BISILVER,3) )  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,48),
                                    setC(LogiTechSlimefunItemStacks.PARADOX,128),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,15,61),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PLATINUM_INGOT,13,49),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CADMIUM_INGOT,23,57),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BISMUTH_INGOT,12,64)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,5,23),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CERIUM,15,27),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MENDELEVIUM,7,17),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DYSPROSIUM,3,24),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,10,14),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ANTIMONY_INGOT,6,11),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.THALLIUM,9,10),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.HYDRAGYRUM,2,8),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BORON,1,9)
                                            ),
                                            Utils.list(15,14,13,12,11,10,9,8,7)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(LogiTechSlimefunItemStacks.PAGOLD,3) )  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.ATOM_INGOT,48),
                                    setC(LogiTechSlimefunItemStacks.PARADOX,128),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,17,43),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PLATINUM_INGOT,11,36),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CADMIUM_INGOT,15,53),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BISMUTH_INGOT,23,49)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,1,9),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CERIUM,3,31),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MENDELEVIUM,7,12),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DYSPROSIUM,12,15),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,23,26),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ANTIMONY_INGOT,1,3),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.THALLIUM,13,19),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.HYDRAGYRUM,11,23),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BORON,6,9)
                                            ),
                                            Utils.list(12,11,10,9,8,7,15,14,13)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(LogiTechSlimefunItemStacks.PLATINUM_INGOT,18) )  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,128),
                                    setC(LogiTechSlimefunItemStacks.PARADOX,128),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,23,53),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PLATINUM_INGOT,32,40),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CADMIUM_INGOT,23,35),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BISMUTH_INGOT,13,58)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,4,22),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CERIUM,6,19),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MENDELEVIUM,8,11),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DYSPROSIUM,1,20),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,2,17),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ANTIMONY_INGOT,10,14),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.THALLIUM,11,24),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.HYDRAGYRUM,13,19),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BORON,7,17)
                                            ),
                                            Utils.list(9,8,7,15,14,13,12,11,10)
                                    )
                            )

                    ),10000,
                    mkP(   mkl(setC(LogiTechSlimefunItemStacks.CADMIUM_INGOT,18) )  ,

                            mkl(
                                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,64),
                                    setC(LogiTechSlimefunItemStacks.PARADOX,128),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PALLADIUM_INGOT,17,43),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.PLATINUM_INGOT,13,50),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CADMIUM_INGOT,17,55),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BISMUTH_INGOT,20,34)
                                            ),
                                            Utils.list(1,1,1,1)
                                    ),
                                    randItemStackFactory(
                                            Utils.list(
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,6,21),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.CERIUM,9,21),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MENDELEVIUM,7,13),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.DYSPROSIUM,7,31),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.MOLYBDENUM,12,14),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.ANTIMONY_INGOT,10,24),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.THALLIUM,7,22),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.HYDRAGYRUM,14,29),
                                                    randAmountItemFactory(LogiTechSlimefunItemStacks.BORON,11,23)
                                            ),
                                            Utils.list(1,1,1,1,1,1,1,1,1)
                                    )
                            )

                    ),10000
            )
            ).setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建元素嬗变机结构方可运行",
                                    "&7该多方块为可变高度多方块机器",
                                    "&7点击开启投影只会显示5层机器",
                                    "&7在顶层之下重复搭建投影的第4层即可增高多方块机器",
                                    "&7最多可重复搭建10层(投影中的第四层计入数量)",
                                    "&a每多搭建一层该机器并行处理数x2(即机器快一倍)"),null,
                            getInfoShow("&f机制",
                                    "&7该机器在构建/待机/运行时候拥有相同的电力消耗量",
                                    "&7当电力不足时会强制关机",
                                    "&7注意:为了防止意外发生,当重启/使用不安全的远程传输时,请确认机器所在区块的加载状态",
                                    "&7并确保能源网络在加载范围内并且能给机器提供足够电力"),null,
                            getInfoShow("&f机制",
                                    "&7机器自动关闭且&e进程未结束时&c会发生&c熔毁",
                                    "&7熔毁会形成范围40的1级辐射圈持续约1小时",
                                    "&7并伴有所有\"原子合金燃料棒\"损坏",
                                    "&7机器会在以下条件自动关闭",
                                    "&7- 电力不足",
                                    "&7- 人为的破坏多方块框架(挖掘,或者数据清除)",
                                    "&7- 手动关机而多方块不处于\"自动构建\"模式",
                                    "&7服务器重启时多方块会安全关闭,并在重启后自动恢复"
                            ),null
                    )
            )
            .register();
    public static final  SlimefunItem TIMER_RD=new TimerRandomtick(ADVANCED, LogiTechSlimefunItemStacks.TIMER_RD,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.LSINGULARITY,3),setC(LogiTechSlimefunItemStacks.LSINGULARITY,3),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),
                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),setC(LogiTechSlimefunItemStacks.LSCHEDULER,2),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.LSCHEDULER,2),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),
                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),setC(LogiTechSlimefunItemStacks.WORLD_FEAT,3),"6TREE_GROWTH_ACCELERATOR","6TREE_GROWTH_ACCELERATOR",setC(LogiTechSlimefunItemStacks.WORLD_FEAT,3),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),
                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),setC(LogiTechSlimefunItemStacks.NETHER_FEAT,3),"3ANIMAL_GROWTH_ACCELERATOR","3ANIMAL_GROWTH_ACCELERATOR",setC(LogiTechSlimefunItemStacks.NETHER_FEAT,3),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),
                    setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),setC(LogiTechSlimefunItemStacks.END_FEAT,3),"6CROP_GROWTH_ACCELERATOR_2","6CROP_GROWTH_ACCELERATOR_2",setC(LogiTechSlimefunItemStacks.END_FEAT,3),setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),
                    setC(LogiTechSlimefunItemStacks.CHIP_CORE,4),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.LENGINE,2),setC(LogiTechSlimefunItemStacks.LENGINE,2),setC(LogiTechSlimefunItemStacks.BISILVER,4),setC(LogiTechSlimefunItemStacks.CHIP_CORE,4)), 0,0)
            .register();
    public static final  SlimefunItem TIMER_BLOCKENTITY=new TimerBlockEntity(ADVANCED, LogiTechSlimefunItemStacks.TIMER_BLOCKENTITY,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.LSINGULARITY,6),LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,setC(LogiTechSlimefunItemStacks.LSINGULARITY,6),
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,setC(LogiTechSlimefunItemStacks.MOLYBDENUM,32),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),setC(LogiTechSlimefunItemStacks.MOLYBDENUM,32),LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.LASER,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.LASER,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.LASER,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.LASER,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,setC(LogiTechSlimefunItemStacks.DYSPROSIUM,32),setC(LogiTechSlimefunItemStacks.LSCHEDULER,2),setC(LogiTechSlimefunItemStacks.LSCHEDULER,2),setC(LogiTechSlimefunItemStacks.DYSPROSIUM,32),LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,
                    setC(LogiTechSlimefunItemStacks.LSINGULARITY,6),LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,setC(LogiTechSlimefunItemStacks.LSINGULARITY,6)),0,0)
            .register();

    public static final  SlimefunItem TIMER_SF=new TimerSlimefun(FUNCTIONAL, LogiTechSlimefunItemStacks.TIMER_SF,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TMP1,Language.get("Tmp.TMP1.name")),0,0)
            .register();
//    public static final  SlimefunItem TIMER_SF_SEQ=new TimerSequentialSlimefun(ADVANCED, AddItem.TIMER_SF_SEQ,NULL,
//            formatInfoRecipe(AddItem.TMP1,Language.get("Tmp.TMP1.name")),0,0)
//            .register();

    //
    //manuals
    public static final SlimefunItem MANUAL_CORE=new MaterialItem(MANUAL,LogiTechSlimefunItemStacks.MANUAL_CORE,ENHANCED_CRAFTING_TABLE,
            recipe("SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL",
                    "SAND","COBBLESTONE","GRAVEL"),null)
            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.MANUAL_CORE,3));
    public static final SlimefunItem CRAFT_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.BUG,
                    null,"CRAFTING_TABLE",null,
                    null,null,null),0,0, BukkitUtils.VANILLA_CRAFTTABLE)
            .register();
    public static final SlimefunItem FURNACE_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.FURNACE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.MANUAL_CORE,"HEATING_COIL",LogiTechSlimefunItemStacks.MANUAL_CORE,
                    "HEATING_COIL","FURNACE","HEATING_COIL",
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG),0,0,BukkitUtils.VANILLA_FURNACE)
            .register();
    public static final SlimefunItem ENHANCED_CRAFT_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("COBBLESTONE","COBBLESTONE","COBBLESTONE",
                    "COBBLESTONE","CRAFTING_TABLE","COBBLESTONE",
                    "COBBLESTONE","REDSTONE","COBBLESTONE"),0,0,ENHANCED_CRAFTING_TABLE)
            .register();
    public static final SlimefunItem GRIND_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.GRIND_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,LogiTechSlimefunItemStacks.BUG,null,
                    null,"OAK_FENCE",null,
                    null,"DISPENSER",null),0,0,GRIND_STONE)
            .register();
    public static final SlimefunItem ARMOR_FORGE_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.ARMOR_FORGE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,LogiTechSlimefunItemStacks.BUG,null,
                    null,"ANVIL",null,
                    null,"DISPENSER",null),0,0,ARMOR_FORGE)
            .register();
    public static final SlimefunItem ORE_CRUSHER_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.ORE_CRUSHER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,LogiTechSlimefunItemStacks.BUG,null,
                    null,"OAK_FENCE",null,
                    "IRON_INGOT","DISPENSER","IRON_INGOT"),0,0,ORE_CRUSHER)
            .register();
    public static final SlimefunItem COMPRESSOR_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.COMPRESSOR_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,LogiTechSlimefunItemStacks.MANUAL_CORE,null,
                    null,"OAK_FENCE",null,
                    "PISTON","DISPENSER","PISTON"),0,0,COMPRESSOR)
            .register();
    public static final SlimefunItem PRESSURE_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.PRESSURE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("GLASS","CAULDRON","GLASS",
                    "PISTON",LogiTechSlimefunItemStacks.MANUAL_CORE,"PISTON",
                    "PISTON","DISPENSER","PISTON"),0,0,PRESSURE_CHAMBER)
            .register();
    public static final SlimefunItem MAGIC_WORKBENCH_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.MAGIC_WORKBENCH_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(
                    "COBBLESTONE",LogiTechSlimefunItemStacks.MANUAL_CORE,"COBBLESTONE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER",
                    "COBBLESTONE",LogiTechSlimefunItemStacks.MANUAL_CORE,"COBBLESTONE"),0,0,MAGIC_WORKBENCH)
            .register();
    public static final SlimefunItem ORE_WASHER_MANUAL=new RandOutManulCrafter(MANUAL,LogiTechSlimefunItemStacks.ORE_WASHER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,"DISPENSER",LogiTechSlimefunItemStacks.BUG,
            null,"OAK_FENCE",null,
                    LogiTechSlimefunItemStacks.BUG,"CAULDRON",LogiTechSlimefunItemStacks.BUG),0,0,ORE_WASHER)
            .register();
    public static final SlimefunItem GOLD_PAN_MANUAL=new RandOutManulCrafter(MANUAL,LogiTechSlimefunItemStacks.GOLD_PAN_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.BUG,
                   null,"OAK_TRAPDOOR",null,
                    LogiTechSlimefunItemStacks.BUG,"CAULDRON",LogiTechSlimefunItemStacks.BUG),0,0,GOLD_PAN)
            .register();
    public static final SlimefunItem ANCIENT_ALTAR_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_ALTAR","ANCIENT_PEDESTAL",
                    "ANCIENT_PEDESTAL","ANCIENT_PEDESTAL","ANCIENT_PEDESTAL"),0,0,ANCIENT_ALTAR)
            .register();
    public static final SlimefunItem SMELTERY_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.SMELTERY_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,"NETHER_BRICK_FENCE",null,
                    "NETHER_BRICKS","DISPENSER","NETHER_BRICKS",
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG),0,0,SMELTERY)
            .register();
    public static final SlimefunItem CRUCIBLE_MANUAL=new ManualMachine(MANUAL,LogiTechSlimefunItemStacks.CRUCIBLE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe("TERRACOTTA",LogiTechSlimefunItemStacks.MANUAL_CORE,"TERRACOTTA",
                    "TERRACOTTA",LogiTechSlimefunItemStacks.BUG,"TERRACOTTA",
                    "TERRACOTTA","FLINT_AND_STEEL","TERRACOTTA"),0,0,()->{
                        return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.ELECTRIFIED_CRUCIBLE.getItem(),new ArrayList<>());
                })
            .register();
    public static final SlimefunItem PULVERIZER_MANUAL=new ManualMachine(MANUAL,LogiTechSlimefunItemStacks.PULVERIZER_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,"ELECTRIC_ORE_GRINDER",null,
                    "LEAD_INGOT",LogiTechSlimefunItemStacks.BUG,"LEAD_INGOT",
                    "MEDIUM_CAPACITOR","HEATING_COIL","MEDIUM_CAPACITOR"),0,0,()->{
        //keep a question,if we get 铸锭机 recipe.
                        return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.ELECTRIC_INGOT_PULVERIZER.getItem(),new ArrayList<>());
            })
            .register();
    public static final SlimefunItem MULTICRAFTTABLE_MANUAL=new ManualCrafter(MANUAL,LogiTechSlimefunItemStacks.MULTICRAFTTABLE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.MANUAL_CORE,"OUTPUT_CHEST",
                    LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,"CRAFTING_TABLE",
                    "BOOKSHELF","CRAFTING_TABLE","DISPENSER"),0,0,ENHANCED_CRAFTING_TABLE,MAGIC_WORKBENCH,ANCIENT_ALTAR,SMELTERY)
            .register();
    public static final SlimefunItem TABLESAW_MANUAL=new ManualMachine(MANUAL,LogiTechSlimefunItemStacks.TABLESAW_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,
                    "SMOOTH_STONE_SLAB","STONECUTTER","SMOOTH_STONE_SLAB",
                    LogiTechSlimefunItemStacks.MANUAL_CORE,"IRON_BLOCK",LogiTechSlimefunItemStacks.MANUAL_CORE),0,0,()->{
                        return RecipeSupporter.MULTIBLOCK_RECIPES.get(SlimefunItems.TABLE_SAW.getItem());
                 })
            .register();
    public static final SlimefunItem COMPOSTER=new ManualMachine(MANUAL,LogiTechSlimefunItemStacks.COMPOSTER,ENHANCED_CRAFTING_TABLE,
            recipe("OAK_SLAB",LogiTechSlimefunItemStacks.MANUAL_CORE,"OAK_SLAB",
                    "OAK_SLAB",LogiTechSlimefunItemStacks.BUG,"OAK_SLAB",
                    "OAK_SLAB","CAULDRON","OAK_SLAB"),0,0,()->{
                return RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.COMPOSTER.getItem(),new ArrayList<>());
            })
            .register();
    public static final SlimefunItem MULTIMACHINE_MANUAL=new ManualMachine(MANUAL,LogiTechSlimefunItemStacks.MULTIMACHINE_MANUAL,COMMON_TYPE,
            recipe(null,null,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,null,null,
                    null,LogiTechSlimefunItemStacks.LFIELD,"HEATED_PRESSURE_CHAMBER","HEATED_PRESSURE_CHAMBER",LogiTechSlimefunItemStacks.LFIELD,null,
                    LogiTechSlimefunItemStacks.LFIELD,"FOOD_COMPOSTER",LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,"FOOD_COMPOSTER",LogiTechSlimefunItemStacks.LFIELD,
                    LogiTechSlimefunItemStacks.LFIELD,"FOOD_FABRICATOR",LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.NOLOGIC,"FOOD_FABRICATOR",LogiTechSlimefunItemStacks.LFIELD,
                    null,LogiTechSlimefunItemStacks.LFIELD,"HEATED_PRESSURE_CHAMBER","HEATED_PRESSURE_CHAMBER",LogiTechSlimefunItemStacks.LFIELD,null,
                    null,null,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,null,null),0,0,()->{
                List<MachineRecipe> recipelist=new ArrayList<>();
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.HEATED_PRESSURE_CHAMBER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.AUTO_DRIER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.REFINERY.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.FOOD_COMPOSTER.getItem(),new ArrayList<>()));
                recipelist.addAll(RecipeSupporter.MACHINE_RECIPELIST.getOrDefault(SlimefunItems.FOOD_FABRICATOR.getItem(),new ArrayList<>()));
                return recipelist;
            })
            .register();
    public static final SlimefunItem MULTIBLOCK_MANUAL=new MultiBlockManual(MANUAL,LogiTechSlimefunItemStacks.MULTIBLOCK_MANUAL,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.LFIELD,"OAK_FENCE","OAK_FENCE",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.LCRAFT,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.LFIELD,"DISPENSER","DISPENSER",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT),0,0,null)
            .register();
    public static final SlimefunItem FAKE_UI=new MaterialItem(MANUAL,LogiTechSlimefunItemStacks.FAKE_UI,ENHANCED_CRAFTING_TABLE,
            recipe("COBBLESTONE","GLASS_PANE","COBBLESTONE","COBBLESTONE","GLASS_PANE","COBBLESTONE",
                    "COBBLESTONE","GLASS_PANE","COBBLESTONE"),List.of(getInfoShow("&f机制 - &7伪装","&7该物品可以在快捷多方块结构模拟器中","&7填充多方块模拟部分的空白","&7多方块模拟检测会忽视该物品")))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.FAKE_UI,4));
    public static final ReplaceCard REPLACE_CARD=(ReplaceCard) (new ReplaceCard(MANUAL,LogiTechSlimefunItemStacks.REPLACE_CARD,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.CARD_MAKER,Language.get("manuals.CARD_MAKER.name")), ReplaceCard.ReplaceType.MATERIAL)
            .register());
    public static final ReplaceCard REPLACE_SF_CARD=(ReplaceCard) new ReplaceCard(MANUAL,LogiTechSlimefunItemStacks.REPLACE_SF_CARD,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.CARD_MAKER,Language.get("manuals.CARD_MAKER.name")), ReplaceCard.ReplaceType.SLIMEFUN)
            .register();
    public static final  SlimefunItem CARD_MAKER=new EWorkBench(MANUAL, LogiTechSlimefunItemStacks.CARD_MAKER,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.MANUAL_CORE,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.MANUAL_CORE,
                    LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.MANUAL_CORE,LogiTechSlimefunItemStacks.MANUAL_CORE),0,0,64,
            ()->{
                List<MachineRecipe> shapedRecipesVanilla=RecipeSupporter.PROVIDED_SHAPED_RECIPES.get(BukkitUtils.VANILLA_CRAFTTABLE);
                List<MachineRecipe> cardRecipe=new ArrayList<>();
                cardRecipe.addAll(RecipeSupporter.UNUSTACKABLE_ITEM_RECIPES);

                for(MachineRecipe rp:shapedRecipesVanilla){
                    if(rp.getOutput()[0].getType().getMaxStackSize()==1){
                        cardRecipe.add(MachineRecipeUtils.shapeFrom(-1,rp.getInput(),recipe(REPLACE_CARD.getReplaceCard(rp.getOutput()[0].getType()))));
                    }
                }
                HashSet<RecipeType> supportedType=new HashSet<>(){{
                    add(ENHANCED_CRAFTING_TABLE);
                    add(RecipeType.MAGIC_WORKBENCH);
                    add(RecipeType.ANCIENT_ALTAR);
                    add(RecipeType.SMELTERY);
                }};
                for(SlimefunItem item: Slimefun.getRegistry().getAllSlimefunItems()){
                    if(item.getRecipeOutput().getType().getMaxStackSize()==1&& supportedType.contains( item.getRecipeType())&&item.getRecipe().length<=9){
                        cardRecipe.add(MachineRecipeUtils.shapeFrom(-1,Arrays.copyOf(item.getRecipe(), 9),recipe(REPLACE_SF_CARD.getReplaceCard(item))));
                    }
                }
                return cardRecipe;
            }){
        private List<MachineRecipe> unreplacedRecipe=null;
        private List<MachineRecipe> getUnreplacedRecipe(){
            if(unreplacedRecipe==null){
                List<MachineRecipe> targetRecipe = new ArrayList<>();
                int size=machineRecipes.size();
                for (int i=0;i<size;++i) {
                    targetRecipe.add(MachineRecipeUtils.shapeFrom(machineRecipes.get(i).getTicks(),
                            machineRecipes.get(i).getInput(),
                            new ItemStack[]{CRAFT_PROVIDER.get(Settings.INPUT,machineRecipes.get(i).getOutput()[0],-1).getItem()} ));
                }
                unreplacedRecipe=targetRecipe;
            }
            return unreplacedRecipe;
        }
        {
            CRAFT_PROVIDER= FinalFeature.MANUAL_CARD_READER;
        }
        @Override
        public List<MachineRecipe> provideDisplayRecipe(){
            List<MachineRecipe> machineRecipes =  getUnreplacedRecipe();
            List<MachineRecipe> targetRecipe = new ArrayList<>();
            int size=machineRecipes.size();
            for (int i=0;i<size;++i) {
                targetRecipe.add(MachineRecipeUtils.stackFrom(machineRecipes.get(i).getTicks(),
                        Utils.array(getInfoShow("&f有序配方合成","&7请在配方显示界面或者机器界面查看")),
                        machineRecipes.get(i).getOutput() ));
            }
            return targetRecipe;
        }
        @Override
        public MenuFactory getRecipeMenu(Block b, BlockMenu inv){

            return MenuUtils.createMRecipeListDisplay(getItem(),getUnreplacedRecipe(),null,(MenuUtils.RecipeMenuConstructor)(itemstack, recipes, backhandler,optional,history)->{
                return MenuUtils.createMRecipeDisplay(itemstack,recipes,backhandler,optional).addOverrides(8,LAZY_ONECLICK).addHandler(8,((player, i, itemStack, clickAction) -> {
                    moveRecipe(player,inv,recipes, clickAction.isRightClicked());
                    return false;
                }));
            });
        }
    }
            .setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f配方说明",
                                    "&7你将会在这里的配方中找到所有可以在原版工作台中合成的物品的替代卡配方",
                                    "&7其配方读取于原版合成配方",
                                    "&a你还会找到一些由@haiman添加的其他不可堆叠物品的替代卡配方",
                                    "&7如各种桶,下界合金工具等等"),
                            FinalFeature.MANUAL_CARD_INFO

                    )
            )
            .register();
    public static final SlimefunItem ADV_MANUAL=new AdvancedManual(MANUAL,LogiTechSlimefunItemStacks.ADV_MANUAL,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.SMELTERY_MANUAL,"ALUMINUM_BRASS_INGOT",setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),"ALUMINUM_BRASS_INGOT",LogiTechSlimefunItemStacks.FURNACE_MANUAL,
                    LogiTechSlimefunItemStacks.SMELTERY_MANUAL,"ALUMINUM_BRASS_INGOT",setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),"ALUMINUM_BRASS_INGOT",LogiTechSlimefunItemStacks.FURNACE_MANUAL,
                    LogiTechSlimefunItemStacks.SMELTERY_MANUAL,"ALUMINUM_BRASS_INGOT",setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),"ALUMINUM_BRASS_INGOT",LogiTechSlimefunItemStacks.FURNACE_MANUAL,
                    LogiTechSlimefunItemStacks.SMELTERY_MANUAL,"ALUMINUM_BRASS_INGOT",setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),setC(LogiTechSlimefunItemStacks.MANUAL_CORE,6),"ALUMINUM_BRASS_INGOT",LogiTechSlimefunItemStacks.FURNACE_MANUAL,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,LogiTechSlimefunItemStacks.BUG))
            .register();
    public static final SlimefunItem PORTABLE_MANUAL=new PortableManual(MANUAL,LogiTechSlimefunItemStacks.PORTABLE_MANUAL,ENHANCED_CRAFTING_TABLE,
            recipe(null,"PORTABLE_CRAFTER",null,null,LogiTechSlimefunItemStacks.ADV_MANUAL,null,
                    null,null,null))
            .register();

    //cargo items
    public static final SlimefunItem CARGO_PART=new MaterialItem(CARGO,LogiTechSlimefunItemStacks.CARGO_PART,ENHANCED_CRAFTING_TABLE,
            recipe("CARGO_NODE","CARGO_NODE_OUTPUT","CARGO_NODE"
                    ,LogiTechSlimefunItemStacks.BOOLEAN_FALSE,null,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,
                    "CARGO_NODE","CARGO_NODE_INPUT","CARGO_NODE"),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.CARGO_PART,64));
    public static final SlimefunItem CARGO_CONFIG=new ConfigCard(CARGO,LogiTechSlimefunItemStacks.CARGO_CONFIG,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BOOLEAN_TRUE,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.BOOLEAN_TRUE,
                    LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.LOGIC,
                    LogiTechSlimefunItemStacks.BOOLEAN_FALSE,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.BOOLEAN_FALSE))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.CARGO_CONFIG,32));
    public static final SlimefunItem CARGO_CONFIGURATOR=new CargoConfigurator(CARGO,LogiTechSlimefunItemStacks.CARGO_CONFIGURATOR,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    "OAK_PLANKS","CARTOGRAPHY_TABLE","OAK_PLANKS"))
            .register();
    public static final SlimefunItem CONFIGURE=new ConfigCopier(CARGO,LogiTechSlimefunItemStacks.CONFIGURE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,ChipCardCode.CHIP_0,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.BUG,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,ChipCardCode.CHIP_1,LogiTechSlimefunItemStacks.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem ADV_TRASH=new TrashCan(CARGO,LogiTechSlimefunItemStacks.ADV_TRASH,ENHANCED_CRAFTING_TABLE,
            recipe("PORTABLE_DUSTBIN",LogiTechSlimefunItemStacks.LOGIC,"PORTABLE_DUSTBIN",LogiTechSlimefunItemStacks.LOGIC,"FLINT_AND_STEEL",LogiTechSlimefunItemStacks.LOGIC,
                    "PORTABLE_DUSTBIN",LogiTechSlimefunItemStacks.LOGIC,"PORTABLE_DUSTBIN"))
            .register();
    public static final SlimefunItem QUANTUM_TRASH=new QuantumTrashCan(CARGO,LogiTechSlimefunItemStacks.QUANTUM_TRASH,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ADV_TRASH,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ADV_TRASH,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.QUANTUM_LINK,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LENGINE))
            .register();
    public static final  SlimefunItem STORAGE=new Storage(CARGO, LogiTechSlimefunItemStacks.STORAGE,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CHEST",LogiTechSlimefunItemStacks.BUG,"CHEST",
                    null,null,null),Storage.COMMON_INPUT_SLOT,Storage.COMMON_OUTPUT_SLOT)
            .register();
    public static final SlimefunItem STORAGE_INPUT=new Storage(CARGO  ,LogiTechSlimefunItemStacks.STORAGE_INPUT,ENHANCED_CRAFTING_TABLE,
            recipe(null,"HOPPER",null,"CHEST",LogiTechSlimefunItemStacks.BUG,"CHEST",
                    null,null,null),Storage.COMMON_INPUT_SLOT,new int[0])
            .register();
    public static final SlimefunItem STORAGE_OUTPUT=new Storage(CARGO,LogiTechSlimefunItemStacks.STORAGE_OUTPUT,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CHEST",LogiTechSlimefunItemStacks.BUG,"CHEST",
                    null,"HOPPER",null),new int[0],Storage.COMMON_OUTPUT_SLOT)
            .register();
    public static final SlimefunItem CARGO_PIP=new CargoPipe(CARGO,LogiTechSlimefunItemStacks.CARGO_PIP,ENHANCED_CRAFTING_TABLE,
            recipe("SOLDER_INGOT","HOPPER","SOLDER_INGOT","SOLDER_INGOT","BRASS_INGOT","SOLDER_INGOT",
                    "SOLDER_INGOT","HOPPER","SOLDER_INGOT"))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.CARGO_PIP,2));
    public static final SlimefunItem SIMPLE_CARGO=new AdjacentCargo(CARGO,LogiTechSlimefunItemStacks.SIMPLE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"HOPPER",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    null,LogiTechSlimefunItemStacks.CARGO_PART ,null,
                    "IRON_INGOT","IRON_INGOT","IRON_INGOT"),
            list(getInfoShow("&f机制","")))
            .register();
    public static final SlimefunItem REMOTE_CARGO=new RemoteCargo(CARGO,LogiTechSlimefunItemStacks.REMOTE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.HYPER_LINK,LogiTechSlimefunItemStacks.ABSTRACT_INGOT
                    ,"CARGO_NODE",LogiTechSlimefunItemStacks.CARGO_PART,"CARGO_NODE",
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX),
            null)
            .register();
    public static final SlimefunItem LINE_CARGO=new LineCargo(CARGO,LogiTechSlimefunItemStacks.LINE_CARGO,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"HOPPER",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.NOLOGIC,
                    "IRON_INGOT","HOPPER","IRON_INGOT"),null)
            .register();
    public static final  SlimefunItem BISORTER=new BiSorter(CARGO, LogiTechSlimefunItemStacks.BISORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.PARADOX,null,
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),LogiTechSlimefunItemStacks.LBOOLIZER,"HOPPER","HOPPER",LogiTechSlimefunItemStacks.LBOOLIZER,setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.LSCHEDULER,"HOPPER",LogiTechSlimefunItemStacks.LBOOLIZER,setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.PARADOX,null))
            .register();
    public static final SlimefunItem QUARSORTER=new QuarSorter(CARGO,LogiTechSlimefunItemStacks.QUARSORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.PARADOX,null,
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),LogiTechSlimefunItemStacks.CARGO_PART,"2HOPPER","HOPPER",LogiTechSlimefunItemStacks.CARGO_PART,setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.BISORTER,"2HOPPER",LogiTechSlimefunItemStacks.CARGO_PART,setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.PARADOX,null
                    ))
            .register();
    public static final SlimefunItem OCTASORTER=new OctaSorter(CARGO,LogiTechSlimefunItemStacks.OCTASORTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,null,
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),"HOPPER",setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),LogiTechSlimefunItemStacks.LSCHEDULER,"HOPPER",setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    setC(LogiTechSlimefunItemStacks.PARADOX,1),"HOPPER",LogiTechSlimefunItemStacks.QUARSORTER,setC(LogiTechSlimefunItemStacks.LBOOLIZER,1),"HOPPER",setC(LogiTechSlimefunItemStacks.PARADOX,1),
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,null))
            .register();
    public static final  SlimefunItem BIFILTER=new BiFilter(CARGO, LogiTechSlimefunItemStacks.BIFILTER,COMMON_TYPE   ,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,"CHEST","CHEST",LogiTechSlimefunItemStacks.PARADOX,null,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LOGIC_GATE,"HOPPER","HOPPER",LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.PARADOX,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ADV_TRASH,LogiTechSlimefunItemStacks.ADV_TRASH,LogiTechSlimefunItemStacks.PARADOX,null))
            .register();
    public static final SlimefunItem QUARFILTER=new QuarFilter(CARGO,LogiTechSlimefunItemStacks.QUARFILTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.PARADOX,null,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.LOGIC_GATE,"HOPPER",LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.BIFILTER,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.LFIELD,LogiTechSlimefunItemStacks.PARADOX,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.CARGO_PART,LogiTechSlimefunItemStacks.PARADOX,null))
            .register();
    public static final SlimefunItem OCTAFILTER=new OctaFilter(CARGO,LogiTechSlimefunItemStacks.OCTAFILTER,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,null,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LBOOLIZER,"HOPPER","HOPPER",LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.ADV_TRASH,LogiTechSlimefunItemStacks.QUARFILTER,LogiTechSlimefunItemStacks.LBOOLIZER,LogiTechSlimefunItemStacks.PARADOX,
                    null,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.PARADOX,null))
            .register();
    public static final SlimefunItem STORAGE_OPERATOR=new StorageOperator(SINGULARITY,LogiTechSlimefunItemStacks.STORAGE_OPERATOR,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem ADV_ADJACENT_CARGO=new AdjacentCargoPlus(CARGO,LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.SIMPLE_CARGO,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_REMOTE_CARGO=new RemoteCargoPlus(CARGO,LogiTechSlimefunItemStacks.ADV_REMOTE_CARGO,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.REMOTE_CARGO,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX),null)
            .register();
    public static final SlimefunItem ADV_LINE_CARGO=new LineCargoPlus(CARGO,LogiTechSlimefunItemStacks.ADV_LINE_CARGO,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LINE_CARGO,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    LogiTechSlimefunItemStacks.PARADOX,"CARGO_MOTOR",LogiTechSlimefunItemStacks.PARADOX),null)
            .register();
    public static final  SlimefunItem REDSTONE_ADJACENT_CARGO=new RedstoneAdjacentCargo(CARGO, LogiTechSlimefunItemStacks.REDSTONE_ADJACENT_CARGO,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.REDSTONE_ENGINE,LogiTechSlimefunItemStacks.LOGIC_GATE,
                    LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO), null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.REDSTONE_ADJACENT_CARGO,4));
    public static final SlimefunItem CHIP_ADJ_CARGO=new ChipAdjacentCargo(CARGO,LogiTechSlimefunItemStacks.CHIP_ADJ_CARGO,MAGIC_WORKBENCH,
            recipe(LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.CHIP_CORE,LogiTechSlimefunItemStacks.LOGIC_GATE,
                    LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO,LogiTechSlimefunItemStacks.LOGIC_GATE,LogiTechSlimefunItemStacks.ADV_ADJACENT_CARGO),null)
            .register().setOutput(setC(LogiTechSlimefunItemStacks.CHIP_ADJ_CARGO,4));
    public static final SlimefunItem RESETTER=new StorageCleaner(SINGULARITY,LogiTechSlimefunItemStacks.RESETTER,ENHANCED_CRAFTING_TABLE,
            recipe("CRAFTING_TABLE",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"TRASH_CAN_BLOCK",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
                    "CRAFTING_TABLE",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CRAFTING_TABLE"))
            .register();
    public static final SlimefunItem STORAGE_SINGULARITY=new Singularity(SINGULARITY, LogiTechSlimefunItemStacks.STORAGE_SINGULARITY, ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,
            LogiTechSlimefunItemStacks.PARADOX,"CHEST",LogiTechSlimefunItemStacks.PARADOX,
            LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.ABSTRACT_INGOT))
            .register();
    public static final SlimefunItem QUANTUM_LINK=new QuantumLink(SINGULARITY,LogiTechSlimefunItemStacks.QUANTUM_LINK,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.ABSTRACT_INGOT))
            .register();
    public static final  SlimefunItem INPORT=new InputPort(SINGULARITY, LogiTechSlimefunItemStacks.INPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.PARADOX,"CHEST","CHEST",LogiTechSlimefunItemStacks.PARADOX,null,
                    null,"CHEST",LogiTechSlimefunItemStacks.UNIQUE,LogiTechSlimefunItemStacks.EXISTE,"CHEST",null,
                    null,"CHEST",LogiTechSlimefunItemStacks.LOGIC,LogiTechSlimefunItemStacks.NOLOGIC,"CHEST",null,
                    null,LogiTechSlimefunItemStacks.PARADOX,"CHEST","CHEST",LogiTechSlimefunItemStacks.PARADOX,null
                    ))
            .register();
    public static final  SlimefunItem OUTPORT=new OutputPort(SINGULARITY, LogiTechSlimefunItemStacks.OUTPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CHEST","CHEST",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,"CHEST",LogiTechSlimefunItemStacks.EXISTE,LogiTechSlimefunItemStacks.UNIQUE,"CHEST",null,
                    null,"CHEST",LogiTechSlimefunItemStacks.NOLOGIC,LogiTechSlimefunItemStacks.LOGIC,"CHEST",null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CHEST","CHEST",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null
                    ))
            .register();
    public static final SlimefunItem IOPORT=new IOPort(SINGULARITY,LogiTechSlimefunItemStacks.IOPORT,COMMON_TYPE,
            recipe(null,null,null,null,null,null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CHEST","CHEST",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null,
                    null,"CHEST",LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX,"CHEST",null,
                    null,"CHEST",LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.PARADOX,"CHEST",null,
                    null,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,"CHEST","CHEST",LogiTechSlimefunItemStacks.ABSTRACT_INGOT,null
            ))
            .register();




//TODO add descriptions to these shits


    //special
    public static final SlimefunItem HEAD_ANALYZER= new HeadAnalyzer(SPECIAL,LogiTechSlimefunItemStacks.HEAD_ANALYZER,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"PLAYER_HEAD",BUG,"PLAYER_HEAD",null,null,null)
    ).register();
    public static final SlimefunItem RECIPE_LOGGER=new RegisteryLogger(SPECIAL,LogiTechSlimefunItemStacks.RECIPE_LOGGER,ENHANCED_CRAFTING_TABLE,
            recipe(null,null,null,"CRAFTING_TABLE",BUG,Material.WRITABLE_BOOK,null,null))
            .register();
    public static final SlimefunItem LASER_GUN= new LaserGun(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.LASER_GUN, SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT, LogiTechSlimefunItemStacks.LSINGULARITY, LogiTechSlimefunItemStacks.STAR_GOLD_INGOT, "ENERGIZED_CAPACITOR", LogiTechSlimefunItemStacks.LASER, "ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT, LogiTechSlimefunItemStacks.LSINGULARITY, LogiTechSlimefunItemStacks.STAR_GOLD_INGOT))
            .register();
    public static final SlimefunItem TRACE_ARROW=new TrackingArrowLauncher(TOOLS_FUNCTIONAL,LogiTechSlimefunItemStacks.TRACE_ARROW,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(LogiTechSlimefunItemStacks.LSINGULARITY,"EXPLOSIVE_BOW",LogiTechSlimefunItemStacks.LSINGULARITY,"ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.ATOM_INGOT,"ENERGIZED_CAPACITOR",
                    LogiTechSlimefunItemStacks.LSINGULARITY,"ICY_BOW",LogiTechSlimefunItemStacks.LSINGULARITY))
            .register();
    public static final SlimefunItem RTP_RUNE=new CustomItemWithHandler<ItemDropHandler>(SPACE,LogiTechSlimefunItemStacks.RTP_RUNE,ANCIENT_ALTAR,
            recipe("ANCIENT_RUNE_EARTH","ANCIENT_RUNE_ENDER","ANCIENT_RUNE_FIRE",LogiTechSlimefunItemStacks.HYPER_LINK,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.HYPER_LINK,
                    "ANCIENT_RUNE_WATER","ANCIENT_RUNE_ENDER","ANCIENT_RUNE_AIR"),null) {
        HashSet<String> BANNED_WORLD_NAME=new HashSet<>(){{
           add("logispace" );
        }};
//        protected ItemSetting<Boolean> rtpChangeWorld = createForce("rtp-change-world",false);
        protected ItemSetting<Integer> rtpRange2pow =createForce("rtp-max-amount",14);
        public void addInfo(ItemStack item){
            item.setItemMeta(addLore(item, "&7世界范围: "+
                    "&7坐标范围: 2^(max(10+丢出数量,%s))".formatted(String.valueOf(10+rtpRange2pow.getValue()))).getItemMeta());
        }
        int DELAY_BEFORE_TP=60;
        int RETRY_TIME=5;
        public boolean onDrop(PlayerDropItemEvent var1, Player var2, Item var3){
            if(isItem(var3.getItemStack())){
                if(canUse(var2,true)){
                    Schedules.launchSchedules(
                            ()-> {
                                if(!var3.isValid())return;
                                ItemStack stack=var3.getItemStack();
                                int amount=stack.getAmount();
                                stack.setAmount(0);
                                var3.setItemStack(stack);
                                Location center= var3.getLocation();
                                center.getWorld().strikeLightningEffect(center);
                                int ranges=1<<(10+Math.min(amount/2,rtpRange2pow.getValue()));
                                sendMessage(var2,"&a即将开始随机传送,传送范围: "+ranges);
                                BukkitUtils.executeAsync(
                                        ()->{
                                            onRtp(var2,center,ranges,RETRY_TIME);
                                        }
                                );
                            }
                            ,30,true,0 );
                }
                return true;
            }
            return false;
        }
        public Random rand=new Random();
        public double validCoord(double origin,double min,double max){
            return Math.max(min,Math.min(origin,max));
        }
        public void onRtp(Player player,Location center,int range,int leftTime){
            AsyncResultRunnable<Boolean> effectResult=new AsyncResultRunnable<Boolean>() {
                @Override
                public Boolean result() {
                    return onEffect(player);
                }
            };
            CountDownLatch latch=effectResult.runThreadBackground();
            Location loc=onRandomLocationFind(center,range);
            //预加载的过程中执行例子效果
            try{
                latch.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(!effectResult.getResult()){
                return;
            }
            if(loc!=null){
                Schedules.launchSchedules(()->{
                    player.teleport(loc);
                    sendTitle(player,"&a传送成功!","");
                },0,true,0);
            }
            else{
                if(leftTime>0){
                    sendTitle(player,"&c传送失败!剩余次数%d次".formatted(leftTime),"&e将在三秒后重新传送");
                    Schedules.launchSchedules(()->{
                        onRtp(player,center,range,leftTime-1);
                    },50,false,0);
                }else{
                    sendTitle(player,"&c传送失败!","");
                }
            }
        }
        int EFFECT_RANGE=3;
        int EFFECT_PERIOD=4;
        public Location onRandomLocationFind(Location center,int range){
            World world;
            //不切换世界
            if(!false &&rand.nextInt(20)%2==0){
                world=center.getWorld();
            }else{
                do{
                    world=Bukkit.getWorlds().get(rand.nextInt(Bukkit.getWorlds().size()));
                }while (BANNED_WORLD_NAME.contains(world.getName()));
            }
            WorldBorder border=world.getWorldBorder();
            Location worldcenter=border.getCenter();
            double size=border.getSize()/2;
            double minX=worldcenter.getX()-size;
            double maxX=worldcenter.getX()+size;
            double minZ=worldcenter.getZ()-size;
            double maxZ=worldcenter.getZ()+size;
            Location newLocation=new Location(world,validCoord(center.getBlockX()+rand.nextDouble(-range,range),minX,maxX),
                    rand.nextDouble(world.getMinHeight()+16, world.getMaxHeight()-16),
                    validCoord(center.getBlockZ()+rand.nextDouble(-range,range),minZ,maxZ));
            int x=newLocation.getBlockX()>>4;
            int z=newLocation.getBlockZ()>>4;
            //执行预加载 提前生成3x3范围
            Schedules.launchRepeatingSchedule((i)->{

                newLocation.getWorld().getChunkAt(x-1+i%3,z-1+i/3,true);


            },1,false,6,9);
            while(true){
                if(newLocation.getBlock().getType().isAir()){
                    return newLocation;
                }
                newLocation.add(0,-1,0);

                if(newLocation.getY()<world.getMinHeight()+16){
                    return null;
                }
            }

        }
        public boolean onEffect(Player player){
            HashSet<Location> originLocations=new HashSet<>();
            Location loc=player.getLocation();
            World world=player.getWorld();
            for(int i=-89;i<89;i+=2){
                for(int j=0;j<4;++j){
                    Location testLoc=loc.clone();
                    testLoc.setYaw(testLoc.getYaw()+45+j*90 );
                    testLoc.setPitch(i);
                    originLocations.add(testLoc);
                }
            }
            HashSet<Location> effectLocations=new HashSet<>(originLocations);
            AtomicBoolean hasCancelled=new AtomicBoolean(false);
            Schedules.asyncWaithRepeatingSchedule((i)->{
                if(!hasCancelled.get() ){
                    if(player.getWorld()!=world||player.getLocation().distance(loc)>0.25){
                        hasCancelled.set(true);
                        sendTitle(player,"&c传送已被取消!","&e您的位置移动了!");
                        return;
                    }


                    if((DELAY_BEFORE_TP-EFFECT_PERIOD*i) %20<EFFECT_PERIOD){
                        sendTitle(player,"&a即将传送,请勿移动!","&e倒计时 %d 秒".formatted((DELAY_BEFORE_TP-EFFECT_PERIOD*i) /20));
                    }
                    for(Location location:originLocations){
                        Location testLoc2=location.clone();
                        testLoc2.setYaw(location.getYaw()+(i*90*EFFECT_PERIOD)/DELAY_BEFORE_TP);
                        effectLocations.add(testLoc2);
                    }
                    for(Location location:effectLocations){
                        Location particlePosition=location.clone().add(location.getDirection().multiply(3));
                        world.spawnParticle(Particle.SOUL_FIRE_FLAME,particlePosition,0,0.0,0.0,0.0,1,null,true);
                    }
                }
            },0,false,EFFECT_PERIOD,DELAY_BEFORE_TP/EFFECT_PERIOD);
            return !hasCancelled.get();
        }
        public ItemDropHandler[] getItemHandler(){
            return new ItemDropHandler[]{this::onDrop};
        }
    }
            .register().setOutput(setC(LogiTechSlimefunItemStacks.RTP_RUNE,2));
    public static final SlimefunItem ANTIGRAVITY=new ChargableProps(SPACE, LogiTechSlimefunItemStacks.ANTIGRAVITY, RecipeType.ANCIENT_ALTAR,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,"ANCIENT_RUNE_ENDER",LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,"ANCIENT_RUNE_ENDER",
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT)) {
        private final float maxCharge=1_296_000.0f;
        private final float energyConsumption=640.0f;
        @Override
        public float getMaxItemCharge(ItemStack var1) {
            return maxCharge;
        }
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            ItemStack item=event.getItem();
            float itemCharge;
            if(item==null||item.getType().isAir()){
                return;
            }
            if( (itemCharge=getItemCharge(item))>=energyConsumption){
                setItemCharge(item,itemCharge-energyConsumption);
                event.getPlayer().addPotionEffect(PotionEffectType.LEVITATION.createEffect(160,255));
            }else{
                sendMessage(event.getPlayer(),concat("&c电力不足! ",String.valueOf(itemCharge),"J/",String.valueOf(energyConsumption),"J"));
            }
        }
    }
            .register();
    public static final SlimefunItem SPACE_CARD=new SpaceStorageCard(SPACE,LogiTechSlimefunItemStacks.SPACE_CARD,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.LSINGULARITY,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT))
            .register();
    public static final  SlimefunItem SPACETOWER_FRAME=new SpaceTowerFrame(SPACE, LogiTechSlimefunItemStacks.SPACETOWER_FRAME,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),LogiTechSlimefunItemStacks.STACKFRAME,LogiTechSlimefunItemStacks.STACKFRAME,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),LogiTechSlimefunItemStacks.STACKFRAME,LogiTechSlimefunItemStacks.STACKFRAME,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BISILVER))
            .register().setOutput(setC(LogiTechSlimefunItemStacks.SPACETOWER_FRAME,16));
    public static final  SlimefunItem SPACETOWER=new SpaceTower(SPACE, LogiTechSlimefunItemStacks.SPACETOWER,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,
                    LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.LIOPORT,
                    LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.SPACE_PLATE,
                    LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.SPACE_PLATE,
                    LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.LIOPORT,
                    LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.VIRTUAL_SPACE,LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY),0,0)
            .register();
    public static final SlimefunItem RADIATION_CLEAR=new CustomProps(SPECIAL, LogiTechSlimefunItemStacks.RADIATION_CLEAR, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
                public void onClickAction(PlayerRightClickEvent event) {
                    Optional<Block> b= event.getClickedBlock();
                    if(b.isPresent()){
                        if(RadiationRegionManager.removeNearRadiation(b.get().getLocation(),40)){
                            sendMessage(event.getPlayer(),"&a成功清除附近40格内的辐射区");
                        }else {
                            sendMessage(event.getPlayer(),"&c附近没有辐射区!");
                        }
                    }
                }
            }
            .register();
    public static final SlimefunItem ANTIMASS_CLEAR=new CustomProps(SPECIAL, LogiTechSlimefunItemStacks.ANTIMASS_CLEAR, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
        public void onClickAction(PlayerRightClickEvent event) {
            Player p = event.getPlayer();
            if(p.isOp()||p.getGameMode() == GameMode.CREATIVE){
                if(p.isSneaking()){
                    AddUtils.sendMessage(p,"&a输入一种材料以替换&c当前世界&a所有逻辑核心,输入取消以取消");
                    AddUtils.asyncWaitPlayerInput(p,(str)->{
                        try{
                            Material material = WorldUtils.getBlock(  Material.getMaterial(str.toUpperCase(Locale.ROOT)) );
                            Preconditions.checkNotNull(material );
                            HashSet<SlimefunBlockData> data = new HashSet<>();
                            HashSet<Location> locations = new HashSet<>();
                            Slimefun.getDatabaseManager().getBlockDataController().getAllLoadedChunkData().forEach(c->data.addAll(c.getAllBlockData()));
                            data.stream().filter(d->d.getSfId().equals(LOGIC_CORE.getId())).map(SlimefunBlockData::getLocation).filter(l->l.getWorld()==p.getWorld()).forEach((l)->{
                                locations.add(l);
                                l.getBlock().setType(material);
                            });
                            Schedules.launchSchedules(()->{
                                locations.stream().forEach(Slimefun.getDatabaseManager().getBlockDataController()::removeBlock);
                            },0,false,0);
                            AddUtils.sendMessage(p,"&a已替换材质,并删除原有的逻辑核心");
                        }catch  (Throwable e){
                            AddUtils.sendMessage(p,"&a已取消");
                        }
                    });
                }else{
                    if(ANTIMASS instanceof SpreadBlock sb){
                        sb.getSpreadOwner().clear();
                        sb.getSpreadTicker().clear();
                        WorldUtils.abortCreatingQueue();
                        HashSet<SlimefunBlockData> data = new HashSet<>();
                        Slimefun.getDatabaseManager().getBlockDataController().getAllLoadedChunkData().forEach(c->data.addAll(c.getAllBlockData()));Set<Location> location =  data.stream().filter(d->d.getSfId().equals(ANTIMASS.getId())).map(SlimefunBlockData::getLocation).collect(Collectors.toSet());
                        location.forEach(Slimefun.getDatabaseManager().getBlockDataController()::removeBlock);

                        sendMessage(event.getPlayer(),"&a反概念物质已成功清除");
                        Schedules.launchSchedules(()->{
                            location.forEach(sb::createResultAt);
                        },30,false,0);
                    }


                }
            }else{
                AddUtils.sendMessage(p,"&c你没有权限使用这个道具");
            }

        }
    }
            .register();
    public static final SlimefunItem HOLOGRAM_REMOVER=new CustomProps(SPECIAL, LogiTechSlimefunItemStacks.HOLOGRAM_REMOVER, ENHANCED_CRAFTING_TABLE,
            recipe(null, null, null, LogiTechSlimefunItemStacks.BUG, "DIAMOND_SWORD", LogiTechSlimefunItemStacks.BUG, null, null, null), null) {
        protected final HashSet<Player> COOLDOWNS=new HashSet();
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            Player p=event.getPlayer();
            if(p!=null){
                if(WorldUtils.hasPermission(p,p.getLocation().getBlock(), Interaction.INTERACT_BLOCK)){
                    if(COOLDOWNS.contains(p)){
                        sendMessage(p,"&c物品冷却中!");
                    }else{
                        MultiBlockService.removeUnrecordedHolograms(p.getLocation(),20);
                        sendMessage(p,"&a成功清除!进入5s(100gt)冷却");
                        COOLDOWNS.add(p);
                        Schedules.launchSchedules(()->COOLDOWNS.remove(p),100,true,0);
                    }
                }else{
                    sendMessage(p,"&c你没有权限在这里使用该道具");
                }
            }
            event.cancel();
        }
    }
            .register();
    public static final SlimefunItem MULTIBLOCKBUILDER=new MultiBlockBuilder(SPECIAL,LogiTechSlimefunItemStacks.MULTIBLOCKBUILDER,NULL,
            NULL_RECIPE.clone())
            .register();
    //final
    public static final SlimefunItem ANTIMASS=new SpreadBlock(BEYOND,LogiTechSlimefunItemStacks.ANTIMASS,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.LOGIC_CORE,9),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,64),"64ENERGIZED_CAPACITOR",setC(LogiTechSlimefunItemStacks.PDCECDMD,16),LogiTechSlimefunItemStacks.FINAL_FRAME),LOGIC_CORE,Material.COMMAND_BLOCK,Material.SCULK)
            .register();
    public static final  SlimefunItem FINAL_LASER=new Laser(BEYOND, LogiTechSlimefunItemStacks.FINAL_LASER,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),setC(LogiTechSlimefunItemStacks.HGTLPBBI,1),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.HGTLPBBI,1),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),setC(LogiTechSlimefunItemStacks.HGTLPBBI,1),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.TECH_CORE,4),setC(LogiTechSlimefunItemStacks.HGTLPBBI,1),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,4),
                    setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,1),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,1),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),
                    setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,1),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,1),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),
                    setC(LogiTechSlimefunItemStacks.PAGOLD,4),setC(LogiTechSlimefunItemStacks.PDCECDMD,1),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.LASER,2),setC(LogiTechSlimefunItemStacks.PDCECDMD,1),setC(LogiTechSlimefunItemStacks.PAGOLD,4),
                    null,setC(LogiTechSlimefunItemStacks.PAGOLD,4),setC(LogiTechSlimefunItemStacks.LSINGULARITY,1),setC(LogiTechSlimefunItemStacks.LSINGULARITY,1),setC(LogiTechSlimefunItemStacks.PAGOLD,4),null), 8_000_000,1_200_000,"final.sub")
            .register();
    public static final SlimefunItem FINAL_BASE=new MultiPart(BEYOND,LogiTechSlimefunItemStacks.FINAL_BASE,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.LOGIC_CORE,8),LogiTechSlimefunItemStacks.BISILVER),"final.base")
            .register();
    public static final SlimefunItem FINAL_ALTAR=new FinalAltarCore(BEYOND,LogiTechSlimefunItemStacks.FINAL_ALTAR,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.HGTLPBBI,4),LogiTechSlimefunItemStacks.FINAL_BASE,setC(LogiTechSlimefunItemStacks.PDCECDMD,4)),"final.core")
            .register();
    public static final  SlimefunItem FINAL_SEQUENTIAL=new FinalSequenceConstructor(BEYOND, LogiTechSlimefunItemStacks.FINAL_SEQUENTIAL,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.STACKFRAME,64),
                    LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.STORAGE_SINGULARITY,LogiTechSlimefunItemStacks.STORAGE_SINGULARITY,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.PDCECDMD,
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.LIOPORT,LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,LogiTechSlimefunItemStacks.SEQ_CONSTRUCTOR,LogiTechSlimefunItemStacks.LIOPORT,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,16),setC(LogiTechSlimefunItemStacks.LSINGULARITY,64),setC(LogiTechSlimefunItemStacks.LSINGULARITY,64),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,16),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,16),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,16),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8)
                    ,LogiTechSlimefunItemStacks.HGTLPBBI, setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.STACKFRAME,64)), addGlow( new ItemStack(Material.FIRE_CHARGE)),            10240,114514,
            mkMp(
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.MASS_CORE,256),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,256),setC(LogiTechSlimefunItemStacks.HGTLPBBI,256),
                            setC(LogiTechSlimefunItemStacks.LSINGULARITY,128),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,128),setC(LogiTechSlimefunItemStacks.PDCECDMD,256),
                            setC(LogiTechSlimefunItemStacks.TECH_CORE,256),setC(LogiTechSlimefunItemStacks.METAL_CORE,256)),mkl(setC(LogiTechSlimefunItemStacks.FINAL_FRAME,2))),2,
            mkP(mkl(setC(LogiTechSlimefunItemStacks.LSINGULARITY,512),"1919810IRON_DUST","1919810GOLD_DUST","1919810COPPER_DUST",
                    "1919810TIN_DUST","1919810SILVER_DUST","1919810LEAD_DUST",
                    "1919810ALUMINUM_DUST","1919810ZINC_DUST","1919810MAGNESIUM_DUST"),mkl(LogiTechSlimefunItemStacks.LOGIC_CORE)),2,
            mkP(mkl("512COPPER_INGOT","512IRON_INGOT","512GOLD_INGOT","512NETHERITE_INGOT"),mkl(LogiTechSlimefunItemStacks.METAL_CORE)),0,
            mkP(mkl("512MAGNESIUM_INGOT","512LEAD_INGOT","512TIN_INGOT","512ZINC_INGOT"),mkl(LogiTechSlimefunItemStacks.SMELERY_CORE)),0,
            mkP(mkl("512SILVER_INGOT","512ALUMINUM_INGOT","512DIAMOND","512REDSTONE"),mkl(LogiTechSlimefunItemStacks.TECH_CORE)),0,
            mkP(mkl("512COAL","512LAPIS_LAZULI","512QUARTZ","512EMERALD"),mkl(LogiTechSlimefunItemStacks.MASS_CORE)),0,
            mkP(mkl(setC(LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,1024)),mkl(LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY)),0

            ))
            .register();
    public static final SlimefunItem FINAL_STONE_MG=new FinalMGenerator(BEYOND, LogiTechSlimefunItemStacks.FINAL_STONE_MG,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.STACKFRAME,64),
                    LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.MASS_CORE,64),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.MASS_CORE,64),LogiTechSlimefunItemStacks.HGTLPBBI,
                    LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.NETHER_FEAT,16),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,16),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.HGTLPBBI,
                    LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.FINAL_FRAME,setC(LogiTechSlimefunItemStacks.WORLD_FEAT,16),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.PDCECDMD,
                    LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.MASS_CORE,64),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.MASS_CORE,64),LogiTechSlimefunItemStacks.PDCECDMD,
                    setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.STACKFRAME,64)),1,1_440_000,57_600,
            new PairList<>(){{
                put(mkl("COBBLESTONE"),mkl("666666COBBLESTONE"));
                put(mkl("NETHERRACK"),mkl("114514NETHERRACK"));
                put(mkl("END_STONE"),mkl("114514END_STONE"));
                put(mkl("GRANITE"),mkl("114514GRANITE"));
                put(mkl("DIORITE"),mkl("114514DIORITE"));
                put(mkl("ANDESITE"),mkl("114514ANDESITE"));
                put(mkl("STONE"),mkl("114514STONE"));
            }}
            )
            .register();

    public static final SlimefunItem FINAL_MANUAL=new FinalManual(BEYOND,LogiTechSlimefunItemStacks.FINAL_MANUAL,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.CRAFT_MANUAL,LogiTechSlimefunItemStacks.FURNACE_MANUAL,LogiTechSlimefunItemStacks.ENHANCED_CRAFT_MANUAL,LogiTechSlimefunItemStacks.GRIND_MANUAL,setC(LogiTechSlimefunItemStacks.STACKFRAME,64),
                    LogiTechSlimefunItemStacks.ORE_CRUSHER_MANUAL,null,setC(LogiTechSlimefunItemStacks.LASER,64),setC(LogiTechSlimefunItemStacks.LASER,64),null,LogiTechSlimefunItemStacks.TABLESAW_MANUAL,
                    LogiTechSlimefunItemStacks.PRESSURE_MANUAL,setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,64),setC(LogiTechSlimefunItemStacks.MANUAL_CORE,64),setC(LogiTechSlimefunItemStacks.LSINGULARITY,64),setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,64),LogiTechSlimefunItemStacks.COMPRESSOR_MANUAL,
                    LogiTechSlimefunItemStacks.CRUCIBLE_MANUAL,setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,64),LogiTechSlimefunItemStacks.FINAL_FRAME,LogiTechSlimefunItemStacks.FINAL_FRAME,setC(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,64),LogiTechSlimefunItemStacks.SMELTERY_MANUAL,
                    LogiTechSlimefunItemStacks.GOLD_PAN_MANUAL,null,setC(LogiTechSlimefunItemStacks.LASER,64),setC(LogiTechSlimefunItemStacks.LASER,64),null,LogiTechSlimefunItemStacks.ANCIENT_ALTAR_MANUAL,
                    setC(LogiTechSlimefunItemStacks.STACKFRAME,64),LogiTechSlimefunItemStacks.MAGIC_WORKBENCH_MANUAL,LogiTechSlimefunItemStacks.COMPOSTER,LogiTechSlimefunItemStacks.ARMOR_FORGE_MANUAL,LogiTechSlimefunItemStacks.ORE_WASHER_MANUAL,setC(LogiTechSlimefunItemStacks.STACKFRAME,64)),0,0)
            .register();


    public static final  SlimefunItem FINAL_CONVERTOR=new FinalConvertor(BEYOND, LogiTechSlimefunItemStacks.FINAL_CONVERTOR,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.LOGIC_CORE,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.NETHER_FEAT,LogiTechSlimefunItemStacks.NETHER_FEAT,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.LOGIC_CORE,
                    LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.WORLD_FEAT,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.LOGIC_CORE,
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.FINAL_FRAME,LogiTechSlimefunItemStacks.FINAL_FRAME,LogiTechSlimefunItemStacks.PDCECDMD,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.LOGIC_CORE,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8)), 840_000_000,100_000_000,
            eqRandItemStackFactory(
                    Utils.list(
                            probItemStackFactory(LogiTechSlimefunItemStacks.VIRTUALWORLD,50),
                           setC(  LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,32),
                         setC(LogiTechSlimefunItemStacks.ATOM_INGOT,32),
                            setC(LogiTechSlimefunItemStacks.LSINGULARITY,16),
                            probItemStackFactory(randItemStackFactory(
                                    Utils.list(LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.ANTIMASS),
                                    Utils.list(40,40,1)
                            ),36),
                            probItemStackFactory(LogiTechSlimefunItemStacks.VIRTUAL_SPACE,25)
                    )
            ))
            .register();
    public static final  SlimefunItem RAND_EDITOR=new RandomEditor(BEYOND, LogiTechSlimefunItemStacks.RAND_EDITOR,COMMON_TYPE,
            recipe(setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.FINAL_FRAME,LogiTechSlimefunItemStacks.FINAL_FRAME,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.ATTR_OP,LogiTechSlimefunItemStacks.ATTR_OP,LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.PDCECDMD,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.LOGIC_CORE,
                    LogiTechSlimefunItemStacks.LOGIC_CORE,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.ANTIMASS,LogiTechSlimefunItemStacks.ANTIMASS,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.LOGIC_CORE,
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.FINAL_CONVERTOR,LogiTechSlimefunItemStacks.FINAL_CONVERTOR,LogiTechSlimefunItemStacks.HGTLPBBI,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),
                    setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),LogiTechSlimefunItemStacks.FINAL_FRAME,LogiTechSlimefunItemStacks.FINAL_FRAME,setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8),setC(LogiTechSlimefunItemStacks.SPACE_PLATE,8)), 200_000_000,25_000_000)
            .register();
    public static final  SlimefunItem FINAL_STACKMACHINE=new FinalStackMachine(BEYOND, LogiTechSlimefunItemStacks.FINAL_STACKMACHINE,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")), Material.NETHERITE_PICKAXE,100,400_000_000,16)
            .register();
    public static final SlimefunItem FINAL_STACKMGENERATOR=new FinalStackMGenerator(BEYOND, LogiTechSlimefunItemStacks.FINAL_STACKMGENERATOR,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,Language.get("machine.ENDFRAME_MACHINE.name")),1,400_000_000,100,
            16)
            .register();
    //stack machine maker
    public static final  SlimefunItem ENDFRAME_MACHINE=new EMachine(VANILLA, LogiTechSlimefunItemStacks.ENDFRAME_MACHINE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.PARADOX,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.PARADOX,
                    LogiTechSlimefunItemStacks.END_FEAT,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD,LogiTechSlimefunItemStacks.END_FEAT,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT), Material.STONE,324,2_000,
            mkMp(
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.END_FEAT,1),"16END_STONE"),mkl("4END_PORTAL_FRAME")),6,
                    mkP(mkl(LogiTechSlimefunItemStacks.STAR_GOLD,LogiTechSlimefunItemStacks.DIMENSIONAL_SHARD),mkl(LogiTechSlimefunItemStacks.PORTAL_FRAME)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,3),"16QUARTZ_BLOCK"),mkl(LogiTechSlimefunItemStacks.SOLAR_REACTOR_FRAME)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.LPLATE,2),"16GLASS"),mkl(LogiTechSlimefunItemStacks.SOLAR_REACTOR_GLASS)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.STACKFRAME,4),LogiTechSlimefunItemStacks.BOOLEAN_TRUE),mkl(LogiTechSlimefunItemStacks.STACKMACHINE)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.STACKFRAME,4),LogiTechSlimefunItemStacks.BOOLEAN_FALSE),mkl(LogiTechSlimefunItemStacks.STACKMGENERATOR)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.STACKFRAME,10),"4ENERGY_REGULATOR"),mkl(LogiTechSlimefunItemStacks.ENERGY_AMPLIFIER)),6,
                    mkP(mkl(LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.MASS_CORE),mkl(LogiTechSlimefunItemStacks.TRANSMUTATOR_FRAME)),6,
                    mkP(mkl(LogiTechSlimefunItemStacks.SPACE_PLATE,setC(LogiTechSlimefunItemStacks.LFIELD,24)),mkl(setC(LogiTechSlimefunItemStacks.TRANSMUTATOR_GLASS,2))),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.ATOM_INGOT,16),setC(LogiTechSlimefunItemStacks.BISILVER,2)),mkl(LogiTechSlimefunItemStacks.TRANSMUTATOR_ROD)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.VIRTUALWORLD,4),setC(LogiTechSlimefunItemStacks.HGTLPBBI,7)),mkl(LogiTechSlimefunItemStacks.FINAL_STACKMACHINE)),6,
                    mkP(mkl(setC(LogiTechSlimefunItemStacks.VIRTUALWORLD,4),setC(LogiTechSlimefunItemStacks.PDCECDMD,7)),mkl(LogiTechSlimefunItemStacks.FINAL_STACKMGENERATOR)),6
            ))
            .register();
    public static final  SlimefunItem FINAL_CRAFT=new SpecialCrafter(BEYOND, LogiTechSlimefunItemStacks.FINAL_CRAFT,STARSMELTERY,
            recipe(setC(LogiTechSlimefunItemStacks.PDCECDMD,8),setC(LogiTechSlimefunItemStacks.VIRTUALWORLD,11),setC(LogiTechSlimefunItemStacks.HGTLPBBI,8),setC(LogiTechSlimefunItemStacks.CRAFTER,16)), Material.CRAFTING_TABLE,0,18_000, 7_200_000){
        {
            CRAFT_PROVIDER=FinalFeature.STORAGE_AND_LOCPROXY_READER;
            MACHINE_PROVIDER=FinalFeature.STORAGE_READER;
        }
        @Override
        public void registerTick(SlimefunItem item){
            this.addItemHandler(FinalFeature.FINAL_SYNC_TICKER);
        }
        @Override
        public HashMap<SlimefunItem, RecipeType> getRecipeTypeMap() {
            return CRAFTTYPE_MANUAL_RECIPETYPE;
        }
        public boolean advanced(){
            return true;
        }
        public int getCraftLimit(Block b,BlockMenu menu){
            return super.getCraftLimit(b,menu)*4;
        }
    }
            .register();


    public static final  SlimefunItem SMITH_WORKSHOP=new SmithingWorkshop(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.SMITH_WORKSHOP,COMMON_TYPE,
            recipe(LogiTechSlimefunItemStacks.BISILVER,LogiTechSlimefunItemStacks.SPACE_PLATE,"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.SPACE_PLATE,LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),LogiTechSlimefunItemStacks.LSCHEDULER,LogiTechSlimefunItemStacks.LSCHEDULER,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),LogiTechSlimefunItemStacks.BISILVER,
                    setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),setC(LogiTechSlimefunItemStacks.PAGOLD,2),
                    setC(LogiTechSlimefunItemStacks.PAGOLD,2),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.LSINGULARITY,2),setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),setC(LogiTechSlimefunItemStacks.PAGOLD,2),
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),LogiTechSlimefunItemStacks.LENGINE,LogiTechSlimefunItemStacks.LENGINE,setC(LogiTechSlimefunItemStacks.SMELERY_CORE,2),LogiTechSlimefunItemStacks.BISILVER,
                    LogiTechSlimefunItemStacks.BISILVER,setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),"ENERGIZED_CAPACITOR","ENERGIZED_CAPACITOR",setC(LogiTechSlimefunItemStacks.ATOM_INGOT,8),LogiTechSlimefunItemStacks.BISILVER), "smith.core")
            .setDisplayRecipes(
                    Utils.list(
                            getInfoShow("&f机制",
                                    "&7该机器需要搭建锻铸工坊的结构方可运行",
                                    "&7该多方块投影中海晶灯部分需放置\"工坊接口\"",
                                    "&7其余方块均为原版方块",
                                    "&7对于铜质组成部分,任意生锈状态/打蜡状态的同类型方块均可识别",
                                    "&a每多搭建一层该机器并行处理数x2(即机器快一倍)"),null,
                            getInfoShow("&f机制",
                                    "&7任意\"工坊接口\"类型的机器需要在该多方块",
                                    "&7中方可执行其功效",
                                    "&7"
                            ),null
                    )
            )
            .register();
    public static final  SlimefunItem SMITH_INTERFACE_NONE=new SmithingInterface(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.SMITH_INTERFACE_NONE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.ATOM_INGOT,
                    LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT),0,0,false)
            .register();
    public static final  SlimefunItem SMITH_INTERFACE_CRAFT=new SmithInterfaceProcessor(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.SMITH_INTERFACE_CRAFT,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,"CRAFTING_TABLE",LogiTechSlimefunItemStacks.SMITH_INTERFACE_NONE,"CRAFTING_TABLE",
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.CARGO_PIP,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT),24_000,1200)
            .register();

    public static final  SlimefunItem ATTR_OP=new AttributeOperator(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.ATTR_OP,MAGIC_WORKBENCH,
            recipe("AUTO_DISENCHANTER_2",LogiTechSlimefunItemStacks.LENGINE,"AUTO_DISENCHANTER_2",LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,"BOOK_BINDER",LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    "AUTO_ENCHANTER_2",LogiTechSlimefunItemStacks.LENGINE,"AUTO_ENCHANTER_2"), 1200,120)
            .register();
    public static final  SlimefunItem CUSTOM_CHARGER=new SmithInterfaceCharger(TOOLS_FUNCTIONAL, LogiTechSlimefunItemStacks.CUSTOM_CHARGER,ENHANCED_CRAFTING_TABLE,
            recipe("ENERGIZED_CAPACITOR",LogiTechSlimefunItemStacks.TECH_CORE,"ENERGIZED_CAPACITOR","CHARGING_BENCH",LogiTechSlimefunItemStacks.SMITH_INTERFACE_NONE,"CHARGING_BENCH",
                    LogiTechSlimefunItemStacks.LINE_CHARGER_PLUS,LogiTechSlimefunItemStacks.ENERGY_PIPE_PLUS,LogiTechSlimefunItemStacks.LINE_CHARGER_PLUS), 64_000_000,4000)
            .register();
    public static final SlimefunItem FU_BASE=new MaterialItem(TOOLS_FUNCTIONAL,LogiTechSlimefunItemStacks.FU_BASE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.TECH_CORE,LogiTechSlimefunItemStacks.LPLATE,
                    LogiTechSlimefunItemStacks.ABSTRACT_INGOT,LogiTechSlimefunItemStacks.LPLATE,LogiTechSlimefunItemStacks.ABSTRACT_INGOT),Utils.list(
                            getInfoShow("&f介绍","&7用于合成子分类\"功能单元\"中的物品","&7功能单元是一种&e类似附魔的组件型物品","&7安装在装备上可以给玩家带来增益")
    ))
            .register();
    public static final SlimefunItem AMPLIFY_BASE=new MaterialItem(TOOLS_SUBGROUP_1,LogiTechSlimefunItemStacks.AMPLIFY_BASE,ENHANCED_CRAFTING_TABLE,
            recipe(LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.HGTLPBBI,LogiTechSlimefunItemStacks.PAGOLD,
                    LogiTechSlimefunItemStacks.BUG,LogiTechSlimefunItemStacks.PAGOLD,LogiTechSlimefunItemStacks.BUG),null)
            .register()
            .setOutput(setC(LogiTechSlimefunItemStacks.AMPLIFY_BASE,6));
    public static final SWAmplifyComponent SWAMP_SPEED=(SWAmplifyComponent)new SWAmplifyComponent(TOOLS_SUBGROUP_1,LogiTechSlimefunItemStacks.SWAMP_SPEED,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.AMPLIFY_BASE,LogiTechSlimefunItemStacks.ATOM_INGOT,
                    LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT,LogiTechSlimefunItemStacks.ATOM_INGOT))
            .register();
    public static final SWAmplifyComponent SWAMP_RANGE=(SWAmplifyComponent)new SWAmplifyComponent(TOOLS_SUBGROUP_1,LogiTechSlimefunItemStacks.SWAMP_RANGE,SmithInterfaceProcessor.INTERFACED_CRAFTTABLE,
            recipe(LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.AMPLIFY_BASE,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,
                    LogiTechSlimefunItemStacks.STAR_GOLD_INGOT,LogiTechSlimefunItemStacks.DIMENSIONAL_SINGULARITY,LogiTechSlimefunItemStacks.STAR_GOLD_INGOT))
            .register();
    public static final SlimefunItem DISPLAY_FU_USE=new DisplayUseTrimmerLogicLate(TOOLS_SUBGROUP_2,LogiTechSlimefunItemStacks.DISPLAY_FU_USE,
            recipe(LogiTechSlimefunItemStacks.DISPLAY_FU_USE_1,LogiTechSlimefunItemStacks.DISPLAY_FU_USE_2,LogiTechSlimefunItemStacks.DISPLAY_FU_USE_3,
                    null,null,null,null,null,null),
            ()->{
                return EquipmentFUItem.getEntries().stream().map(SlimefunItem::getItem).collect(Collectors.toSet());
            },()->{
                HashSet<Material> set = new HashSet<>();
                EquipmentFUItem.getEntries().stream().map(EquipmentFUItem::getFUnit).map(EquipmentFU::getCanEquipedMaterial).forEach(set::addAll);
                return new RecipeChoice.MaterialChoice( set.stream().toList() );
            },()->{
                HashSet<ItemStack> set = new HashSet<>();
                EquipmentFUItem.getEntries().stream().map(EquipmentFUItem::getFUnit).forEach(fu->set.addAll(fu.getEquipCostable()));
                return new RecipeChoice.ExactChoice(set.stream().toList());
            },(smith)->{
                ItemStack item = smith.getItem(0);
                if( SlimefunItem.getByItem(item) instanceof EquipmentFUItem fu){
                    ItemStack equipment =  smith.getItem(1);
                    ItemStack cost = smith.getItem(2);
                    if(equipment!=null && cost!=null && fu.getFUnit().canEquipedTo(equipment,cost)){
                        ItemStack copied = equipment.clone();
                        EquipmentFU fuu = fu.getFUnit();
                        var returnedLevel= EquipmentFUManager.getManager().addEquipmentFU(copied, fuu,1);
                        if(returnedLevel<=0){
                            return Triplet.of(new ItemStack[]{copied},fuu.getEquipCost(copied,cost),fuu.getEquipTimeCost(copied,cost));
                        }
                    }
                }
                return null;
            }
            )
            .register();
    public static final SlimefunItem DISPLAY_REMOVE_FU=new MaterialItem(TOOLS_SUBGROUP_2,LogiTechSlimefunItemStacks.DISPLAY_REMOVE_FU,SmithInterfaceProcessor.INTERFACED_GRIND,
            recipe(null,LogiTechSlimefunItemStacks.DISPLAY_REMOVE_FU_1,null,
                    null,LogiTechSlimefunItemStacks.DISPLAY_REMOVE_FU_2,null,
                    null,null,null),null){
            @Override
            public void load(){
                super.load();
                SmithInterfaceProcessor.registerGrindstoneLogic((grind)->{
                    ItemStack item1 = grind.getItem(0);
                    ItemStack item2 = grind.getItem(1);
                    if(item1!=null&&item1.getAmount()==1&& item2!=null && SlimefunItem.getByItem(item2) instanceof EquipmentFUItem fu){
                        int level = EquipmentFUManager.getManager().getEquipmentFULevel(item1,fu.getFUnit());
                        int amount = item2.getAmount();
                        if(level>=amount){
                            return ()->{
                                ItemStack nowitem = grind.getItem(0);
                                Preconditions.checkNotNull(nowitem);
                                int removedLeft = EquipmentFUManager.getManager().removeEquipmentFU(nowitem,fu.getFUnit(),amount);
                                int actuallyRemoved = amount-removedLeft;
                                return new MultiCraftingOperation(new ItemGreedyConsumer[]{CraftUtils.getGreedyConsumerAsAmount(fu.getItem(),actuallyRemoved)},2*actuallyRemoved);
                            };
                        }
                    }
                    return null;
                });
            }
        }
            .register();
    public static final SlimefunItem DEMO_FU=new EquipmentFUItem(TOOLS_SUBGROUP_2,LogiTechSlimefunItemStacks.DEMO_FU,NULL,
            formatInfoRecipe(LogiTechSlimefunItemStacks.TMP1,Language.get("Tmp.TMP1.name")), EFUImplements.DEMO)
            .register();




    public static final SlimefunItem TMP1=new MaterialItem(FUNCTIONAL,LogiTechSlimefunItemStacks.TMP1,NULL,
            NULL_RECIPE.clone())
            .register();
    public static final SlimefunItem RESOLVE_FAILED=new MaterialItem(FUNCTIONAL, LogiTechSlimefunItemStacks.RESOLVE_FAILED,NULL,
            NULL_RECIPE.clone(),null)
            .register();
    public static final SlimefunItem SHELL=new CustomProps(FUNCTIONAL, LogiTechSlimefunItemStacks.SHELL, RecipeType.NULL,
            NULL_RECIPE.clone(), null) {
        @Override
        public void onClickAction(PlayerRightClickEvent event) {
            CommandShell.setup(event.getPlayer(),"0");
        }
    }
            .register();



    public static final SlimefunItem TESTUNIT1=register(new TestStorageUnit(FUNCTIONAL, LogiTechSlimefunItemStacks.TESTUNIT1,NULL,NULL_RECIPE,0,0));

}

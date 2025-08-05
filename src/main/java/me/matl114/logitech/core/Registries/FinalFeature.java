package me.matl114.logitech.core.Registries;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.matl114.logitech.core.LogiTechSlimefunItems;
import me.matl114.logitech.core.Blocks.Laser;
import me.matl114.logitech.core.Blocks.MultiBlock.FinalAltarCore;
import me.matl114.logitech.core.Items.SpecialItems.ReplaceCard;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.Settings;
import me.matl114.logitech.utils.UtilClass.CargoClass.Directions;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusher;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemPusherProvider;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemReplacerPusher;
import me.matl114.logitech.utils.UtilClass.StorageClass.ItemStorageCache;
import me.matl114.logitech.utils.UtilClass.StorageClass.LocationStorageProxy;
import me.matl114.logitech.utils.UtilClass.TickerClass.Ticking;
import me.matl114.matlib.utils.inventory.itemStacks.CleanItemStack;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.function.IntFunction;

public class FinalFeature {
    public static final ItemPusherProvider STORAGE_READER =new ItemPusherProvider() {
        @Override
        public ItemPusher get(Settings mod, ItemStack item, int slot) {
            if(item==null||item.getAmount()!=1){
                return CraftUtils.getpusher.get(mod,item,slot);
            }
            //not null.
            ItemPusher tar=null;
            ItemMeta meta=null;
            if(item.getAmount()==1){
                meta=item.getItemMeta();
                //只生成不是代理的缓存
                tar= ItemStorageCache.get(item,meta,slot,(i->!i.isStorageProxy()));
            }
            //get no yet
            if(tar==null){
                tar= CraftUtils.getpusher.get(mod,item,slot);
                if(meta!=null){//make a convenience
                    tar.setMeta(meta);
                }
            }
            return tar;
        }
    };



    public static final ItemPusherProvider STORAGE_AND_LOCPROXY_READER=new ItemPusherProvider() {
        @Override
        public ItemPusher get(Settings mod, ItemStack item, int slot) {
            if(item==null||item.getAmount()!=1){
                return CraftUtils.getpusher.get(mod,item,slot);
            }
            //not null.
            ItemPusher tar=null;
            ItemMeta meta=null;
            if(item.getAmount()==1){
                meta=item.getItemMeta();
                //全部生成 全部启动
                tar= ItemStorageCache.get(item,meta,slot,i->true);
            }
            //get no yet
            if(tar==null){
                tar= CraftUtils.getpusher.get(mod,item,slot);
                if(meta!=null){//make a convenience
                    tar.setMeta(meta);
                }
            }
            return tar;
        }
        @Override
        public IntFunction<ItemPusher> getMenu(Settings settings, BlockMenu inv, IntFunction<ItemStack> stackFunction, int[] slots){
            return new IntFunction<ItemPusher>() {
                HashSet<Location> proxyLocation;
                public ItemPusher apply(int slot) {

                    ItemPusher tar= get(settings,stackFunction.apply(slot),slots[slot]);
                    if(tar instanceof LocationStorageProxy lsp){
                        if(proxyLocation==null){
                            proxyLocation=new HashSet<>();
                        }
                        //禁止在一次模拟中制造超过两个连接
                        Location loc=lsp.getProxyLocation();
                        if(proxyLocation.contains(loc)){
                            //视为正常物品 不参与存储代理

                            return CraftUtils.getpusher.get(settings,stackFunction.apply(slot),slots[slot]);
                        }else {
                            proxyLocation.add(loc);
                        }
                    }
                    return tar;
                }
            };
        }

    };
    //todo: add the FAKE_UI support,see FAKE_UI as null
    public static final ItemPusherProvider MANUAL_CARD_READER=new ItemPusherProvider() {
        @Override
        public ItemPusher get(Settings settings, ItemStack item, int slot) {
            if(settings==Settings.OUTPUT){
                return CraftUtils.getpusher.get(settings, item, slot);
            }
            if(item !=null){
                ItemMeta meta = item.getItemMeta();
                if(meta !=null){
                    if(LogiTechSlimefunItems.FAKE_UI.getId().equals( CraftUtils.parseSfId(meta) )){
                        return CraftUtils.getpusher.get(settings, null, slot);
                    }
                    ItemStack stack= ReplaceCard.getAllCardReplacement(item,meta);
                    if(stack!=item){
                        return ItemReplacerPusher.get(item,stack);
                    }
                }
            }
            return CraftUtils.getpusher.get(settings, item, slot);


        }
    };
    public static final ItemStack MANUAL_CARD_INFO = AddUtils.addGlow( new CleanItemStack(Material.CRAFTING_TABLE,"&7机制 - &f替代",
            "&7本机器支持你使用物品替代卡替代原有物品进行合成",
            "&7同时,你可以使用槽位伪装板充当占位符",
            "&a机器会自动忽略这些伪装板"));
    public static boolean isFinalAltarCharged(SlimefunItem that, SlimefunBlockData data){
        if(that instanceof FinalAltarCore.FinalAltarChargable fac&&that instanceof Laser.LaserChargable lc){
            if(fac.mayForced(data)>=2){
                int t;
                if((t=lc.hasCharged(data,Directions.EAST))>0){
                    Laser.LaserChargable.setCharged(data,Directions.EAST,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.WEST))>0){
                    Laser.LaserChargable.setCharged(data,Directions.WEST,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.NORTH))>0){
                    Laser.LaserChargable.setCharged(data,Directions.NORTH,t-1);
                }else {
                    return false;
                }
                if((t=lc.hasCharged(data,Directions.SOUTH))>0){
                    Laser.LaserChargable.setCharged(data,Directions.SOUTH,t-1);
                }else {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    //actually ,about location proxy, machines should run in order AT ANY TIME,otherwise it will cause negative itemStorage
    public static final BlockTicker FINAL_SYNC_TICKER = new BlockTicker() {
        @Override
        public boolean isSynchronized() {
            return false;
        }
        //make tick Method synchronized in case of outsource parallel running
        @Override
        public synchronized void tick(Block b, SlimefunItem item, SlimefunBlockData data) {
            BlockMenu menu = data.getBlockMenu();
            //BlockMenu menu = BlockStorage.getInventory(b);
            if(item instanceof Ticking tickingMachine){
                tickingMachine.tick(b, menu,data,0);
            }
        }
    };
}

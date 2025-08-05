package me.matl114.logitech.core.Blocks.MultiBlock;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.manager.Schedules;
import me.matl114.logitech.core.LogiTechSlimefunItemStacks;
import me.matl114.logitech.core.Blocks.MultiBlockCore.MultiCore;
import me.matl114.logitech.core.Cargo.Links.HyperLink;
import me.matl114.logitech.utils.*;
import me.matl114.logitech.utils.UtilClass.FunctionalClass.OutputStream;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.AbstractMultiBlockHandler;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockHandler;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockService;
import me.matl114.logitech.utils.UtilClass.MultiBlockClass.MultiBlockType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PortalCore extends MultiCore {
    protected final int[] BORDER=new int[]{0,1,2,3,5,6,7,9,10,11,12,14,15,16,17};
    protected final int TOGGLE_SLOT=4;
    protected final int LINK_SLOT=13;
    protected final int HOLOGRAM_SLOT=8;
    protected final ItemStack TOGGLE_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &a开启","&7若需要更改目标坐标需要重新构建传送门");
    protected final ItemStack TOGGLE_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换传送门状态","&7当前状态: &c关闭");
    protected final ItemStack HOLOGRAM_ITEM_ON=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a南北向");
    protected final ItemStack HOLOGRAM_ITEM_ON_2=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &a东西向");
    protected final ItemStack HOLOGRAM_ITEM_OFF=new CustomItemStack(Material.RED_STAINED_GLASS_PANE,"&6点击切换全息投影","&e或使用/logitech multiblock 查看搭建教程","&7当前状态: &c关闭");
    protected final MultiBlockType MBTYPE;
    public HashMap<String,ItemStack> MBID_TO_ITEM=new HashMap<>(){{
        put("portal.part", LogiTechSlimefunItemStacks.PORTAL_FRAME.clone());
    }};
    public Map<String,ItemStack> getIdMappingDisplayUse(){
        return Map.copyOf(MBID_TO_ITEM);
    }
    public int[] getInputSlots(){
        return new int[0];
    }
    public int[] getOutputSlots(){
        return new int[0];
    }
    public PortalCore(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType,
                     ItemStack[] recipe, String blockId, MultiBlockType type){
        super(itemGroup, item, recipeType, recipe, blockId);
        this.MBTYPE = type;
    }
    public MultiBlockType getMultiBlockType(){
        return MBTYPE;
    }
    public MultiBlockService.MultiBlockBuilder BUILDER=((core, type, uid) -> {
        BlockMenu inv= DataCache.getMenu(core);
        if(inv!=null&&loadLink(inv)){
            return  MultiBlockHandler.createHandler(core,type,uid);
        }else {
            return null;
        }
    });
    public MultiBlockService.MultiBlockBuilder getBuilder(){
        return BUILDER;
    }
    public boolean loadLink(BlockMenu inv){
        ItemStack link=inv.getItemInSlot(LINK_SLOT);
        if(link!=null&& HyperLink.isLink(link.getItemMeta())){
            Location loc=HyperLink.getLink(link.getItemMeta());
            if(loc!=null&&validPortalCore(loc)!=null){
                DataCache.setLastLocation(inv.getLocation(),loc);
                return true;
            }
        }
        DataCache.setLastLocation(inv.getLocation(),null);
        return false;
    }

    /**
     * check linkage, try enable target portal if not enabled
     * @param loc
     * @return
     */
    public Location checkLink(Location loc){
        SlimefunBlockData data = DataCache.safeGetBlockCacheWithLoad(loc);
        if(data!=null){
            if(data.isDataLoaded()){
                Location loc2=DataCache.getLastLocation(data);
                SlimefunBlockData target;
                if(loc2!=null&&(target=validPortalCore(loc2))!=null){
                    if(MultiBlockService.getStatus(loc)!=0){
                        return loc2;
                    }else{
                        //may not be built yet
                        DataCache.runAfterSafeLoad(target,(data1)->{
                            MultiBlockService.tryCreateMultiBlock(loc2,getMultiBlockType(),(str)->{});
                        },false);
                    }
                    return null;
                }
                return null;
            }else{
                return null;
            }
        }
        return null;

    }
    public static SlimefunBlockData validPortalCore(Location loc){
        SlimefunBlockData data= DataCache.safeGetBlockCacheWithLoad(loc);
        if(data!=null){
            SlimefunItem it=SlimefunItem.getById(data.getSfId());
            //test if getStatus ok
            if((it instanceof PortalCore)){
                return data;
            }
        }
        return null;
    }

    public void constructMenu(BlockMenuPreset inv){
        int[] border=BORDER;
        int len=border.length;
        for(int i=0;i<len;i++){
            inv.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }

    }

    public void setupPortal(Block block1){
        //block1.setType(Material.NETHER_PORTAL);
        Block block2=block1.getRelative(BlockFace.UP);
        Block block3=block2.getRelative(BlockFace.UP);
        WorldUtils.removeSlimefunBlock(block2.getLocation(),true);
        WorldUtils.removeSlimefunBlock(block3.getLocation(),true);
        Schedules.launchSchedules(()->{
        block3.setType(Material.NETHER_PORTAL);
        block2.setType(Material.NETHER_PORTAL);
        //南北向的
        if(block2.getRelative(BlockFace.EAST).getType().isAir()||block2.getRelative(BlockFace.WEST).getType().isAir()){
            BlockData state2=block2.getBlockData();
            BlockData state3=block3.getBlockData();
            if(state2 instanceof Orientable){
                ((Orientable)state2).setAxis(Axis.Z);

                block2.setBlockData(state2);
            }
            if(state3 instanceof Orientable){
                ((Orientable)state3).setAxis(Axis.Z);
                block3.setBlockData(state3);
            }
        }
        },0,true,0);
    }
    public void deletePortal(Block block1){
        Block block2=block1.getRelative(BlockFace.UP);
        Block block3=block2.getRelative(BlockFace.UP);
        Schedules.launchSchedules(()->{
        if(block2.getType()==Material.NETHER_PORTAL){
            block2.setType(Material.AIR);
        }
        if(block3.getType()==Material.NETHER_PORTAL){
            block3.setType(Material.AIR);
        }
        },0,true,0);
    }
    public void onMultiBlockDisable(Location loc, AbstractMultiBlockHandler handler, MultiBlockService.DeleteCause cause){
        super.onMultiBlockDisable(loc,handler,cause);
        deletePortal(loc.getBlock());
        BlockMenu inv= DataCache.getMenu(loc);
        if(inv!=null){
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
        }
    }
    public void onMultiBlockEnable(Location loc,AbstractMultiBlockHandler handler){
        super.onMultiBlockEnable(loc,handler);
        setupPortal(loc.getBlock());
    }
    public MultiBlockService.DeleteCause NOLINK=new MultiBlockService.DeleteCause("不存在超链接",false);
    public void updateMenu(BlockMenu inv, Block block, Settings mod){
        int holoStatus=DataCache.getCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
        if(holoStatus==0){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_OFF);

        }else if(holoStatus==1){
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON);

        }else{
            inv.replaceExistingItem(HOLOGRAM_SLOT,HOLOGRAM_ITEM_ON_2);
        }
        if(!loadLink(inv)){
            MultiBlockService.deleteMultiBlock(inv.getLocation(),NOLINK);
        }
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        Location loc2=block.getLocation();
        if (MultiBlockService.getStatus(loc2)!=0){
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_ON);
        }else {
            inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
        }
        inv.addMenuClickHandler(TOGGLE_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            if(MultiBlockService.getStatus(loc)==0){//not working
                if(loadLink(inv)){
                    AddUtils.sendMessage(player,"&a成功检测到目标传送门,成功建立超链接");

                    if(MultiBlockService.createNewHandler(loc,getBuilder(),getMultiBlockType(), OutputStream.getPlayerOut(player))){
                        inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_ON);
                        AddUtils.sendMessage(player,"&a成功激活传送门!");
                    }else {
                        AddUtils.sendMessage(player,"&c传送门结构不完整或者结构冲突!");
                        inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
                    }
                }else {
                    AddUtils.sendMessage(player,"&c目标传送门已损坏,请检查超链接");
                    inv.replaceExistingItem(TOGGLE_SLOT,TOGGLE_ITEM_OFF);
                }
            }else {//working toggle off
                MultiBlockService.deleteMultiBlock(loc,MultiBlockService.MANUALLY);
                AddUtils.sendMessage(player,"&a传送门成功关闭");
            }
            return false;
        }));
        DataCache.setCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
        inv.addMenuClickHandler(HOLOGRAM_SLOT,((player, i, itemStack, clickAction) -> {
            Location loc=inv.getLocation();
            int holoStatus=DataCache.getCustomData(inv.getLocation(),MultiBlockService.getHologramKey(),0);
            int statusCode=MultiBlockService.getStatus(loc);
            MultiBlockService.removeHologramSync(loc);
            if(statusCode==0){
                if(holoStatus==0){
                    AddUtils.sendMessage(player,"&a全息投影已切换至南北向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.NORTH, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,MultiBlockService.getHologramKey(),1);
                }else if(holoStatus==1){
                    AddUtils.sendMessage(player,"&a全息投影已切换至东西向!");
                    MultiBlockService.createHologram(loc,MBTYPE, MultiBlockService.Direction.WEST, MBID_TO_ITEM);
                    DataCache.setCustomData(loc,MultiBlockService.getHologramKey(),2);
                }else {
                    AddUtils.sendMessage(player,"&a全息投影已关闭!");
                }
            }
            updateMenu(inv,block,Settings.RUN);
            return false;
        }));
        updateMenu(inv,block,Settings.RUN);
    }
    public void processCore(Block b, BlockMenu menu){
        if(menu.hasViewer()){
            updateMenu(menu,b,Settings.RUN);
        }
    }
    public boolean preCondition(Block b,BlockMenu inv,SlimefunBlockData data){
        return inv.getItemInSlot(LINK_SLOT)!=null;
    }

    public void onBreak(BlockBreakEvent e, BlockMenu inv){
        if(inv!=null){
            Location loc=inv.getLocation();
            inv.dropItems(loc,LINK_SLOT);
        }
        super.onBreak(e,inv);

    }
}

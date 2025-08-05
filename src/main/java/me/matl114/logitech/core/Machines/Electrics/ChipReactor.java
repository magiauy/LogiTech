package me.matl114.logitech.core.Machines.Electrics;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.core.LogiTechSlimefunItemStacks;
import me.matl114.logitech.core.Cargo.Config.ChipCardCode;
import me.matl114.logitech.core.Machines.Abstracts.AbstractEnergyProcessor;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.MenuUtils;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemConsumer;
import me.matl114.logitech.utils.UtilClass.RecipeClass.EnergyProviderOperation;
import me.matl114.logitech.utils.Utils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;

public class ChipReactor extends AbstractEnergyProcessor {
    protected final int[] BORDER=new int[]{
            0,1,2,6,7,8,18,19,20,24,25,26
    };
    protected final int[] INPUT_BORDER=new int[]{
            3,5,21,23
    };
    protected final int[] OUTPUT_BORDER=new int[]{
            9,10,11,12,14,15,16,17
    };
    protected final int[] INPUT_SLOTS=new int[]{
            13
    };
    protected final int INFO_SLOT=4;
    protected final ItemStack INFO_ITEM=new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE,"&6当前机器信息");
    public int[] getInputSlots(){
        return INPUT_SLOTS;
    }
    public int[] getOutputSlots(){
        return OUTPUT_SLOTS;
    }



    protected final int[] OUTPUT_SLOTS=new int[0];
    protected double multiple;
    protected int time;
    public ChipReactor(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                     int energyBuffer,double multiple,int time) {
        super(category, item, recipeType, recipe, Material.TARGET,  energyBuffer,1,null);
        this.multiple=multiple;
        this.PROCESSOR_SLOT=22;
        this.time=time;
        ItemStack example=LogiTechSlimefunItemStacks.CHIP.clone();
        example.setItemMeta(ChipCardCode.getCard(114514));
        AddUtils.addGlow(this.processor.getProgressBar());
        this.setDisplayRecipes(Utils.list(
                AddUtils.getInfoShow("&f机制","&7当插入%s时".
                        formatted(Language.get("item.CHIP.name")),"&7机器读取其中二进制码对应数字","&7并按%f倍率发电%dtick".formatted(this.multiple,this.time)),
                example
        ));
    }
    public void addInfo(ItemStack stack){
        stack.setItemMeta(AddUtils.capacitorInfoAdd(stack,this.energybuffer).getItemMeta());
    }
    public void constructMenu(BlockMenuPreset preset){
        int[] border=BORDER;
        int len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=INPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getInputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }
        border=OUTPUT_BORDER;
        len= border.length;
        for (int i=0;i<len;i++){
            preset.addItem(border[i], ChestMenuUtils.getOutputSlotTexture(),ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(INFO_SLOT,INFO_ITEM,ChestMenuUtils.getEmptyClickHandler());
        preset.addItem(PROCESSOR_SLOT,MenuUtils.PROCESSOR_NULL,ChestMenuUtils.getEmptyClickHandler());


    }
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull SlimefunBlockData data){
        BlockMenu inv=data.getBlockMenu();
        //增加电力检测
        if(inv!=null && conditionHandle(null,inv)){
            EnergyProviderOperation currentOperation=processor.getOperation(l);
            if(currentOperation==null){
                ItemStack it= inv.getItemInSlot(INPUT_SLOTS[0]);
                if(it!=null){
                    ItemMeta meta=it.getItemMeta();
                    if(ChipCardCode.isConfig(meta)){
                        int code=ChipCardCode.getConfig(meta);
                        currentOperation=new EnergyProviderOperation(new ItemConsumer[0],this.time,(int)(code*multiple));
                        processor.startOperation(l,currentOperation);
                        it.setAmount(it.getAmount()-1);
                    }
                }

            }
            if(currentOperation==null){
                if(inv.hasViewer()){
                    inv.replaceExistingItem(INFO_SLOT,AddUtils.addLore(this.INFO_ITEM,
                            AddUtils.concat("&7当前不在发电")));

                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                return 0;
            }
            if(currentOperation.isFinished()){
                ItemConsumer[] var4=currentOperation.getResults();
                CraftUtils.forcePush(var4,inv,getOutputSlots(),CRAFT_PROVIDER);
                if(inv.hasViewer()){
                    inv.replaceExistingItem(PROCESSOR_SLOT, MenuUtils.PROCESSOR_NULL);
                }
                this.processor.endOperation(l);
            }
            else{
                if(inv.hasViewer()){
                    this.processor.updateProgressBar(inv, PROCESSOR_SLOT, currentOperation);

                }
                currentOperation.progress(1);

            }
            if(inv.hasViewer()){
                inv.replaceExistingItem(INFO_SLOT,AddUtils.addLore(this.INFO_ITEM,
                        AddUtils.concat("&7当前发电: ",String.valueOf(currentOperation.getEnergy()),"J/t")));
            }
            return currentOperation.getEnergy();

        }else return 0;
    }
}

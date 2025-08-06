package me.matl114.logitech.core.Cargo.WorkBench;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.matl114.logitech.Language;
import me.matl114.logitech.core.LogiTechSlimefunItemStacks;
import me.matl114.logitech.core.Blocks.AbstractBlock;
import me.matl114.logitech.core.Cargo.Config.CargoConfigCard;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Algorithms.PairList;
import me.matl114.logitech.utils.CraftUtils;
import me.matl114.logitech.utils.UtilClass.CargoClass.CargoConfigs;
import me.matl114.logitech.utils.UtilClass.ItemClass.ItemCounter;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CargoConfigurator extends AbstractBlock {
    protected final int[] BORDER=new int[]{
            0,1,2,3,5,6,7,8,27,28,34,35
    };
    protected final int[] INFO_SLOTS=new int[]{
            4,9,10,11,12,13,14,15,16,17,30,32
    };
    protected final int[] CONFIG_SLOTS=new int[]{
            18,19,20,21,22,23,24,25,26,29,33
    };
    protected final int OUTPUT_SLOT=31;
    protected final ItemStack[] INFO_ITEMS=new ItemStack[]{
            new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
                    "&6点击构建货运配置卡","&e将任意货运配置卡置于下方槽位","&7并在对应的配置槽位中放入指定物品",
                    "&a若配置卡上已有配置,只有非空配置槽位的配置会覆盖原有配置项!",
                   "&c若配置卡上没有配置, 留空将默认false/0",
                   "&a点击这个按钮,即可进行配置",
                    "&e支持一次配置一组配置卡"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 强对称 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否使用强对称传输","&7即是否将物品按槽位进行对应的运输"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 仅空运输 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否使用仅空传输","&7即是否只传向空槽位"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 懒惰模式 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否使用懒惰模式","&7即是否在传输一次后停止"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 白名单 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否使用白名单","&7即是否将物品列表视为白名单,默认黑名单"),

            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 逆向传输 的配置槽位",
            "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否使用逆向传输","&7交换原有的目标方块和源方块!即:","&7⇨ 使用目标方块模式&e从目标方块抓取","&7⇨ 使用源方块模式向源方块推送","&7⇨ &e运输模式会随着方块转换"),
            //Modified
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 源方块槽位优先级 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置源方块模式(抓取)","&7若该项为True,则优先从源方块输入槽进行货运","&7蕴含最终源方块模式 &e输入槽 &7和 &e输入槽+输出槽","&7若该项为False,则优先从源方块输出槽进行货运","&7蕴含最终源方块模式 &e输出槽 &7和 &e输出槽+输入槽"),

            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 目标方块槽位优先级 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置目标方块模式(推送)","&7若该项为True,则优先从目标方块输出槽进行货运","&7蕴含最终目标方块模式 &e输出槽 &7和 &e输出槽+输入槽","&7若该项为False,则优先从目标方块输入槽进行货运","&7蕴含最终目标方块模式 &e输入槽 &7和 &e输入槽+输出槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 源方块++ 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否启用源方块++模式","&7该模式为True时,货运会同时涉及输入槽和输出槽","&7++模式且抓取输入槽优先:True时,最终源方块模式为 &e输入槽+输出槽",
                    "&7++模式且抓取输入槽优先:False,最终源方块模式为 &e输出槽+输入槽"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 目标方块++ 的配置槽位",
                    "&e将[%s]或[%s]置于下方".formatted(Language.get("item.boolean_true.name"),Language.get("item.boolean_false.name")),"&7配置是否启用目标方块++模式","&7该模式为True时,货运会同时涉及输入槽和输出槽","&7++模式且推送输出槽优先:True时,最终目标方块模式为 &e输出槽+输入槽",
                    "&7++模式且推送输出槽优先:False,最终目标方块模式为 &e输入槽+输出槽"),


            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 传输数量限制x64 的配置槽位",
                    "&e将[%s]置于左侧".formatted(Language.get("item.logic_cargo_unit.name")),"&7配置传输数量","&a传输数量增加<物品数量>*64"),
            new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, "&6配置 传输数量限制x1 的配置槽位",
                    "&e将[%s]置于右侧".formatted(Language.get("item.logic_cargo_unit.name")),"&7配置传输数量","&a传输数量增加<物品数量>"),
    };
    protected final ItemStack TIPS_ITEM=AddUtils.randItemStackFactory(
            new PairList(){{
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),400);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7强对称模式很强大,或许你会很好的利用它"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7懒惰模式会在首次传输某槽位后终止传输"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7请不要让传输双方的目标槽位重叠,除非你想丢物品!"),800);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7若无特殊需求,尽量不要放置黑白名单,这有助于服务器健康"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7货运配置卡可以同时配置一组!"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7已经配置好的配置卡可以放入配置槽重配,这将覆盖之前的设置"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7将仅空模式设置为true可以减少机器运行开销"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7逆向模式看起来似乎没什么用,不过你可以在链式运输中使用它"),200);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7本附属通用货运方法由matl114设计","若有疑问可以寻找作者"),100);
                put(new CustomItemStack(Material.KNOWLEDGE_BOOK,"&a小tips","&7最有效的配置是除了仅空模式以外全设置false,同时调制合适的传输数量"),100);
                put(AddUtils.addGlow( new CustomItemStack(Material.KNOWLEDGE_BOOK,"&x&E&B&3&3&E&B小彩蛋","&b恭喜您抽中了1/3600概率的彩蛋","&b想来您今天的运势一定很好!")),1);

            }}
    );
    protected final int TIPS_SLOT=35;
    protected ItemCounter MATCHED_CARGO=CraftUtils.getConsumer(LogiTechSlimefunItemStacks.CARGO_PART);
    public CargoConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }
    public int[] getConfigureSlots(){
        return CONFIG_SLOTS;
    }
    public int getConfigCardSlot(){
        return OUTPUT_SLOT;
    }

    public void constructMenu(BlockMenuPreset preset){
        int[] slot=BORDER;
        int len=slot.length;
        for (int i=0;i<len;i++){
            preset.addItem(slot[i], ChestMenuUtils.getBackground(),ChestMenuUtils.getEmptyClickHandler());
        }
        slot=INFO_SLOTS;
        len=slot.length;
        for (int i=0;i<len;++i){
            preset.addItem(slot[i],INFO_ITEMS[i],ChestMenuUtils.getEmptyClickHandler());
        }
    }
    public boolean craft(BlockMenu inv,Player player){
        ItemStack it=inv.getItemInSlot(getConfigCardSlot());
        if(it==null){
            AddUtils.sendMessage(player,"&c配置失败,请放入货运配置卡");
            return false;
        }
        int[] slots=getConfigureSlots();
        ItemMeta meta=it.getItemMeta();
        if(CargoConfigCard.canConfig(meta)){
            int code=0;
            if(CargoConfigCard.isConfig(meta)){
                code=CargoConfigCard.getConfig(meta);
            }
            ItemStack stack;
            CargoConfigs[] configs=CargoConfigs.values();
            for (int i=0;i<9;++i){
               stack=inv.getItemInSlot(slots[i]);
                if(stack==null){
                    continue;
                }
                if(stack.getType()==Material.MUSIC_DISC_5&&stack.hasItemMeta()){
                    //有附魔是true 没是false
                    int icode=stack.getEnchantments().isEmpty()?0:1;
                   code=  configs[i].setConfig(code,icode);//CargoConfigs.setConfigBit(code,icode,i);
                }else {
                    AddUtils.sendMessage(player,"&c配置失败,这不是布尔组件");
                    return false;
                }
            }
            stack=inv.getItemInSlot(slots[9]);
            if(stack!=null&&stack.getType()!=Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE){
                AddUtils.sendMessage(player,"&c配置失败,配置传输数量限制的物品不符");
                return false;
            }
            int stackAmount=0;
            if(stack!=null){
                stackAmount+=(stack.getAmount())*64;
            }
            stack=inv.getItemInSlot(slots[10]);
            if(stack!=null&&stack.getType()!=Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE){
                AddUtils.sendMessage(player,"&c配置失败,配置传输数量限制的物品不符");
                return false;
            }
            if(stack!=null){
                stackAmount+=stack.getAmount();
            }
            if(stackAmount!=0){
                code=CargoConfigs.TRANSLIMIT.setConfig(code,stackAmount);
            }
            if(CargoConfigs.TRANSLIMIT.getConfigInt(code)<=0){
                AddUtils.sendMessage(player,"&c配置失败,传输数量限制不能为0!");
                return false;
            }
            CargoConfigCard.setConfig(meta,code);
            it.setItemMeta(meta);
            for (int slot : slots) {
                inv.replaceExistingItem(slot, null);
            }
            return true;
        }
        AddUtils.sendMessage(player,"&c配置失败,请放入货运配置卡");
        return false;
    }
    public void newMenuInstance(BlockMenu inv, Block block){
        inv.addMenuOpeningHandler(player -> {
            inv.replaceExistingItem(TIPS_SLOT,TIPS_ITEM.clone());
        });
        inv.addMenuClickHandler(INFO_SLOTS[0],((player, i, itemStack, clickAction) -> {
            if(craft(inv,player)){
                AddUtils.sendMessage(player,"&a配置成功!");
            }
            return false;
        }));
    }
    public void onBreak(BlockBreakEvent event, BlockMenu menu) {
        super.onBreak(event, menu);
        if(menu!=null){
            Location loc =menu.getLocation();
            menu.dropItems(loc, getConfigureSlots());
            menu.dropItems(loc,getConfigCardSlot());
        }
    }
    public void preRegister() {
        super.preRegister();
        this.registerBlockMenu(this);
    }
}

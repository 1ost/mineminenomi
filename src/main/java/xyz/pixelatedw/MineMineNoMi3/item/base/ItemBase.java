package xyz.pixelatedw.MineMineNoMi3.item.base;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.lists.ListCreativeTabs;

public class ItemBase extends Item {

    protected String name;

    public ItemBase(String name) {
        super();
        this.name = name;
        setCreativeTab(ListCreativeTabs.tabWeapons);
        setUnlocalizedName(ID.PROJECT_ID + "." + this.name);
        setRegistryName(new ResourceLocation(ID.PROJECT_ID, this.name));
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName();
    }


    public String getDescriptionText() {
        return MainMod.proxy.translate(ID.PROJECT_ID + "." + this.name + ".description");

    }


    public void registerItemModel() {
        MainMod.proxy.registerItemRenderer(this, 0, name);
    }
}

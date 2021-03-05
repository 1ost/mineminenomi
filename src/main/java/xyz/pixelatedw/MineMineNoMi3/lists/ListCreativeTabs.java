package xyz.pixelatedw.MineMineNoMi3.lists;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import xyz.pixelatedw.MineMineNoMi3.api.WyRegistry;

public class ListCreativeTabs 
{
	
	public static void init()
	{
		WyRegistry.registerName("itemGroup.tab1", "Devil Fruits");
		WyRegistry.registerName("itemGroup.tab2", "Equipment");
		WyRegistry.registerName("itemGroup.tab3", "Miscellaneous");
	}
	
	public static CreativeTabs tabDevilFruits = new CreativeTabs("tab1") {
	    @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(ListDevilFruits.MeraMeraNoMi, 1, 0);
	    }
	};
	
	public static CreativeTabs tabWeapons = new CreativeTabs("tab2") {
	    @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(ListMisc.Yoru, 1, 0);
	    }
	};
	
	public static CreativeTabs tabMisc = new CreativeTabs("tab3") {
	    @SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
	        return new ItemStack(ListMisc.Kairoseki);
	    }
	};

}

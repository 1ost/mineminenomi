package xyz.pixelatedw.MineMineNoMi3.item.devilfruitextras;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class Shadow extends Item
{

    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
    	ExtendedEntityData props = ExtendedEntityData.get(player);

    	props.setHasShadow(true);

    	itemStack.setCount(itemStack.getCount() -1);
		
		return itemStack;
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }
    
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.DRINK;
    }
        	/*UNCOMMENT

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	ExtendedEntityData props = ExtendedEntityData.get(player);

    	if(!world.isRemote && !props.hasShadow())

    		player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }*/
}
package xyz.pixelatedw.MineMineNoMi3.items;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;

public class ItemVivreCard extends Item
{

	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
	{
		if(itemStack.getTagCompound() != null)
		{
			EntityLivingBase entity = WyHelper.getEntityByUUID(player.world, UUID.fromString(itemStack.getTagCompound().getString("owner")));
			
			if(entity != null)
			{
				list.add(TextFormatting.GOLD + "[Owner] " + TextFormatting.RESET + entity.getCommandSenderName());
				list.add(TextFormatting.GOLD + "[Location] " + TextFormatting.RESET + (int)entity.posX + "X " + (int)entity.posY + "Y " + (int)entity.posZ +"Z");
				list.add(TextFormatting.GOLD + "[HP] " + TextFormatting.RESET + entity.getHealth());
			}
		}
	}
	
    @Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) 
    {
    	this.setOwner(itemStack, player);
    	String itemName = itemStack.getDisplayName();
    	itemStack.setStackDisplayName(player.getName() + "'s " + itemName);
    }

	public void setOwner(ItemStack itemStack, EntityLivingBase e)
	{
		itemStack.setTagCompound(new NBTTagCompound());
		itemStack.getTagCompound().setString("owner", e.getUniqueID().toString());
	}
}

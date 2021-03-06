package xyz.pixelatedw.MineMineNoMi3.items.dials;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.ExtraProjectiles.AxeDialProjectile;
import xyz.pixelatedw.MineMineNoMi3.lists.ListExtraAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class DialAxe extends Item
{
	
	public DialAxe()
	{
		this.setMaxStackSize(16);
		this.setMaxDamage(2);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{		
		if(!world.isRemote)
		{
	    	if(!player.isSneaking())
	    	{
	    		AxeDialProjectile proj = new AxeDialProjectile(player.world, player, ListExtraAttributes.DIAL_AXE);
				
	    		world.spawnEntity(proj);
	    		
		    	if(!player.capabilities.isCreativeMode)
		    		WyTelemetry.addMiscStat("axeDialsUsed", "Axe Dials Used", 1);
	    		
				itemStack.damageItem(2, player);
	    	}	    
		}
		
		return itemStack;
	}
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3)
    {
    	if(!world.isRemote && player.isSneaking())
    	{
	    	world.setBlockState(new BlockPos(i1, i2 + 1, i3), (IBlockState) ListMisc.DialAxeBlock);
	    	itemStack.setCount(itemStack.getCount() -1);
    	}
        return false;
    }
}

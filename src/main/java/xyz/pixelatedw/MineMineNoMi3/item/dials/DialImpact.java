package xyz.pixelatedw.MineMineNoMi3.item.dials;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class DialImpact extends Item
{
	
	public DialImpact()
	{
		this.setMaxStackSize(16);
		this.setMaxDamage(1);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{		
		if(!world.isRemote)
		{
	    	if(!player.isSneaking())
	    	{
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1, 100));
				world.newExplosion(player, player.posX, player.posY, player.posZ, 3, false, MainConfig.enableGriefing);
				
		    	if(!player.capabilities.isCreativeMode)
		    		WyTelemetry.addMiscStat("impactDialsUsed", "Impact Dials Used", 1);
		    	
				itemStack.damageItem(2, player);
	    	}	    
		}
		
		return itemStack;
	}
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3)
    {
    	if(!world.isRemote && player.isSneaking())
    	{
			world.setBlockState(new BlockPos(i1, i2 + 1, i3), (IBlockState) ListMisc.DialImpactBlock);
			itemStack.setCount(itemStack.getCount() -1);
    	}
        return false;
    }

}
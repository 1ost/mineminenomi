package xyz.pixelatedw.MineMineNoMi3.item.dials;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class DialReject extends Item
{
	
	public DialReject()
	{
		this.setMaxStackSize(16);
		this.setMaxDamage(1);
	}
	
    @Override
	public boolean hitEntity(ItemStack itemStack, EntityLivingBase user, EntityLivingBase target)
    {
		if(!user.world.isRemote)
		{
	    	if(!user.isSneaking())
	    	{
				itemStack.damageItem(2, user);
	    		
				user.attackEntityFrom(DamageSource.GENERIC, Float.MAX_VALUE);
				target.attackEntityFrom(DamageSource.GENERIC, Float.MAX_VALUE);
				
		    	if(user instanceof EntityPlayer && !((EntityPlayer)user).capabilities.isCreativeMode)
		    		WyTelemetry.addMiscStat("rejectDialsUsed", "Reject Dials Used", 1);
				
				return true;
	    	}	    
		}
    	
        return false;
    }

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3)
    {
    	if(!world.isRemote && player.isSneaking())
    	{
			world.setBlockState(new BlockPos(i1, i2 + 1, i3), (IBlockState) ListMisc.DialRejectBlock);
			itemStack.setCount(itemStack.getCount() -1);
    	}
        return false;
    }
}
package xyz.pixelatedw.MineMineNoMi3.items.dials;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class DialFire extends Item
{
	
	public DialFire()
	{
		this.setMaxStackSize(16);
		this.setMaxDamage(4);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{		
		if(!world.isRemote)
		{
	    	if(!player.isSneaking())
	    	{
				Vec3d look = player.getLookVec();
				EntitySmallFireball fireball = new EntitySmallFireball(world, player, 1, 1, 1);
				fireball.setPosition(
						player.posX,
						player.posY + 1.5, 
						player.posZ);
				fireball.accelerationX = look.x * 0.2;
				fireball.accelerationY = look.y * 0.2;
				fireball.accelerationZ = look.z * 0.2;
				world.spawnEntity(fireball);
				
		    	if(!player.capabilities.isCreativeMode)
		    		WyTelemetry.addMiscStat("fireDialsUsed", "Fire Dials Used", 1);
				
				itemStack.damageItem(2, player);
	    	}	    
		}
		
		return itemStack;
	}
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3)
    {
    	if(!world.isRemote && player.isSneaking())
    	{
			world.setBlockState(new BlockPos(i1, i2 + 1, i3), (IBlockState) ListMisc.DialFireBlock);
			itemStack.setCount(itemStack.getCount() -1);
    	}
        return false;
    }
	
}

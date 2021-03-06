package xyz.pixelatedw.MineMineNoMi3.items.dials;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class DialEisen extends Item
{
	
	public DialEisen()
	{
		this.setMaxStackSize(16);
		this.setMaxDamage(1);
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i1, int i2, int i3, int i4, float f1, float f2, float f3)
    {
    	if(!world.isRemote && player.isSneaking())
    	{
			world.setBlockState(new BlockPos(i1, i2 + 1, i3), (IBlockState) ListMisc.DialEisenBlock);
			itemStack.setCount(itemStack.getCount() -1);
    	}
        return false;
    }
}

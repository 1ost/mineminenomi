package xyz.pixelatedw.MineMineNoMi3.item.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityWantedPoster;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class WantedPoster extends Item
{

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (!world.isRemote && !player.isSneaking() && itemStack.getTagCompound() != null)
			WyNetworkHelper.sendTo(new PacketPlayer("guiWantedPoster", itemStack), (EntityPlayerMP) player);

		return itemStack;
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int posX, int posY, int posZ, int blockFace, float f1, float f2, float f3)
	{
		if (player.isSneaking())
		{
			if (blockFace == 0 || blockFace == 1)
				return false;
			else
			{
				if (blockFace == 2)
					--posZ;
				if (blockFace == 3)
					++posZ;
				if (blockFace == 4)
					--posX;
				if (blockFace == 5)
					++posX;

				if (!Blocks.STANDING_SIGN.canPlaceBlockAt(world,new BlockPos(posX, posY, posZ)))
					return false;
				else
				{
					world.setBlockState(new BlockPos(posX, posY, posZ), (IBlockState) ListMisc.WantedPosterBlock);

					itemStack.setCount(itemStack.getCount() -1);
					TileEntityWantedPoster tileEntity = (TileEntityWantedPoster) world.getTileEntity(new BlockPos(posX, posY, posZ));

					if (tileEntity != null)
					{
						tileEntity.setEntityName(itemStack.getTagCompound().getString("Name"));
						tileEntity.setPosterBounty(itemStack.getTagCompound().getString("Bounty"));
						tileEntity.setBackground(itemStack.getTagCompound().getString("Background"));
						tileEntity.setIssuedDate(itemStack.getTagCompound().getString("Date"));
						tileEntity.markDirty();
					}

					return true;
				}
			}
		}
		return false;
	}
}

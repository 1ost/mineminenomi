package xyz.pixelatedw.MineMineNoMi3.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntitySakeFeast;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class BlockSakeFeast extends BlockContainer
{

	public BlockSakeFeast()
	{
		super(Material.CLAY);
		this.setHardness(1);
	}

	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		TileEntitySakeFeast sakeFeast = (TileEntitySakeFeast) world.getTileEntity(posX, posY, posZ);

		if (world.isRemote)
			return true;

		boolean isItem = player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ListMisc.SakeCup;
		boolean isCupFilledFlag = isItem && player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound() != null && player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getBoolean("IsFilled");
		boolean hasFills = sakeFeast.getAmount() > 0;
		if (isItem && !isCupFilledFlag && hasFills)
		{
			player.inventory.setInventorySlotContents(player.inventory.currentItem ,new ItemStack(ListMisc.SakeCup, 1, 0));
			player.getHeldItem(EnumHand.MAIN_HAND).setTagCompound(new NBTTagCompound());
			player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().setBoolean("IsFilled", true);
			sakeFeast.reduceAmount();
			return true;
		}

		return false;
	}
    
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntitySakeFeast();
	}
}
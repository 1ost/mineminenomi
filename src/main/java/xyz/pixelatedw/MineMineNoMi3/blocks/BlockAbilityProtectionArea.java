package xyz.pixelatedw.MineMineNoMi3.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;

public class BlockAbilityProtectionArea extends Block
{
	private static final AxisAlignedBB BOUNDING_BOX = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

	public BlockAbilityProtectionArea()
	{
		super(Material.IRON);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return BOUNDING_BOX;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return WyHelper.NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

		if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]))
		{
			return true;
		}

		if (block == this)
		{
			return false;
		}

		return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}
}

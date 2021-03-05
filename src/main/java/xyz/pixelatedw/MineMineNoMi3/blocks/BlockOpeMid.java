package xyz.pixelatedw.MineMineNoMi3.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityOpe;

public class BlockOpeMid extends BlockContainer
{
	private TileEntityOpe tile;
	
	public BlockOpeMid()
	{
		super(Material.IRON);
	} 

	public TileEntity createNewTileEntity(World world, int i) 
	{
		tile = new TileEntityOpe();
		return tile;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {return WyHelper.NULL_AABB;} 
	
	public boolean isOpaqueCube() {return false;}

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 0; }

    public boolean renderAsNormalBlock() { return false; }

    public void clearRoom()
    {
    	if(tile != null)
    	{
    		tile.clearRoom();
    	}
    }
}


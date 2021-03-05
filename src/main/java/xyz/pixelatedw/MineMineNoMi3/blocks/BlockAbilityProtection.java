package xyz.pixelatedw.MineMineNoMi3.blocks;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityAbilityProtection;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;

public class BlockAbilityProtection extends BlockContainer
{

	public BlockAbilityProtection()
	{
		super(Material.IRON);
		this.setTickRandomly(true);
	}

	public void onBlockDestroyedByPlayer(World world, int posX, int posY, int posZ, int metaData)
    {
    	ExtendedWorldData worldData = ExtendedWorldData.get(world);

    	worldData.removeRestrictedArea(posX, posY, posZ);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int i) { return new TileEntityAbilityProtection(); }
	
    
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{return WyHelper.NULL_AABB;}
	
	public boolean isOpaqueCube() {return false;}

	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }

	public boolean renderAsNormalBlock() { return false; }
      
	public int getRenderType() { return 0; }

}

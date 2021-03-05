package xyz.pixelatedw.MineMineNoMi3.blocks;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityCustomSpawner;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketWorld;

public class BlockCustomSpawner extends BlockContainer
{
	public BlockCustomSpawner()
	{
		super(Material.IRON);
		this.setTickRandomly(true);
	} 

	public TileEntity createNewTileEntity(World world, int i) { return new TileEntityCustomSpawner();}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {return WyHelper.NULL_AABB;} 
	
	public boolean isOpaqueCube() {return false;}

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }

    public boolean renderAsNormalBlock() { return false; }
      
    public int getRenderType() { return 0; }
    
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
    	if(world.getBlock(x, y - 1, z) == Blocks.AIR)
    		WyNetworkHelper.sendToServer(new PacketWorld(x, y, z, Block.getIdFromBlock(Blocks.AIR)));
	}
}

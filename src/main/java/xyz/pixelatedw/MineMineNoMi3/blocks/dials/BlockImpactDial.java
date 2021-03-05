package xyz.pixelatedw.MineMineNoMi3.blocks.dials;

import java.util.Random;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityImpactDial;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class BlockImpactDial extends BlockContainer
{

	public BlockImpactDial()
	{
		super(Material.IRON);
		this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.425F, 0.75F);
	}  

	public boolean isOpaqueCube() {return false;}

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() { return 1; }

    public boolean renderAsNormalBlock() { return false; }
	
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
    	return true;
    }
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
    	if(entity instanceof EntityLivingBase)
    	{
    		world.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 3, false, true);
    		world.setBlock(x, y, z, Blocks.AIR);
    	}
    }
    
    public Item getItemDropped(int i1, Random rand, int fortune)
    {
        return ListMisc.DialImpact;
    }
    
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        return true;
    }
    
	public int getRenderType() { return -1; }
    
	public TileEntity createNewTileEntity(World wolrd, int i)
	{
		return new TileEntityImpactDial();
	}

}
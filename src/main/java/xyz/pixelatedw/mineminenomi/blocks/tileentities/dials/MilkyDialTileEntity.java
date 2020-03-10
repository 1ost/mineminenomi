package xyz.pixelatedw.mineminenomi.blocks.tileentities.dials;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.wypi.WyRegistry;

public class MilkyDialTileEntity extends TileEntity
{
	public static final TileEntityType TILE_ENTITY = WyRegistry.registerTileEntity("milky_dial_block", MilkyDialTileEntity::new, ModBlocks.MILKY_DIAL);
	
	public MilkyDialTileEntity()
	{
		super(TILE_ENTITY);
	}

}

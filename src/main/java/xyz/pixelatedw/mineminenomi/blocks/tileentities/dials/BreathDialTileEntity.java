package xyz.pixelatedw.mineminenomi.blocks.tileentities.dials;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.wypi.WyRegistry;

public class BreathDialTileEntity extends TileEntity
{
	public static final TileEntityType TILE_ENTITY = WyRegistry.registerTileEntity("breath_dial_block", BreathDialTileEntity::new, ModBlocks.BREATH_DIAL);
	
	public BreathDialTileEntity()
	{
		super(TILE_ENTITY);
	}

}

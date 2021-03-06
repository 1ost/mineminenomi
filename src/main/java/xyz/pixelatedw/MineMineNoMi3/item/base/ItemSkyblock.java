package xyz.pixelatedw.MineMineNoMi3.item.base;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemSkyblock extends ItemBlock
{

	public ItemSkyblock(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		
	}

    @Override
	public int getMetadata(int meta)
    {
        return meta;
    }
}

package xyz.pixelatedw.MineMineNoMi3.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;

public class BountyHelper
{

	public static boolean issueBountyForPlayer(EntityPlayer player)
	{
		ExtendedWorldData worldData = ExtendedWorldData.get(player.world);
		ExtendedEntityData props = ExtendedEntityData.get(player);
		
		if((props.isPirate() || props.isRevolutionary()) && props.getBounty() > 1000)
		{
			worldData.issueBounty(player.getCommandSenderEntity().getName(), props.getBounty());
			return true;
		}
		
		return false;
	}
	
	public static void issueBountyForAllPlayers(World world)
	{
		ExtendedWorldData worldData = ExtendedWorldData.get(world);
		
		for(Object entity : world.loadedEntityList)
		{
			if(entity instanceof EntityPlayer)
				issueBountyForPlayer((EntityPlayer) entity);
		}
	}
	
}

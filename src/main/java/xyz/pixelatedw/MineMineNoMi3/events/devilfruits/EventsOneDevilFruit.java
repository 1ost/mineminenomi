package xyz.pixelatedw.MineMineNoMi3.events.devilfruits;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;

public class EventsOneDevilFruit
{
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if(props.hasDevilFruit())
			{
				ExtendedWorldData worldData = ExtendedWorldData.get(player.world);				
				worldData.removeDevilFruitFromWorld(props.getUsedFruit());
			}
		}	
	}

	@SubscribeEvent
	public void onDFItemExpires(ItemExpireEvent event)
	{
		if(event.getEntityItem().getItem().getItem() instanceof AkumaNoMi)
		{
			ExtendedWorldData worldData = ExtendedWorldData.get(event.getEntity().world);
			
			worldData.removeDevilFruitFromWorld((AkumaNoMi) event.getEntityItem().getItem().getItem());
		}
	}
	
}

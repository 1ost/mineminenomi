package xyz.pixelatedw.MineMineNoMi3.events;

import java.util.Random;

import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.Values;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.items.AkumaNoMi;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class EventsDrops
{

	@SubscribeEvent
    public void onInteractEvent(PlayerInteractEvent event)
    {
		if(event.getEntity() instanceof EntityLivingBase)
		{
			ExtendedEntityData props = ExtendedEntityData.get(event.getEntityPlayer());
			ExtendedEntityData propz = ExtendedEntityData.get((EntityLivingBase) event.getEntity());
			ItemStack heldItem = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
			
			if(props.getUsedFruit().equalsIgnoreCase("kagekage") && propz.hasShadow() && heldItem != null && heldItem.getItem() == ListMisc.Scissors)
			{
				propz.setHasShadow(false);
				event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(ListMisc.Shadow));
			}
		}
    }
	
	@SubscribeEvent	
	public void onBreak(BreakEvent event)
	{
		if(MainConfig.enableDFtoDrop && (event.block == Blocks.LEAVES || event.block == Blocks.LEAVES2))
		{
			Random rand = new Random();
			double chance = rand.nextInt(99) + rand.nextDouble();
			boolean isAvailable = true;
			
			if( chance < MainConfig.rateDFDrops )
			{
				AkumaNoMi df = Values.devilfruits.get(rand.nextInt(Values.devilfruits.size()));
				
				if(MainConfig.enableOneFruitPerWorld)
				{
					ExtendedWorldData worldProps = ExtendedWorldData.get(event.getWorld());
					int chanceForNewFruit = 0;
					while(DevilFruitsHelper.isDevilFruitInWorld(event.getWorld(), df))
					{
						if(chanceForNewFruit >= 10)
						{
							isAvailable = false;
							break;
						}
						df = Values.devilfruits.get(rand.nextInt(Values.devilfruits.size()));
						chanceForNewFruit++;
					}

					if(isAvailable)
						worldProps.addDevilFruitInWorld(df);
				}
				
				if(isAvailable)
					event.getPlayer().inventory.addItemStackToInventory(new ItemStack(df));
			}
			
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{	
			EntityPlayer player = ((EntityPlayer)event.getEntityLiving());

			WyHelper.removeStackFromInventory(player, new ItemStack(ListMisc.CharacterCreator));
		}
	}
	
}
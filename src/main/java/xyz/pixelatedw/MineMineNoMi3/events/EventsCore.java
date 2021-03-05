package xyz.pixelatedw.MineMineNoMi3.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.debug.WyDebug;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;
import xyz.pixelatedw.MineMineNoMi3.data.HistoryProperties;

public class EventsCore
{
	
	// Registering the extended properties
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) 
	{
		if (event.getEntity() instanceof EntityLivingBase && ExtendedEntityData.get((EntityLivingBase) event.getEntity()) == null)
			ExtendedEntityData.register((EntityLivingBase) event.getEntity());

		if (event.getEntity() instanceof EntityPlayer)
		{
			if(QuestProperties.get((EntityPlayer) event.getEntity()) == null)
				QuestProperties.register((EntityPlayer) event.getEntity());
			if(AbilityProperties.get((EntityPlayer) event.getEntity()) == null)
				AbilityProperties.register((EntityPlayer) event.getEntity());
			if(HistoryProperties.get((EntityPlayer) event.getEntity()) == null)
				HistoryProperties.register((EntityPlayer) event.getEntity());
		}
	}
	
	// Cloning the player data to the new entity based on the config option
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone e) 
	{
		if(e.getEntity().isDead) 
		{		
	    	ExtendedWorldData worldProps = ExtendedWorldData.get(e.getEntity().world);

			ExtendedEntityData oldPlayerProps = ExtendedEntityData.get(e.getOriginal());	
			ExtendedEntityData newPlayerProps = ExtendedEntityData.get(e.getEntityPlayer());

			//WyNetworkHelper.sendTo(new PacketNewAABB(0.6F, 1.8F), (EntityPlayerMP) e.getEntityPlayer());
			
			if(MainConfig.enableKeepIEEPAfterDeath.equals("full"))
			{
				NBTTagCompound compound = new NBTTagCompound();
				
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.getOriginal());
				oldProps.saveNBTData(compound);
				oldProps.triggerActiveHaki(false);
				oldProps.triggerBusoHaki(false);
				oldProps.triggerKenHaki(false);
				oldProps.setGear(1);
				oldProps.setZoanPoint("n/a");
				ExtendedEntityData props = ExtendedEntityData.get(e.getEntityPlayer());
				props.loadNBTData(compound);				
				
				compound = new NBTTagCompound();
				AbilityProperties.get(e.getOriginal()).saveNBTData(compound);
				AbilityProperties abilityProps = AbilityProperties.get(e.getEntityPlayer());
				abilityProps.loadNBTData(compound);
				
				if(e.getEntityPlayer() != null && MainConfig.enableExtraHearts)		
				{
					IAttributeInstance maxHp = e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
								
					if(props.getDoriki() / 100 <= 20)
						e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
					else
						e.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(props.getDoriki() / 100);
				}
			}
			else if(MainConfig.enableKeepIEEPAfterDeath.equals("auto"))
			{
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.getOriginal());
				
				String faction = oldProps.getFaction();
				String race = oldProps.getRace();
				String fightStyle = oldProps.getFightStyle();
				String crew = oldProps.getCrew();
				int doriki = MathHelper.ceil(WyMathHelper.percentage(MainConfig.dorikiKeepPercentage, oldProps.getDoriki()));
				int bounty = MathHelper.ceil(WyMathHelper.percentage(MainConfig.bountyKeepPercentage, oldProps.getBounty()));
				int belly = MathHelper.ceil(WyMathHelper.percentage(MainConfig.bellyKeepPercentage, oldProps.getBelly()));

				worldProps.removeDevilFruitFromWorld(oldProps.getUsedFruit());
				
				ExtendedEntityData props = ExtendedEntityData.get(e.getEntityPlayer());
				props.setFaction(faction);
				props.setRace(race);
				props.setFightStyle(fightStyle);
				props.setCrew(crew);			
				props.setMaxCola(100);
				props.setCola(oldProps.getMaxCola());
				props.setDoriki(doriki);
				props.setBounty(bounty);
				props.setBelly(belly);
				
			}
			else if(MainConfig.enableKeepIEEPAfterDeath.equals("custom"))
			{
				ExtendedEntityData oldProps = ExtendedEntityData.get(e.getOriginal());
				ExtendedEntityData props = ExtendedEntityData.get(e.getEntityPlayer());

				for(String stat : MainConfig.statsToKeep)
				{
					switch(WyHelper.getFancyName(stat))
					{
						case "doriki":
							int doriki = MathHelper.ceil(WyMathHelper.percentage(MainConfig.dorikiKeepPercentage, oldProps.getDoriki()));
							props.setDoriki(doriki); 
							break;
						case "bounty":
							int bounty = MathHelper.ceil(WyMathHelper.percentage(MainConfig.bountyKeepPercentage, oldProps.getBounty()));
							props.setBounty(bounty); 
							break;
						case "belly":
							int belly = MathHelper.ceil(WyMathHelper.percentage(MainConfig.bellyKeepPercentage, oldProps.getBelly()));
							props.setBelly(belly); 
							break;
						case "race":
							props.setRace(oldProps.getRace()); break;
						case "faction":
							props.setFaction(oldProps.getFaction()); break;
						case "fightingstyle":
							props.setFightStyle(oldProps.getFightStyle()); break;
						case "devilfruit":
							props.setUsedFruit(oldProps.getUsedFruit()); break;
					}
				}
				
				if(WyHelper.isNullOrEmpty(props.getUsedFruit()))
					worldProps.removeDevilFruitFromWorld(oldProps.getUsedFruit());
			}
			
			NBTTagCompound compound = new NBTTagCompound();
			QuestProperties.get(e.getOriginal()).saveNBTData(compound);
			QuestProperties questProps = QuestProperties.get(e.getEntityPlayer());
			questProps.loadNBTData(compound);
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event)
	{
		if(!WyDebug.isDebug())
		{
			WyTelemetry.addMiscStat("onlinePlayers", "Online Players", 1);
			WyTelemetry.sendAllData();
		}
	}
	
	@SubscribeEvent
	public void onPlayerLoggedOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		if(!WyDebug.isDebug())
		{
			WyTelemetry.addMiscStat("onlinePlayers", "Online Players", -1);
			WyTelemetry.sendAllDataSync();
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END && event.side == Side.SERVER)
		{
			if(event.player.world.getWorldTime() % 1200 == 0)
			{
				WyTelemetry.sendAllData();
			}
		}
	}
}

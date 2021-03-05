package xyz.pixelatedw.MineMineNoMi3.events;

import java.util.HashMap;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.EntityNewMob;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarine;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.helpers.BountyHelper;

public class EventsBounty
{

	private HashMap<EntityPlayer, double[]> cachedPositions = new HashMap<EntityPlayer, double[]>();
	
	private void spawnAmbush(EntityPlayer player, int repeats, Class<? extends EntityNewMob> clz)
	{
		for(int i = 0; i < repeats; i++)
		{
			int posX = (int) (player.posX + WyMathHelper.randomWithRange(-20, 20));
			int posZ = (int) (player.posZ + WyMathHelper.randomWithRange(-20, 20));
			
			if(player.getDistance(posX, player.posY, posZ) < 5)
			{
				posX = 5;
				posZ = 5;
			}
			
			try
			{
				EntityNewMob mobToSpawn = clz.getDeclaredConstructor(World.class).newInstance(player.world);
				mobToSpawn.setAttackTarget(player);
				mobToSpawn.onSpawnWithEgg((IEntityLivingData)null);
				mobToSpawn.setPositionAndRotation(posX, player.posY, posZ, 180, 0);
				player.world.spawnEntity(mobToSpawn);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{		
		if(event.phase == TickEvent.Phase.END && event.side == Side.SERVER)
		{
			EntityPlayer player = event.player;
			ExtendedEntityData props = ExtendedEntityData.get(player);
			
			// Ambushes
			if(MainConfig.enableAmbushes)
			{
				boolean canBeAmbushed = false;
				int bountyLevel = (int) (props.getBounty() / 100000);	
				
				if(props.isPirate() || props.isRevolutionary() && bountyLevel > 0)
				{
					if(player.world.isDaytime())
					{
						float lightLevel = player.getBrightness();
						boolean canSeeSky = player.world.canBlockSeeSky(new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ));
		
						if(player.ticksExisted % MainConfig.rateAmbushesSpawn == 0 && lightLevel > 0.9 && canSeeSky)
						{
							// true = marines; false = bounty hunters
							boolean spawnType = player.world.rand.nextBoolean();
							
							if(bountyLevel <= 6)
							{
								if(spawnType)
								{
									int lowLevelMobs = bountyLevel < 4 ? bountyLevel * 3 : bountyLevel * 2;
									this.spawnAmbush(player, lowLevelMobs, EntityMarine.class);
								}
								else
								{
									
								}
							}
							else if(bountyLevel > 6)
							{
								if(spawnType)
								{
									int lowLevelMobs = bountyLevel < 9 ? player.world.rand.nextInt(10) + 5 : player.world.rand.nextInt(10) + 10;
									this.spawnAmbush(player, lowLevelMobs, EntityMarine.class);
													
									int mediumLevelMobs = bountyLevel < 9 ? 1 : player.world.rand.nextInt(2) + 1;
									this.spawnAmbush(player, mediumLevelMobs, EntityMarineCaptain.class);
								}
								else
								{
									
								}
							}
						}
					}
				}
			}

			// Bounty poster drops
			if(MainConfig.enableWantedPostersPackages)
			{
				double currentPosX = player.posX;
				double currentPosZ = player.posZ;

				// Every ~15 minutes, by default
				if(player.ticksExisted % MainConfig.rateWantedPostersPackagesSpawn == 0)
				{
					if(!this.cachedPositions.containsKey(player))
						this.cachedPositions.put(player, new double[] {currentPosX, currentPosZ});
					else
					{
						double[] positions = this.cachedPositions.get(player);
						double cachedPosX = positions[0];
						double cachedPosZ = positions[1];
						
						boolean flagPosX = Math.abs(currentPosX - cachedPosX) > 100;
						boolean flagPosZ = Math.abs(currentPosZ - cachedPosZ) > 100;						
						
						if(flagPosX || flagPosZ)
						{						
							if(BountyHelper.issueBountyForPlayer(event.player))
							{				
								EntityWantedPostersPackage pkg = new EntityWantedPostersPackage(player.world);
								pkg.setLocationAndAngles(player.posX + WyMathHelper.randomWithRange(-10, 10), player.posY + 30, player.posZ + WyMathHelper.randomWithRange(-10, 10), 0, 0);
								player.world.spawnEntity(pkg);
							}
							
							this.cachedPositions.remove(player);					
							this.cachedPositions.put(player, new double[] {currentPosX, currentPosZ});
						}
					}
				}
			}
		}
	}
	
	
}

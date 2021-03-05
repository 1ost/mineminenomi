package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.WorldServer;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.effects.DFEffect;
import xyz.pixelatedw.MineMineNoMi3.abilities.effects.DFEffectZushiAbareHimatsuri;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.JuryoProjectiles;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.helpers.ItemsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class JuryoAbilities
{
	public static Ability[] abilitiesArray = new Ability[] {new SagariNoRyusei(), new Juryoku(), new Moko(), new AbareHimatsuri()};
	
	public static class AbareHimatsuri extends Ability
	{
		private DFEffect extra = null;
		
		public AbareHimatsuri() 
		{
			super(ListAttributes.ABARE_HIMATSURI); 
		}

		@Override
		public void passive(EntityPlayer player) 
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if(!player.capabilities.isFlying && player.onGround)
			{
				if(extra == null)
					extra = new DFEffectZushiAbareHimatsuri(player, 99999);
				else
				{
					extra.forceStop();
					extra = null;
				}
				super.passive(player);
			}
			else if(!player.onGround)
			{
				if(extra != null)
				{
					extra.forceStop();
					extra = null;
				}
				super.passive(player);
			}
		}
		
		@Override
		public void startPassive(EntityPlayer player)
		{
			
		}
		
		@Override
		public void endPassive(EntityPlayer player)
		{
			
		}
	}
	
	public static class Moko extends Ability
	{
		public Moko() 
		{
			super(ListAttributes.MOKO); 
		}
		
		@Override
		public void use(EntityPlayer player)
		{
			if(!this.isOnCooldown())
			{
				if (!ItemsHelper.isSword(player.getHeldItem(EnumHand.MAIN_HAND)))
				{
					WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
					return;
				}
				
				for(int j = 0; j < 50; j++)
				{
					AbilityProjectile proj = new JuryoProjectiles.Moko(player.world, player, ListAttributes.MOKO);
	
					proj.setLocationAndAngles(
							player.posX + WyMathHelper.randomWithRange(-5, 5) + player.world.rand.nextDouble(), 
							(player.posY + 0.3) + WyMathHelper.randomWithRange(0, 5) + player.world.rand.nextDouble(), 
							player.posZ + WyMathHelper.randomWithRange(-5, 5) + player.world.rand.nextDouble(), 
							0, 0);
					player.world.spawnEntity(proj);
				}
				if (player.world instanceof WorldServer)
					((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
				super.use(player);
			}
		}
	}
	
	public static class Juryoku extends Ability
	{
		public Juryoku() 
		{
			super(ListAttributes.JURYOKU); 
		}

		@Override
		public void duringPassive(EntityPlayer player, int passiveTimer) 
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);
			if(passiveTimer > 400)
			{
				this.setPassiveActive(false);
				this.startCooldown();
				this.startExtUpdate(player);
			}
			
			for(EntityLivingBase entity : WyHelper.getEntitiesNear(player, 10))
			{
				entity.motionX = 0;
				entity.motionZ = 0;
				entity.motionY -= 5;
				entity.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 100, 10));
				
				if(++passiveTimer % 100 == 0)
				{
					entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 8 * props.getDamageMultiplier());	
					if(MainConfig.enableGriefing)
					{
						for(int x = -2; x < 2; x++)
						for(int z = -2; z < 2; z++)
						{
							int posX = (int)entity.posX + x;
							int posY = (int)entity.posY - 1;
							int posZ = (int)entity.posZ + z;
							
							DevilFruitsHelper.placeBlockIfAllowed(player.world, posX, posY, posZ, Blocks.AIR, "all", "restricted", "ignore liquid");
						}
					}
				}
			}	
		}
		
		@Override
		public void endPassive(EntityPlayer player) 
		{
			this.startCooldown();
			this.startExtUpdate(player);
		}

	}
	
	public static class SagariNoRyusei extends Ability
	{
		public SagariNoRyusei() 
		{
			super(ListAttributes.SAGARI_NO_RYUSEI); 
		}
		
		@Override
		public void use(EntityPlayer player)
		{	
			if(!this.isOnCooldown)		
			{
				RayTraceResult mop = WyHelper.rayTraceBlocks(player);
				
				if(mop != null)
				{
					double x = mop.hitVec.x;
					double y = mop.hitVec.y;
					double z = mop.hitVec.z;

					AbilityProjectile proj = new JuryoProjectiles.SagariNoRyusei(player.world, player, ListAttributes.SAGARI_NO_RYUSEI);	
					proj.setLocationAndAngles(x, (y + 90), z, 0, 0);
					proj.motionX = 0;
					proj.motionZ = 0;
					proj.motionY = -2.4;
					player.world.spawnEntity(proj);
				}
			}
			super.use(player);
		}
	}
}

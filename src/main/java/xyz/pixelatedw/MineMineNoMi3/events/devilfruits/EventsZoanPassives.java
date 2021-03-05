package xyz.pixelatedw.MineMineNoMi3.events.devilfruits;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class EventsZoanPassives
{
	@SubscribeEvent
	public void onEntityAttack(LivingHurtEvent event)
	{
		if (event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			ExtendedEntityData props = ExtendedEntityData.get(attacker);
			AbilityProperties abilityProps = AbilityProperties.get(attacker);
			EntityLivingBase attacked = event.getEntityLiving();
			
			if(props.getUsedFruit().equalsIgnoreCase("ushiushibison") && props.getZoanPoint().equalsIgnoreCase("power"))
				event.ammount += 3;
			
			if(props.getUsedFruit().equalsIgnoreCase("zouzou") && props.getZoanPoint().equalsIgnoreCase("hybrid"))
				event.ammount += 3;
				
			if(props.getUsedFruit().equalsIgnoreCase("mogumogu") && props.getZoanPoint().equalsIgnoreCase("power"))
				event.ammount += 3;
		}
	}
	
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ExtendedEntityData props = ExtendedEntityData.get(player);
			AbilityProperties abilityProps = AbilityProperties.get(player);
			ItemStack heldItem = player.getHeldItem();
			
			if(props.getUsedFruit().equalsIgnoreCase("mogumogu") && props.getZoanPoint().equalsIgnoreCase("power"))
			{
				if(!player.world.isRemote && player.world.getBlockLightValue((int)player.posX, (int)player.posY, (int)player.posZ) < 7)
				{
					player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 1, true));					
				}
			}
			
			if(props.getUsedFruit().equals("zouzou"))
			{
				if(props.getZoanPoint().equals("full"))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 2 * 20, 1, true));
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 2 * 20, 0, true));
				}
				if(props.getZoanPoint().equals("hybrid"))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 2 * 20, 0, true));
				}
			}
			
			if(props.getUsedFruit().equalsIgnoreCase("ushiushigiraffe"))
			{
				if(props.getZoanPoint().equalsIgnoreCase("speed"))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(1).id, 2 * 20, 1, true));
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 2 * 20, 1, true));
				}
			}
			
			if(props.getUsedFruit().equals("ushiushibison"))
			{
				if(props.getZoanPoint().equals("speed"))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(1).id, 2 * 20, 1, true));
					
					double mX = -MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4;
					double mZ = MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4;
						
					double f2 = MathHelper.sqrt(mX * mX + player.motionY * player.motionY + mZ * mZ);
					mX /= f2;
					mZ /= f2;
					mX += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
					mZ += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
					mX *= 2;
					mZ *= 2;
					
					for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 0.5))
					{
						e.attackEntityFrom(DamageSource.causePlayerDamage(player), 2);
						e.motionX = mX;
						e.motionZ = mZ;
					}
				}
				else
				{
					player.removePotionEffect(Potion.getPotionById(1).id);
				}
			}

		}
	}
}

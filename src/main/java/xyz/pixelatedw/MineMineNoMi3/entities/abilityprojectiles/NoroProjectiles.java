package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.abilities.effects.DFEffectNoroSlowness;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSyncInfo;

public class NoroProjectiles 
{

	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {NoroNoroBeam.class, ListAttributes.NORO_NORO_BEAM});
	}
	
	public static class NoroNoroBeam extends AbilityProjectile
	{
		public NoroNoroBeam(World world)
		{super(world);}
		
		public NoroNoroBeam(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public NoroNoroBeam(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.entityHit != null && hit.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase target = ((EntityLivingBase)hit.entityHit);
				if( target.isPotionActive(Potion.getPotionById(2)) && target.isPotionActive(Potion.getPotionById(4)) )
				{				
					int newTimer = 0;
					int newAmplifier = 0;
					
					newTimer = target.getActivePotionEffect(event.getType(2).getDuration() + 240;
					if(target.getActivePotionEffect(event.getType(2).getAmplifier() + 10 < 200)
						newAmplifier = target.getActivePotionEffect(event.getType(2).getAmplifier() + 10;
					else
						newAmplifier = 200;
					target.removePotionEffect(Potion.getPotionById(2));
					target.removePotionEffect(Potion.getPotionById(4));
					target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), newTimer, newAmplifier));
					target.addPotionEffect(new PotionEffect(Potion.getPotionById(4), newTimer, newAmplifier));
					
					ExtendedEntityData props = ExtendedEntityData.get(target);
					if(!props.hasExtraEffects(ID.EXTRAEFFECT_NORO))
						new DFEffectNoroSlowness(target, newTimer);
				}
				else
				{
					target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 240, 10));
					target.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 240, 10));
					new DFEffectNoroSlowness(target, 240);
				}			
			}
		}
	}	
	
	
}

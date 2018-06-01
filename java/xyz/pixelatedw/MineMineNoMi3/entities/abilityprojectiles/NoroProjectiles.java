package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.HieProjectiles.IceBall;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.HieProjectiles.IceBlockPartisan;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class NoroProjectiles 
{

	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {NoroNoroBeam.class, ListAttributes.NORONOROBEAM});
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
		
		public void tasksImapct(MovingObjectPosition hit)
		{
			if(hit.entityHit != null && hit.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase target = ((EntityLivingBase)hit.entityHit);
				if( target.isPotionActive(Potion.moveSlowdown.id) && target.isPotionActive(Potion.digSlowdown.id) )
				{				
					int newTimer = 0;
					int newAmplifier = 0;
					
					newTimer = target.getActivePotionEffect(Potion.moveSlowdown).getDuration() + 240;
					if(target.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 10 < 200)
						newAmplifier = target.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 10;
					else
						newAmplifier = 200;
					target.removePotionEffect(Potion.moveSlowdown.id);
					target.removePotionEffect(Potion.digSlowdown.id);
					target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, newTimer, newAmplifier));
					target.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, newTimer, newAmplifier));
				}
				else
				{
					target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 240, 10));
					target.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 240, 10));
				}
				
				System.out.println(target.getActivePotionEffect(Potion.moveSlowdown).getDuration());
			}
		}
	}	
	
	
}

package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.MeraProjectiles.DaiEnkaiEntei;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.MeraProjectiles.Hidaruma;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.MeraProjectiles.Higan;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.MeraProjectiles.Hiken;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.MeraProjectiles.Jujika;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class HieProjectiles 
{

	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {IceBall.class, ListAttributes.ICEBALL});
		abilitiesClassesArray.add(new Object[] {IceBlockPartisan.class, ListAttributes.ICEBLOCKPARTISAN});
		abilitiesClassesArray.add(new Object[] {IceBlockPheasant.class, ListAttributes.ICEBLOCKPHEASANT});
	}
	
	public static class IceBlockPheasant extends AbilityProjectile
	{
		public IceBlockPheasant(World world)
		{super(world);}
		
		public IceBlockPheasant(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public IceBlockPheasant(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		public void onUpdate()
		{	
			if(this.worldObj.isRemote)
			{
				for(int i = 0; i < 5; i++)
				{
					double offsetX = WyMathHelper.randomWithRange(-1, 1);
					double offsetY = WyMathHelper.randomWithRange(-1, 1);
					double offsetZ = WyMathHelper.randomWithRange(-1, 1);
				      
					MainMod.proxy.spawnCustomParticles(this,
							new EntityParticleFX(this.worldObj, ID.PARTICLE_ICON_HIE, 
									posX + offsetX + this.rand.nextFloat(), 
									posY + offsetY + this.rand.nextFloat(), 
									posZ + offsetZ + this.rand.nextFloat(), 
									0, 0, 0)
							.setParticleScale(1).setParticleAge(5));
				}
			}
			
			super.onUpdate();
		}
	}	
	
	public static class IceBlockPartisan extends AbilityProjectile
	{
		public IceBlockPartisan(World world)
		{super(world);}
		
		public IceBlockPartisan(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public IceBlockPartisan(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		public void tasksImapct(MovingObjectPosition hit)
		{
			DevilFruitsHelper.placeBlockIfAllowed(this.worldObj, (int)posX, (int)posY, (int)posZ, Blocks.packed_ice, "air");
		}
		
		public void onUpdate()
		{	
			if(this.worldObj.isRemote)
			{
				double offsetX = this.rand.nextFloat() * 1 - 0.5;
				double offsetY = this.rand.nextFloat() * 1 - 0.5;
				double offsetZ = this.rand.nextFloat() * 1 - 0.5;
			      
				MainMod.proxy.spawnCustomParticles(this,
						new EntityParticleFX(this.worldObj, ID.PARTICLE_ICON_HIE, 
								posX + offsetX, 
								posY + offsetY, 
								posZ + offsetZ, 
								0, 0, 0)
						.setParticleScale(1).setParticleAge(2));
			}
			
			super.onUpdate();
		}
	}
	
	public static class IceBall extends AbilityProjectile
	{
		public IceBall(World world)
		{super(world);}
		
		public IceBall(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public IceBall(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		public void tasksImapct(MovingObjectPosition hit)
		{
			if(!this.worldObj.isRemote)
			{	
				if(MainConfig.enableGriefing)
				{
					WyHelper.createEmptySphere(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 6, Blocks.packed_ice);
				}
			}
		}
	}
}

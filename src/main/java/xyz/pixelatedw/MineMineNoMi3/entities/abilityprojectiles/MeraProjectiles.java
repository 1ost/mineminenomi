package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.RayTraceResult;

import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class MeraProjectiles
{
	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {Hiken.class, ListAttributes.HIKEN});
		abilitiesClassesArray.add(new Object[] {Higan.class, ListAttributes.HIGAN});
		abilitiesClassesArray.add(new Object[] {DaiEnkaiEntei.class, ListAttributes.DAI_ENKAI_ENTEI});
		abilitiesClassesArray.add(new Object[] {Hidaruma.class, ListAttributes.HIDARUMA});
		abilitiesClassesArray.add(new Object[] {Jujika.class, ListAttributes.JUJIKA});
	}
	
	public static class Hiken extends AbilityProjectile
	{
		private Random r = new Random();
		
		public Hiken(World world)
		{super(world);}
		
		public Hiken(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public Hiken(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		@Override
		public void onUpdate()
		{		
			if(this.world.isRemote)
			{			
				for (int i = 0; i < DevilFruitsHelper.getParticleSettingModifier(25); i++)
				{
					double offsetX = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					double offsetY = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					double offsetZ = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					
					EntityParticleFX particle = new EntityParticleFX(this.world, ID.PARTICLE_ICON_MERA,
							posX + offsetX, 
							posY + offsetY, 
							posZ + offsetZ, 
							0, 0, 0)
							.setParticleAge(10).setParticleScale(1.3F);
					
					MainMod.proxy.spawnCustomParticles(this, particle);				
				}
				
				for (int i = 0; i < DevilFruitsHelper.getParticleSettingModifier(2); i++)
				{
					double offsetX = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					double offsetY = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					double offsetZ = (new Random().nextInt(50) + 1.0D - 25.0D) / 30.0D;
					
					EntityParticleFX particle = new EntityParticleFX(this.world, ID.PARTICLE_ICON_MOKU,
							posX + offsetX, 
							posY + offsetY, 
							posZ + offsetZ, 
							0, 0, 0)
							.setParticleAge(7).setParticleScale(1.2F);
					
					MainMod.proxy.spawnCustomParticles(this, particle);					
				}
			}
			super.onUpdate();
		}
		
	}
	
	public static class Higan extends AbilityProjectile
	{
		public Higan(World world)
		{super(world);}
		
		public Higan(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public Higan(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			this.world.setBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), (IBlockState) Blocks.FIRE);
		}
		
		@Override
		public void onUpdate()
		{	
			if(this.world.isRemote)
			{
				double posXOffset = this.world.rand.nextGaussian() * 0.42D;
				double posYOffset = this.world.rand.nextGaussian() * 0.22D;
				double posZOffset = this.world.rand.nextGaussian() * 0.42D;
	
				EntityParticleFX particle = new EntityParticleFX(this.world, ID.PARTICLE_ICON_MERA,
						posX + posXOffset, 
						posY + posYOffset, 
						posZ + posZOffset, 
						0, 0, 0)
						.setParticleAge(6).setParticleScale(0.7F);
				
				MainMod.proxy.spawnCustomParticles(this, particle);		
			}
			super.onUpdate();
		}
	}
	
	public static class DaiEnkaiEntei extends AbilityProjectile
	{
		public DaiEnkaiEntei(World world)
		{ super(world); }
		
		public DaiEnkaiEntei(World world, double x, double y, double z)
		{ super(world, x, y, z); }
		
		public DaiEnkaiEntei(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		@Override
		public void onUpdate()
		{	
			if(this.world.isRemote)
			{
				for (int i = 0; i < DevilFruitsHelper.getParticleSettingModifier(75); i++)
				{
					double offsetX = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
					double offsetY = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
					double offsetZ = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
			      
					EntityParticleFX particle = new EntityParticleFX(this.world, ID.PARTICLE_ICON_MERA,
							posX + offsetX, 
							posY + offsetY, 
							posZ + offsetZ, 
							0, 0, 0)
							.setParticleAge(10).setParticleScale(1.3F);
					
					MainMod.proxy.spawnCustomParticles(this, particle);					
				}
				
				for (int i = 0; i < DevilFruitsHelper.getParticleSettingModifier(10); i++)
				{
					double offsetX = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
					double offsetY = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
					double offsetZ = (new Random().nextInt(40) + 2.0D - 20.0D) / 10.0D;
			      
					EntityParticleFX particle = new EntityParticleFX(this.world, ID.PARTICLE_ICON_MOKU,
							posX + offsetX, 
							posY + offsetY, 
							posZ + offsetZ, 
							0, 0, 0)
							.setParticleAge(7).setParticleScale(1.1F);
					
					MainMod.proxy.spawnCustomParticles(this, particle);	
				}
			}
			
			super.onUpdate();
		}
	}
	
	public static class Hidaruma extends AbilityProjectile
	{
		public Hidaruma(World world)
		{super(world);}
		
		public Hidaruma(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public Hidaruma(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.entityHit != null)
				hit.entityHit.setFire(this.ticks);

			this.world.setBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), (IBlockState) Blocks.FIRE);
		};
		
		@Override
		public void onUpdate()
		{	
			for (int i = 0; i < DevilFruitsHelper.getParticleSettingModifier(25); i++)
			{
				double offsetX = (new Random().nextInt(10) + 1.0D - 5.0D) / 10.0D;
				double offsetY = (new Random().nextInt(10) + 1.0D - 5.0D) / 10.0D;
				double offsetZ = (new Random().nextInt(10) + 1.0D - 5.0D) / 10.0D;
		      
				this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleName(), this.posX + offsetX, this.posY + offsetY, this.posZ + offsetZ, 0.0D, 0.05D, 0.0D);
			}
			
			super.onUpdate();
		}
	}
	
	public static class Jujika extends AbilityProjectile
	{
		public Jujika(World world)
		{super(world);}
		
		public Jujika(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public Jujika(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}
		
		@Override
		public void tasksImapct(RayTraceResult hit)
		{
			if(hit.entityHit != null)
			{
				hit.entityHit.setFire(this.ticks);
				if(MainConfig.enableGriefing)
				{
					for(int j = -2; j <= 2; j++)
					{
						for(int i = -5; i <= 5; i++)
							if(this.world.isAirBlock(new BlockPos((int)hit.entityHit.posX + i, (int)hit.entityHit.posY + j, (int)hit.entityHit.posZ)))
								this.world.setBlockState(new BlockPos((int)hit.entityHit.posX + i, (int)hit.entityHit.posY + j, (int)hit.entityHit.posZ), (IBlockState) Blocks.FIRE);
							
						for(int i = -5; i <= 5; i++)
							if(this.world.isAirBlock(new BlockPos((int)hit.entityHit.posX, (int)hit.entityHit.posY + j, (int)hit.entityHit.posZ + i)))
								this.world.setBlockState(new BlockPos((int)hit.entityHit.posX, (int)hit.entityHit.posY + j, (int)hit.entityHit.posZ + i), (IBlockState) Blocks.FIRE);
					}
				}
			}

			if(MainConfig.enableGriefing)
			{
				for(int j = -2; j <= 2; j++)
				{
					for(int i = -5; i <= 5; i++)
						if(this.world.isAirBlock(new BlockPos((int)this.posX, (int)this.posY + j, (int)this.posZ + i)))
							this.world.setBlockState(new BlockPos((int)this.posX, (int)this.posY + j, (int)this.posZ + i), (IBlockState) Blocks.FIRE);

					for(int i = -5; i <= 5; i++)
						if(this.world.isAirBlock(new BlockPos((int)this.posX, (int)this.posY + j, (int)this.posZ + i)))
							this.world.setBlockState(new BlockPos((int)this.posX, (int)this.posY + j, (int)this.posZ + i), (IBlockState) Blocks.FIRE);
				}
			}
		};
	}
}

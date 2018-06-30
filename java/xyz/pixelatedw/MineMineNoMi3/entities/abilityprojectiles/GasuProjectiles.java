package xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityProjectile;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.DokuProjectiles.Hydra;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class GasuProjectiles 
{

	public static ArrayList<Object[]> abilitiesClassesArray = new ArrayList();
	
	static
	{
		abilitiesClassesArray.add(new Object[] {Gastille.class, ListAttributes.GASTILLE});
		abilitiesClassesArray.add(new Object[] {GasRobe.class, ListAttributes.GASROBE});
	}
	
	public static class Gastille extends AbilityProjectile
	{
		public Gastille(World world)
		{super(world);}
		
		public Gastille(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public Gastille(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}	
	}
	
	public static class GasRobe extends AbilityProjectile
	{
		public GasRobe(World world)
		{super(world);}
		
		public GasRobe(World world, double x, double y, double z)
		{super(world, x, y, z);}
		
		public GasRobe(World world, EntityLivingBase player, AbilityAttribute attr) 
		{		
			super(world, player, attr);		
		}	
		
		public void onUpdate()
		{	
			double posXOffset = this.worldObj.rand.nextGaussian() * 0.42D;
			double posYOffset = this.worldObj.rand.nextGaussian() * 0.22D;
			double posZOffset = this.worldObj.rand.nextGaussian() * 0.42D;		
			
			MainMod.proxy.spawnCustomParticles(this, ID.PARTICLE_NAME_GASU, this.posX + posXOffset, this.posY + posYOffset, this.posZ + posZOffset, 0.0D, 0.0D, 0.0D);
			
			posXOffset = this.worldObj.rand.nextGaussian() * 0.12D;
			posYOffset = this.worldObj.rand.nextGaussian() * 0.06D;
			posZOffset = this.worldObj.rand.nextGaussian() * 0.12D;		
			
			MainMod.proxy.spawnCustomParticles(this, ID.PARTICLE_NAME_GASU2, this.posX + posXOffset, this.posY + posYOffset, this.posZ + posZOffset, 0.0D, 0.0D, 0.0D);

			
			super.onUpdate();
		}
	}
	
	
}

package xyz.pixelatedw.MineMineNoMi3.api.abilities;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityExplosion;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;

public class AbilityProjectile extends EntityThrowable
{
	public int ticks, maxticks;
	private AbilityAttribute attr;
	private EntityLivingBase user;
	public float gravity = 0.001F;

	public AbilityProjectile(World world)
	{
		super(world);
	}

	public AbilityProjectile(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	public AbilityProjectile(World world, EntityLivingBase player, AbilityAttribute attr)
	{
		super(world, player);
		this.attr = attr;
		this.ticks = attr.getProjectileTicks();
		this.maxticks = ticks;
		this.user = player;

		if(this.getThrower() != null && this.getThrower() instanceof EntityPlayer && DevilFruitsHelper.checkForRestriction((EntityPlayer) this.getThrower()))
			this.setDead();

		if (this.attr != null)
		{
			this.setLocationAndAngles(this.user.posX, this.user.posY + this.user.getEyeHeight(), this.user.posZ, this.user.rotationYaw, this.user.rotationPitch);
			this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4;
			this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4;
			//probably not getMaxInPortalTime
			this.motionY = -MathHelper.sin((this.rotationPitch + this.getMaxInPortalTime()) / 180.0F * (float) Math.PI) * 0.4;
			this.setLocationAndAngles(this.motionX, this.motionY, this.motionZ, attr.getProjectileSpeed(), 1.0F);
		}

	}

	public AbilityAttribute getAttribute()
	{
		return this.attr;
	}

	public void tasksImapct(RayTraceResult hit)
	{
	};

	@Override
	public void onEntityUpdate()
	{
		if (this.attr != null)
		{
			if (ticks <= 0)
			{
				ticks = maxticks;
				this.setDead();
			}
			else
				ticks--;
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if(this.attr != null)
		{
			Vec3d vec31 = Vec3d.ZERO.addVector(this.posX, this.posY, this.posZ);
			Vec3d vec3 = Vec3d.ZERO.addVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult movingobjectposition = this.world.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3d.ZERO.addVector(this.posX, this.posY, this.posZ);
			vec3 = Vec3d.ZERO.addVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			
			double sizeX = this.attr.getProjectileCollisionSizes()[0];
			double sizeY = this.attr.getProjectileCollisionSizes()[1];
			double sizeZ = this.attr.getProjectileCollisionSizes()[2];
			
			Entity entity = null;
			List list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getRenderBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(sizeX, sizeY, sizeZ));
			double d0 = 0.0D;
			int i;
			float f1;
			
			for (i = 0; i < list.size(); ++i)
			{
				Entity entity1 = (Entity) list.get(i);
				
				if (entity1.canBeCollidedWith() && (entity1 != this.getThrower() || this.ticksExisted >= 5))
				{
					AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(sizeX, sizeY, sizeZ);
					RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
					
					if (movingobjectposition1 != null)
					{
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
						
						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
			
			if (entity != null)
				movingobjectposition = new RayTraceResult(entity);
			
			if (movingobjectposition != null && movingobjectposition.entityHit != null)
				this.onImpact(movingobjectposition);
		}
	}

	@Override
	protected void onImpact(RayTraceResult hit)
	{
		if (!this.world.isRemote)
		{
			if (this.attr != null)
			{
				if (hit.entityHit != null && hit.entityHit instanceof EntityLivingBase)
				{
					ExtendedEntityData props = ExtendedEntityData.get(this.getThrower());
					ExtendedEntityData propz = ExtendedEntityData.get((EntityLivingBase) hit.entityHit);

					if(propz.isLogia() && this.getAttribute().isProjectilePhysical() && !props.hasBusoHakiActive())
						return;
						
					if (this.attr.getPotionEffectsForProjectile() != null)
						for (PotionEffect p : this.attr.getPotionEffectsForProjectile())
							((EntityLivingBase) hit.entityHit).addPotionEffect(new PotionEffect(p));

					if (this.attr.getProjectileExplosionPower() > 0)
					{
						AbilityExplosion explosion = WyHelper.newExplosion(this.getThrower(), this.posX, this.posY, this.posZ, this.attr.getProjectileExplosionPower());
						explosion.setDamageOwner(false);
						explosion.setFireAfterExplosion(this.attr.canProjectileExplosionSetFire());
						explosion.setDestroyBlocks(this.attr.canProjectileExplosionDestroyBlocks());
						explosion.doExplosion();
					}

					if (this.attr.getProjectileDamage() > 0)
						hit.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.attr.getProjectileDamage() * props.getDamageMultiplier());
					
					tasksImapct(hit);

					this.setDead();
				}
				else
				{
					if (this.attr.getProjectileExplosionPower() > 0)
					{
						AbilityExplosion explosion = WyHelper.newExplosion(this.getThrower(), this.posX, this.posY, this.posZ, this.attr.getProjectileExplosionPower());
						explosion.setDamageOwner(false);
						explosion.setFireAfterExplosion(this.attr.canProjectileExplosionSetFire());
						explosion.setDestroyBlocks(this.attr.canProjectileExplosionDestroyBlocks());
						explosion.doExplosion();
					}

					tasksImapct(hit);

					Material hitMat = this.world.getBlockState(new BlockPos(hit.hitVec.x, hit.hitVec.y, hit.hitVec.z)).getMaterial();

					if (!this.attr.canProjectileMoveThroughBlocks() && (hitMat != Material.PLANTS && hitMat != Material.VINE && hitMat != Material.WATER))
						this.setDead();
				}
			}
			else
				this.setDead();
		}
	}

	@Override
	protected float getGravityVelocity()
	{
		return gravity;
	}

}
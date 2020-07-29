package xyz.pixelatedw.mineminenomi.entities.mobs.marines;

import javax.annotation.Nullable;

import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.entities.projectiles.extra.NormalBulletProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.projectiles.AbilityProjectileEntity;

public class MarineWithGunEntity extends GenericMarineEntity implements IRangedAttackMob
{
	public MarineWithGunEntity(World world)
	{
		super(ModEntities.MARINE_WITH_GUN, world, new String[] {"marine1", "marine2", "marine3", "marine4", "marine5"});
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 40, 15.0F));
	}
	
	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23F);
		this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		
		this.setDoriki(10 + WyHelper.randomWithRange(0, 5));
		this.setBelly(5 + WyHelper.randomWithRange(0, 5));
	}

	@Override
	protected void registerData()
	{
		super.registerData();
	}

	@Override
	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT dataTag) 
	{
		spawnData = super.onInitialSpawn(world, difficulty, reason, spawnData, dataTag);
		
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModWeapons.FLINTLOCK));

		return spawnData;
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distance)
	{
		AbilityProjectileEntity proj = new NormalBulletProjectile(this.world, this);
<<<<<<< HEAD
		
		if(this.getAttackTarget() == null)
=======
		proj.setDamage(2);
		if (this.getAttackTarget() == null)
>>>>>>> 901d2fa2... Doriki scale for captains and various fixes and nerfs for marines/pirates/bandits
			return;
		
		double velX = target.posX - this.posX;
		double velY = target.getBoundingBox().minY + this.getAttackTarget().getHeight() / 3.0F - (this.posY + this.getHeight());
		double velZ = target.posZ - this.posZ;
		double x = MathHelper.sqrt(velX * velX + velZ * velZ);

		proj.shoot(velX, velY + x * 0.1F, velZ, 1.6F, 14 - this.world.getDifficulty().getId() * 4);
		this.world.addEntity(proj);
	}
}

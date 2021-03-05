package xyz.pixelatedw.MineMineNoMi3.entities.mobs.ai.abilities.rokushiki;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.RokushikiProjectiles.Rankyaku;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.EntityNewMob;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.ai.EntityAICooldown;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class EntityAIRankyaku extends EntityAICooldown
{
	private EntityNewMob entity;
	
	public EntityAIRankyaku(EntityNewMob entity)
	{
		super(entity, 120, entity.getRNG().nextInt(10));
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute()
	{
		ItemStack itemStack = this.entity.getHeldItem(EnumHand.MAIN_HAND);

		if(this.entity.getAttackTarget() == null)
			return false;

		if(this.entity.getDistanceSq(this.entity.getAttackTarget()) < 5)
			return false;
		
		if(this.isOnCooldown())
		{
			this.countDownCooldown();
			return false;
		}
		
		this.execute();
		return true;
	}
	
	@Override
	public void endCooldown()	
	{
		super.endCooldown();
		this.entity.setCurrentAI(null);
		this.entity.setPreviousAI(this);
	}

    public void execute()
    {
    	double d0 = entity.getDistanceSq(entity.getAttackTarget());
		float f = MathHelper.sqrt(MathHelper.sqrt(d0));
		double d1 = entity.getAttackTarget().posX - entity.posX;
		double d2 = entity.getAttackTarget().getCollisionBoundingBox().minY + entity.getAttackTarget().height / 2.0F - (entity.posY + entity.height / 2.0F);
		double d3 = entity.getAttackTarget().posZ - entity.posZ;
    	
    	Rankyaku projectile = new Rankyaku(this.entity.world, this.entity, ListAttributes.RANKYAKU);
    	
    	projectile.posY = entity.posY + entity.height / 2.0F + 0.5D;
		projectile.setLocationAndAngles(d1 + entity.getRNG().nextGaussian(), d2, d3 + entity.getRNG().nextGaussian(), 1, 0);
		entity.world.spawnEntity(projectile);
    	
    	this.entity.setCurrentAI(this);
	    this.setOnCooldown(true); 	
    }
}

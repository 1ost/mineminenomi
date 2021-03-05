package xyz.pixelatedw.MineMineNoMi3.entities.mobs.ai.abilities.swordsman;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.SwordsmanAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.debug.WyDebug;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.SwordsmanProjectiles.Yakkodori;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.EntityNewMob;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.ai.EntityAICooldown;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketShounenScream;

public class EntityAIYakkodori extends EntityAICooldown
{
	private EntityNewMob entity;
	
	public EntityAIYakkodori(EntityNewMob entity)
	{
		super(entity, 120, entity.getRNG().nextInt(20));
		this.entity = entity;
	}

	public boolean shouldExecute()
	{
		ItemStack itemStack = this.entity.getHeldItem(EnumHand.MAIN_HAND);
		
		//if(this.entity.getPreviousAI() != null && this.entity.getPreviousAI() == this.entity.getCurrentAI())
		//	return false;
		
		if(itemStack == null || this.entity.getAttackTarget() == null)
			return false;

		if(this.entity.getDistanceSq(this.entity.getAttackTarget()) < 10)
			return false;
		
		if(this.isOnCooldown())
		{
			this.countDownCooldown();
			return false;
		}
		
		this.execute();
		return true;
	} 
	
	public void endCooldown()	
	{
		super.endCooldown();
		this.entity.setCurrentAI(null);
		this.entity.setPreviousAI(this);
	}

    public void execute()
    {
		//if(MainConfig.enableAnimeScreaming)
    	//	WyNetworkHelper.sendToAllAround(new PacketShounenScream(this.entity.getCommandSenderName(), ListAttributes.YAKKODORI.getAbilityDisplayName(), 0), this.entity.dimension, this.entity.posX, this.entity.posY, this.entity.posZ, 15);
		
    	double d0 = entity.getDistanceSq(entity.getAttackTarget());
		float f = MathHelper.sqrt(MathHelper.sqrt(d0));
		double d1 = entity.getAttackTarget().posX - entity.posX;
		double d2 = entity.getAttackTarget().getCollisionBoundingBox().minY + (double)(entity.getAttackTarget().height / 2.0F) - (entity.posY + (double)(entity.height / 2.0F));
		double d3 = entity.getAttackTarget().posZ - entity.posZ;
    	
    	Yakkodori projectile = new Yakkodori(this.entity.world, this.entity, ListAttributes.YAKKODORI);
    	
    	projectile.posY = entity.posY + (double)(entity.height / 2.0F) + 0.5D;
		projectile.setLocationAndAngles(d1 + entity.getRNG().nextGaussian(), d2, d3 + entity.getRNG().nextGaussian(), 1, 0);
		entity.world.spawnEntity(projectile);
    	
    	this.entity.setCurrentAI(this);
	    this.setOnCooldown(true); 	
    }
}

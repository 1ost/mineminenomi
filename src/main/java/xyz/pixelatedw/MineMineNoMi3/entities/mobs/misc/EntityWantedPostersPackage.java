package xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.helpers.ItemsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class EntityWantedPostersPackage extends EntityMob
{

	public EntityWantedPostersPackage(World world)
	{
		super(world);
	}

	public void applyEntityAttributes()
	{ 
		super.applyEntityAttributes(); 
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4);
	}
	
	protected void entityInit() 
	{
		super.entityInit();
	}

    protected boolean isAIEnabled()
    {
        return false;
    }
	
    public void setDead()
    {
    	if(!this.onGround)
    		ItemsHelper.dropWantedPosters(this.world, (int)posX, (int)posY, (int)posZ);
    	super.setDead();
    }
    
	public void onEntityUpdate()
	{
		this.motionY /= 1.5 + this.world.rand.nextDouble();
		this.fallDistance = 0;
		
		if(this.onGround && !this.world.isRemote)
		{
			if(this.world.isAirBlock((int)this.posX, (int)this.posY, (int)this.posZ))
			{
				this.world.setBlock((int)this.posX, (int)this.posY, (int)this.posZ, ListMisc.WantedPostersPackage);
				this.setDead();
			}
			else if(this.world.isAirBlock((int)this.posX, (int)this.posY + 1, (int)this.posZ))
			{
				this.world.setBlock((int)this.posX, (int)this.posY + 1, (int)this.posZ, ListMisc.WantedPostersPackage);
				this.setDead();
			}
		}
		
		if(this.isInWater() || this.isInsideOfMaterial(Material.LAVA))
			this.setDead();
		
		super.onEntityUpdate();
	}
}

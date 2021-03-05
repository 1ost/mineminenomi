package xyz.pixelatedw.MineMineNoMi3.entities.mobs.baroqueWorks;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.PirateData;

public class EntityMr0 extends PirateData
{

	public EntityMr0(World worldIn)
	{
		super(worldIn);
	}
	
	@Override
	public void applyEntityAttributes()
	{ 
		super.applyEntityAttributes(); 
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(55.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
		
		ExtendedEntityData props = ExtendedEntityData.get(this);
		
		props.setUsedFruit("sunasuna");
		props.setIsLogia(true);
		
		this.setDoriki(500 + this.world.rand.nextInt(50));
		this.setBelly(1000 + this.world.rand.nextInt(100));
	}
}

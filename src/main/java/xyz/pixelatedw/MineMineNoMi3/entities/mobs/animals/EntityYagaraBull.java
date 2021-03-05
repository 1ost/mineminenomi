package xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.EntityNewMob;

public class EntityYagaraBull extends EntityNewMob implements IEntityOwnable
{
	private Item[] food = new Item[]
		{
				Items.COOKED_FISH, Items.FISH
		};
	private boolean isTamed, isSaddled;
	private boolean is2ndSeatEmpty;
	private EntityPlayer owner;
	private UUID ownerUUID;
	private int timesFed = 0;
	private double speedMultiplier = 1;

	public EntityYagaraBull(World world)
	{
		super(world, new String[] {"yagarabull1", "yagarabull2", "yagarabull3"});
		this.setSize(1.4F, 1.6F);
		this.tasks.addTask(1, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.01D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.ticksExisted < 20)
			this.updateNBT();

		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, this.boundingBox.minY + 0.9, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ);

		if (this.world.isAABBInMaterial(aabb, Material.WATER))
			this.motionY += 0.03;
		
		if (!this.world.isRemote)
		{
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
			{
				EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;
				float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
				this.motionX += -Math.sin(f * (float) Math.PI / 180.0F) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
				this.motionZ += Math.cos(f * (float) Math.PI / 180.0F) * this.speedMultiplier * entitylivingbase.moveForward * 0.05000000074505806D;
			}
			
			for (int l = 0; l < 4; ++l)
            {
                int i1 = MathHelper.floor(this.posX + (l % 2 - 0.5D) * 1.4D);
                int j = MathHelper.floor(this.posZ + (l / 2 - 0.5D) * 1.4D);

                for (int j1 = 0; j1 < 2; ++j1)
                {
                    int k = MathHelper.floor(this.posY) + j1;
                    Block block = this.world.getBlock(i1, k, j);

                    if (block == Blocks.SNOW_LAYER)
                    {
                        this.world.setBlockToAir(i1, k, j);
                        this.isCollidedHorizontally = false;
                    }
                    else if (block == Blocks.WATERLILY)
                    {
                        this.world.func_147480_a(i1, k, j, true);
                        this.isCollidedHorizontally = false;
                    }
                }
            }
		}
	}

	@Override
	public boolean interact(EntityPlayer player)
	{
		ItemStack heldStack = player.getHeldItem(EnumHand.MAIN_HAND);

		if (!this.isTamed())
		{
			if (heldStack == null)
				return false;

			ItemStack foodItemStack = null;
			
			for(Item food : this.food)
			{
				if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() == food)
				{
					foodItemStack = new ItemStack(food);
					break;
				}
			}
			
			if(foodItemStack == null)
				return false;
			
			--foodItemStack.stackSize;
			for (int i = 0; i < 2; ++i)
			{
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				double d2 = this.rand.nextGaussian() * 0.02D;
				this.world.spawnParticle((EnumParticleTypes.HEART.getParticleName()), this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
			}
			this.timesFed++;
				
			if (this.timesFed >= 5 + WyMathHelper.randomWithRange(2, 5))
			{
				this.setTamed(true);
				for (int i = 0; i < 10; ++i)
				{
					double d0 = this.rand.nextGaussian() * 0.03D;
					double d1 = this.rand.nextGaussian() * 0.03D;
					double d2 = this.rand.nextGaussian() * 0.03D;
					this.world.spawnParticle((EnumParticleTypes.HEART.getParticleName()), this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
				}
				this.updateNBT();
			}

			return true;
		}
		else
		{
			if (!this.isSaddled())
			{
				if (heldStack == null)
					return false;

				if (heldStack.getItem() == Items.SADDLE)
				{
					--heldStack.stackSize;
					this.setSaddled(true);
					this.updateNBT();
				}

				return true;
			}
			else
			{
				if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player)				
					return true;
				else
				{
					if (!this.world.isRemote)
					{
				        player.rotationYaw = this.rotationYaw;
				        player.rotationPitch = this.rotationPitch;
						player.mountEntity(this);
					}

					return true;
				}
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float x, float y)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.isSaddled())
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			y = ((EntityLivingBase) this.riddenByEntity).moveForward;

			if (y <= 0.0F)
			{
				y *= 0.25F;
			}

			if (!this.world.isRemote)
				super.moveEntityWithHeading(x, y);
		}
		else
			super.moveEntityWithHeading(x, y);
	}

	@Override
	public void updateRiderPosition()
	{
		super.updateRiderPosition();

		float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f2 = -0.5F;
		float f3 = 0.15F;
		this.riddenByEntity.setPosition(this.posX + f2 * f, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + f3, this.posZ - f2 * f1);

		if (this.riddenByEntity instanceof EntityLivingBase)
		{
			((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
		}
	}

	@Override
	public double getMountedYOffset()
	{
		return this.height * 0.5D;
	}

    @Override
	protected void dropFewItems(boolean flag, int looting)
    {
        int j = (int) (1 + WyMathHelper.randomWithRange(1 + looting, 5 + looting));

        for (int k = 0; k < j; ++k)
        {
            if (this.isBurning())
                this.dropItem(Items.COOKED_FISH, 1);
            else
                this.dropItem(Items.FISH, 1);
        }

        if (this.isSaddled())
            this.dropItem(Items.SADDLE, 1);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		nbt.setBoolean("IsTamed", this.isTamed);
		nbt.setBoolean("IsSaddled", this.isSaddled);

		nbt.setString("OwnerUUID", this.ownerUUID != null ? this.ownerUUID.toString() : "");
	}

	@Override
	public void readEntityFromExtraNBT(NBTTagCompound nbt)
	{
		this.readEntityFromNBT(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.isTamed = nbt.getBoolean("IsTamed");
		this.isSaddled = nbt.getBoolean("IsSaddled");

		String uuid = nbt.getString("OwnerUUID");

		if (!WyHelper.isNullOrEmpty(uuid))
		{
			this.ownerUUID = UUID.fromString(uuid);
			this.owner = this.world.func_152378_a(this.ownerUUID);
			this.isTamed = true;
		}
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	public boolean isTamed()
	{
		return this.isTamed;
	}

	public void setTamed(boolean value)
	{
		this.isTamed = value;
	}

	public boolean isSaddled()
	{
		return this.isSaddled;
	}

	public void setSaddled(boolean value)
	{
		this.isSaddled = value;
	}

	@Override
	public EntityPlayer getOwner()
	{
		return this.owner;
	}

	@Override
	protected boolean canDespawn()
	{
		if (this.isTamed())
			return false;
		else
			return true;
	}

	@Override
	public String func_152113_b()
	{
		if (this.ownerUUID != null)
			return this.ownerUUID.toString();
		else
			return "";
	}
}

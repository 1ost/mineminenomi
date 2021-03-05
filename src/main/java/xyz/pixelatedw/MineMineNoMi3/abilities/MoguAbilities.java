package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.*;

public class MoguAbilities
{

	static
	{
	}

	public static Ability[] abilitiesArray = new Ability[] { new PowerPoint(), new MoguraBanana(), new MoguraTonpo() };

	public static class MoguraTonpo extends Ability
	{
		
		private int initialY;
		private boolean breakBlocks;

		public MoguraTonpo()
		{
			super(ListAttributes.MOGURA_TONPO);
		}

		public void use(EntityPlayer player)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if (!this.isOnCooldown)
			{
				if (MainConfig.enableGriefing)
				{
					if (props.getZoanPoint().equals("power"))
					{
						if(player.isSneaking())
						{
							int i = 0;
							for (int x = -1; x < 1; x++)
								for (int y = 0; y < 10; y++)
									for (int z = -1; z < 1; z++)
									{
										int posX = (int) player.posX + x;
										int posY = (int) player.posY - y;
										int posZ = (int) player.posZ + z;
										
										player.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 50, 100, true, false));
										player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 400, 2, true, false));
		
										Block tempBlock = player.world.getBlock(posX, posY, posZ);
										if (DevilFruitsHelper.placeBlockIfAllowed(player.world, posX, posY, posZ, Blocks.AIR, "all", "restricted", "ignore liquid"))
										{
											player.inventory.addItemStackToInventory(new ItemStack(tempBlock));
											WyNetworkHelper.sendToAllAround(new PacketParticles(ID.PARTICLEFX_BAKUMUNCH, posX, posY, posZ), player.dimension, posX, posY, posZ, ID.GENERIC_PARTICLES_RENDER_DISTANCE);
										}
									}
							this.attr.setAbilityCooldown(2);
						}
						else
						{
							double mX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
							double mZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
							
							this.initialY = (int) player.posY;
							this.breakBlocks = true;
							
							double f2 = MathHelper.sqrt(mX * mX + player.motionY * player.motionY + mZ * mZ);
							mX /= (double)f2;
							mZ /= (double)f2;
							mX += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
							mZ += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
							mX *= 4;
							mZ *= 4;
						
							motion("=", mX, player.motionY, mZ, player);
							
							this.attr.setAbilityCooldown(10);
						}
						
						super.use(player);
					}
					else
						WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used while Mole Point is active !");
				}
			}
		}
		
	    public void duringCooldown(EntityPlayer player, int currentCooldown)
	    {
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if(currentCooldown > 100 && player.posY >= this.initialY && props.getZoanPoint().equals("power"))
			{
				for(int[] location : WyHelper.getBlockLocationsNearby(player, 2))
				{
					if(location[1] >= player.posY)
					{
						Block tempBlock = player.world.getBlock(location[0], location[1], location[2]);
						if(DevilFruitsHelper.placeBlockIfAllowed(player.world, location[0], location[1], location[2], Blocks.AIR, "core", "foliage"))
						{
							player.inventory.addItemStackToInventory(new ItemStack(tempBlock));
							WyNetworkHelper.sendToAllAround(new PacketParticles(ID.PARTICLEFX_BAKUMUNCH, location[0], location[1], location[2]), player.dimension, location[0], location[1], location[2], ID.GENERIC_PARTICLES_RENDER_DISTANCE);
							if (player.world instanceof WorldServer)
								((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
						}
					}
				}
			}
			else if(currentCooldown < 10)
			{
				this.breakBlocks = false;
			}
	    }
	}

	public static class MoguraBanana extends Ability
	{
		public MoguraBanana()
		{
			super(ListAttributes.MOGURA_BANANA);
		}

		public void startPassive(EntityPlayer player)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);
			AbilityProperties abilityProps = AbilityProperties.get(player);
			
			if (!props.getZoanPoint().equals("power"))
			{
				this.setPassiveActive(false);
				WyNetworkHelper.sendTo(new PacketAbilitySync(abilityProps), (EntityPlayerMP) player);
				WyHelper.sendMsgToPlayer(player, "" + this.getAttribute().getAttributeName() + " can only be used while Power Point is active !");
			}
		}

		public void hitEntity(EntityPlayer player, EntityLivingBase target)
		{
			double mX = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
			double mZ = (double)(MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4);
				
			double f2 = MathHelper.sqrt(mX * mX + player.motionY * player.motionY + mZ * mZ);
			mX /= (double)f2;
			mZ /= (double)f2;
			mX += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
			mZ += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
			mX *= 2.5;
			mZ *= 2.5;
			
			WyNetworkHelper.sendToAll(new PacketEntityVelocity(target.getEntityId(), mX, 0.1, mZ));

			super.hitEntity(player, target);

		}
	}

	public static class PowerPoint extends Ability
	{
		public PowerPoint()
		{
			super(ListAttributes.MOGU_POWER_POINT);
		}

		public void passive(EntityPlayer player)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if (!this.isOnCooldown && (props.getZoanPoint().equalsIgnoreCase("n/a") || props.getZoanPoint().equalsIgnoreCase("power")))
			{
				super.passive(player);
			}
		}

		public void startPassive(EntityPlayer player)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if (props.getZoanPoint().isEmpty())
				props.setZoanPoint("n/a");

			 player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), Integer.MAX_VALUE, 3, true, false));

			WyNetworkHelper.sendTo(new PacketNewAABB(0.5F, 1.5F), (EntityPlayerMP) player);

			props.setZoanPoint("power");
			WyNetworkHelper.sendTo(new PacketSync(props), (EntityPlayerMP) player);
			WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getName(), props));
		}

		public void endPassive(EntityPlayer player)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			player.removePotionEffect(Potion.getPotionById(3));

			WyNetworkHelper.sendTo(new PacketNewAABB(0.6F, 1.8F), (EntityPlayerMP) player);

			props.setZoanPoint("n/a");
			WyNetworkHelper.sendTo(new PacketSync(props), (EntityPlayerMP) player);
			WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getName(), props));
		}
	}

	private static void motion(String c, double x, double y, double z, EntityPlayer p)
	{
		WyNetworkHelper.sendTo(new PacketPlayer("motion" + c, x, y, z), (EntityPlayerMP) p);
	}
}

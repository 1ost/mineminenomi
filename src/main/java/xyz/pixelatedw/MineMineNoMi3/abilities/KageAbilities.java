package xyz.pixelatedw.MineMineNoMi3.abilities;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.RayTraceResult;
import xyz.pixelatedw.MineMineNoMi3.Values;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.KageProjectiles;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.KageProjectiles.TsunotokagePillar;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDoppelman;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListExtraAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class KageAbilities
{
	
	static
	{
		Values.abilityWebAppExtraParams.put("brickbat", new String[] {"desc", "Launches bats created from the user's shadow at the the opponent."});
		Values.abilityWebAppExtraParams.put("blackbox", new String[] {"desc", "Encases and suffocates the opponent in a box made of shadows."});
		Values.abilityWebAppExtraParams.put("tsunotokage", new String[] {"desc", "The user creates a lizard-like shadow under his opponent, which pierces them from below."});
		Values.abilityWebAppExtraParams.put("doppelman", new String[] {"desc", "Creates a living version of the user's shadow to help them fight."});
	}

	public static Ability[] abilitiesArray = new Ability[]
	{
			new Doppelman(), new Kagemusha(), new BrickBat(), new BlackBox(), new Tsunotokage()
	};

	public static class BrickBat extends Ability
	{
		public BrickBat()
		{
			super(ListAttributes.BRICK_BAT);
		}

		public void use(EntityPlayer player)
		{
			this.projectile = new KageProjectiles.BrickBat(player.world, player, attr);
			super.use(player);
		}
	}

	public static class Kagemusha extends Ability
	{
		public Kagemusha()
		{
			super(ListAttributes.KAGEMUSHA);
		}

		public void use(EntityPlayer player)
		{
			if (!WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).isEmpty())
			{
				EntityDoppelman dopp = (EntityDoppelman) WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).stream().findFirst().orElse(null);

				if (dopp != null)
				{
					int[] auxPos = new int[]
					{
							(int) player.posX, (int) player.posY, (int) player.posZ
					};
					player.setPositionAndUpdate(dopp.posX, dopp.posY, dopp.posZ);
					dopp.setPositionAndUpdate(auxPos[0], auxPos[1], auxPos[2]);
				}
			}
			else
			{
				WyHelper.sendMsgToPlayer(player, "Your Doppelman is too far away");
			}
			super.use(player);
		}
	}

	public static class Doppelman extends Ability
	{
		private EntityDoppelman doppelman;

		public Doppelman()
		{
			super(ListAttributes.DOPPELMAN);
		}

		public void passive(EntityPlayer player)
		{			
			if(this.passiveActive && player.isSneaking() && doppelman != null)
			{
				doppelman.isAggressive = !doppelman.isAggressive;
				WyHelper.sendMsgToPlayer(player, "Your Doppelman is now " + (doppelman.isAggressive ? "aggressive" : "defensive"));
			}
			else
				super.passive(player);
		}
		
		public void startPassive(EntityPlayer player)
		{
			doppelman = new EntityDoppelman(player.world, player);
			doppelman.setPositionAndRotation(player.posX, player.posY, player.posZ, 180, 0);
			player.world.spawnEntity(doppelman);
		}

		public void endPassive(EntityPlayer player)
		{
			if (!WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).isEmpty())
				WyHelper.getEntitiesNear(player, 20, EntityDoppelman.class).forEach(x -> x.setDead());

			this.startCooldown();
			this.startExtUpdate(player);
		}

	}

	public static class BlackBox extends Ability
	{
		public BlackBox()
		{
			super(ListAttributes.BLACK_BOX);
		}

		public void use(EntityPlayer player)
		{
			this.projectile = new KageProjectiles.BlackBox(player.world, player, attr);
			super.use(player);
		}
	}

	public static class Tsunotokage extends Ability
	{
		public Tsunotokage()
		{
			super(ListAttributes.TSUNOTOKAGE);
		}

		public void use(EntityPlayer player)
		{
			if (!this.isOnCooldown)
			{
				RayTraceResult mop = WyHelper.rayTraceBlocks(player);

				if (mop != null)
				{
					double i = mop.getBlockPos().getX();
					double j = mop.getBlockPos().getY();
					double k = mop.getBlockPos().getZ();

					TsunotokagePillar pillar = new TsunotokagePillar(player.world, player, ListExtraAttributes.TSUNOTOKAGE_PILLAR);
					pillar.setLocationAndAngles(i, j + 1, k, 0, 0);
					pillar.motionX = 0;
					pillar.motionZ = 0;
					pillar.motionY = 0.7;
					player.world.spawnEntity(pillar);
				}

				super.use(player);
			}
		}
	}
}

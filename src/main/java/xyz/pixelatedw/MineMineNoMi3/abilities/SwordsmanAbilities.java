package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.Values;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.SwordsmanProjectiles;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.helpers.ItemsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketParticles;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketPlayer;

public class SwordsmanAbilities 
{
	static
	{
		Values.abilityWebAppExtraParams.put("shishishisonson", new String[] {"desc", "The user dashes forward and rapidly slashes the opponent."});
		Values.abilityWebAppExtraParams.put("yakkodori", new String[] {"desc", "Launches a crescent moon-shaped slash, which destroys everything in its path."});
		Values.abilityWebAppExtraParams.put("sanbyakurokujupoundho", new String[] {"desc", "The user launches a powerful slash, causing great destruction."});
		Values.abilityWebAppExtraParams.put("otatsumaki", new String[] {"desc", "By spinning, the user creates a small tornado, which slashes and weakens nearby opponents."});
	}
	
	public static final Ability SHI_SHISHI_SONSON = new ShiShishiSonson();
	public static final Ability SANBYAKUROKUJU_POUND_HO = new SanbyakurokujuPoundHo();
	public static final Ability YAKKODORI = new Yakkodori();
	public static final Ability OTATSUMAKI = new OTatsumaki();
	
	public static Ability[] abilitiesArray = new Ability[] {SHI_SHISHI_SONSON, SANBYAKUROKUJU_POUND_HO, YAKKODORI, OTATSUMAKI};
	
	public static class OTatsumaki extends Ability
	{
		public OTatsumaki() 
		{
			super(ListAttributes.O_TATSUMAKI); 
		}
			
		@Override
		public void use(EntityPlayer player)
		{
			if (!ItemsHelper.isSword(player.getHeldItem(EnumHand.MAIN_HAND)) && !DevilFruitsHelper.canUseSwordsmanAbilities(player))
			{
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
				return;
			}
			
			if(!this.isOnCooldown)
			{
		    	ExtendedEntityData props = ExtendedEntityData.get(player);

				for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 2.5))
				{
					e.attackEntityFrom(DamageSource.causePlayerDamage(player), 12 * props.getDamageMultiplier());					
					e.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 10 * 20, 1, true, false));
				}
					
				WyNetworkHelper.sendToAllAround(new PacketParticles(ID.PARTICLEFX_KOKUTEICROSS, player), player.dimension, player.posX, player.posY, player.posZ, ID.GENERIC_PARTICLES_RENDER_DISTANCE);
					
				if (player.world instanceof WorldServer)
					((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
			}
			super.use(player);
		}
	}
	
	public static class Yakkodori extends Ability
	{
		public Yakkodori() 
		{
			super(ListAttributes.YAKKODORI); 
		}
			
		@Override
		public void use(EntityPlayer player)
		{
			if (!ItemsHelper.isSword(player.getHeldItem(EnumHand.MAIN_HAND)) && !DevilFruitsHelper.canUseSwordsmanAbilities(player))
			{
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
				return;
			}
			
			this.projectile = new SwordsmanProjectiles.Yakkodori(player.world, player, ListAttributes.YAKKODORI);
			if(!this.isOnCooldown)
				if (player.world instanceof WorldServer)
					((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
			super.use(player);
		}
	}
	
	public static class ShiShishiSonson extends Ability
	{
		public ShiShishiSonson() 
		{
			super(ListAttributes.SHI_SHISHI_SONSON); 
		}
			
		@Override
		public void use(EntityPlayer player)
		{
			if (!ItemsHelper.isSword(player.getHeldItem(EnumHand.MAIN_HAND)) && !DevilFruitsHelper.canUseSwordsmanAbilities(player))
			{
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
				return;
			}
			
			if(!this.isOnCooldown)
			{
				double mX = -MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4;
				double mZ = MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * 0.4;
						
				double f2 = MathHelper.sqrt(mX * mX + player.motionY * player.motionY + mZ * mZ);
				mX /= f2;
				mZ /= f2;
				mX += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
				mZ += player.world.rand.nextGaussian() * 0.007499999832361937D * 1.0;
				mX *= 3;
				mZ *= 3;
				
				motion("=", mX, player.motionY, mZ, player);
					
				if (player.world instanceof WorldServer)
					((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
			}
				
			super.use(player);
		}
		
	    @Override
		public void duringCooldown(EntityPlayer player, int currentCooldown)
	    {
	    	ExtendedEntityData props = ExtendedEntityData.get(player);

			if(currentCooldown > 4 * 20)
			{
				for(EntityLivingBase e : WyHelper.getEntitiesNear(player, 1.6))
					e.attackEntityFrom(DamageSource.causePlayerDamage(player), 8 * props.getDamageMultiplier());
			}
	    }
	}
	
	public static class SanbyakurokujuPoundHo extends Ability
	{
		public SanbyakurokujuPoundHo() 
		{
			super(ListAttributes.SANBYAKUROKUJU_POUND_HO); 
		}
			
		@Override
		public void use(EntityPlayer player)
		{
			if (!ItemsHelper.isSword(player.getHeldItem(EnumHand.MAIN_HAND)) && !DevilFruitsHelper.canUseSwordsmanAbilities(player))
			{
				WyHelper.sendMsgToPlayer(player, "You need a sword to use this ability !");
				return;
			}
			
			this.projectile = new SwordsmanProjectiles.SanbyakurokujuPoundHo(player.world, player, ListAttributes.SANBYAKUROKUJU_POUND_HO);
			if(!this.isOnCooldown)
				if (player.world instanceof WorldServer)
					((WorldServer)player.world).getEntityTracker().sendToTracking(player, new SPacketAnimation(player, 0));
			super.use(player);
		}
	}
	
	private static void motion(String c, double x, double y, double z, EntityPlayer p)
	{
		WyNetworkHelper.sendTo(new PacketPlayer("motion" + c, x, y, z), (EntityPlayerMP) p);
	}
}

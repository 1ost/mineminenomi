package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTextures;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class VenomDemonParticleEffect extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		for (int i = 0; i < 3; i++)
		{
			double offsetX = WyMathHelper.randomWithRange(-2, 2) + WyMathHelper.randomDouble();
			double offsetY = WyMathHelper.randomWithRange(-2, 0) + WyMathHelper.randomDouble();
			double offsetZ = WyMathHelper.randomWithRange(-2, 2) + WyMathHelper.randomDouble();
			
			SimpleParticle cp = new SimpleParticle(world, ModParticleTextures.DOKU,
					posX + offsetX, 
					posY + 1 + offsetY,
					posZ + offsetZ, 
					0, 0, 0)
					.setParticleAge((int) (1 + WyMathHelper.randomWithRange(0, 4))).setParticleScale(1.5F);
			cp.setColor(1, 0, 0);
			
			Minecraft.getInstance().particles.addEffect(cp);
		}
	}

}
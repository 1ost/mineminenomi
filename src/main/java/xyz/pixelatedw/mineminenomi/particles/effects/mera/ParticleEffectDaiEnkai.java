package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.api.math.WyMathHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTextures;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ParticleEffectDaiEnkai extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		for (int i = 0; i < 10; i++)
		{
			double offsetX = WyMathHelper.randomWithRange(-3, 3) + WyMathHelper.randomDouble();
			double offsetY = WyMathHelper.randomWithRange(-3, 3) + WyMathHelper.randomDouble();
			double offsetZ = WyMathHelper.randomWithRange(-3, 3) + WyMathHelper.randomDouble();
			
	        motionX = WyMathHelper.randomWithRange(-1, 1) + WyMathHelper.randomDouble();
	        motionY = WyMathHelper.randomWithRange(-1, 1) + WyMathHelper.randomDouble();
	        motionZ = WyMathHelper.randomWithRange(-1, 1) + WyMathHelper.randomDouble();
	        
            double middlePoint = 0.5D / (5 / 0.5);
            middlePoint *= (WyMathHelper.randomDouble() * 2) + 0.3F;
	        
	        motionX *= middlePoint / 2;
	        motionY *= middlePoint / 2;
	        motionZ *= middlePoint / 2;
			
			SimpleParticle cp = new SimpleParticle(world, ModParticleTextures.MERA,
					posX + offsetX, 
					posY + 1.5 + offsetY,
					posZ + offsetZ, 
					motionX,
					motionY + 0.05,
					motionZ)
					.setParticleScale(1.3F)
					.setParticleAge(10);
			Minecraft.getInstance().particles.addEffect(cp);
	
		}
	}

}

package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.wypi.WyHelper;

public class KorouzuParticleEffect extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		for (int i = 0; i < 30; i++)
		{
			double offsetX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
			double offsetY = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
			double offsetZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
			
			SimpleParticle cp = new SimpleParticle(world, ModResources.DARKNESS,
					posX + offsetX , 
					posY + offsetY,
					posZ + offsetZ, 
					0, 0, 0)
					.setParticleScale(3F).setParticleGravity(0).setParticleAge(1);		
			Minecraft.getInstance().particles.addEffect(cp);
		}
	}

}

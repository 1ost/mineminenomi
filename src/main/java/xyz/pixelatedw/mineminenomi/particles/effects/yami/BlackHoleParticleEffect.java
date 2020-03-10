package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.particles.SimpleParticle;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.wypi.WyHelper;

public class BlackHoleParticleEffect extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		for (int i = 0; i < 1024; i++)
		{
			double offsetX = WyHelper.randomWithRange(-10, 10) + WyHelper.randomDouble();
			double offsetZ = WyHelper.randomWithRange(-10, 10) + WyHelper.randomDouble();
			
			SimpleParticle cp = new SimpleParticle(world, ModResources.DARKNESS,
					posX - 1 + offsetX, 
					posY - 0.5 + WyHelper.randomDouble(),
					posZ - 1 + offsetZ, 
					0, 0, 0)
					.setParticleGravity(-1 + world.rand.nextFloat()).setParticleScale((float) (1 + WyHelper.randomWithRange(0, 3)));
			Minecraft.getInstance().particles.addEffect(cp);
		}
	}

}

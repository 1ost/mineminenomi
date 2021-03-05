package xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.common;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.ParticleEffect;

public class ParticleEffectAboveHead extends ParticleEffect
{
	private String particle;
	
	public ParticleEffectAboveHead(String particle)
	{
		this.particle = particle;
	}
	
	@Override
	public void spawn(EntityPlayer player, double posX, double posY, double posZ)
	{
		Random rand = player.world.rand;
		for (int i = 0; i < 5; ++i)
		{
			double locX = posX + WyMathHelper.randomDouble();
			double locY = posY + WyMathHelper.randomDouble();
			double locZ = posZ + WyMathHelper.randomDouble();

			double motionX = rand.nextGaussian() * 0.02D;
			double motionY = rand.nextGaussian() * 0.02D;
			double motionZ = rand.nextGaussian() * 0.02D;
			
			
			player.world.spawnParticle(EnumParticleTypes.valueOf(this.particle), locX, locY, locZ, motionX, motionY, motionZ);
		}
	}

}

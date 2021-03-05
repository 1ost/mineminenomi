package xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.ParticleEffect;

public class ParticleEffectCommonExplosion extends ParticleEffect
{

	public void spawn(EntityPlayer player, double posX, double posY, double posZ)
	{	
        player.world.spawnParticle(EnumParticleTypes.valueOf("hugeexplosion"), posX, posY, posZ, 1.0D, 0.0D, 0.0D);
		
		for (int i = 0; i < 256; i++)
		{
			int explosionSize = 5;
			
	        double motionX = WyMathHelper.randomWithRange(-2, 2) + player.world.rand.nextDouble();
	        double motionY = WyMathHelper.randomWithRange(-2, 2) + player.world.rand.nextDouble();
	        double motionZ = WyMathHelper.randomWithRange(-2, 2) + player.world.rand.nextDouble();
	        
            double middlePoint = 0.5D / (5 / explosionSize + 0.1D);
            middlePoint *= (double)(player.world.rand.nextFloat() * player.world.rand.nextFloat() + 0.3F);
	        
	        motionX *= middlePoint / 2;
	        motionY *= middlePoint / 2;
	        motionZ *= middlePoint / 2;
			
            player.world.spawnParticle(EnumParticleTypes.valueOf("explode"), posX, posY + 1, posZ, motionX, motionY, motionZ);
            player.world.spawnParticle(EnumParticleTypes.valueOf("smoke"), posX, posY + 1, posZ, motionX, motionY, motionZ);
		}
	}

}
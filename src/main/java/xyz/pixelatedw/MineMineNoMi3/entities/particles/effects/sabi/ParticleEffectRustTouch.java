package xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.sabi;

import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.ParticleEffect;

public class ParticleEffectRustTouch extends ParticleEffect
{

	public void spawn(EntityPlayer player, double posX, double posY, double posZ)
	{	
		for (int i = 0; i < 64; i++)
		{
	        double motionX = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        double motionY = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        double motionZ = WyMathHelper.randomWithRange(-3, 3) + player.world.rand.nextDouble();
	        
            double middlePoint = 0.1;
            middlePoint *= (double)(player.world.rand.nextFloat() * player.world.rand.nextFloat() + 0.3F);
	        
	        motionX *= middlePoint / 2;
	        motionY *= middlePoint / 2;
	        motionZ *= middlePoint / 2;
			
			MainMod.proxy.spawnCustomParticles(player, new EntityParticleFX(player.world, ID.PARTICLE_ICON_RUST,
					posX,
					posY + 1,
					posZ,
					motionX,
					motionY,
					motionZ)
			.setParticleScale(1).setParticleAge(10));	
		}
	}
}
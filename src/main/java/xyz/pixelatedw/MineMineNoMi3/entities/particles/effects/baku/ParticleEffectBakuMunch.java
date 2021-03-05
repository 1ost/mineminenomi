package xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.baku;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.api.EnumParticleTypes;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.effects.ParticleEffect;

public class ParticleEffectBakuMunch extends ParticleEffect
{

	public void spawn(EntityPlayer player, double posX, double posY, double posZ)
	{
		for (int i = 0; i < 15; i++)
		{
			double offsetX = player.world.rand.nextDouble();
			double offsetY = 1;
			double offsetZ = player.world.rand.nextDouble();

			player.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK.getParticleName() + "_" + Block.getIdFromBlock(player.world.getBlock((int)posX, (int)posY, (int)posZ))  + "_" + player.world.getBlockMetadata((int)posX, (int)posY, (int)posZ), posX + offsetX, posY + offsetY, posZ + offsetZ, 0, 0, 0);
		}
	}

}

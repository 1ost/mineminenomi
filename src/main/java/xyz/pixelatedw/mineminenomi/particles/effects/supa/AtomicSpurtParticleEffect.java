package xyz.pixelatedw.mineminenomi.particles.effects.supa;

import net.minecraft.block.BlockState;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.wypi.WyHelper;

public class AtomicSpurtParticleEffect extends ParticleEffect
{

	@Override
	public void spawn(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ)
	{
		for (int i = 0; i < 2; i++)
		{
			double offsetX = WyHelper.randomDouble() / 2;
			double offsetY = 0.25;
			double offsetZ = WyHelper.randomDouble() / 2;

			BlockState BlockState = world.getBlockState(new BlockPos(posX, posY, posZ).down());
			
			world.addParticle(
					new BlockParticleData(ParticleTypes.BLOCK, BlockState), 
					posX + offsetX, 
					posY, 
					posZ + offsetZ, 
					0, 
					0, 
					0);			
		}
	}

}

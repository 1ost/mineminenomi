package xyz.pixelatedw.MineMineNoMi3.abilities.effects;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class DFEffectNoroSlowness extends DFEffect
{

	public DFEffectNoroSlowness(EntityLivingBase entity, int timer)
	{
		super(entity, timer, ID.EXTRAEFFECT_NORO);
	}

	public void onEffectStart(EntityLivingBase entity)
	{

	}

	public void onEffectEnd(EntityLivingBase entity)
	{
		ExtendedEntityData props = ExtendedEntityData.get(entity);
		
		if(entity.isPotionActive(Potion.getPotionById(2)))
		{
			int timer = entity.getActivePotionEffect(event.getType(2).getDuration();
			new DFEffectNoroSlowness(entity, timer);
		}
	}
		
}

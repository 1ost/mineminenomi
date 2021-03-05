package xyz.pixelatedw.MineMineNoMi3.abilities.effects;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.pixelatedw.MineMineNoMi3.ID;

public class DFEffectTensionHormone extends DFEffect
{

	public DFEffectTensionHormone(EntityLivingBase entity, int timer)
	{
		super(entity, timer, ID.EXTRAEFFECT_TENSIONHORMONE);
	}

	public void onEffectStart(EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(Potion.getPotionById(1).id, 600, 1));
		entity.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 600, 1));
		entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 1));
		entity.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 600, 1));
	}

	public void onEffectEnd(EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 300, 1));
	}
		
}

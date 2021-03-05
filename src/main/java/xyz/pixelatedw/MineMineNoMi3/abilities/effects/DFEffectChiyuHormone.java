package xyz.pixelatedw.MineMineNoMi3.abilities.effects;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.pixelatedw.MineMineNoMi3.ID;

public class DFEffectChiyuHormone extends DFEffect
{

	public DFEffectChiyuHormone(EntityLivingBase entity, int timer)
	{
		super(entity, timer, ID.EXTRAEFFECT_CHIYUHORMONE);
	}

	public void onEffectStart(EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 100, 2));
	}

	public void onEffectEnd(EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 1));
	}
}

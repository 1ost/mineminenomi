package xyz.pixelatedw.MineMineNoMi3.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;

public class CommonProxy 
{

	public void preInit(){}
	public void init(){}
	
	public EntityPlayer getPlayerEntity (MessageContext ctx) 
	{
        return ctx.getServerHandler().player;
	}

	public void spawnCustomParticles(Entity theEntity, EntityParticleFX particle) { }

	public void openQuestYesOrNoWorkaround(EntityPlayer player, EnumQuestlines questline) { }

	//New
	public String translate(String s) {
		return s;
	}
	public void registerItemRenderer(Item item, int meta, String id) {
		// Do nothing
	}
}

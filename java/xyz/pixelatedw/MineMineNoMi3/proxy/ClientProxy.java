package xyz.pixelatedw.MineMineNoMi3.proxy;

import java.util.ArrayList;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityRenderer;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.models.ModelFlameDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.models.ModelImpactDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.models.ModelMilkyDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.models.ModelRejectDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityAxeDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityEisenDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityFlameDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityImpactDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityMilkyDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.dials.tileentities.TileEntityRejectDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.models.ModelWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.blocks.renderers.RenderBlockDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.blocks.renderers.RenderBlockDial;
import xyz.pixelatedw.MineMineNoMi3.blocks.renderers.RenderBlockWantedPoster;
import xyz.pixelatedw.MineMineNoMi3.blocks.renderers.RenderBlockWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityWantedPoster;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.TileEntityWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.MobRenderer;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityArlong;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityChew;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityKuroobi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.models.ModelArlong;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.models.ModelChew;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.models.ModelKuroobi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.bandits.EntityBandit;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.baroqueWorks.EntityMr0;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.baroqueWorks.models.ModelMr0;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityDonKrieg;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityGin;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityPearl;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.models.ModelGin;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.models.ModelKrieg;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.models.ModelPearl;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarine;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineWithGun;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMorgan;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.models.ModelMarine;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.models.ModelMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.models.ModelMarineWithGun;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.models.ModelMorgan;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDoppelman;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.models.ModelDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.models.ModelDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.renderers.RenderDoppelman;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirate;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateWithGun;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityMokuPower;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityZouFull;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityZouHybrid;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityBlueno;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityFukuro;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityJabra;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityJabraL;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityKaku;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityKakuL;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityKalifa;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityKumadori;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityLucci;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntityLucciL;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.EntitySpandam;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelBlueno;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelFukuro;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelJabra;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelJabraWolf;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelKaku;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelKakuGiraffe;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelKalifa;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelKumadori;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelLucci;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelLucciLeopard;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.models.ModelSpandam;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.RenderZoanMorph;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelMoguPower;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelZouFull;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelZouHybrid;
import xyz.pixelatedw.MineMineNoMi3.lists.ListDevilFruits;

public class ClientProxy extends CommonProxy
{

	public void preInit()
	{

	}
	
	public void init()
	{	
		for(int i = 0; i < ListDevilFruits.ALL_ENTITIES.length; i++)
		{
			for(int j = 0; j < ((ArrayList)ListDevilFruits.ALL_ENTITIES[i]).size(); j++)
			{
				RenderingRegistry.registerEntityRenderingHandler(((Class)((Object[])((ArrayList)ListDevilFruits.ALL_ENTITIES[i]).get(j))[0]), new AbilityRenderer(((AbilityAttribute)((Object[])((ArrayList)ListDevilFruits.ALL_ENTITIES[i]).get(j))[1])));
			}
		}
		
		//Bandits
		RenderingRegistry.registerEntityRenderingHandler(EntityBandit.class, new MobRenderer(new ModelMarine()));
		
		//Marines
		RenderingRegistry.registerEntityRenderingHandler(EntityMarine.class, new MobRenderer(new ModelMarine()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMarineWithGun.class, new MobRenderer(new ModelMarineWithGun()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMarineCaptain.class, new MobRenderer(new ModelMarineCaptain()));
		RenderingRegistry.registerEntityRenderingHandler(EntityMorgan.class, new MobRenderer(new ModelMorgan(), "morgan"));
		
		//W.GOV
		RenderingRegistry.registerEntityRenderingHandler(EntityLucci.class, new MobRenderer(new ModelLucci(), "lucci"));
		RenderingRegistry.registerEntityRenderingHandler(EntityLucciL.class, new MobRenderer(new ModelLucciLeopard(), "luccil"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKaku.class, new MobRenderer(new ModelKaku(), "kaku"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKakuL.class, new MobRenderer(new ModelKakuGiraffe(), "kakul"));
		RenderingRegistry.registerEntityRenderingHandler(EntityJabra.class, new MobRenderer(new ModelJabra(), "jabra"));
		RenderingRegistry.registerEntityRenderingHandler(EntityJabraL.class, new MobRenderer(new ModelJabraWolf(), "jabral"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKalifa.class, new MobRenderer(new ModelKalifa(), "kalifa"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKumadori.class, new MobRenderer(new ModelKumadori(), "kumadori"));
		RenderingRegistry.registerEntityRenderingHandler(EntityFukuro.class, new MobRenderer(new ModelFukuro(), "fukuro"));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlueno.class, new MobRenderer(new ModelBlueno(), "blueno"));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpandam.class, new MobRenderer(new ModelSpandam(), "spandam")); 
				
		//Pirates
		RenderingRegistry.registerEntityRenderingHandler(EntityPirate.class, new MobRenderer(new ModelMarine()));
		RenderingRegistry.registerEntityRenderingHandler(EntityPirateWithGun.class, new MobRenderer(new ModelMarineWithGun()));		
		RenderingRegistry.registerEntityRenderingHandler(EntityPirateCaptain.class, new MobRenderer(new ModelMarine()));
		//Arlong Pirates
		RenderingRegistry.registerEntityRenderingHandler(EntityArlong.class, new MobRenderer(new ModelArlong(), "arlong"));
		RenderingRegistry.registerEntityRenderingHandler(EntityChew.class, new MobRenderer(new ModelChew(), "chew"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKuroobi.class, new MobRenderer(new ModelKuroobi(), "kuroobi"));
		//Krieg Pirates
		RenderingRegistry.registerEntityRenderingHandler(EntityDonKrieg.class, new MobRenderer(new ModelKrieg(), "krieg"));
		RenderingRegistry.registerEntityRenderingHandler(EntityGin.class, new MobRenderer(new ModelGin(), "gin"));
		RenderingRegistry.registerEntityRenderingHandler(EntityPearl.class, new MobRenderer(new ModelPearl(), "pearl"));
		//Baroque Works Pirates
		RenderingRegistry.registerEntityRenderingHandler(EntityMr0.class, new MobRenderer(new ModelMr0(), "mr0"));
		
		//Others
		RenderingRegistry.registerEntityRenderingHandler(EntityDoppelman.class, new RenderDoppelman());
		RenderingRegistry.registerEntityRenderingHandler(EntityDenDenMushi.class, new MobRenderer(new ModelDenDenMushi(), "denden"));
		RenderingRegistry.registerEntityRenderingHandler(EntityDojoSensei.class, new MobRenderer(new ModelDojoSensei()));
		RenderingRegistry.registerEntityRenderingHandler(EntityWantedPostersPackage.class, new MobRenderer(new ModelWantedPostersPackage(), "wantedposterspackage"));
		
		//Special Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDenDenMushi.class, new RenderBlockDenDenMushi());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWantedPoster.class, new RenderBlockWantedPoster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWantedPostersPackage.class, new RenderBlockWantedPostersPackage());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAxeDial.class, new RenderBlockDial(new ModelImpactDial(), "axedial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEisenDial.class, new RenderBlockDial(new ModelMilkyDial(), "eisendial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlameDial.class, new RenderBlockDial(new ModelFlameDial(), "flamedial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityImpactDial.class, new RenderBlockDial(new ModelImpactDial(), "impactdial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMilkyDial.class, new RenderBlockDial(new ModelMilkyDial(), "milkydial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRejectDial.class, new RenderBlockDial(new ModelRejectDial(), "rejectdial"));
		
		//Temp Shit
		RenderingRegistry.registerEntityRenderingHandler(TempEntityZouHybrid.class, new RenderZoanMorph(new ModelZouHybrid(), "zouhybrid"));
		RenderingRegistry.registerEntityRenderingHandler(TempEntityZouFull.class, new RenderZoanMorph(new ModelZouFull(), "zoufull"));
		RenderingRegistry.registerEntityRenderingHandler(TempEntityMokuPower.class, new RenderZoanMorph(new ModelMoguPower(), "mokupower"));

	}
	
	public void spawnCustomParticles(Entity theEntity, EntityParticleFX particle)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

}

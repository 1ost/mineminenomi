package xyz.pixelatedw.MineMineNoMi3.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityRenderer;
import xyz.pixelatedw.MineMineNoMi3.blocks.tileentities.*;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals.EntityDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals.EntityKungFuDugong;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals.EntityLapahn;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.animals.EntityYagaraBull;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityArlong;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityChew;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.arlongPirates.EntityKuroobi;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.bandits.EntityBandit;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.baroqueWorks.EntityMr0;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityDonKrieg;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityGin;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.kriegPirates.EntityPearl;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarine;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMarineWithGun;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.marines.EntityMorgan;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityBlackKnight;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityDoppelman;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityMirageClone;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.misc.EntityWantedPostersPackage;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityFatPirate;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirate;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateCaptain;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.pirates.EntityPirateWithGun;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.givers.EntityDojoSensei;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.objectives.EntitySniperTarget;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.quest.objectives.models.ModelSniperTarget;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityDugong;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityLapahn;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.temp.TempEntityYagaraBull;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.worldGovernment.*;
import xyz.pixelatedw.MineMineNoMi3.entities.particles.EntityParticleFX;
import xyz.pixelatedw.MineMineNoMi3.gui.GUIQuestYesNo;
import xyz.pixelatedw.MineMineNoMi3.lists.ListDevilFruits;
import xyz.pixelatedw.MineMineNoMi3.models.blocks.*;
import xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.animals.ModelDenDenMushi;
import xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.animals.ModelKungFuDugong;
import xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.animals.ModelLapahn;
import xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.animals.ModelYagaraBull;
import xyz.pixelatedw.MineMineNoMi3.models.entities.mobs.humanoids.*;
import xyz.pixelatedw.MineMineNoMi3.quests.EnumQuestlines;
import xyz.pixelatedw.MineMineNoMi3.renderers.blocks.*;
import xyz.pixelatedw.MineMineNoMi3.renderers.entities.MobRenderer;
import xyz.pixelatedw.MineMineNoMi3.renderers.entities.mobs.humanoids.RenderBlackKnight;
import xyz.pixelatedw.MineMineNoMi3.renderers.entities.mobs.humanoids.RenderDoppelman;

import java.util.ArrayList;

public class ClientProxy extends CommonProxy
{


	@Override
	public void preInit()
	{

	}
	
	@Override
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
		RenderingRegistry.registerEntityRenderingHandler(EntityFatPirate.class, new MobRenderer(new ModelFatPirate(), 1.25F, null));
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
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackKnight.class, new RenderBlackKnight());
		RenderingRegistry.registerEntityRenderingHandler(EntityMirageClone.class, new RenderBlackKnight());
		RenderingRegistry.registerEntityRenderingHandler(EntityDojoSensei.class, new MobRenderer(new ModelDojoSensei()));
		RenderingRegistry.registerEntityRenderingHandler(EntityWantedPostersPackage.class, new MobRenderer(new ModelWantedPostersPackage(), "wantedposterspackage"));
		RenderingRegistry.registerEntityRenderingHandler(EntitySniperTarget.class, new MobRenderer(new ModelSniperTarget(), "snipertarget"));
		
		//Animals
		RenderingRegistry.registerEntityRenderingHandler(EntityDenDenMushi.class, new MobRenderer(new ModelDenDenMushi(), "denden"));
		RenderingRegistry.registerEntityRenderingHandler(EntityKungFuDugong.class, new MobRenderer(new ModelKungFuDugong(), "kungfudugong"));
		RenderingRegistry.registerEntityRenderingHandler(EntityLapahn.class, new MobRenderer(new ModelLapahn(), "lapahn"));
		RenderingRegistry.registerEntityRenderingHandler(EntityYagaraBull.class, new MobRenderer(new ModelYagaraBull(), 1.5F, null));
		
		//TEMP
		RenderingRegistry.registerEntityRenderingHandler(TempEntityDugong.class, new MobRenderer(new ModelKungFuDugong(), "kungfudugong"));
		RenderingRegistry.registerEntityRenderingHandler(TempEntityLapahn.class, new MobRenderer(new ModelLapahn(), "lapahn"));
		RenderingRegistry.registerEntityRenderingHandler(TempEntityYagaraBull.class, new MobRenderer(new ModelYagaraBull(), 2F, "yagarabull"));
		
		//Special Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDenDenMushi.class, new RenderBlockDenDenMushi());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWantedPoster.class, new RenderBlockWantedPoster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWantedPostersPackage.class, new RenderBlockWantedPostersPackage());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCannon.class, new RenderBlockCannon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySakeFeast.class, new RenderBlockSakeFeast());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAxeDial.class, new RenderBlockDial(new ModelImpactDial(), "axedial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEisenDial.class, new RenderBlockDial(new ModelMilkyDial(), "eisendial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlameDial.class, new RenderBlockDial(new ModelFlameDial(), "flamedial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityImpactDial.class, new RenderBlockDial(new ModelImpactDial(), "impactdial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMilkyDial.class, new RenderBlockDial(new ModelMilkyDial(), "milkydial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRejectDial.class, new RenderBlockDial(new ModelRejectDial(), "rejectdial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFlashDial.class, new RenderBlockDial(new ModelImpactDial(), "flashdial"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBreathDial.class, new RenderBlockDial(new ModelMilkyDial(), "breathdial"));

		//Temp Shit
		//RenderingRegistry.registerEntityRenderingHandler(TempEntityDummy.class, new RenderZoanMorph(new Model(), texture));

		//Items
		/*MinecraftForgeClient.registerItemRenderer(ListMisc.MarineSword, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Scissors, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Yoru, new RenderCoreWeapon());		
		MinecraftForgeClient.registerItemRenderer(ListMisc.Hammer5t, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Hammer10t, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Hammer100t, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.SoulSolid, new RenderCoreWeapon());
		
		MinecraftForgeClient.registerItemRenderer(ListMisc.IceSaber, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.AmaNoMurakumo, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.NoroNoroBeamSword, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.DoruDoruArtsKen, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.BlueSword, new RenderCoreWeapon());
		MinecraftForgeClient.registerItemRenderer(ListMisc.TabiraYuki, new RenderCoreWeapon());

		MinecraftForgeClient.registerItemRenderer(ListMisc.PirateCutlass, new RenderCoreWeapon2());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Kikoku, new RenderCoreWeapon2());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Jitte, new RenderCoreWeapon2());
		
		MinecraftForgeClient.registerItemRenderer(ListMisc.Biseto, new RenderWeaponBisento());
		MinecraftForgeClient.registerItemRenderer(ListMisc.BoStick, new RenderWeaponBisento());
		MinecraftForgeClient.registerItemRenderer(ListMisc.ClimaTact, new RenderWeaponBisento());
		MinecraftForgeClient.registerItemRenderer(ListMisc.PerfectClimaTact, new RenderWeaponBisento());
		MinecraftForgeClient.registerItemRenderer(ListMisc.SorceryClimaTact, new RenderWeaponBisento());

		MinecraftForgeClient.registerItemRenderer(ListMisc.WadoIchimonji, new RenderWeaponKatana());
		MinecraftForgeClient.registerItemRenderer(ListMisc.SandaiKitetsu, new RenderWeaponKatana());
		MinecraftForgeClient.registerItemRenderer(ListMisc.NidaiKitetsu, new RenderWeaponKatana());
		MinecraftForgeClient.registerItemRenderer(ListMisc.Shusui, new RenderWeaponKatana());
		
		MinecraftForgeClient.registerItemRenderer(ListMisc.Pipe, new RenderWeaponPipe());

		MinecraftForgeClient.registerItemRenderer(ListMisc.Kiribachi, new RenderWeaponKiribachi());

		MinecraftForgeClient.registerItemRenderer(ListMisc.Durandal, new RenderWeaponDurandal());
		
		MinecraftForgeClient.registerItemRenderer(ListMisc.Mace, new RenderWeaponMace());*/


	}
	
	@Override
	public void spawnCustomParticles(Entity theEntity, EntityParticleFX particle)
	{
		//Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	public void openQuestYesOrNoWorkaround(EntityPlayer player, EnumQuestlines questline)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GUIQuestYesNo(player, (int)player.posX, (int)player.posY, (int)player.posZ, questline));
	}
}

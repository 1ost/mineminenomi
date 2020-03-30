package xyz.pixelatedw.mineminenomi.events.passives;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.entities.zoan.ZoanInfoZouHeavy;
import xyz.pixelatedw.wypi.APIConfig;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class ZouPassiveEvents
{

	@SubscribeEvent
	public static void onEntityAttack(LivingHurtEvent event)
	{
		if (!(event.getSource().getTrueSource() instanceof PlayerEntity))
			return;
		
		PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
		IDevilFruit props = DevilFruitCapability.get(attacker);
		LivingEntity attacked = event.getEntityLiving();

		if (!props.getDevilFruit().equalsIgnoreCase("zou_zou"))
			return;

		if (props.getZoanPoint().equalsIgnoreCase(ZoanInfoZouHeavy.FORM))
			event.setAmount(3);
		
	}

}
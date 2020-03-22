package xyz.pixelatedw.mineminenomi.abilities.hie;

import java.util.List;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.DevilFruitsHelper;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.Ability;

public class IceTimeCapsuleAbility extends Ability
{
	public static final Ability INSTANCE = new IceTimeCapsuleAbility();

	public IceTimeCapsuleAbility()
	{
		super("Ice Time Capsule", AbilityCategory.DEVIL_FRUIT);
		this.setMaxCooldown(15);
		this.setDescription("A wave of ice is sent along the ground and freezes every enemy it hits, locking them in an ice capsule.");

		this.onUseEvent = this::onUseEvent;
	}

	private boolean onUseEvent(PlayerEntity player)
	{
		List<LivingEntity> list = WyHelper.<LivingEntity>getEntitiesNear(player.getPosition(), player.world, 25);
		list.remove(player);
		
		for (LivingEntity target : list)
		{
			DevilFruitsHelper.createFilledCube(target, new int[] { 2, 4, 2 }, Blocks.PACKED_ICE, "air", "foliage");
		}

		return true;
	}
}
package xyz.pixelatedw.MineMineNoMi3.quests;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public interface ITimedQuest
{

	void onTimePassEvent(EntityPlayer player);
	
}

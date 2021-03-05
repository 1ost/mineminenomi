package xyz.pixelatedw.MineMineNoMi3.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.quests.ITimedQuest;

public class PacketQuestExtra implements IMessage
{
	public int questId;

	public PacketQuestExtra() {}
	
	public PacketQuestExtra(int questId) 
	{
		this.questId = questId;
	}

	public void fromBytes(ByteBuf buffer) 
	{
		questId = buffer.readInt();
	}
	
	public void toBytes(ByteBuf buffer) 
	{
		buffer.writeInt(questId);
	}

	
	public static class ClientHandler implements IMessageHandler<PacketQuestExtra, IMessage>
	{
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketQuestExtra message, MessageContext ctx) 
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			QuestProperties props = QuestProperties.get(player);	 
			
			try
			{
				((ITimedQuest)props.getQuestIndexFromTracker(message.questId)).onTimePassEvent(player);
			}
			catch(Exception e)
			{
				System.err.println("Checking different objects to check for nulls \n"
						+ "Quest Props - " + props + "\n"
						+ "Quest - " + props.getQuestIndexFromTracker(message.questId) + "\n"
						+ "Player - " + player.getDisplayName() + "\n");
			}

			return null;
		}
	}
	
	public static class ServerHandler implements IMessageHandler<PacketQuestExtra, IMessage>
	{
		public IMessage onMessage(PacketQuestExtra message, MessageContext ctx) 
		{
			EntityPlayer player = MainMod.proxy.getPlayerEntity(ctx);
			QuestProperties props = QuestProperties.get(player);	 
			
			try
			{
				((ITimedQuest)props.getQuestIndexFromTracker(message.questId)).onTimePassEvent(player);
				//WyNetworkHelper.sendTo(new PacketQuestExtra(message.questId), (EntityPlayerMP) player);
			}
			catch(Exception e)
			{
				System.err.println("Checking different objects to check for nulls \n"
						+ "Quest Props - " + props + "\n"
						+ "Quest - " + props.getQuestIndexFromTracker(message.questId) + "\n"
						+ "Player - " + player.getDisplayName() + "\n");
			}
				
			return null;
		}
	}
}
package xyz.pixelatedw.MineMineNoMi3.api.network;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;

public class PacketQuestSync implements IMessage
{
	public NBTTagCompound data;

	public PacketQuestSync() {}
	
	public PacketQuestSync(QuestProperties props) 
	{
		data = new NBTTagCompound();
		props.saveNBTData(data);
	}

	@Override
	public void fromBytes(ByteBuf buffer) 
	{
		data = ByteBufUtils.readTag(buffer);
	}
	
	@Override
	public void toBytes(ByteBuf buffer) 
	{
		ByteBufUtils.writeTag(buffer, data);
	}
	
	public static class ClientHandler implements IMessageHandler<PacketQuestSync, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketQuestSync message, MessageContext ctx) 
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			QuestProperties props = QuestProperties.get(player);	 

			props.loadNBTData(message.data);

			return null;
		}
	}
	
	public static class ServerHandler implements IMessageHandler<PacketQuestSync, IMessage>
	{
		@Override
		public IMessage onMessage(PacketQuestSync message, MessageContext ctx) 
		{
			EntityPlayer player = MainMod.proxy.getPlayerEntity(ctx);
			QuestProperties props = QuestProperties.get(player);	 
			
			props.loadNBTData(message.data);

			return new PacketQuestSync(props);
		}
	}
}
package xyz.pixelatedw.MineMineNoMi3.packets;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;

public class PacketWorldData implements IMessage
{

	private boolean isSwordsmanDojoSpawned;
	private int totalDojosSpawned;

	public PacketWorldData() {}

	public PacketWorldData(ExtendedWorldData data)
	{
		isSwordsmanDojoSpawned = data.isSwordsmanDojoSpawned();
		totalDojosSpawned = data.getTotalDojosSpawned();
	}

	public void fromBytes(ByteBuf buf)
	{
		this.isSwordsmanDojoSpawned = buf.readBoolean();
		this.totalDojosSpawned = buf.readInt();
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(this.isSwordsmanDojoSpawned);
		buf.writeInt(this.totalDojosSpawned);
	}

	public static class ClientHandler implements IMessageHandler<PacketWorldData, IMessage>
	{
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(final PacketWorldData message, final MessageContext ctx)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			ExtendedWorldData worldData = ExtendedWorldData.get(player.world);
			worldData.setSwordsmanDojoSpawned(message.isSwordsmanDojoSpawned);
			worldData.setDojoSpawned(message.totalDojosSpawned);		

			return null;
		}
	}

}

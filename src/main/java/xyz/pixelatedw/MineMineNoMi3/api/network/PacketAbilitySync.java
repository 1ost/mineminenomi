package xyz.pixelatedw.MineMineNoMi3.api.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;

public class PacketAbilitySync implements IMessage
{
	public NBTTagCompound data;

	public PacketAbilitySync() {}
	
	public PacketAbilitySync(AbilityProperties props) 
	{
		data = new NBTTagCompound();
		props.saveNBTData(data);
	}

	public void fromBytes(ByteBuf buffer) 
	{
		data = ByteBufUtils.readTag(buffer);
	}
	
	public void toBytes(ByteBuf buffer) 
	{
		ByteBufUtils.writeTag(buffer, data);
	}
	
	public static class ClientHandler implements IMessageHandler<PacketAbilitySync, IMessage>
	{
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketAbilitySync message, MessageContext ctx) 
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			AbilityProperties props = AbilityProperties.get(player);	 

			props.loadNBTData(message.data);

			return null;
		}
	}
	
	public static class ServerHandler implements IMessageHandler<PacketAbilitySync, IMessage>
	{
		public IMessage onMessage(PacketAbilitySync message, MessageContext ctx) 
		{
			EntityPlayer player = MainMod.proxy.getPlayerEntity(ctx);
			AbilityProperties props = AbilityProperties.get(player);	 

			props.loadNBTData(message.data);
			
			return null;
		}
	}
}
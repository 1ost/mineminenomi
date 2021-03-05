package xyz.pixelatedw.MineMineNoMi3.packets;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.MainMod;

public class PacketSpecialFlying implements IMessage
{
	public boolean specialFlying;

	public PacketSpecialFlying() {}

	public PacketSpecialFlying(boolean specialFlying)
	{
		this.specialFlying = specialFlying;
	}

	public void fromBytes(ByteBuf buffer)
	{
		this.specialFlying = buffer.readBoolean();
	}

	public void toBytes(ByteBuf buffer)
	{
		buffer.writeBoolean(this.specialFlying);
	}

	public static class ClientHandler implements IMessageHandler<PacketSpecialFlying, IMessage>
	{
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketSpecialFlying message, MessageContext ctx)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			
			player.capabilities.allowFlying = message.specialFlying;
			if(!message.specialFlying)
			{
				player.capabilities.isFlying = false;
			}
			
			return null;
		}
	}
	
	public static class ServerHandler implements IMessageHandler<PacketSpecialFlying, IMessage>
	{
		public IMessage onMessage(PacketSpecialFlying message, MessageContext ctx)
		{
			return null;		
		}
	}
}

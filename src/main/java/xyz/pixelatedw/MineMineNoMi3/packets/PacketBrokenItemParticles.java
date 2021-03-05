package xyz.pixelatedw.MineMineNoMi3.packets;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketBrokenItemParticles implements IMessage
{
	
	private ItemStack itemStack;
	
	public PacketBrokenItemParticles() {}
	
	public PacketBrokenItemParticles(ItemStack itemStack) 
	{
		this.itemStack = itemStack;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.itemStack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, this.itemStack);
	}

	public static class ClientHandler implements IMessageHandler<PacketBrokenItemParticles, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketBrokenItemParticles message, MessageContext ctx)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			player.renderBrokenItemStack(message.itemStack);
			return null;
		}
	}
}

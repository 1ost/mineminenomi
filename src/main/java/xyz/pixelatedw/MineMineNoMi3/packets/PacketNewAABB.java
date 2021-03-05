package xyz.pixelatedw.MineMineNoMi3.packets;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import xyz.pixelatedw.MineMineNoMi3.MainMod;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class PacketNewAABB implements IMessage
{
	public float sizeX, sizeY, sizeZ;

	public PacketNewAABB() {}

	public PacketNewAABB(float sizeX, float sizeY, float sizeZ)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}
	
	public PacketNewAABB(float sizeX, float sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeX;
	}

	public void fromBytes(ByteBuf buffer)
	{
		this.sizeX = buffer.readFloat();
		this.sizeY = buffer.readFloat();
		this.sizeZ = buffer.readFloat();
	}

	public void toBytes(ByteBuf buffer)
	{
		buffer.writeFloat(this.sizeX);
		buffer.writeFloat(this.sizeY);
		buffer.writeFloat(this.sizeZ);
	}

	public static class ClientHandler implements IMessageHandler<PacketNewAABB, IMessage>
	{
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketNewAABB message, MessageContext ctx)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;

			player.width = message.sizeX;
			player.height = message.sizeY;

			double minX = player.getEntityBoundingBox().minX;
			double minY = player.getEntityBoundingBox().minY;
			double minZ = player.getEntityBoundingBox().minZ;
			double maxX = minX + message.sizeX;
			double maxY = minY + message.sizeY;
			double maxZ = minZ + message.sizeZ;
			
			player.setEntityBoundingBox(new AxisAlignedBB(
					minX, 
					minY, 
					minZ, 
					maxX, 
					maxY, 
					maxZ
				));

			return null;
		}
	}

	public static class ServerHandler implements IMessageHandler<PacketNewAABB, IMessage>
	{
		public IMessage onMessage(PacketNewAABB message, MessageContext ctx)
		{
			EntityPlayer player = MainMod.proxy.getPlayerEntity(ctx);

			player.width = message.sizeX;
			player.height = message.sizeY;
					
			double minX = player.getEntityBoundingBox().minX;
			double minY = player.getEntityBoundingBox().minY;
			double minZ = player.getEntityBoundingBox().minZ;
			double maxX = minX + message.sizeX;
			double maxY = minY + message.sizeY;
			double maxZ = minZ + message.sizeZ;

			player.setEntityBoundingBox(new AxisAlignedBB(
					minX,
					minY,
					minZ,
					maxX,
					maxY,
					maxZ
			));
			return new PacketNewAABB(message.sizeX, message.sizeY, message.sizeZ);
		}
	}
}

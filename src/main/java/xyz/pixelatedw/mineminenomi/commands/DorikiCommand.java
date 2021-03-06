package xyz.pixelatedw.mineminenomi.commands;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.events.custom.DorikiEvent;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.debug.WyDebug;
import xyz.pixelatedw.wypi.network.WyNetwork;

public class DorikiCommand
{
	public static void register(CommandDispatcher<CommandSource> dispatcher)
	{
		LiteralArgumentBuilder<CommandSource> builder = Commands.literal("doriki").requires(source -> source.hasPermissionLevel(3));

		builder
			.then(Commands.literal("+")
				.then(Commands.argument("value", IntegerArgumentType.integer(1, ModValues.MAX_DORIKI))
					.then(Commands.argument("targets", EntityArgument.players())
						.executes(context -> 
							{ 
								return addValue(context, IntegerArgumentType.getInteger(context, "value"), EntityArgument.getPlayers(context, "targets")); 
							}
						)
					)	
				)
			);
		
		builder
			.then(Commands.literal("=")
				.then(Commands.argument("value", IntegerArgumentType.integer(0, ModValues.MAX_DORIKI))
					.then(Commands.argument("targets", EntityArgument.players())
						.executes(context -> 
							{ 
								return setValue(context, IntegerArgumentType.getInteger(context, "value"), EntityArgument.getPlayers(context, "targets")); 
							}
						)
					)	
				)
				.then(Commands.literal("MAX")
					.then(Commands.argument("targets", EntityArgument.players())
						.executes(context ->
							{
								return setValue(context, ModValues.MAX_DORIKI, EntityArgument.getPlayers(context, "targets")); 
							}
						)
					)
				)
			);
		
		builder
			.then(Commands.literal("-")
				.then(Commands.argument("value", IntegerArgumentType.integer(1, ModValues.MAX_DORIKI))
					.then(Commands.argument("targets", EntityArgument.players())
						.executes(context -> 
							{ 
								return subtractValue(context, IntegerArgumentType.getInteger(context, "value"), EntityArgument.getPlayers(context, "targets")); 
							}
						)
					)	
				)
			);
		
		dispatcher.register(builder);
	}
	
	private static int subtractValue(CommandContext<CommandSource> context, int value, Collection<ServerPlayerEntity> targets)
	{		
		for (ServerPlayerEntity player : targets)
		{
			IEntityStats entityStatsProps = EntityStatsCapability.get(player);
			
			entityStatsProps.alterDoriki(-value);
			
			if(WyDebug.isDebug())
				WyHelper.sendMsgToPlayer(player, TextFormatting.GREEN + "" + TextFormatting.ITALIC + "[DEBUG] Substracted " + value + " doriki from " + player.getName().getFormattedText()); 

			DorikiEvent e = new DorikiEvent(player);
			if (MinecraftForge.EVENT_BUS.post(e))
				return 1;
			
			WyNetwork.sendTo(new SSyncEntityStatsPacket(player.getEntityId(), entityStatsProps), player);
		}
		
		return 1;
	}
	
	private static int setValue(CommandContext<CommandSource> context, int value, Collection<ServerPlayerEntity> targets)
	{		
		for (ServerPlayerEntity player : targets)
		{
			IEntityStats entityStatsProps = EntityStatsCapability.get(player);
			
			entityStatsProps.setDoriki(value);
			
			if(WyDebug.isDebug())
				WyHelper.sendMsgToPlayer(player, TextFormatting.GREEN + "" + TextFormatting.ITALIC + "[DEBUG] " + player.getName().getFormattedText() + " now has " + value + " doriki"); 

			DorikiEvent e = new DorikiEvent(player);
			if (MinecraftForge.EVENT_BUS.post(e))
				return 1;
			
			WyNetwork.sendTo(new SSyncEntityStatsPacket(player.getEntityId(), entityStatsProps), player);
		}
		
		return 1;
	}

	private static int addValue(CommandContext<CommandSource> context, int value, Collection<ServerPlayerEntity> targets) throws CommandSyntaxException 
	{
		for (ServerPlayerEntity player : targets)
		{
			IEntityStats entityStatsProps = EntityStatsCapability.get(player);
			
			entityStatsProps.alterDoriki(value);
			
			if(WyDebug.isDebug())
				WyHelper.sendMsgToPlayer(player, TextFormatting.GREEN + "" + TextFormatting.ITALIC + "[DEBUG] Added " + value + " doriki to " + player.getName().getFormattedText()); 

			DorikiEvent e = new DorikiEvent(player);
			if (MinecraftForge.EVENT_BUS.post(e))
				return 1;
			
			WyNetwork.sendTo(new SSyncEntityStatsPacket(player.getEntityId(), entityStatsProps), player);
		}
		
		return 1;
	}
}

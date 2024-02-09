package com.xujiayao.mcdiscordchat.minecraft;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

import java.util.Optional;

/**
 * @author Xujiayao
 */
public interface MinecraftEvents {

	Event<ServerMessage> SERVER_MESSAGE = EventFactory.createArrayBacked(ServerMessage.class, callbacks -> (playerChatMessage, commandSourceStack) -> {
		for (ServerMessage callback : callbacks) {
			callback.message(playerChatMessage, commandSourceStack);
		}
	});

	Event<PlayerMessage> PLAYER_MESSAGE = EventFactory.createArrayBacked(PlayerMessage.class, callbacks -> (player, playerChatMessage) -> {
		Optional<Component> result = Optional.empty();
		for (PlayerMessage callback : callbacks) {
			result = callback.message(player, playerChatMessage);
		}
		return result;
	});

	Event<PlayerCommand> PLAYER_COMMAND = EventFactory.createArrayBacked(PlayerCommand.class, callbacks -> (player, command) -> {
		for (PlayerCommand callback : callbacks) {
			callback.command(player, command);
		}
	});

	Event<PlayerAdvancement> PLAYER_ADVANCEMENT = EventFactory.createArrayBacked(PlayerAdvancement.class, callbacks -> (player, advancementHolder, isDone) -> {
		for (PlayerAdvancement callback : callbacks) {
			callback.advancement(player, advancementHolder, isDone);
		}
	});

	Event<PlayerDie> PLAYER_DIE = EventFactory.createArrayBacked(PlayerDie.class, callbacks -> (player, source) -> {
		for (PlayerDie callback : callbacks) {
			callback.die(player, source);
		}
	});

	Event<PlayerJoin> PLAYER_JOIN = EventFactory.createArrayBacked(PlayerJoin.class, callbacks -> player -> {
		for (PlayerJoin callback : callbacks) {
			callback.join(player);
		}
	});

	Event<PlayerQuit> PLAYER_QUIT = EventFactory.createArrayBacked(PlayerQuit.class, callbacks -> player -> {
		for (PlayerQuit callback : callbacks) {
			callback.quit(player);
		}
	});

	interface ServerMessage {
		void message(PlayerChatMessage playerChatMessage, CommandSourceStack commandSourceStack);
	}

	interface PlayerMessage {
		Optional<Component> message(ServerPlayer player, PlayerChatMessage playerChatMessage);
	}

	interface PlayerCommand {
		void command(ServerPlayer player, String command);
	}


	interface PlayerAdvancement {
		void advancement(ServerPlayer player, AdvancementHolder advancementHolder, boolean isDone);
	}

	interface PlayerDie {
		void die(ServerPlayer player, DamageSource source);
	}

	interface PlayerJoin {
		void join(ServerPlayer player);
	}

	interface PlayerQuit {
		void quit(ServerPlayer player);
	}
}

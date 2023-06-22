package com.pixelstorm.freelook_for_clients;

import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.option.StickyKeyBind;

public class FreelookForClients implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Freelook For Clients");

	public static final KeyBind HOLD_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new KeyBind("key.freelook_for_clients.hold", GLFW.GLFW_KEY_WORLD_1,
					"key.categories.freelook_for_clients"));

	public static final KeyBind TOGGLE_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new StickyKeyBind("key.freelook_for_clients.toggle", GLFW.GLFW_KEY_UNKNOWN,
					"key.categories.freelook_for_clients", () -> true));

	@Override
	public void onInitializeClient(ModContainer mod) {
		// ClientTickEvents.START.register(client -> {
		// LOGGER.info("{}, {}", HOLD_FREE_LOOK_KEYBIND.getKeyName(),
		// HOLD_FREE_LOOK_KEYBIND.getKeyTranslationKey());
		// });
	}
}

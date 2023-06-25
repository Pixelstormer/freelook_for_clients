package com.pixelstorm.freelook_for_clients;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.StickyKeyBinding;

public class FreelookForClients implements ClientModInitializer {
	public static final KeyBinding HOLD_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("key.freelook_for_clients.hold", GLFW.GLFW_KEY_WORLD_1,
					"key.categories.freelook_for_clients"));

	public static final KeyBinding TOGGLE_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new StickyKeyBinding("key.freelook_for_clients.toggle", GLFW.GLFW_KEY_UNKNOWN,
					"key.categories.freelook_for_clients", () -> true));

	@Override
	public void onInitializeClient() {
	}
}

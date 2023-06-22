package com.pixelstorm.freelook_for_clients;

import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import net.minecraft.client.option.StickyKeyBind;

public class FreelookForClients implements ClientModInitializer {
	public static final KeyBind HOLD_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new KeyBind("key.freelook_for_clients.hold", GLFW.GLFW_KEY_WORLD_1,
					"key.categories.freelook_for_clients"));

	public static final KeyBind TOGGLE_FREE_LOOK_KEYBIND = KeyBindingHelper.registerKeyBinding(
			new StickyKeyBind("key.freelook_for_clients.toggle", GLFW.GLFW_KEY_UNKNOWN,
					"key.categories.freelook_for_clients", () -> true));

	@Override
	public void onInitializeClient(ModContainer mod) {
	}
}

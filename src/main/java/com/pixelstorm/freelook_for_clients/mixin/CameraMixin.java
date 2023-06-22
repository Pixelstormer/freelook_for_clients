package com.pixelstorm.freelook_for_clients.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.pixelstorm.freelook_for_clients.CanFreelook;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;

@Mixin(Camera.class)
public abstract class CameraMixin {
	@Shadow
	private Entity focusedEntity;

	@Shadow
	protected abstract void setRotation(float yaw, float pitch);

	@Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 0))
	private void setFreelookRotation(Camera self, float yaw, float pitch) {
		if (focusedEntity instanceof CanFreelook freelooker
				&& freelooker.getFreelookState().isFreelookingOrInterpolating()) {
			this.setRotation(freelooker.getFreelookYaw(), freelooker.getFreelookPitch());
		} else {
			this.setRotation(yaw, pitch);
		}
	}
}

package com.pixelstorm.freelook_for_clients.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.pixelstorm.freelook_for_clients.CanFreelook;
import com.pixelstorm.freelook_for_clients.FreelookForClients;
import com.pixelstorm.freelook_for_clients.FreelookState;

import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;

@Mixin(Mouse.class)
public abstract class MouseMixin {
	@Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V"))
	private void changeFreelookDirection(ClientPlayerEntity self, double cursorDeltaX, double cursorDeltaY) {
		// Handle mouse movement, keybinds and starting/stopping freelooking
		CanFreelook freelooker = (CanFreelook) self;

		if (FreelookForClients.HOLD_FREE_LOOK_KEYBIND.isPressed()
				|| FreelookForClients.TOGGLE_FREE_LOOK_KEYBIND.isPressed()) {
			freelooker.setFreelookState(FreelookState.Freelooking);
		} else if (freelooker.getFreelookState().isFreelooking()) {
			freelooker.setFreelookState(FreelookState.Interpolating);
		}

		switch (freelooker.getFreelookState()) {
			case Freelooking:
				freelooker.changeFreelookDirection(cursorDeltaX, cursorDeltaY);
				break;
			case Interpolating:
				self.changeLookDirection(cursorDeltaX, cursorDeltaY);
				// Interpolate to original orientation - instead of instantly snapping back - to
				// avoid disorienting the player
				float lerpedPitch = MathHelper.lerpAngleDegrees(0.3f, freelooker.getFreelookPitch(), self.getPitch());
				float lerpedYaw = MathHelper.lerpAngleDegrees(0.3f, freelooker.getFreelookYaw(), self.getYaw());
				freelooker.setFreelookPitch(lerpedPitch);
				freelooker.setFreelookYaw(lerpedYaw);

				// Cut off interpolation once it's close enough to original orientation
				float pitchDiff = Math.abs(self.getPitch() - freelooker.getFreelookPitch());
				float yawDiff = Math.abs(self.getYaw() - freelooker.getFreelookYaw());

				if ((pitchDiff * pitchDiff) + (yawDiff * yawDiff) <= 0.02f) {
					freelooker.setFreelookState(FreelookState.NotFreelooking);
				}
				break;
			case NotFreelooking:
				self.changeLookDirection(cursorDeltaX, cursorDeltaY);
				// When not freelooking, sync pitch & yaw so camera doesn't snap to some other
				// orientation when activating freelooking
				freelooker.setFreelookPitch(self.getPitch());
				freelooker.setFreelookYaw(self.getYaw());
				break;
		}
	}
}

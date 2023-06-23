package com.pixelstorm.freelook_for_clients.mixin;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.pixelstorm.freelook_for_clients.CanFreelook;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.MathConstants;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow
	private Camera camera;

	@Inject(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"))
	private void modifyHandMatrix(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo ci) {
		Entity entity = camera.getFocusedEntity();
		if (entity instanceof CanFreelook freelooker && freelooker.getFreelookState().isFreelookingOrInterpolating()) {
			// Rotate the player hand/held item so it appears to remain fixed in space while
			// freelooking, to emphasise that the player is freelooking, and thus the aim
			// vector for clicking on stuff is fixed
			float pitchDiff = freelooker.getFreelookPitch() - entity.getPitch(tickDelta);
			float yawDiff = freelooker.getFreelookYaw() - entity.getYaw(tickDelta);

			Vector3f positive_y = new Vector3f(0f, 1f, 0f);
			Vector3f positive_y_local = positive_y
					.rotateX(freelooker.getFreelookPitch() * MathConstants.RADIANS_PER_DEGREE);

			matrices.multiply(
					new Quaternionf().rotationAxis(yawDiff * MathConstants.RADIANS_PER_DEGREE, positive_y_local));
			matrices.multiply(Axis.X_POSITIVE.rotationDegrees(pitchDiff));
		}
	}
}

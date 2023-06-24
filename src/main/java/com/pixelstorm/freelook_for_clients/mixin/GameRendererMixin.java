package com.pixelstorm.freelook_for_clients.mixin;

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
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

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
			float yawDiff = freelooker.getFreelookYaw() - entity.getYaw(tickDelta);
			float pitchDiff = freelooker.getFreelookPitch() - entity.getPitch(tickDelta);

			Vec3f camera_local_up = Vec3f.POSITIVE_Y.copy();
			camera_local_up.rotate(new Quaternion(Vec3f.POSITIVE_X, freelooker.getFreelookPitch(), true));

			matrices.multiply(new Quaternion(camera_local_up, yawDiff, true));
			matrices.multiply(new Quaternion(Vec3f.POSITIVE_X, pitchDiff, true));
		}
	}
}

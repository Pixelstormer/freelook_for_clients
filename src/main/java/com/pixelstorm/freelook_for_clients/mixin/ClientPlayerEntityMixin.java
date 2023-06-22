package com.pixelstorm.freelook_for_clients.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;
import com.pixelstorm.freelook_for_clients.CanFreelook;
import com.pixelstorm.freelook_for_clients.FreelookState;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

// Holds state for freelooking to communicate between other mixins
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements CanFreelook {
	private float freelookPitch;
	private float freelookYaw;
	private FreelookState freelookState;

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
		throw new AssertionError();
	}

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onConstruct(CallbackInfo ci) {
		freelookPitch = 0;
		freelookYaw = 0;
		freelookState = FreelookState.NotFreelooking;
	}

	@Override
	public void changeFreelookDirection(double cursorDeltaX, double cursorDeltaY) {
		// Copied from Entity::changeLookDirection
		float pitchDelta = (float) cursorDeltaY * 0.15F;
		this.setFreelookPitch(MathHelper.clamp(this.getFreelookPitch() + pitchDelta, -90.0F, 90.0F));

		float yawDelta = (float) cursorDeltaX * 0.15F;
		this.setFreelookYaw(this.getFreelookYaw() + yawDelta);
	}

	@Override
	public void setFreelookPitch(float pitch) {
		this.freelookPitch = pitch;
	}

	@Override
	public float getFreelookPitch() {
		return this.freelookPitch;
	}

	@Override
	public void setFreelookYaw(float yaw) {
		this.freelookYaw = yaw;
	}

	@Override
	public float getFreelookYaw() {
		return this.freelookYaw;
	}

	@Override
	public FreelookState getFreelookState() {
		return this.freelookState;
	}

	@Override
	public void setFreelookState(FreelookState state) {
		if (!this.freelookState.isFreelooking() && state.isFreelooking()) {
			this.sendMessage(Text.translatable("message.freelook_for_clients.enabled"), true);
		} else if (this.freelookState.isFreelooking() && !state.isFreelooking()) {
			this.sendMessage(Text.translatable("message.freelook_for_clients.disabled"), true);
		}
		this.freelookState = state;
	}
}

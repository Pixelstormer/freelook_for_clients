package com.pixelstorm.freelook_for_clients;

public interface CanFreelook {
	void changeFreelookDirection(double cursorDeltaX, double cursorDeltaY);

	void setFreelookPitch(float pitch);

	float getFreelookPitch();

	void setFreelookYaw(float yaw);

	float getFreelookYaw();

	void setFreelookState(FreelookState state);

	FreelookState getFreelookState();
}

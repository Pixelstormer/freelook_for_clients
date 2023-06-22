package com.pixelstorm.freelook_for_clients;

public enum FreelookState {
	Freelooking,
	Interpolating,
	NotFreelooking;

	public boolean isFreelooking() {
		return this == Freelooking;
	}

	public boolean isFreelookingOrInterpolating() {
		return !isNotFreelooking();
	}

	public boolean isNotFreelooking() {
		return this == NotFreelooking;
	}
}

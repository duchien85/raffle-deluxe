package com.rptr.raffle_deluxe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rptr.raffle_deluxe.RaffleGame;

public class DesktopLauncher {
	public static final int width = 800;
	public static final int height = 600;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Raffle Deluxe";
		config.width = width;
		config.height = height;
		new LwjglApplication(new RaffleGame(), config);
	}
}

package com.mygdx.twocarsclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.twocarsclone.TwoCarsClone;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
		Settings settings = new Settings();
		settings.debug = drawDebugOutline;
		if (rebuildAtlas) {
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images/", "../assets/images", "twoCars.pack");
			TexturePacker.process(settings, "assets-raw/images-ui", "../assets/images", "twoCars-ui.pack");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 468;
		config.height = 832;
		new LwjglApplication(new TwoCarsClone(), config);
	}
}

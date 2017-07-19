package com.mygdx.twocarsclone.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.twocarsclone.TwoCarsClone;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
            GwtApplicationConfiguration config = new GwtApplicationConfiguration(468, 832);
            return config;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new TwoCarsClone();
        }
}
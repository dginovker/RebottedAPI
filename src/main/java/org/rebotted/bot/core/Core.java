package org.rebotted.bot.core;

import org.rebotted.Client;
import org.rebotted.bot.data.APIManifest;
import org.rebotted.bot.data.RebottedAPI;

@APIManifest(version = 1.0)
public class Core extends RebottedAPI {

    private final Client client;

    public Core(Client client) {
        this.client = client;
    }

    public static int getCameraZoom() {
        return Client.cameraZoom;
    }

    @Override
    public boolean onStartUp() {
        return true;
    }

}

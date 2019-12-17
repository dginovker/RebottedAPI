package org.rebotted.bot.core;

import org.rebotted.Client;
import org.rebotted.bot.api.interactive.Npcs;
import org.rebotted.bot.api.wrappers.entities.NPC;
import org.rebotted.bot.data.APIManifest;
import org.rebotted.bot.data.RebottedAPI;

@APIManifest(version = 1.1)
public class Core extends RebottedAPI {

    private static Client client;

    public Core(Client client) {
        this.client = client;
    }

    @Override
    public boolean onStartUp() {
        final NPC n = Npcs.getClosest("Man");
        if(n != null) {
            System.out.println("We have NPC");
            n.interact("Pickpocket");
        } else {
            System.out.println("no NPC");
        }
        return true;
    }

    public static Client getClient() {
        return client;
    }
}

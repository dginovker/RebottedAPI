package org.rebotted.bot.api.data;

import org.rebotted.Client;
import org.rebotted.bot.core.Core;

public class Game {

    public static int getOpenInterfaceId() {
        return Client.openInterfaceId;
    }

    public static int getBaseX() {
        return Core.getClient().regionBaseX;
    }

    public static int getBaseY() {
        return Core.getClient().regionBaseY;
    }

    public static int getPlane() {
        return Core.getClient().plane;
    }

    public static int getTick() {
        return Client.tick;
    }



}

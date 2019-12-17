package org.rebotted.bot.api.interactive;


import org.rebotted.Client;
import org.rebotted.bot.api.wrappers.Character;
import org.rebotted.bot.api.wrappers.Player;
import org.rebotted.bot.core.Core;
import org.rebotted.util.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Players {

    private static final Filter<Player> ALL_FILTER = p -> true;

    private static final Comparator<Player> NEAREST_SORTER = Comparator.comparingInt(Character::distanceTo);

    public static final Player[] getPlayers(final Filter<Player> filter) {
        final List<Player> playerList = new ArrayList<>();
        final org.rebotted.entity.Player[] accPlayers = Core.getClient().players;
        for (int i = 0; i < accPlayers.length; i++) {
            if (accPlayers[i] == null) {
                continue;
            }
            final Player player = new Player(accPlayers[i], i);
            if (filter.accept(player)) {
                playerList.add(player);
            }
        }

        return playerList.toArray(new Player[playerList.size()]);
    }

    public static final Player[] getPlayers() {
        return getPlayers(ALL_FILTER);
    }

    public static Player getMyPlayer() {
        return new Player(Client.localPlayer, -1);
    }

    public static final Player[] getNearest(final Filter<Player> filter) {
        final Player[] players = getPlayers(filter);
        Arrays.sort(players, NEAREST_SORTER);
        return players;
    }

    public static final Player[] getNearest() {
        return getNearest(ALL_FILTER);
    }

    public static final Player getClosest(String... names) {
        Player[] players = getNearest(names);
        if (players == null || players.length == 0) {
            return null;
        }

        return players[0];
    }

    public static final Player[] getNearest(final String... names) {
        final Player[] players = getPlayers(player -> {
            if (player != null) {
                if (player.getName() != null) {
                    for (final String name : names) {
                        if (name.toLowerCase().equals(player.getName().toLowerCase())) {
                            return true;
                        }
                    }
                }
            }

            return false;
        });
        Arrays.sort(players, NEAREST_SORTER);

        return players;
    }

}

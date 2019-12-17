package org.rebotted.bot.api.interactive;

import org.rebotted.bot.api.wrappers.entities.Character;
import org.rebotted.bot.api.wrappers.entities.NPC;
import org.rebotted.bot.core.Core;
import org.rebotted.entity.Npc;
import org.rebotted.util.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Npcs {

    private static final Filter<NPC> ALL_FILTER = n -> true;

    private static final Comparator<NPC> NEAREST_SORTER = Comparator.comparingInt(Character::distanceTo);

    public static final NPC[] getNpcs(final Filter<NPC> filter) {
        final List<NPC> npcList = new ArrayList<>();
        final Npc[] npcs = Core.getClient().npcs;
        for (int i = 0; i < npcs.length; i++) {
            if (npcs[i] == null) {
                continue;
            }
            final NPC npc = new NPC(npcs[i], i);
            if (filter.accept(npc)) {
                npcList.add(npc);
            }
        }

        return npcList.toArray(new NPC[npcList.size()]);
    }

    public static final NPC[] getNpcs() {
        return getNpcs(ALL_FILTER);
    }

    public static final NPC getClosest(final Filter<NPC> filter) {
        final NPC[] npcs = getNearest(filter);
        if (npcs == null || npcs.length == 0) {
            return null;
        }

        return npcs[0];
    }

    public static final NPC getClosest(int... ids) {
        final NPC[] npcs = getNearest(ids);
        if (npcs == null || npcs.length == 0) {
            return null;
        }

        return npcs[0];
    }

    public static final NPC getClosest(String... names) {
        final NPC[] npcs = getNearest(names);
        if (npcs == null || npcs.length == 0) {
            return null;
        }

        return npcs[0];
    }

    public static final NPC[] getNearest(final Filter<NPC> filter) {
        final NPC[] npcs = getNpcs(filter);
        Arrays.sort(npcs, NEAREST_SORTER);

        return npcs;
    }

    public static final NPC[] getNearest(final int... ids) {
        final NPC[] npcs = getNpcs(npc -> {
            if (npc != null && npc.getNpcDefinition() != null) {
                for (final int id : ids) {
                    if (id == npc.getId()) {
                        return true;
                    }
                }
            }

            return false;
        });
        Arrays.sort(npcs, NEAREST_SORTER);

        return npcs;
    }

    public static final NPC[] getNearest(final String... names) {
        final NPC[] npcs = getNpcs(npc -> {
            if (npc != null && npc.getNpcDefinition() != null && npc.getName() != null) {
                for (final String name : names) {
                    if (name.toLowerCase().equals(npc.getName().toLowerCase())) {
                        return true;
                    }
                }
            }

            return false;
        });
        Arrays.sort(npcs, NEAREST_SORTER);

        return npcs;
    }


    public static final NPC[] getNearest() {
        return getNearest(ALL_FILTER);
    }
}

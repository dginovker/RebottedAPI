package org.rebotted.bot.api.wrappers.entities;


import org.rebotted.bot.api.data.Calculations;
import org.rebotted.bot.api.data.Game;
import org.rebotted.bot.api.interaction.InteractionHandler;
import org.rebotted.bot.api.interactive.Players;
import org.rebotted.bot.api.wrappers.location.Tile;
import org.rebotted.bot.core.Core;
import org.rebotted.bot.interfaces.Interactable;
import org.rebotted.entity.Mob;
import org.rebotted.entity.Npc;

public class Character implements Interactable {

    private Mob accessor;
    private int index;
    private InteractionHandler interactionHandler;
    public Character(Mob accessor, int index) {
        this.accessor = accessor;
        this.index = index;
        this.interactionHandler = new InteractionHandler(this);
    }

    public final int getX() {
        return getLocalX() + Game.getBaseX();
    }

    public final int getY() {
        return getLocalY() + Game.getBaseY();
    }

    public final int getLocalX() {
        return accessor.pathX[0];
    }

    public final int getLocalY() {
        return accessor.pathY[0];
    }

    public final int[] getPathX() {
        return accessor.pathX;
    }

    public final int[] getPathY() {
        return accessor.pathY;
    }

    public final int getOrientation() {
        return accessor.orientation;
    }

    public final int getHealth() {
        return accessor.currentHealth;
    }

    public final int getMaxHealth() {
        return accessor.maxHealth;
    }

    public final int getTargetIndex() {
        return accessor.interactingEntity;
    }

    public final int getCombatCycle() {
        return accessor.loopCycleStatus;
    }

    public final String getSpokenText() {
        return accessor.spokenText;
    }

    public final int getPathSize() {
        return accessor.remainingPath;
    }

    public final boolean isMoving() {
        return getPathSize() > 0;
    }

    public final int getAnimation() {
        return accessor.currentAnimation;
    }

    public final int getTextCycle() {
        return accessor.textCycle;
    }


    public final boolean isInCombat() {
        return getCombatCycle() > Game.getTick();
    }


    public boolean isInteracting() {
        return getTarget() != null;
    }


    public String getCharacterName() {
        if (this instanceof Player) {
            Player p = ((Player) this);
            if (p.getName() == null)
                return null;
            return p.getName();
        } else if (this instanceof NPC) {
            NPC p = ((NPC) this);
            if (p.getName() == null)
                return null;
            return p.getName();
        }
        return null;
    }

    public Tile getLocation() {
        return new Tile(getX(), getY(), Game.getPlane());
    }

    public int distanceTo() {
        return (int) Calculations.distanceTo(getLocation());
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean interact(String action) {
        return interactionHandler.interact(action);
    }

    public Character getTarget() {
        final int index = getTargetIndex();
        if (index == -1 || index == 0 || index == 32768) {
            return null;
        }
        if (index < 32768) {
            final Npc[] localNpcs = Core.getClient().npcs;
            return index >= 0 && index < localNpcs.length ? new NPC(localNpcs[index], index) : null;
        } else {
            final int pos = index - 32768;
            final int localIndex = Core.getClient().localPlayerIndex;
            if (pos == localIndex) {
                return Players.getMyPlayer();
            }

            final org.rebotted.entity.Player[] players = Core.getClient().players;
            return pos >= 0 && pos < players.length ? new Player(players[pos], index) : null;
        }
    }

    @Override
    public String[] getActions() {
        if (this instanceof Player) {
            final Player p = ((Player) this);
            if (p.getActions() == null || p.getActions().length <= 0)
                return null;
            return p.getActions();
        } else if (this instanceof NPC) {
            final NPC n = ((NPC) this);
            if (n.getNpcDefinition().actions == null || n.getNpcDefinition().actions.length <= 0)
                return new String[4];
            return n.getNpcDefinition().actions;
        }
        return null;
    }
}

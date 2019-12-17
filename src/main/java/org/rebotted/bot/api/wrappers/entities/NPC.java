package org.rebotted.bot.api.wrappers.entities;


import org.rebotted.cache.def.NpcDefinition;
import org.rebotted.entity.Npc;

public class NPC extends Character {
    private Npc accessor;
    private int index;

    public NPC(Npc accessor, int index) {
        super(accessor, index);
        this.accessor = accessor;
        this.index = index;
    }

    public NpcDefinition getNpcDefinition() {
        return accessor.desc;
    }

    public String getName() {
        if(accessor == null || getNpcDefinition() == null)
            return "null";
        return accessor.desc.name;
    }

    public int getId() {
        if(accessor == null || getNpcDefinition() == null)
            return -1;
        return getNpcDefinition().id;
    }

    public int getHeadIcon() {
        if(accessor == null || getNpcDefinition() == null)
            return -1;
        return getNpcDefinition().headIcon;
    }

    public int getCombatLevel() {
        if(accessor == null || getNpcDefinition() == null)
            return -1;
        return getNpcDefinition().combatLevel;
    }

    public String[] getActions() {
        if(accessor == null || getNpcDefinition() == null)
            return new String[5];
        return getNpcDefinition().actions;
    }

    public int getSize() {
        if(accessor == null || getNpcDefinition() == null)
            return -1;
        return getNpcDefinition().size;
    }


    public int getIndex() {
        return index;
    }
}

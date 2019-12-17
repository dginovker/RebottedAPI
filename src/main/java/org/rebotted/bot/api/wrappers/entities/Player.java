package org.rebotted.bot.api.wrappers.entities;


public class Player extends Character {

    private int index;
    private org.rebotted.entity.Player accessor;

    public Player(org.rebotted.entity.Player accessor, int index) {
        super(accessor, index);
        this.accessor = accessor;
        this.index = index;
    }

    public boolean isVisible() {
        return accessor.isVisible();
    }

    public String getName() {
        if(accessor == null)
            return null;
        return accessor.name;
    }

    public int getCombatLevel() {
        return accessor.combatLevel;
    }

    public int getHeadIcon() {
        return accessor.headIcon;
    }

    public int getSkill() {
        return accessor.skill;
    }

    public int getTeam() {
        return accessor.team;
    }

    public int[] getAppearanceColors() {
        return accessor.appearanceColors;
    }

}

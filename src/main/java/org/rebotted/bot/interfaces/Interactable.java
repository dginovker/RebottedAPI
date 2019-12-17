package org.rebotted.bot.interfaces;


public interface Interactable {

    boolean interact(final String action);

    String[] getActions();

}
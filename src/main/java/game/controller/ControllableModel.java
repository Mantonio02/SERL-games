package game.controller;

import game.entities.movingEntities.attackEntities.player.Player;
import game.entities.npc.NPC;
import game.level.Level;
import java.util.HashSet;

public interface ControllableModel {

  Level getCurrentLevel();

  /**
   * Calls on the {@link Player} to handle the input.
   *
   * @param keycode the alphanumeric code of the input
   * @param down true if the key is down, false if it is up
   * @return whether the input was processed
   */
  boolean handlePlayerInput(int keycode, boolean down);

  /**
   * Calls on the {@link Player} to handle the mouse wheel input.
   *
   * @param amountY the amount the user scrolled
   * @return whether the input was processed
   */
  boolean handlePlayerScroll(float amountY);

  /**
   * Calls on the {@link NPC} to handle the input.
   *
   * @param npc the {@link NPC} that received the input.
   * @param keycode the alphanumeric code of the input
   * @param down true if the key is down, false if it is up
   * @return whether the input was processed
   */
  boolean handleNPCInput(NPC npc, int keycode, boolean down);

  /**
   * @return the set of keycodes {@link Player} responds to.
   */
  HashSet<Integer> getPlayerKeyCodes();

  /**
   * @return the set of keycodes {@link NPC} responds to.
   */
  HashSet<Integer> getInteractionKeys();
}

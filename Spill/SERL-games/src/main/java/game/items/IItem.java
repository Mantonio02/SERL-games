package game.items;

import com.badlogic.gdx.graphics.Texture;
import game.entities.movingEntities.attackEntities.player.Player;

public interface IItem {
  /**
   * Interact with the item, using any special effects it might have.
   *
   * @param owner the player who has the item
   */
  public void interact(Player owner);

  /**
   * @return the items icon
   */
  public Texture getItemIcon();

  /**
   * @return description of what item do
   */
  public String description();

  /* @return How much the item cost to buy. */
  public int price();
}

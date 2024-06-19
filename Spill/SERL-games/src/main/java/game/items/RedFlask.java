package game.items;

import com.badlogic.gdx.graphics.Texture;
import game.buffs.DamageBuff;
import game.entities.movingEntities.attackEntities.player.Player;

public class RedFlask implements IItem {
  private final Texture itemIcon = new Texture("sprites/itemSprites/RedFlask.png");

  @Override
  public void interact(Player owner) {
    long duration = 30000;
    owner.appendBuff(new DamageBuff(duration, 20));
  }

  @Override
  public Texture getItemIcon() {
    return itemIcon;
  }

  @Override
  public String toString() {
    return "Red Flask";
  }

  @Override
  public String description() {
    return "Boost your attack";
  }

  @Override
  public int price() {
    return 30;
  }
}

package game.items;

import com.badlogic.gdx.graphics.Texture;
import game.buffs.SpeedBuff;
import game.entities.movingEntities.attackEntities.player.Player;

public class BlueFlask implements IItem {
  private final Texture itemIcon = new Texture("sprites/itemSprites/BlueFlask.png");

  @Override
  public void interact(Player owner) {
    long duration = 30000;
    owner.appendBuff(new SpeedBuff(duration, 2));
  }

  @Override
  public Texture getItemIcon() {
    return itemIcon;
  }

  @Override
  public String toString() {
    return "Blue Flask";
  }

  @Override
  public String description() {
    return "Boost your speed";
  }

  @Override
  public int price() {
    return 30;
  }
}

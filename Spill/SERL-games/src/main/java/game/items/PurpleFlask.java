package game.items;

import com.badlogic.gdx.graphics.Texture;
import game.buffs.DamageBuff;
import game.entities.movingEntities.attackEntities.player.Player;

public class PurpleFlask implements IItem {
  private final Texture itemIcon = new Texture("sprites/itemSprites/PurpleFlask.png");

  @Override
  public void interact(Player owner) {
    long duration = 30000;
    owner.appendBuff(new DamageBuff(duration, 60));
    owner.setHealth(owner.getHealth() / 2);
  }

  @Override
  public Texture getItemIcon() {
    return itemIcon;
  }

  @Override
  public String toString() {
    return "Purple Flask";
  }

  @Override
  public String description() {
    return "Greatly boost your attack, at the cost of your health";
  }

  @Override
  public int price() {
    return 30;
  }
}

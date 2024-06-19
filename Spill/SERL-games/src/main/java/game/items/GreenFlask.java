package game.items;

import com.badlogic.gdx.graphics.Texture;
import game.buffs.HealthBuff;
import game.entities.movingEntities.attackEntities.player.Player;

public class GreenFlask implements IItem {
  private final Texture itemIcon = new Texture("sprites/itemSprites/GreenFlask.png");

  @Override
  public void interact(Player owner) {
    long duration = 30000;
    owner.appendBuff(new HealthBuff(duration, owner.getHealth() / 2));
  }

  @Override
  public Texture getItemIcon() {
    return itemIcon;
  }

  @Override
  public String toString() {
    return "Green Flask";
  }

  @Override
  public String description() {
    return "Temporarily increase your health";
  }

  @Override
  public int price() {
    return 30;
  }
}

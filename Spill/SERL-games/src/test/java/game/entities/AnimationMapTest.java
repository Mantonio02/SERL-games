package game.entities;

import static org.junit.jupiter.api.Assertions.*;

import game.AbstractTest;
import game.entities.movingEntities.attackEntities.player.Player;
import game.model.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AnimationMapTest extends AbstractTest {

  @Test
  void correctMapEntryBuilt() {
    String path = "sprites/playerSprites/IdleRight.png";

    // Expected
    String name = "Idle";
    Direction direction = Direction.RIGHT;

    // Actual
    List<Map.Entry<String, Integer>> animationList = new ArrayList<>();
    animationList.add(Map.entry(path, 4));

    AnimationMap animationMap = new AnimationMap(animationList);

    assertFalse(animationMap.isEmpty());
    assertTrue(animationMap.containsKey(name));
    assertNotNull(animationMap.getAnimation(name, direction));
  }

  @Test
  void playerAnimationMapLoadedCorrectly() {
    Model model = new Model();
    model.setDebugMode();
    model.create();
    Player player = model.getPlayer();

    assertFalse(player.getAnimationHandler().getAnimationMap().isEmpty());
    assertNotNull(
        player.getAnimationHandler().getAnimationMap().getAnimation("Idle", Direction.RIGHT));
  }
}

package game.entities.movingEntities.attackEntities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import util.ExcludeFromGeneratedCoverageReport;

/** Represent healthbar for an enemy. */
public class EnemyHealthBar extends Actor {
  private Enemy enemy;
  private float maxHealth;
  private float enemyHealth;
  private ShapeRenderer shapeRenderer;
  private BitmapFont font;
  private String text;

  /**
   * Enemy healthbar needs to be added to stage for it to appear.
   *
   * @param enemy who the healthbar belongs to
   * @param width width of healthbar
   * @param height height of healthbar
   * @param text text which should appear over healthbar.
   */
  public EnemyHealthBar(Enemy enemy, float width, float height, String text) {
    this.enemy = enemy;
    this.text = text;
    font = new BitmapFont();
    font.getData().setScale(2f);
    setSize(width, height);
    updateHealth();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    setPosition(enemy.getX(), enemy.getY() + enemy.getHeight() + 10);
    updateHealth();
    if (enemyHealth <= 0) {
      this.remove();
    }
  }

  private void updateHealth() {
    maxHealth = enemy.getMaxHealth();
    enemyHealth = enemy.getHealth();
  }

  @Override
  @ExcludeFromGeneratedCoverageReport
  public void draw(Batch batch, float parentAlpha) {
    batch.end();
    shapeRenderer = new ShapeRenderer();

    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.BLACK);
    shapeRenderer.rect(getX() - 2, getY() - 2, getWidth() + 4, getHeight() + 4);

    shapeRenderer.setColor(Color.RED);
    shapeRenderer.rect(getX(), getY(), getWidth() * (enemyHealth / maxHealth), getHeight());
    shapeRenderer.end();

    batch.begin();
    font.setColor(Color.WHITE);
    font.draw(batch, text, getX(), getY() + getHeight() + 40);
  }

  /**
   * @return current enemy health
   */
  public float getHealth() {
    return enemyHealth;
  }
}

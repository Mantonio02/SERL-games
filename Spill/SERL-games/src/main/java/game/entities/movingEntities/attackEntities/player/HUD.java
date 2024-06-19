package game.entities.movingEntities.attackEntities.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import util.ExcludeFromGeneratedCoverageReport;

public class HUD {
  private final HealthBar healthBar;
  private final ExperienceBar expBar;
  private final HotBar hotBar;

  private final Player player;

  private final BitmapFont font;
  private final GlyphLayout layout;

  private final Color hotBarBG;
  private final Color hotBarOutline;
  private final Color hotBarFocusedOutline;

  /**
   * Creates a new HUD to manage the {@link HealthBar}, {@link ExperienceBar} and [{@link HotBar}
   * for a {@link Player}.
   *
   * @param player of the game.
   */
  public HUD(Player player) {
    this.player = player;

    this.healthBar = new HealthBar(player.getMaxHealth());
    this.expBar = new ExperienceBar();
    this.hotBar = new HotBar(player.getItems());

    this.font = new BitmapFont();
    font.setColor(Color.WHITE);

    this.layout = new GlyphLayout();

    this.hotBarBG = new Color(0x1d1d1d60);
    this.hotBarOutline = new Color(0x6d6d6d00);
    this.hotBarFocusedOutline = new Color(0xfdfdfd00);
  }

  /**
   * Draws the {@link HealthBar}, {@link ExperienceBar} and {@link HotBar} of the HUD, and draws
   * text for any active {@link game.buffs.IBuff}s of the {@link Player}.
   *
   * @param batch to draw to.
   * @param camera to display the drawing.
   */
  @ExcludeFromGeneratedCoverageReport
  public void draw(Batch batch, OrthographicCamera camera) {
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    drawHotBar(batch, camera, shapeRenderer);
    drawHealthBar(batch, camera, shapeRenderer);
    drawExpBar(batch, camera, shapeRenderer);
    drawBuffText(batch, camera);
  }

  @ExcludeFromGeneratedCoverageReport
  private void drawHotBar(Batch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
    int width = (int) (camera.viewportWidth * 0.5);
    int height = (int) (camera.viewportHeight * 0.075);

    float x =
        ((camera.position.x - camera.viewportWidth / 2) + (camera.viewportWidth - width) / 2f);
    float y = ((camera.position.y - camera.viewportHeight / 2) + 1.5f * height);

    shapeRenderer.setColor(hotBarBG);
    shapeRenderer.rect(x, y, width, height);

    shapeRenderer.end();
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(hotBarOutline);

    int blockWidth = width / this.hotBar.getNumSlots();

    batch.begin();
    for (int i = 0; i < this.hotBar.getNumSlots(); i++) {
      shapeRenderer.rect(x, y, blockWidth, height);

      var item = this.hotBar.getItem(i);
      if (item != null && item.getItemIcon() != null) {
        batch.draw(item.getItemIcon(), x, y, blockWidth, height);
      }

      x += blockWidth;
    }
    batch.end();
    shapeRenderer.setColor(hotBarFocusedOutline);

    shapeRenderer.rect(
        x - blockWidth * (this.hotBar.getNumSlots() - this.hotBar.getSelectedItem()),
        y,
        blockWidth,
        height);

    shapeRenderer.end();
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
  }

  @ExcludeFromGeneratedCoverageReport
  private void drawHealthBar(Batch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
    float healthBarX = camera.position.x - camera.viewportWidth / 2 + 20;
    float healthBarY =
        camera.position.y + camera.viewportHeight / 2 - this.healthBar.getHeight() - 20;

    shapeRenderer.setColor(Color.BLACK);
    shapeRenderer.rect(
        healthBarX, healthBarY, this.healthBar.getWidth(), this.healthBar.getHeight());
    shapeRenderer.setColor(Color.RED);
    shapeRenderer.rect(
        healthBarX,
        healthBarY,
        (int)
            (this.healthBar.getWidth()
                * (this.player.getHealth() / (double) this.player.getMaxHealth())),
        this.healthBar.getHeight());
  }

  @ExcludeFromGeneratedCoverageReport
  private void drawExpBar(Batch batch, OrthographicCamera camera, ShapeRenderer shapeRenderer) {
    float xpBarX = camera.position.x + camera.viewportWidth / 2f - this.expBar.getWidth() - 20;
    float xpBarY = camera.position.y - camera.viewportHeight / 2f + 20;

    shapeRenderer.setColor(Color.GOLDENROD);
    shapeRenderer.rect(xpBarX, xpBarY, this.expBar.getWidth(), this.expBar.getHeight());

    shapeRenderer.end();

    batch.begin();
    this.font.draw(
        batch,
        String.format("Level: %d XP: %d", this.player.getLevel(), this.player.getXp()),
        xpBarX + 5,
        xpBarY + this.expBar.getHeight() / 2f + 6);
  }

  @ExcludeFromGeneratedCoverageReport
  private void drawBuffText(Batch batch, OrthographicCamera camera) {
    float yOffset = 0;
    for (var buff : this.player.getActiveBuffs()) {
      String buffText = buff.toString();
      this.layout.setText(this.font, buffText);
      this.font.draw(
          batch,
          buffText,
          camera.position.x + (camera.viewportWidth / 2f) - this.layout.width - 5,
          camera.position.y + (camera.viewportHeight / 2f) - yOffset);
      yOffset += this.layout.height;
    }
    batch.end();
  }

  public HotBar getHotBar() {
    return hotBar;
  }
}

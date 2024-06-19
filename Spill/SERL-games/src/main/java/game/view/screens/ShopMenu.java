package game.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import game.controller.Controller;
import game.entities.npc.Merchant;
import game.items.BlueFlask;
import game.items.GreenFlask;
import game.items.IItem;
import game.items.PurpleFlask;
import game.items.RedFlask;
import game.model.Model;
import game.utils.SaveManager;
import util.ExcludeFromGeneratedCoverageReport;

/** Represent the merchant shop menu. */
@ExcludeFromGeneratedCoverageReport
public class ShopMenu implements Screen {
  private Merchant merchant; // The merchant whose shop this is.
  private SpriteBatch spriteBatch;
  private Stage stage;
  private Viewport viewport;
  private Model model;
  private IItem[] items;
  private GlyphLayout layout;
  private BitmapFont font;
  private String messageToDraw = "";
  private float messageTime = 0;

  public ShopMenu(Model model, Merchant merchant) {
    this.model = model;
    this.merchant = merchant;
    viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    items =
        new IItem[] {
          new RedFlask(), new BlueFlask(), new GreenFlask(), new PurpleFlask(),
        };
    this.layout = new GlyphLayout();
    this.font = new BitmapFont();
    font.setColor(Color.BLACK);

    spriteBatch = model.getBatch();
    stage = new Stage(viewport, spriteBatch);
    spriteBatch.setProjectionMatrix(stage.getCamera().combined);
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {
    Skin skin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));
    Table table = new Table();
    table.setFillParent(true);
    stage.addActor(table);

    table.add(new Label("Items", skin)).padRight(50);
    table.add(new Label("Descriptions", skin)).colspan(2);
    table.add(new Label("Price", skin)).padLeft(50).row();

    for (IItem item : items) {
      TextureRegionDrawable imageUp = new TextureRegionDrawable(item.getItemIcon());
      ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
      float buttonSize = 100;
      style.imageUp = imageUp;
      style.imageDown = imageUp;
      style.imageUp.setMinWidth(buttonSize);
      style.imageUp.setMinHeight(buttonSize);
      style.imageDown.setMinWidth(buttonSize);
      style.imageDown.setMinHeight(buttonSize);
      ImageButton itemButton = new ImageButton(style);
      itemButton.getImageCell().expand().fill();

      table.add(itemButton).size(buttonSize, buttonSize).padRight(50);
      table.add(new Label(item.description(), skin)).colspan(2);
      table.add(new Label("" + item.price(), skin)).padLeft(50);
      itemButton.addListener(
          new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
              if (model.getPlayer().getXp() >= item.price()) {
                messageToDraw = "Bought: " + item.toString();
                model.getPlayer().spendXP(item.price());
                model.getPlayer().addItem(item);
                new SaveManager(model).saveGameState();
              } else {
                messageToDraw = "You don't any enough to buy " + item.toString();
              }
              messageTime = 5f;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
              return true;
            }
          });
      table.row();
    }

    TextButton closeButton = new TextButton("Close", skin);
    closeButton.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            Gdx.app.log("SHOP", "Player is done buying.");
            merchant.playerFinishedBuying();
            model.goToGameScreen();
            Gdx.input.setInputProcessor(new Controller(model));
          }
        });
    table.add(closeButton).padTop(50);
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.valueOf("855e42"));
    stage.getCamera().update();

    spriteBatch.begin();
    if (messageTime > 0) {
      drawText(spriteBatch, messageToDraw);
      messageTime -= delta;
    }
    spriteBatch.end();

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    spriteBatch.dispose();
    stage.dispose();
    font.dispose();
  }

  private void drawText(SpriteBatch batch, String text) {
    int yOffset = 50;
    this.layout.setText(this.font, text);
    this.font.draw(
        batch,
        text,
        stage.getCamera().position.x
            + (stage.getCamera().viewportWidth / 2)
            - this.layout.width
            - yOffset,
        stage.getCamera().position.y + (stage.getCamera().viewportHeight / 2) - yOffset);
  }
}

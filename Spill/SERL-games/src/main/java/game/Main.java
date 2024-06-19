package game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import game.model.Model;
import game.view.screens.ScreenConfigurations;

public class Main extends ScreenConfigurations {
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
    cfg.setTitle(ScreenConfigurations.GAME_TITLE);
    cfg.setWindowedMode(ScreenConfigurations.SCREEN_WIDTH, ScreenConfigurations.SCREEN_HEIGHT);

    Model model = new Model();
    new Lwjgl3Application(model, cfg);
  }
}

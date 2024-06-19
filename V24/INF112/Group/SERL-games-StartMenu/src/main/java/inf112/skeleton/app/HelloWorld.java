package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

public class HelloWorld implements ApplicationListener {
	private int counter = 0;
	private int SCREENWIDTH = 480; // should be equal to Main.cfg.setWindowedMode()'s width.
	private int SCREENHEIGHT = 320; // should be equal to Main.cfg.setWindowedMode()'s height.
	private String GAMETITLE = "PROTOTYPE";
	private SpriteBatch batch;
	private BitmapFont font;
	//private Texture spriteImage;
	private Sound bellSound;
	//private Rectangle spriteRect;
	private Rectangle screenRect = new Rectangle();
	private float dx = 1, dy = 1;
	private Stage stage;
	private TextButton startGame;
	private TextButton settings;
	private TextButton quit;
	private TextButton.TextButtonStyle tbs;
	//private Skin skin;

	private TextButton addButton(TextButton button, String buttonText, int x, int y) {
		button = new TextButton(buttonText, tbs);
		stage.addActor(button);
		button.setPosition(x, y);

		return button;
	}

	@Override
	public void create() {
		// Called at startup
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();
		tbs = new TextButton.TextButtonStyle();
		tbs.font = font;

		// Create buttons for Start Menu
		startGame = addButton(startGame, "Start Game", 20, SCREENHEIGHT - 60);
		settings = addButton(settings, "Settings", 20, SCREENHEIGHT - 80);
		quit = addButton(quit, "Quit", 20, SCREENHEIGHT - 100);

		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.RED);
		//spriteImage = new Texture(Gdx.files.internal("obligator.png"));
		//spriteRect = new Rectangle(1, 1, spriteImage.getWidth() / 2, spriteImage.getHeight() / 2);
		bellSound = Gdx.audio.newSound(Gdx.files.internal("blipp.ogg"));
		Gdx.graphics.setForegroundFPS(60);
	}

	@Override
	public void dispose() {
		// Called at shutdown

		// Graphics and sound resources aren't managed by Java's garbage collector, so
		// they must generally be disposed of manually when no longer needed. But,
		// any remaining resources are typically cleaned up automatically when the
		// application exits, so these aren't strictly necessary here.
		// (We might need to do something like this when loading a new game level in
		// a large game, for instance, or if the user switches to another application
		// temporarily (e.g., incoming phone call on a phone, or something).
		batch.dispose();
		font.dispose();
		//spriteImage.dispose();
		bellSound.dispose();
	}

	@Override
	public void render() {
		// Called when the application should draw a new frame (many times per second).

		// This is a minimal example – don't write your application this way!

		// Start with a blank screen
		ScreenUtils.clear(Color.BLACK);

		// Draw calls should be wrapped in batch.begin() ... batch.end()
		batch.begin();
		//System.out.println("Title's X-start coordinate is " + (SCREENWIDTH - 460));
		font.draw(batch, GAMETITLE, SCREENWIDTH - 460, SCREENHEIGHT - 20);
		//batch.draw(spriteImage, spriteRect.x, spriteRect.y, spriteRect.width, spriteRect.height);
		batch.end();

		stage.draw();

		// Move the alligator a bit. You normally shouldn't mix rendering with logic in
		// this way. (Also, movement should probably be based on *time*, not on how
		// often we update the graphics!)
		//Rectangle.tmp.set(spriteRect);
		//Rectangle.tmp.x += dx;
		//Rectangle.tmp2.set(spriteRect);
		//Rectangle.tmp2.y += dy;
		//if (screenRect.contains(Rectangle.tmp))
		//	spriteRect.x += dx;
		//else
		//	dx = -dx;
		//if (screenRect.contains(Rectangle.tmp2))
		//	spriteRect.y += dy;
		//else
		//	dy = -dy;

		// Don't handle input this way – use event handlers!
		if (Gdx.input.justTouched()) { // check for mouse click
			bellSound.play();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // check for key press
			Gdx.app.exit();
		}

		startGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent changeEvent, Actor actor) {
				//counter++;
				//System.out.println("Start Game Pressed for " + counter + " time.");
				System.out.println("Start Game Pressed.");
			}
		});
		settings.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent changeEvent, Actor actor) {
				System.out.println("Settings Pressed.");
			}
		});
		quit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent changeEvent, Actor actor) {
				System.out.println("Quit Pressed.");
			}
		});
	}

	@Override
	public void resize(int width, int height) {
		// Called whenever the window is resized (including with its original site at
		// startup)

		screenRect.width = width;
		screenRect.height = height;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}

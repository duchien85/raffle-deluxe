package com.rptr.raffle_deluxe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import org.w3c.dom.Text;
import org.w3c.dom.css.Rect;

import java.awt.Desktop;
import java.util.ArrayList;

public class RaffleGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	// Storyteller story;
	// LevelGenerator lev_gen;

	Texture texBow,
			texBloon,
			texArrow,
			texCastle,
			texWall;
	TextureRegion regionBow, regionArrow;
	BitmapFont font1;

	Array<Balloon> balloons;
	Array<Arrow> arrows;
	Rectangle bow;

	double bowAngle = 0.0f;

	final static int width = 800;
	final static int height = 600;
	final static int tile_width = 50;
	final static int tile_margin = 10;
	final static int tile_total_width = tile_width + tile_margin * 2;

	final static int side_panel_w = 200;

	long lastFire, reloadTime = 500;
	long lastBalloon, balloonInterval = 6 * 1000;

	int score = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		texBow = new Texture("Bow1.png");
		texBloon = new Texture("balloon_green.png");
		texArrow = new Texture("Arrow1.png");

		texCastle = new Texture("castle.jpg");
		texWall = new Texture("wall.png");

		font1 = new BitmapFont(Gdx.files.internal("HamletOrNot.fnt"), false);

		regionBow = new TextureRegion(texBow);
		regionArrow = new TextureRegion(texArrow,
				texArrow.getWidth(), texArrow.getHeight());

		arrows = new Array<Arrow>();
		balloons = new Array<Balloon>();

		int w = width - side_panel_w;
		int h = height;
		bow = new Rectangle(w / 2, 0, 50, 50);

		generateLevel();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		float degs = (float)bowAngle * 180 / (float)Math.PI;
		float h = regionBow.getRegionHeight();
		float delta = Gdx.graphics.getDeltaTime();

		// background
		batch.draw(texCastle, 0, 0, width, height);
		batch.draw(texWall, 0, 0, width, height);

		font1.draw(batch, "Score: "+score, width - side_panel_w, height - 50);

		// bow
		batch.draw(regionBow, bow.x, bow.y, 0, h / 2,
				regionBow.getRegionWidth(), h,
				1.0f, 1.0f, degs);
		batch.draw(texArrow, bow.x, bow.y);

		// arrows
		for (Arrow a : arrows) {
			a.tick(this);
			degs = a.angle * 180 / (float)Math.PI;
			batch.draw(regionArrow, a.x, a.y, 0, 0,
					texArrow.getWidth(), texArrow.getHeight(),
					0.5f, 0.5f, degs);
		}

		// balloons
		for (Balloon b : balloons) {
			b.tick(delta);
			batch.draw(texBloon, b.x, b.y, 50, 50);
		}

		long time = System.currentTimeMillis();

		if (time >= lastBalloon + balloonInterval) {
			generateBallonRow(1);
			lastBalloon = time;
		}

		batch.end();

		boolean touched = Gdx.input.isTouched();

		float x = Gdx.input.getX();
		float y = height - Gdx.input.getY();
		bowAngle = Math.atan2(y - bow.y, x - bow.x);

		if (touched && time >= lastFire + reloadTime) {
			spawnArrow();
			lastFire = time;
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	private void spawnArrow () {
		double angle = bowAngle;
		double dx = Math.cos(angle);
		double dy = Math.sin(angle);
		float power = 4;
		float vx = (float)dx * power;
		float vy = (float)dy * power;

		Arrow arrow = new Arrow(bow.x, bow.y, vx, vy, (float)angle);
		arrows.add(arrow);
	}

	private void spawnBalloon (float x, float y) {
		Balloon ball = new Balloon(x, y);
		balloons.add(ball);
	}

	private void generateLevel () {
		int numy = 5;

		for (int y = 1; y <= numy; y ++) {
			generateBallonRow(y);
		}
	}

	private void generateBallonRow (int y) {
		int numx = 8;
		float w = 50;
		float h = 50;
		float margin = 20;

		for (int x = 0; x < numx; x ++) {
			spawnBalloon((float)x * (w + margin),
					height - (float)y * (h + margin));
		}
	}

	public void hitArea (int tx, int ty, Arrow arrow) {
		Balloon bloon = getBalloonAt(tx, ty);

		if (bloon != null && bloon.touchingMe(arrow)) {
			balloons.removeValue(bloon, false);
			score += 1;
		}
	}

	private Balloon getBalloonAt (int tx, int ty) {
		for (Balloon b : balloons) {
			if (b.getTileX() == tx && b.getTileY() == ty)
				return b;
		}

		return null;
	}

	public void removeMe (Arrow arrow) {

	}

	public static int randint (int i) {
		return (int)Math.random() % i;
	}
}

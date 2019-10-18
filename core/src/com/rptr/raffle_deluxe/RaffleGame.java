package com.rptr.raffle_deluxe;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
			texArrow;
	TextureRegion regionBow, regionArrow;

	Array<Balloon> balloons;
	Array<Arrow> arrows;
	Rectangle bow;

	double bowAngle = 0.0f;

	final static int width = 800;
	final static int height = 600;
	int side_panel_w = 200;

	long lastFire, reloadTime = 500;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		texBow = new Texture("Bow1.png");
		texBloon = new Texture("balloon_green.png");
		texArrow = new Texture("Arrow1.png");

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

		for (Balloon b : balloons) {
			batch.draw(texBloon, b.x, b.y, 50, 50);
		}

		batch.end();

		long time = System.currentTimeMillis();
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
		float power = 2;
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
		int numx = 8;
		int numy = 5;
		float w = 50;
		float h = 50;
		float margin = 10;

		for (int x = 0; x < numx; x ++) {
			for (int y = 1; y <= numy; y ++) {
				spawnBalloon((float)x * (w + margin),
						height - (float)y * (h + margin));
			}
		}
	}

	public void hitArea () {

	}
}

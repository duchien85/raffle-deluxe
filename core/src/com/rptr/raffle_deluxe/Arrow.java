package com.rptr.raffle_deluxe;

public class Arrow extends Entity {
    float
          velX, velY,
          angle;
    // should be oldTileX
    int tileX = -1, tileY = -1;

    Arrow (float x, float y, float velX, float velY, float angle) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.angle = angle;
    }

    public void tick (RaffleGame game) {
        float timeDelta = 1.0f;

        x += timeDelta * velX;
        y += timeDelta * velY;

        int tx = getTileX();
        int ty = getTileY();

        if (tx != tileX || ty != tileY) {
            game.hitArea(tx, ty, this);
            tileX = tx;
            tileY = ty;
        }

        if (outsideScreen()) {
            game.removeMe(this);
        }
    }
}

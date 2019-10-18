package com.rptr.raffle_deluxe;

public class Arrow {
    float x, y,
          velX, velY,
          angle;
    int tileX = 0, tileY = 0;

    Arrow (float x, float y, float velX, float velY, float angle) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.angle = angle;
    }

    public void tick (RaffleGame game) {
        float timeDelta = 1.0f;

        x += timeDelta * velX;
        y += timeDelta * velY;

        tileX =

        game.hitArea();
    }
}

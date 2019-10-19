package com.rptr.raffle_deluxe;

public class Arrow extends Entity {
    float
          velX, velY,
          angle,
          power = 500;
    boolean suicide = false;

    // should be oldTileX
    int tileX = -1, tileY = -1;

    Arrow (float x, float y, float velX, float velY, float angle) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.angle = angle;
    }

    public void tick (RaffleGame game, float timeDelta) {
        x += timeDelta * velX * power;
        y += timeDelta * velY * power;

        int tx = getTileX();
        int ty = getTileY();

        if (tx != tileX || ty != tileY) {
            game.hitArea(tx, ty, this);
            tileX = tx;
            tileY = ty;
        }

        float grav = 0.5f;

        velY -= timeDelta * grav;

        float nx = x + velX;
        float ny = y + velY;
        double ang = Math.atan2(ny - y, nx - x);
        angle = (float)ang;

        if (outsideScreen()) {
            bounce();
        }

        if (suicide) {
            game.removeMe(this);
        }
    }

    private void bounce () {
        if (y <= RaffleGame.screen_bottom_edge) {
            suicide = true;
            return;
        }

        if (x > RaffleGame.screen_right_edge) {
            x = RaffleGame.screen_right_edge - 1;
        }
        else if (x < RaffleGame.screen_left_edge) {
            x = RaffleGame.screen_left_edge + 1;
        }

        if (y > RaffleGame.screen_top_edge) {
            y = RaffleGame.screen_top_edge - 1;
            angle = -angle;

        } else {
            angle = ((float)Math.PI - angle);
        }

        // set vel based on angle
        double dx = Math.cos(angle);
        double dy = Math.sin(angle);

        velX = (float)dx * Math.abs(velX);
        velY = (float)dy ;
    }
}

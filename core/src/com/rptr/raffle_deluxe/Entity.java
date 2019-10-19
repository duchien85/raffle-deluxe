package com.rptr.raffle_deluxe;

public class Entity {
    float x, y;

    Entity (float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getTileX () {
        return (int)(x) / RaffleGame.tile_total_width;
    }

    public int getTileY () {
        return (int)(RaffleGame.height - y) / RaffleGame.tile_total_width;
    }

    protected boolean outsideScreen () {
        return x < RaffleGame.screen_left_edge ||
                y <  RaffleGame.screen_left_edge ||
                x >= RaffleGame.screen_right_edge ||
                y >= RaffleGame.screen_top_edge;
    }
}

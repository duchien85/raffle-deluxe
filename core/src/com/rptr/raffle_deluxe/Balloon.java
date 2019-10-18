package com.rptr.raffle_deluxe;

public class Balloon extends Entity {
    Balloon (float x, float y) {
        super(x, y);
    }

    public boolean touchingMe (Arrow arrow) {
        return true;
    }

    public void tick (float timeDelta) {
        y -= timeDelta * 10;
    }
}

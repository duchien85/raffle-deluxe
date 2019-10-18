package com.rptr.raffle_deluxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class Jukebox {

    Array<Sound> twangs;
    Array<Sound> pops;

    Jukebox () {
        twangs = new Array<>();
        twangs.add(loadSound("sfx/arrowHit01.wav"));
        twangs.add(loadSound("sfx/arrowHit02.wav"));
        twangs.add(loadSound("sfx/arrowHit03.wav"));
        twangs.add(loadSound("sfx/arrowHit04.wav"));
        twangs.add(loadSound("sfx/arrowHit05.wav"));

        pops = new Array<>();
        pops.add(loadSound("sfx/swish_2.wav"));
    }

    public void playTwang () {
        playRandom(twangs);
    }

    public void playPop () {
        playRandom(pops);
    }

    private void playRandom (Array<Sound> sounds) {
        int i = RaffleGame.randint(sounds.size);
        sounds.get(i).play();
    }

    private Sound loadSound (String file) {
        return Gdx.audio.newSound(Gdx.files.internal(file));
    }
}

package coder.victorydst3.mangareader.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by lantm-mac-air on 3/22/16
 */
public final class BusProvider {

    private static Bus sBus;

    private BusProvider() {
        //not call
    }

    public static synchronized Bus getInstance() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}

package coder.victorydst3.mangareader.model;

import lombok.Data;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/28/16.
 */

@Data
public class NavigationDrawerItem {
    private boolean showNotify;
    private String title;

    public NavigationDrawerItem() {
    }

    public NavigationDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }
}

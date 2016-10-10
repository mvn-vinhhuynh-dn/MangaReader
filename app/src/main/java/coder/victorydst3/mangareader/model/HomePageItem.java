package coder.victorydst3.mangareader.model;

import android.support.v4.app.Fragment;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright © 2015 AsianTech inc.
 * Created by tientun on 5/28/15.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class HomePageItem {
    private Fragment fragment;
    private String title;
    private int drawableResource;
}

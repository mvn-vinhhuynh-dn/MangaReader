package coder.victorydst3.mangareader.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by lantm-mac-air on 10/6/16
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class MainItem {
    private String title;
    private int drawableResource;
}


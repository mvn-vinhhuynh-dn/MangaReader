package coder.victorydst3.mangareader.util;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/28/16.
 */

public final class ScriptParser {
    private ScriptParser() {
        // No-op
    }

    public static List<String> extractLinks(String text) {
        List<String> links = new ArrayList<>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
            links.add(url);
        }
        return links;
    }
}

package coder.victorydst3.mangareader.model;

import java.util.List;

import lombok.Data;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */
@Data
public class MangaDetail {
    List<Chapter> chapters;
    Manga manga;
}

package coder.victorydst3.mangareader.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Builder;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 9/27/16.
 */
@Getter
@Builder
public class MangaDetail {
    @Setter
    List<Chapter> chapters;
    @Setter
    Manga manga;
}

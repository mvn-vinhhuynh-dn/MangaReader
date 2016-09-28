package coder.victorydst3.mangareader.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

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
@Parcel
public class Manga {
    String name;
    String dateUpdate;
    List<String> categories;
    String newChap;
    String numRead;
    @Setter
    String imageUrl;
    List<String> author;
    String processUrl;
    @Setter
    String status;

    @ParcelConstructor
    Manga(String name, String dateUpdate, List<String> categories, String newChap, String numRead, String imageUrl, List<String> author, String processUrl, String status) {
        this.name = name;
        this.dateUpdate = dateUpdate;
        this.categories = categories;
        this.newChap = newChap;
        this.numRead = numRead;
        this.imageUrl = imageUrl;
        this.author = author;
        this.processUrl = processUrl;
        this.status = status;
    }
}

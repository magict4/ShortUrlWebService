package me.jiarui.shorturl.repository;

/**
 * Created by Sher10ck on 7/20/15.
 */
public interface UniqueNumRepository {
    void add(int num);

    int getCountOfNumsEndingWithSpecificDigits(int endingDigits);
}

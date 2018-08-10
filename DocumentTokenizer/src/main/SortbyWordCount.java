package main;

import java.util.Comparator;

public class SortbyWordCount implements Comparator<WordCollection> {

    @Override
    public int compare(WordCollection o1, WordCollection o2) {
        return o1.totalWord - o2.totalWord;
    }
}

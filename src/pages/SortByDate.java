package pages;

import java.util.Comparator;

import main.page.Reply;

/**
 * Project 5 - SortByDate
 * <p>
 * Description - Sorts replies in a forum by date in descending order
 *
 * @author Changxiang Gao
 * @version 11/15/2021
 */
public class SortByDate implements Comparator<Reply> {
    // Used for sorting in descending order of dates
    public int compare(Reply a, Reply b) {
        return b.getTime().compareTo(a.getTime());
    }
}
package pages;

import java.util.Comparator;
import main.page.Reply;

/**
* Project 4 - SortByUpvotes
*
* Description - Sorts replies in a forum by upvotes in descending order
*
* @author Changxiang Gao
*
* @version 11/15/2021
*/

public class SortByUpvotes implements Comparator<Reply> {
    // Used for sorting in ascending order of
    // upvotes in descending order
    public int compare(Reply a, Reply b) {
        return b.getUpvotedStudents().size() - a.getUpvotedStudents().size();
    }
}
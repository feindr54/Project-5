package pages; 

import java.util.Comparator;
import main.page.Reply;

/**
* Project 4 - SortByName
*
* Description - Sorts replies in a forum by name in ascending alphabetical order
*
* @author Changxiang Gao
*
* @version 11/15/2021
*/
public class SortByName implements Comparator<Reply> {
    // Used for sorting in ascending order of student identifier
    public int compare(Reply a, Reply b) {
        return a.getOwner().getIdentifier().compareTo(b.getOwner().getIdentifier());
    }
}
package users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import main.page.*;

/**
 * Project 5 - User
 * <p>
 * Description - A child class of User which gives the teacher special
 * permissions
 *
 * @author Alex Younkers
 * @version 11/8/2021
 */

public class Teacher extends User implements Serializable {

    public Teacher(String email, String password) {
        super(email, password, false);
        this.identifier = this.email.substring(0, this.email.indexOf('@'));
    }

    // COURSE methods

    // FORUM methods
    // There are a few ways i can think of to implement these methods
    // One is to have the arrayList of forums as a paremeter as well as the forum
    // they want to access
    // Or we can just have the current forum as a paremeter and just call its
    // methods

    // I think that for the delete forum, we need the list and the forum to delete
    // so that it can update
    // the forum list accordingly

    @Override
    public String toString() {
        return "Teacher [email=" + email + ", identifier=" + identifier + ", password="
                + password + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Teacher)) {
            return false;
        }

        Teacher u = (Teacher) o;
        return ((Teacher) o).getEmail().equals(this.getEmail());
    }
}

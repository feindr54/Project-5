package users;

import java.io.Serializable;

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
        return u.getEmail().equals(this.getEmail());
    }
}

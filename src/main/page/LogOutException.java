package main.page;

/**
 * Project 4 - LogOutException
 * <p>
 * Description - A custom exception that once caught, allows users to log out of the system at any
 * point in time. The try-catch statement will be located in the Main class, which allows the user
 * to quit the LMS access() method.
 * <p>
 * The exception will be thrown by every page.
 *
 * @author Changxiang Gao
 * @version 11/9/2021
 */
public class LogOutException extends Exception {
    public LogOutException() {
        super();
    }
}

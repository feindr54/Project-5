package networking; 

// client sends request to server
/**
* Project 5 - Request
*
* Description - This class is a representation of the message send by the client to the server to request for
 * information to be received and loaded by the client to present to the user
*
* @author Changxiang Gao
*
* @version 12/7/2021
*/
public class Request {
    private final int OPERATION; // 0 - access, 1 - add, 2 - edit, 3 - delete, 4 - create account, 5 - login
    private final Object OBJ; // this object could be an instance of LMS, course or, forum, or an array containing username and
    // password

    public Request(int operation, Object obj) {
        this.OPERATION = operation;
        this.OBJ = obj;
    }

    public int getOPERATION() {
        return OPERATION;
    }

    public Object getOBJ() {
        return OBJ;
    }
}
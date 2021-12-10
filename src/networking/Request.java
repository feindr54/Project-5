package networking; 

// client sends request to server

import java.io.Serializable;

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
public class Request implements Serializable {
    private final int OPERATION; // 0 - access, 1 - add, 2 - edit, 3 - delete, 4 - create account, 5 - login, 6 - ask
    // to sort replies, 7 - change username, 8 - change password, 9 - logout
    private final int OPERAND; // 0 - course, 1 - forum, 2 - reply, 3 - comment
    private final Object OBJ; // this object could be an instance of LMS, course or, forum, or an array containing
    // username and password, and the role ("student" or "teacher")
    // if the user is trying to add/delete, the user will send the String nameOfObject
    // if the user is trying to edit, user will send 2 Strings, oldName and newName
    // if the user is trying to

    // when the user logs out, object sent is null 

    // in the case of accessing a page, the structure would be (0, String type_of_object/name_of_object)
    // eg (0, "Course/CS 180")
    public Request(int operation, Object obj) {
        this.OPERATION = operation;
        this.OBJ = obj;
        this.OPERAND = -1;
    }

    public Request(int operation, int operand, Object obj) {
        this.OPERATION = operation;
        this.OBJ = obj;
        this.OPERAND = operand;
    }

    public int getOPERATION() {
        return OPERATION;
    }

    public Object getOBJ() {
        return OBJ;
    }
    public int getOPERAND() {
        return OPERAND;
    }
}
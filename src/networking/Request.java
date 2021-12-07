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
    int operations; // 0 - access, 1 - add, 2 - edit, 3 - delete, 4 - create account, 5 - login 
    Object obj; // this object could be an instance of LMS, course or, forum, or an array containing username and password    

    public Request(int operation, Object obj) {
        this.operations = operation;
        this.obj = obj; 
    }
}
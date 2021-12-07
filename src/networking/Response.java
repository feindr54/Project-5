package networking; 

/**
* Project 5 - Response
*
* Description - This class is a representation of the information sent by the server to the client.
*
* @author Changxiang Gao
*
* @version 12/7/2021
*/

public class Response {
    // TODO - settle how many types of responses there can be
    int type; // 0 - information, 1 - errors (invalid input)
    Object obj; // object sent to the client to display

    public Response(int type, Object obj) {
        this.type = type;
        this.obj = obj;
    }
}
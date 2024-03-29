package networking;

import java.io.Serializable;

/**
 * Project 5 - Response
 * <p>
 * Description - This class is a representation of the information sent by the server to the client.
 *
 * @author Changxiang Gao
 * @version 12/7/2021
 */

public class Response implements Serializable {
    private final int type; // 0 - information, 1 - errors (invalid input), 2 - log out
    private final Object obj; // object sent to the client to display
    // the only exception is when the user has successfully logged in, the object sent back is an array of Objects
    // holding the User object and the LMS object

    public Response(int type, Object obj) {
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public Object getObj() {
        return obj;
    }
}
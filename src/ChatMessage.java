
import java.io.Serializable;


public class ChatMessage implements Serializable {
    String user;
    String message;

    public ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }
    
    
}

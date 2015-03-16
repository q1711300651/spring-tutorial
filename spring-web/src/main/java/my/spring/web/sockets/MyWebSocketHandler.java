package my.spring.web.sockets;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleBinaryMessage( WebSocketSession session, BinaryMessage message ) {
        super.handleBinaryMessage( session, message );
    }
}

package ml.streamcore.communication.types;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import ml.streamcore.utils.tcp.TcpConnection;


public class Client {

  /**
   * The Tcp connection the client communicates with.
   */
  @Getter
  private TcpConnection socket;

  /**
   * This id is the same as the tcp connections uuid.
   */
  @Getter
  private UUID clientID;

  /**
   * This is only important if the server is private and has a password / security key.
   */
  @Getter @Setter
  private boolean authorized;

  /**
   * Datastructure for mananaging a client.
   * @param socket
   */
  public Client(TcpConnection socket) {
    this.socket = socket;
    this.clientID = socket.getUuid();
  }
}

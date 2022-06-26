package ml.streamcore.communication.types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;


public class Room {

  /**
   * Unique room identifier.
   */
  @Getter
  private UUID roomID = UUID.randomUUID();

  /**
   * The Person streaming.
   */
  @Getter
  private Client host;

  /**
   * The viewers watching the stream.
   */
  @Getter
  private List<Client> viewers = new ArrayList<>();


  /**
   * Manages a room with a single host and multiple viewers.
   * @param host The host of the room.
   */
  public Room(Client host) {
    this.host = host;
  }

  /**
   * Adds a viewer to the room.
   * @param viewer The viewer to add.
   */
  public void addViewer(Client viewer) {
    viewers.add(viewer);
  }

  /**
   * Removes a viewer from the room.
   * @param viewer The viewer to remove.
   */
  public void removeViewer(UUID viewerID) {
    viewers.removeIf(v -> v.getClientID().equals(viewerID));
  }

  /**
   * Notifies all viewers in the room that the room has been closed.
   */
  public void close() {
    // TODO: close room
    // Notify all viewers that the room is closed.
    // maybe give reason
  }
}

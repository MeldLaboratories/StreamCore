package ml.streamcore.communication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;
import ml.streamcore.utils.tcp.TcpConnection;
import ml.streamcore.utils.tcp.TcpServer;
import ml.streamcore.utils.tcp.events.args.*;
import ml.streamcore.communication.types.*;
import ml.streamcore.communication.enums.*;
import ml.streamcore.communication.formats.receive.commands.*;
import ml.streamcore.communication.formats.send.Result;


@Slf4j
public class Server {

  private int port;

  private TcpServer server;

  /**
   * Gson instance for message serialization and deserialization.
   */
  private Gson gson = new Gson();

  /**
   * Stores all the clients with their respective connections.
   */
  private List<Client> clients = new ArrayList<>();

  /**
   * Stores all the rooms that are currently active.
   */
  private List<Room> rooms = new ArrayList<>();



  /**
   * The StreamCore communication and API server.
   * @param port
   */
  public Server(int port) {
    try {
      this.port = port;

      server = new TcpServer(port);

      // Set the event handler for the server socket connection
      server.onSocketConnect(this::onSocketConnect);
    } catch (IOException e) {
      log.error("Failed to create the server socket.");
      System.exit(1);
    }
  }


  /**
   * Starts listening on the given port.
   */
  public void start() {
    log.info("Server listening on port {}", port);
    server.listen();
  }

  /**
   * Event handler for incoming socket connections.
   */
  private void onSocketConnect(Object sender, OnSocketConnectArgs args) {
    log.info("Client connected: {}", args.socket.getBaseSocket().getRemoteSocketAddress());
    Client client = new Client(args.socket);
    clients.add(client);
    //TODO: dispose of the client when it disconnects or is inactive for time
    client.getSocket().onDataReceived(this::onSocketData);
  }

  /**
   * Event handler for incoming socket data.
   */
  private void onSocketData(TcpConnection socket, OnSocketDataReceivedArgs args) {

    //TODO: authorization
    
    Client client = getClientFromSocket(socket);

    if (client == null) {
      log.error("Client not found.");
      //TODO: handle this well :)
      return;
    }
    
    String message = new String(args.data).trim();

    CommandBase commandBase;
    try {
      commandBase = gson.fromJson(message, CommandBase.class);
    }
    catch (JsonSyntaxException e) {
      log.error("Invalid command received: {}", message);
      //TODO: send result to client bad syntax
      return;
    }

    log.trace("Client data: {}", message);

    switch(commandBase.commandID) {
      case Command.PING:
        handlePing(client);
        break;

      //TODO: Unkown command result code
      default:
        log.warn("Client sent unknown commandID: {}", commandBase.commandID);
        break;
    }
  }


  /**
   * Returns successful ping result.
   */
  private void handlePing(Client client){
    try {
      Result result = new Result(ResultCode.OK, Command.PING);
      String json = gson.toJson(result);
      log.trace("Sending ping result: {}", json);
      client.getSocket().send(json);
    }
    catch(IOException e) {
      log.error("Failed to send ping result.");
    }
  }


  /**
   * Retrieves the client instance for the given socket.
   * @return the client instance or null if not found.
   * @param socket
   */
  private Client getClientFromSocket(TcpConnection socket) {
    UUID socketUuid = socket.getUuid();

    for (Client client : clients) {
      if (client.getClientID().equals(socketUuid)) {
        return client;
      }
    }

    return null;
  }
}

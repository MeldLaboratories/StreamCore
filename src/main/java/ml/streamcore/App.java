package ml.streamcore;

import ml.streamcore.communication.Server;

public final class App {

    /**
     * StreamCore by MeldLabs.
     */
    public static void main(String[] args) {
      Server server = new Server(2000);
      server.start();
    }
}

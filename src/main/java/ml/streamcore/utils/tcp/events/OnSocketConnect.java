package ml.streamcore.utils.tcp.events;

import ml.streamcore.utils.tcp.TcpServer;
import ml.streamcore.utils.tcp.events.args.OnSocketConnectArgs;

public interface OnSocketConnect {
  public void onConnect(TcpServer sender, OnSocketConnectArgs args);
}

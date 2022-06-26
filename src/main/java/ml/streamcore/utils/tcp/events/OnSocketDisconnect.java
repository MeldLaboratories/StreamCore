package ml.streamcore.utils.tcp.events;

import ml.streamcore.utils.tcp.TcpConnection;
import ml.streamcore.utils.tcp.events.args.OnSocketDisconnectArgs;

public interface OnSocketDisconnect {
  public void onDisconnect(TcpConnection sender, OnSocketDisconnectArgs args);
}

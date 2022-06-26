package ml.streamcore.utils.tcp.events;

import ml.streamcore.utils.tcp.TcpConnection;
import ml.streamcore.utils.tcp.events.args.OnSocketDataReceivedArgs;

public interface OnSocketDataReceived {
  public void onDataReceived(TcpConnection sender, OnSocketDataReceivedArgs args);
}

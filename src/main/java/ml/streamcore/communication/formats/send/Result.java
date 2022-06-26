package ml.streamcore.communication.formats.send;

public class Result {

  public final int type = 0;
  public int resultCode;
  public int commandID;

  /**
   * A Result is the answer to a command from a client.
   * @param resultCode
   * @param commandID
   */
  public Result(int resultCode, int commandID) {
    this.resultCode = resultCode;
    this.commandID = commandID;
  }
}

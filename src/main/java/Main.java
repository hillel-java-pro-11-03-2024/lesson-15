import service.TerminalService;

public class Main {

  static TerminalService terminal = new TerminalService("application.yaml");

  public static void main(String[] args) {
    terminal.start();
  }

}

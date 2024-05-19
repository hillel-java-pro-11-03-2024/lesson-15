package processor;

public class StandartMessageProcessor implements MessageProcessor {

  private static final char[] BLACKLIST_CHARS = {'ё','ы','э'};
  private static final String QUESTION = "ага, що таке паляниця?";
  private static final String ANSWER = "хлеб";

  @Override
  public boolean validate(String message) {
    String lowerString = message.toLowerCase();
    for (char c : BLACKLIST_CHARS) {
      if (lowerString.indexOf(c) >= 0) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String answer(String message) {
    return switch(message) {
      case "hello" -> "привiт";
      case "how r u?" -> "все добре, як вашi справи?";
      default -> "";
    };
  }

  @Override
  public String question() {
    return QUESTION;
  }

  @Override
  public boolean answerQuestion(String msg) {
    return msg.equalsIgnoreCase(ANSWER);
  }
}

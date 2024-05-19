package processor;

public interface MessageProcessor {

  boolean validate(String message);

  String answer(String message);

  String question();

  boolean answerQuestion(String msg);

}

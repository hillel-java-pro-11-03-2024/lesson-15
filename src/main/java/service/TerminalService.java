package service;

import static java.lang.System.exit;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.time.format.DateTimeFormatter.ofPattern;
import static utils.StringUtils.isoToUtf8;
import static utils.StringUtils.utf8ToIso;

import configuration.ConfigAutoLoader;
import configuration.model.AppConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import processor.MessageProcessor;
import processor.StandartMessageProcessor;
import user.User;

public class TerminalService {

  private final int port;
  private final MessageProcessor messageProcessor;

  private static final DateTimeFormatter DATE_TIME_FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss");

  public TerminalService(String configFilename) {
    messageProcessor = new StandartMessageProcessor();
    AppConfig config = new ConfigAutoLoader().load(configFilename);
    port = config.getPort();
  }

  public void start() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      String encoding = System.getProperty("file.encoding");
      System.out.println("Console Encoding: " + encoding);
      System.out.println("Listening port: " + port);
      Socket socket = serverSocket.accept();
      User user = newUser();
      System.out.println("User " + user.getClientId() + " connected. Awaiting message.");

      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), ISO_8859_1));
      PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), ISO_8859_1), true);

      while (socket.isConnected()) {
        String message = isoToUtf8(input.readLine());
        user.getMessages().add(message);

        System.out.println("User message: " + message);

        if (message.equals("exit")) {
          socket.close();
          exit(1);
        }

        boolean validMessage = messageProcessor.validate(message);
        if (validMessage) {
          output.println(utf8ToIso(messageProcessor.answer(message)));
        } else {
          output.println(utf8ToIso(messageProcessor.question()));
          String answer = isoToUtf8(input.readLine());
          if (messageProcessor.answerQuestion(answer)) {
            output.println(utf8ToIso(LocalDateTime.now().format(DATE_TIME_FORMATTER)));
          }
          socket.close();
          exit(1);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not start server");
    }
  }

  private User newUser() {
    return new User()
        .withClientId(UUID.randomUUID())
        .withConnected(LocalDateTime.now());
  }
}

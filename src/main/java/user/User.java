package user;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@With
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

  UUID clientId;
  LocalDateTime connected;
  List<String> messages = new LinkedList<>();

}

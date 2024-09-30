package han.aim.se.noyoumaynot.movie.service;

import han.aim.se.noyoumaynot.movie.repository.UserToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Service
public class AuthenticationService {
  ArrayList<UserToken> userTokens = new ArrayList<>();

  private String hardCodedUsername = "testUser";
  private String getHardCodedUsername = "testPassword";

  public UserToken login(String username, String password) {
    if(hardCodedUsername.equals(username) && password.equals(getHardCodedUsername)) {
      UserToken userToken = new UserToken(username);
      userTokens.add(userToken);
      return userToken;
    } else {
      return null;
      //throw new handleInvalideLoginExpetionException("logingegevens incorrect");
    }
  }

  public boolean isValidToken(String token) {
    return userTokens.stream().anyMatch(t -> t.getToken().equals(token));
  }

  public String getUsername(String token) {
    return userTokens.stream()
            .filter(t -> t.getToken().equals(token))
            .map(UserToken::getUsername)
            .findFirst()
            .orElse(null);
  }
}

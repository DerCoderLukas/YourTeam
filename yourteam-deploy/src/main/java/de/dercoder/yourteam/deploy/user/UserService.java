package de.dercoder.yourteam.deploy.user;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import de.dercoder.yourteam.core.user.User;
import de.dercoder.yourteam.core.user.UserRepository;
import de.dercoder.yourteam.deploy.configuration.ConfigurationRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public final class UserService {
  private final UserRepository repository;
  private final ConfigurationRepository configurationRepository;
  private Key secretKey;

  private UserService(
    UserRepository repository, ConfigurationRepository configurationRepository
  ) {
    this.repository = repository;
    this.configurationRepository = configurationRepository;
  }

  public void checkAuthority(Map<String, Object> input) throws Exception {
    Preconditions.checkNotNull(input);
    if (!isAuthorized(input)) {
      throw failedAuthorization();
    }
  }

  private boolean isAuthorized(Map<String, Object> input) throws Exception {
    var claims = extractJwsClaims(input);
    var name = (String) claims.getBody().get("userName");
    return repository.findByName(name)
      .map(user -> true)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Could not find User"
      ));
  }

  private Jws<Claims> extractJwsClaims(Map<String, Object> input) {
    var jws = (String) input.get("apiKey");
    return Jwts.parserBuilder()
      .setSigningKey(secretKey)
      .build()
      .parseClaimsJws(jws);
  }

  private ResponseStatusException failedAuthorization() {
    return new ResponseStatusException(HttpStatus.UNAUTHORIZED,
      "No authorization could be established"
    );
  }

  public Map<String, Object> loginUser(String name, String password) {
    Preconditions.checkNotNull(name);
    Preconditions.checkNotNull(password);
    return repository.findByName(name)
      .map(user -> userFoundResponse(user, password))
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Wrong user name or password"
      ));
  }

  private Map<String, Object> userFoundResponse(User user, String password) {
    if (!user.password().equals(password)) {
      return null;
    }
    var apiKey = generateApiKey(user.name());
    var response = Maps.<String, Object>newHashMap();
    response.put("apiKey", apiKey);
    return response;
  }

  private static final int JWT_EXPIRATION_MILLIS = 1000 * 60 * 60;

  private String generateApiKey(String userName) {
    var expiration = new Date(System.currentTimeMillis() + JWT_EXPIRATION_MILLIS);
    return Jwts.builder()
      .setExpiration(expiration)
      .claim("userName", userName)
      .signWith(secretKey)
      .compact();
  }

  public UserRepository userRepository() {
    return repository;
  }

  @PostConstruct
  public void init() {
    createSecretKeyAlgorithm();
    Runtime.getRuntime().addShutdownHook(new Thread(this::saveRepository));
  }

  private void createSecretKeyAlgorithm() {
    secretKey = Keys.hmacShaKeyFor(configurationRepository.jwtSecretBytes());
  }

  private void saveRepository() {
    try {
      repository.save();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}

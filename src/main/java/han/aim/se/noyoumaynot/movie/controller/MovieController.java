package han.aim.se.noyoumaynot.movie.controller;

import han.aim.se.noyoumaynot.movie.domain.Movie;
import han.aim.se.noyoumaynot.movie.repository.UserToken;
import han.aim.se.noyoumaynot.movie.service.AuthenticationService;
import han.aim.se.noyoumaynot.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.ArrayList;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final AuthenticationService authenticationService;
    private UserToken testToken;

    @Autowired
    public MovieController(MovieService movieService, AuthenticationService authenticationService) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
        String username = "testUser";
        String password = "testPassword";
        testToken = new UserToken(username);
    }

    @GetMapping
    public ArrayList<Movie> getAllMovies(String token) throws AuthenticationException {
        try{
            authenticate(token);
            return movieService.getMovieList();
        } catch (Exception e) {
            throw new AuthenticationException("niet geautoriseerd");
        }
    }

    @GetMapping("/show")
    public Movie getMovieById(@RequestParam("id") String id, String token) throws AuthenticationException {
        try {
            authenticate(token);
            Movie movie = movieService.getMovieById(id);
            return movie;
        } catch (Exception e) {
            throw new AuthenticationException("niet geautoriseerd");
        }
    }

    @GetMapping("/login")
    public  UserToken login(){

    }

    @PostMapping("/add")
    public Movie addMovie(@RequestBody Movie movie, String token) throws AuthenticationException {
        try {
            authenticate(token);
            movieService.insertMovie(movie);
            return movie;
        } catch (Exception e) {
            throw new AuthenticationException("niet geautoriseerd");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") String id, String token) throws AuthenticationException {
        try {
            authenticate(token);
            movieService.deleteMovie(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new AuthenticationException("niet geautoriseerd");
        }
    }

    private String authenticate(String token) throws Exception {
        if (authenticationService.isValidToken(token)) {
            return authenticationService.getUsername(token);
        } else {
            throw new AuthenticationException("Invalid token");
        }
    }


}

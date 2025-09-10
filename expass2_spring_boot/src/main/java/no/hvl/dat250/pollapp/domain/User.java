package no.hvl.dat250.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    private Long id;
    private String username;
    private String email;
    private final List<Poll> polls = new ArrayList<>();
    private final List<Vote> votes = new ArrayList<>();

    // getters/setters...
}

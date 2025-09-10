package no.hvl.dat250.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.Instant;
import lombok.Data;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vote {
    private Long id;
    private Instant publishedAt;
    private User voter;
    private VoteOption option;

    // getters/setters...
}

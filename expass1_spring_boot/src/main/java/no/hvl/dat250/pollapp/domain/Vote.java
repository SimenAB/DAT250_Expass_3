package no.hvl.dat250.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.Instant;
import lombok.Data;
import jakarta.persistence.*;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Vote {
    private Long id;
    private Instant publishedAt;
    private User voter;
    private VoteOption option;

    public void setVotesOn(VoteOption option) {
    }

    // getters/setters...
}

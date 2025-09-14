package no.hvl.dat250.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Poll {
    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private User createdBy;
    private final List<VoteOption> options = new ArrayList<>();
}

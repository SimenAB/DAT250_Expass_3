package no.hvl.dat250.pollapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;


@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class VoteOption {
    private Long id;
    private String caption;
    private int presentationOrder;
    private Poll poll;
    private final List<VoteOption> options = new ArrayList<>();
    private final List<Vote> votes = new ArrayList<>();

    // getters/setters...
}

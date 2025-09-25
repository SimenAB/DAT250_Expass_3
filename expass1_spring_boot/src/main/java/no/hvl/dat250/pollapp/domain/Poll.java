package no.hvl.dat250.pollapp.domain;


import lombok.Data;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Poll {

    @id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;
    private Instant publishedAt;
    private Instant validUntil;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteOption> options = new ArrayList<>();

    public Poll() {}

    public VoteOption addVoteOption(String caption) {
        VoteOption voteOpt = new VoteOption();
        voteOpt.setPoll(this);
        voteOpt.setCaption(caption);
        voteOpt.setPresentationOrder(options.size());
        options.add(voteOpt);
        return voteOpt;
    }


}

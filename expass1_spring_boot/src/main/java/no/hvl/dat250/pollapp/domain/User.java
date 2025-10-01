package no.hvl.dat250.pollapp.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Poll> polls = new ArrayList<>();

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();

    // noarg
    public User() { }

    // new user object
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // createpoll

    public Poll createPoll(String question) {
        Poll poll = new Poll();
        poll.setQuestion(question);
        poll.setPublishedAt(Instant.now());
        poll.setCreatedBy(this);
        this.polls.add(poll);
        return poll;
    }


    public Vote voteFor(VoteOption option) {
        Vote v = new Vote();
        v.setPublishedAt(Instant.now());
        v.setVoter(this);
        v.setVotesOn(option);
        this.votes.add(v);
        option.getVotes().add(v);
        return v;
    }
}

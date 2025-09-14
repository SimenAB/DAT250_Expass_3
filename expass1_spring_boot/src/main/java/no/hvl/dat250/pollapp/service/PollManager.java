package no.hvl.dat250.pollapp.service;

import no.hvl.dat250.pollapp.domain.Poll;
import no.hvl.dat250.pollapp.domain.User;
import no.hvl.dat250.pollapp.domain.Vote;
import no.hvl.dat250.pollapp.domain.VoteOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class PollManager {

    private Map<Long, User> users = new HashMap<>();
    private Map<Long, Poll> polls = new HashMap<>();
    private Map<Long, VoteOption> options = new HashMap<>();
    private Map<Long, Vote> votes = new HashMap<>();

    // ids
    private long userSeq = 1;
    private long pollSeq = 1;
    private long optionSeq = 1;
    private long voteSeq = 1;

    // users

    public List<User> listUsers() {
        return new ArrayList<>(users.values());
    }

    public User createUser(User u) {
        u.setId(userSeq++);
        users.put(u.getId(), u);
        return u;
    }

    public User getUser(Long id) {
        return users.get(id);
    }

    public User updateUser(Long id, User patch) {
        User u = users.get(id);
        if (u == null) return null;
        if (patch.getUsername() != null) u.setUsername(patch.getUsername());
        if (patch.getEmail() != null) u.setEmail(patch.getEmail());
        return u;
    }

    public boolean deleteUser(Long id) {
        User u = users.remove(id);
        if (u == null) return false;

        return true;
    }

    // polls -  did not have time to test for this

    public List<Poll> listPolls() {
        return new ArrayList<>(polls.values());
    }

    public Poll getPoll(Long id) {
        return polls.get(id);
    }

    public Poll createPoll(Long userId, Poll p) {
        User creator = users.get(userId);
        if (creator == null) return null;

        p.setId(pollSeq++);
        p.setCreatedBy(creator);
        if (p.getPublishedAt() == null) p.setPublishedAt(Instant.now());
        polls.put(p.getId(), p);
        creator.getPolls().add(p);
        return p;
    }

    public Poll updatePoll(Long id, Poll patch) {
        Poll p = polls.get(id);
        if (p == null) return null;
        if (patch.getQuestion() != null) p.setQuestion(patch.getQuestion());
        if (patch.getPublishedAt() != null) p.setPublishedAt(patch.getPublishedAt());
        if (patch.getValidUntil() != null) p.setValidUntil(patch.getValidUntil());
        return p;
    }
    // Options - create, list and delete

    public List<VoteOption> listVoteOptions(Long pollId) {
        Poll p = polls.get(pollId);
        return (p == null) ? Collections.emptyList() : p.getOptions()
    }

    public VoteOption addOption (Long pollId, VoteOption voteOption) {
        Poll p = polls.get(pollId);
        if (p == null) return null;

        voteOption.setId(optionSeq++);
        voteOption.setPoll(p);
        options.put(voteOption.getId(), voteOption);
        p.getOptions().add(voteOption);
        return voteOption;
    }

    // votes

    public List<Vote> listVotes() {
        return new ArrayList<>(votes.values());
    }

}

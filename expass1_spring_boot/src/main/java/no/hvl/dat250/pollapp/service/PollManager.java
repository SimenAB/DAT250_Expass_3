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

    public boolean deletePoll(Long id) {
        Poll p = polls.remove(id);
        if (p == null) return false;

        if (p.getCreatedBy() != null) {
            p.getCreatedBy().getPolls().remove(p);
        }

        List<Long> optIds = new ArrayList<>();
        for (VoteOption voteOption : p.getOptions()) optIds.add(voteOption.getId());
        for (Long oid : optIds) deleteOption(oid);

        return true;
    }
    // Options - create, list and delete

    public List<VoteOption> listVoteOptions(Long pollId) {
        Poll p = polls.get(pollId);
        return (p == null) ? Collections.emptyList() : p.getOptions();
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

    public boolean deleteOption(Long optionId) {
        VoteOption voteOption = options.remove(optionId);
        if (voteOption == null) return false;

        //remove link
        if (voteOption.getPoll() != null) {
            voteOption.getPoll().getOptions().remove(voteOption);
        }

        //delete connected votes
        List<Long> toRemove = new ArrayList<>();
        for (Vote vote : votes.values()) {
            if (vote.getOption() != null && vote.getOption().getId().equals(voteOption.getId())) {
                toRemove.add(vote.getId());
            }
        }
        for (Long voteId : toRemove) votes.remove(voteId);
        return true;
    }
    // votes
    public Vote createOrUpdateVote(Long pollId, Long userId, Long optionId) {
        Poll p = polls.get(pollId);
        User u = users.get(userId);
        VoteOption voteOption = options.get(optionId);

        if (p == null || u == null || voteOption == null) return null;
        if (voteOption.getPoll() == null || !voteOption.getPoll().getId().equals(pollId)) return null;

        //find votews
        Vote existingVote = null;
        for (Vote vote : votes.values()) {
            if (vote.getVoter() != null && vote.getVoter().getId().equals(userId)) {
                VoteOption voteOption1 = vote.getOption();
                if (voteOption1 != null && voteOption1.getPoll() != null && voteOption1.getPoll().getId().equals(p.getId())) {
                    existingVote = vote;
                    break;
                }
            }

        }

        if (existingVote != null) {
            // move to new option
            existingVote.getOption().getVotes().remove(existingVote);
            existingVote.setOption(voteOption);
            voteOption.getVotes().add(existingVote);
            existingVote.setPublishedAt(Instant.now());
            return existingVote;

        }

        //create new vote
        Vote vote = new Vote();
        vote.setId(voteSeq++);
        vote.setPublishedAt(Instant.now());
        vote.setVoter(u);
        vote.setOption(voteOption);
        votes.put(vote.getId(), vote);
        u.getVotes().add(vote);
        voteOption.getVotes().add(vote);
        return vote;

    }

    public List<Vote> listVotes(Long pollId) {
        List<Vote> result = new ArrayList<>();
        for (Vote vote : votes.values()) {
            VoteOption voteOption = vote.getOption();
            if (voteOption != null && voteOption.getPoll() != null && voteOption.getPoll().getId().equals(pollId)) {
                result.add(vote);
            }
        }
        return result;
    }


}

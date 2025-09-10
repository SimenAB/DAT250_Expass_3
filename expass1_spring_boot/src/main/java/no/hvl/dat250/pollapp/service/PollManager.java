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

        // remove polls by this user
        List<Long> delPolls = new ArrayList<>();
        for (Poll p : polls.values()) {
            if (p.getCreatedBy() != null && p.getCreatedBy().getId().equals(id)) {
                delPolls.add(p.getId());
            }
        }
        for (Long pid : delPolls) deletePoll(pid);

        // remove votes by this user
        List<Long> delVotes = new ArrayList<>();
        for (Vote v : votes.values()) {
            if (v.getVoter() != null && v.getVoter().getId().equals(id)) {
                delVotes.add(v.getId());
            }
        }
        for (Long vid : delVotes) votes.remove(vid);

        return true;
    }

    // polls

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

        if (p.getCreatedBy() != null) p.getCreatedBy().getPolls().remove(p);

        // delete options + their votes
        List<Long> optIds = new ArrayList<>();
        for (VoteOption o : p.getOptions()) optIds.add(o.getId());
        for (Long oid : optIds) deleteOption(oid);

        return true;
    }

    // ===== OPTIONS =====

    public List<VoteOption> listOptions(Long pollId) {
        Poll p = polls.get(pollId);
        if (p == null) return Collections.emptyList();
        return p.getOptions();
    }

    public VoteOption addOption(Long pollId, VoteOption o) {
        Poll p = polls.get(pollId);
        if (p == null) return null;

        o.setId(optionSeq++);
        o.setPoll(p);
        options.put(o.getId(), o);
        p.getOptions().add(o);
        return o;
    }

    public boolean deleteOption(Long optionId) {
        VoteOption o = options.remove(optionId);
        if (o == null) return false;

        if (o.getPoll() != null) o.getPoll().getOptions().remove(o);

        // delete votes pointing to this option
        List<Long> del = new ArrayList<>();
        for (Vote v : votes.values()) {
            if (v.getOption() != null && v.getOption().getId().equals(optionId)) {
                del.add(v.getId());
            }
        }
        for (Long id : del) votes.remove(id);

        return true;
    }

    // votes

    public List<Vote> listVotes() {
        return new ArrayList<>(votes.values());
    }

    // one vote per
    public Vote castOrChangeVote(Long pollId, Long userId, Long optionId) {
        Poll p = polls.get(pollId);
        User u = users.get(userId);
        VoteOption o = options.get(optionId);
        if (p == null || u == null || o == null) return null;
        if (o.getPoll() == null || !o.getPoll().getId().equals(pollId)) return null;

        Vote existing = null;
        for (Vote v : votes.values()) {
            if (v.getVoter() != null && v.getVoter().getId().equals(userId)) {
                VoteOption vo = v.getOption();
                if (vo != null && vo.getPoll() != null && vo.getPoll().getId().equals(pollId)) {
                    existing = v;
                    break;
                }
            }
        }

        if (existing != null) {
            if (existing.getOption() != null) {
                existing.getOption().getVotes().remove(existing);
            }
            existing.setOption(o);
            o.getVotes().add(existing);
            existing.setPublishedAt(Instant.now());
            return existing;
        }

        Vote v = new Vote();
        v.setId(voteSeq++);
        v.setPublishedAt(Instant.now());
        v.setVoter(u);
        v.setOption(o);
        votes.put(v.getId(), v);
        u.getVotes().add(v);
        o.getVotes().add(v);
        return v;
    }

    public List<Vote> listVotesForPoll(Long pollId) {
        List<Vote> out = new ArrayList<>();
        for (Vote v : votes.values()) {
            VoteOption o = v.getOption();
            if (o != null && o.getPoll() != null && o.getPoll().getId().equals(pollId)) {
                out.add(v);
            }
        }
        return out;
    }
}

    package no.hvl.dat250.pollapp.web;

    import no.hvl.dat250.pollapp.domain.Poll;
    import no.hvl.dat250.pollapp.domain.User;
    import no.hvl.dat250.pollapp.domain.Vote;
    import no.hvl.dat250.pollapp.domain.VoteOption;
    import no.hvl.dat250.pollapp.service.PollManager;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


    @RestController
    @RequestMapping("/polls")
    @CrossOrigin

    public class PollController {
        private final PollManager pollManager;

        public PollController(PollManager pollManager) {
            this.pollManager = pollManager;
        }

        // Polls
        @GetMapping
        public List<Poll> listPolls() {
            return pollManager.listPolls();
        }

        @PostMapping
        public ResponseEntity<Poll> createPoll(@RequestParam Long userId, @RequestBody Poll p) {
            if (p == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Poll created = pollManager.createPoll(userId, p);
            if (created == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(created);
        }

        @GetMapping("/{pollId}")
        public ResponseEntity<Poll> getPoll(@PathVariable Long pollId) {
            Poll p = pollManager.getPoll(pollId);
            return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
        }

        @DeleteMapping("/{pollId}")
        public ResponseEntity<Void> deletePoll(@PathVariable Long pollId) {
            boolean ok = pollManager.deletePoll(pollId);
            return ResponseEntity.notFound().build();

        }

        //Options

        @GetMapping("/{pollId}/options")
        public List<VoteOption> listOptions(@PathVariable Long pollId) {
            return pollManager.listVoteOptions(pollId);
        }

        @PostMapping("/pollId/options")
        public ResponseEntity<VoteOption> addOption(@PathVariable Long pollId, @RequestBody VoteOption body) {
            VoteOption created = pollManager.addOption(pollId, body);
            return created == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(created);

        }

        @DeleteMapping("/{pollId}/options/{optionId}")
        public ResponseEntity<Void> deleteOption(@PathVariable Long pollId, @PathVariable Long optionId) {
            boolean ok = pollManager.deleteOption(optionId);
            return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }

        // votes

        @GetMapping("/{pollId}/votes")
        public List<Vote> listVoteForPoll(@PathVariable Long pollId) {
            return pollManager.listVotes(pollId);

        }

        @PostMapping("/{pollId}/votes")
        public ResponseEntity<Vote> vote(@PathVariable Long pollId, @RequestParam Long userId, @RequestParam Long optionId) {
            Vote vote = pollManager.createOrUpdateVote(pollId, userId, optionId);
            return vote == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(vote);
        }


    }


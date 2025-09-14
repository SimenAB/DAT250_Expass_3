    package no.hvl.dat250.pollapp.web;

    import no.hvl.dat250.pollapp.domain.Poll;
    import no.hvl.dat250.pollapp.domain.User;
    import no.hvl.dat250.pollapp.service.PollManager;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;


    @RestController
    @RequestMapping("/polls")
    @CrossOrigin

    public class PollController {
        private final  PollManager pollManager;
        public PollController(PollManager pollManager) {
            this.pollManager = pollManager;
        }

        @GetMapping
        public List<Poll> listPolls() {
            return pollManager.listPolls();
        }
        @PostMapping
        public ResponseEntity<Poll> createPoll(@RequestParam Long userId, @RequestBody Poll poll) {
            if (poll == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Poll created = pollManager.createPoll(userId, poll);
            if (created == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(created);
        }
    }





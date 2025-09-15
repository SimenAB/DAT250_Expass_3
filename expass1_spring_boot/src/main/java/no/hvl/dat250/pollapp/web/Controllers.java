package no.hvl.dat250.pollapp.web;

import no.hvl.dat250.pollapp.domain.User;
import no.hvl.dat250.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class Controllers {

    private final PollManager mgr;

    public Controllers(PollManager mgr) {
        this.mgr = mgr;
    }

    @GetMapping
    public List<User> list() {
        return mgr.listUsers();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        if (u == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mgr.createUser(u));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User u = mgr.getUser(id);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User patch) {
        User u = mgr.updateUser(id, patch);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean ok = mgr.deleteUser(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

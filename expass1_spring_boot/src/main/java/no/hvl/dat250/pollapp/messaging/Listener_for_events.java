package no.hvl.dat250.pollapp.messaging;


import no.hvl.dat250.pollapp.service.PollManager;
import no.hvl.dat250.pollapp.web.PollController;
import no.hvl.dat250.pollapp.config.PollRabbit_Config;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;


//@Component
//public class Listener_for_events {
//    private final PollManager pollManager;
//    public Listener_for_events(PollManager pollManager) {
//        this.pollManager = pollManager;
//    }
//    //listens to all events with poll.-id-
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "eventQueue", durable = "true"),
//            exchange = @Exchange(value = "pollExchange", type = ExchangeTypes.TOPIC),
//            key = "poll.*"
//    ))
//
//    public void handleVoteEvent(VoteEvent event) {
//        pollManager.createOrUpdateVote(event.getPollId(), event.getUserId(), event.getOptionId());
//
//    } //her må du gå igjennom domain og for å lagre voteEvents

}

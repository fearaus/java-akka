package chapter2.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import lombok.Data;

import java.util.Random;

public class ChildActor extends AbstractLoggingActor {

    @Data public static class Move {}
    @Data public static class Stop {}

    public static Props props() {
        return Props.create(ChildActor.class, ChildActor::new);
    }

    private ChildActor() {}

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Move.class, this::onMove)
                .match(Stop.class, this::onStop)
                .build();
    }

    private void onMove(Move message) {
        this.log().info(">> {}", message);

        final Random random = new Random();
        final int nextInt = random.nextInt(10);
        if ((nextInt % 2) == 0) {
            this.getContext().stop(this.getSelf());
        }
    }

    private void onStop(Stop message) {
        this.log().info(">> {}", message);
    }
}

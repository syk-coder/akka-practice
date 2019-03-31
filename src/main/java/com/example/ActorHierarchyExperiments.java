package com.example;

import akka.actor.AbstractActor;
import akka.actor.AbstractActor.Receive;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

class PrintMyActorRefActor extends AbstractActor {
    static Props props() {
        return Props.create(PrintMyActorRefActor.class, PrintMyActorRefActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "printit",
                        p -> {
                            ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
                            System.out.println("Second: " + secondRef);
                        })
                .build();
    }
}
public class ActorHierarchyExperiments {
    public static void main(String[] args) throws java.io.IOException {
        ActorSystem system = ActorSystem.create("testSystem");

        System.out.println("System: "+system);
        ActorRef firstRef = system.actorOf(PrintMyActorRefActor.props(), "first-actor");
        System.out.println("First: " + firstRef);
        firstRef.tell("printit", ActorRef.noSender());

        System.out.println(">>> Press ENTER to exit <<<");


        ActorRef first = system.actorOf(StartStopActor1.props(), "first");
        first.tell("stop", ActorRef.noSender());

        try {
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}

class StartStopActor1 extends AbstractActor {
    static Props props() {
        return Props.create(StartStopActor1.class, StartStopActor1::new);
    }

    @Override
    public void preStart() {
        System.out.println("first started");
        getContext().actorOf(StartStopActor2.props(), "second");
    }

    @Override
    public void postStop() {
        System.out.println("first stopped");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "stop",
                        s -> {
                            getContext().stop(getSelf());
                        })
                .build();
    }
}

class StartStopActor2 extends AbstractActor {

    static Props props() {
        return Props.create(StartStopActor2.class, StartStopActor2::new);
    }

    @Override
    public void preStart() {
        System.out.println("second started");
    }

    @Override
    public void postStop() {
        System.out.println("second stopped");
    }

    // Actor.emptyBehavior is a useful placeholder when we don't
    // want to handle any messages in the actor.
    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
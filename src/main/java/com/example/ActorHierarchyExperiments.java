package com.example;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorHierarchyExperiments {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("testActor");


    }



    class PrintMyActorRefActor extends AbstractActor {

        static Props props(){
            return Props.create(PrintMyActorRefActor.class, PrintMyActorRefActor::new);

        }

        @Override
        public Receive createReceive() {
            return null;
        }
    }
}

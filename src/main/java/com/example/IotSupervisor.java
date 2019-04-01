package com.example;

import akka.actor.AbstractActor;
import akka.actor.Props;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IotSupervisor extends AbstractActor{

    static Props props(){
        return Props.create(IotSupervisor.class,IotSupervisor::new);
    }

    @Override
    public void preStart() {
        log.info("IoT Application started");
    }

    @Override
    public void postStop() {
        log.info("IoT Application stopped");
    }

    // No need to handle any messages
    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }

}

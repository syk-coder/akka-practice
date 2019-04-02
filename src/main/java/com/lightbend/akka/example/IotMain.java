package com.lightbend.akka.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class IotMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("iot-system");

        ActorRef supervisor = system.actorOf(IotSupervisor.props(),"iot-supervisor");


    }
}

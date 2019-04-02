package com.primeCalculator;

import akka.actor.*;

public class PrimeCalculator {

    public void calculate( long startNumber, long endNumber )
    {
        // Create our ActorSystem, which owns and configures the classes
        ActorSystem actorSystem = ActorSystem.create( "primeCalculator" );

        // Create our listener
        final ActorRef primeListener = actorSystem.actorOf( new Props( PrimeListener.class ), "primeListener" );

        // Create the PrimeMaster: we need to define an UntypedActorFactory so that we can control
        // how PrimeMaster instances are created (pass in the number of workers and listener reference
        ActorRef primeMaster = actorSystem.actorOf( new Props( new UntypedActorFactory() {
            public UntypedActor create() {
                return new PrimeMaster( 10, primeListener );
            }
        }), "primeMaster" );

        // Start the calculation
        primeMaster.tell( new NumberRangeMessage( startNumber, endNumber ) );
    }

    public static void main(String[] args) {

        PrimeCalculator primeCalculator = new PrimeCalculator();
        primeCalculator.calculate(1, 100);

    }
}

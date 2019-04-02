package com.primeCalculator;

import akka.actor.UntypedActor;

public class PrimeListener extends UntypedActor {
    @Override
    public void onReceive(Object message) {
        if (message instanceof Result) {
            Result result = (Result) message;
            System.out.println("Results: ");

            for (Long value : result.getResults()) {
                System.out.print(value + ",");
            }

            getContext().system().shutdown();
        }else {
            unhandled(message);
        }
    }
}

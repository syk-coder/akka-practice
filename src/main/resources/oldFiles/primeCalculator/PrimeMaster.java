package com.primeCalculator;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class PrimeMaster extends UntypedActor {

    private  ActorRef workerRouter;
    private final ActorRef listener;
    private final int numOfWorkers;
    private int numOfResults = 0;
    private Result finalResults = new Result();

    public PrimeMaster(final int numOfWorkers,ActorRef listener) {
        this.listener = listener;
        this.numOfWorkers = numOfWorkers;

        this.workerRouter = this.getContext()
                .actorOf( new Props(PrimeWorker::new)
                        .withRouter( new RoundRobinRouter( numOfWorkers )), "workerRouter" );
    }


    @Override
    public void onReceive(Object message) {

        if (message instanceof NumberRangeMessage){

            NumberRangeMessage numberRangeMessage = (NumberRangeMessage) message;

            long numberOfNumbers = numberRangeMessage.getStartNumber() - numberRangeMessage.getEndNumber();
            long segmentLength = numberOfNumbers/10;

            for (int i = 0 ; i < numOfWorkers; i++){
                long startNumber = numberRangeMessage.getStartNumber() + (i * segmentLength);
                long endNumber = startNumber + segmentLength - 1;

                // Handle any remainder
                if( i == numOfWorkers - 1 )
                {
                    // Make sure we get the rest of the list
                    endNumber = numberRangeMessage.getEndNumber();
                }

                workerRouter.tell(new NumberRangeMessage(startNumber,endNumber),getSelf());

            }

        }else if( message instanceof Result){
            Result result = (Result) message;
            finalResults.getResults().addAll(result.getResults());

            if (++numOfResults >= 10) {
                listener.tell(finalResults, getSelf());
                getContext().stop(getSelf());
            }
        }else {
            unhandled(message);
        }

    }
}

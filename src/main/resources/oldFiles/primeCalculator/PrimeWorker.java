package com.primeCalculator;

import akka.actor.UntypedActor;

public class PrimeWorker extends UntypedActor{
    @Override
    public void onReceive(Object message) {
        if (message instanceof NumberRangeMessage){

            NumberRangeMessage numberRangeMessage = (NumberRangeMessage) message;
            System.out.println("Recieved the number range from: "+numberRangeMessage.getStartNumber()+" to: "+((NumberRangeMessage) message).getEndNumber());

            Result result = new Result();

            for (long l = numberRangeMessage.getStartNumber(); l < numberRangeMessage.getEndNumber(); l++) {
                if (isPrime(l)){
                    result.getResults().add(l);
                }

                getSender().tell(result,getSelf());
            }
        }else {
            unhandled(message);
        }
    }

    private boolean isPrime(long l) {
        if (l == 1 || l==2 || l==3) return true;
        else if(l %2 == 0){
            return false;
        }

        for (long i = 3 ; i < l ; i++ ){
            if (l %i == 0) return false;
        }
        return true;
    }
}

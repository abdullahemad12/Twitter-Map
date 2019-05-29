package observer;

import java.util.*;

public class Observable {
    List<Observer> observers;

    public Observable(){
        observers = new LinkedList<>();
    }

    public void subscribe(Observer o){
        observers.add(o);
    }
    public void unsubscribe(Observer o){
        observers.remove(o);
    }
    public void notifyObservers(Object arg){
        for(Observer o : observers){
            o.update(this, arg);
        }
    }
}

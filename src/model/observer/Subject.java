package model.observer;

import java.util.ArrayList;

public interface Subject {		  
	ArrayList<Observer> observers = new ArrayList<Observer>();

    public abstract void attach(Observer observer);  
    public abstract void detach(Observer observer);  
    abstract void notifyObservers();  

}

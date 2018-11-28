package classes;

import java.io.Serializable;

public class State<T> implements Comparable<State<T>>,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T state;
	private double cost;
	private State<T> cameFrom ; 

	public State() {
	}
	public State(T State) {
		this.state= State;
		this.cost= 0;
		this.cameFrom=null;
	}
	public State(State<T> state) {
		this.state= state.state;
		this.cost= state.cost;
		this.cameFrom=state.cameFrom;
	}
	
	
	public State(T state, double cost, State<T> cameFrom) {
		super();
		this.state = state;
		this.cost = cost;
		this.cameFrom = cameFrom;
	}
	@Override
	public int hashCode() {
		return this.state.hashCode();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;	
		else if (obj == this)
			return true;
		else
			return this.equals(((State<T>) obj));
	}
	public boolean equals(State<T> s) {
		return state.equals(s.state);
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int compareTo(State<T> o) {
		return Double.compare(this.cost, o.cost);
	}


}
package classes;

import java.io.*;
import java.util.*;

public class Solution<T> implements Serializable{
   private static final long serialVersionUID = 1L;
   ///Collection order by start to goal
   Collection<State<T>> states;
   
   public Solution() {
      this.states=new ArrayList<State<T>>();
   }

   public Solution(Collection<State<T>> states) {
      super();
      this.states = states;
   }

   public Collection<State<T>> getStates() {
      return states;
   }
   public ArrayList<State<T>> getArrayListStates() {
      ArrayList<State<T>> arrayList=new ArrayList<>(states);
      return arrayList;
   }

   public void setStates(Collection<State<T>> states) {
      this.states = states;
   }
   
   public boolean add(State<T> state)
   {
      return this.states.add(state);
   }
   
   
   

}

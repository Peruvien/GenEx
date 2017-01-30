/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

/**
 *
 * @author robin
 * @author Vincent
 */
public class Pair implements Comparable <Pair> {
    
    private Boolean first;
    private Integer second;
    
    public Pair(Boolean first, Integer second) {
        super();
        this.first = first;
        this.second = second;
    }
    
    @Override
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;
        
        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return 
            ((  this.first == otherPair.first ||
                ( this.first != null && otherPair.first != null &&
                  this.first.equals(otherPair.first))) &&
             (  this.second == otherPair.second ||
                ( this.second != null && otherPair.second != null &&
                  this.second.equals(otherPair.second))) );
        }
        
        return false;
    }
    
    @Override
    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }
    
    public Boolean getFirst() {
        return first;
    }
    
    public void setFirst(Boolean first) {
        this.first = first;
    }
    
    public Integer getSecond() {
        return second;
    }
    
    public void setSecond(Integer second) {
        this.second = second;
    }
    
    @Override
    public int compareTo(Pair o) {
        int res = first.compareTo(o.first);
        if (res == 0) {
            res = second.compareTo(o.second);
        }
        return res;
    }
    
}
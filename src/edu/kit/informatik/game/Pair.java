package edu.kit.informatik.game;

/**
 * This class represents a pair of values. The values ​​stored in an object of
 * this class should be in a key value relation.
 * 
 * @author ulqch
 * @version 6.3
 * @param <K> Type of the key
 * @param <V> Type of the value
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    /**
     * This constructor creates a new Pair of values.
     * 
     * @param key   Key
     * @param value Value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * This method returns the key of this pair.
     * 
     * @return Key of this pair
     */
    public K getKey() {
        return this.key;
    }

    /**
     * This method returns the value of this pair.
     * 
     * @return Value of this pair
     */
    public V getValue() {
        return this.value;
    }

}

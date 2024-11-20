package edu.ktu.ds.lab3.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * The key-meaning of pairs ("mappings") is a collection of objects - the realization of a view in a hash table,
 * solving collisions by the method of separate chains.
 * Remember, if the pair key is an object of its own class, e.g. class Car object,
 * the class must overlap the methods equals (Object o) and hashCode ().
 * @param <K> view key
 * @param <V> view value
 * @author darius.matulis@ktu.lt
 * @Užduotis Review and understand the methods provided.
 */
public class HashMap<K, V> implements EvaluableMap<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 8;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashManager.HashType DEFAULT_HASH_TYPE = HashManager.HashType.DIVISION;

    // Hash table
    protected Node<K, V>[] table;
    // The number of key-value pairs in the table
    public int size = 0;
    // Load factor
    protected float loadFactor;
    // Hash method
    protected HashManager.HashType ht;
    //--------------------------------------------------------------------------
    //  Hash table evaluation parameters
    //--------------------------------------------------------------------------
    // Maximum chain length of the formed hash table
    protected int maxChainSize = 0;
    // Amount of mixing
    protected int rehashesCounter = 0;
    // The index of the last placed chain in the hash table
    protected int lastUpdatedChain = 0;
    // Number of chains in the table
    protected int chainsCounter = 0;

    /* There are 4 overlapping constructors in the class that define the individual parameters of the hash table.
     * If any parameter is not set, the default value is assigned.
     */
    public HashMap()
    {
        this(DEFAULT_HASH_TYPE);
    }

    public HashMap(HashManager.HashType ht)
    {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public HashMap(int initialCapacity, HashManager.HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public HashMap(float loadFactor, HashManager.HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public HashMap(int initialCapacity, float loadFactor, HashManager.HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }

    /**
     * Checking if the view is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of pairs in the view.
     *
     * @return Returns the number of pairs in the view.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * The view is being cleared.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }

    /**
     * Checking if a pair exists in the view.
     *
     * @param key key.
     * @return Checking if a pair exists in the view.
     */
    @Override
    public boolean contains(K key)
    {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in contains(K key)");
        }

        return get(key) != null;
    }

    /**
     * A new pair is added to the view.
     *
     * @param key   key,
     * @param value value.
     * @return A new pair is added to the view.
     */
    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(K key, V value)");
        }
        int index = HashManager.hash(key.hashCode(), table.length, ht);
        if (table[index] == null)
        {
            chainsCounter++;
        }

        Node<K, V> node = getInChain(key, table[index]);
        if (node == null) {
            table[index] = new Node<>(key, value, table[index]);
            size++;

            if (size > table.length * loadFactor) {
                rehash();
            } else {
                lastUpdatedChain = index;
            }
        } else {
            node.value = value;
            lastUpdatedChain = index;
        }

        return value;
    }

    /**
     * Returns the value of the view pair.
     *
     * @param key key.
     * @return Returns the value of the view pair.
     */
    @Override
    public V get(K key) {
        if (key == null)
        {
            throw new IllegalArgumentException("Key is null in get(K key)");
        }

        int index = HashManager.hash(key.hashCode(), table.length, ht);
        Node<K, V> node = getInChain(key, table[index]);
        return node == null ? null : node.value;
    }

    /**
     * The pair is removed from the view.
     *
     * @param key The pair is removed from the view.
     * @return key.
     */
    @Override
    public V remove(K key)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key is null in remove(K key)");
        }
        int index = HashManager.hash(key.hashCode(), table.length, ht);
        Node<K,V> findRemove = table[index];

        if(findRemove.key.equals(key))
        {
            Node<K,V> p = table[index];
            table[index]= table[index].next;
            size--;
            return p.value;

        }
       else
        {
            for (Node<K,V> mm = table[index]; mm != null; mm = mm.next)
            {
                if (mm.next.key.equals(key))
                {
                    Node<K,V>  p = mm.next;
                    mm.next =mm.next.next;
                    size--;
                    return p.value;
                }
            }
        }
       return null;
    }


/*

if (key == null)
        {
            throw new IllegalArgumentException("Key is null in remove(K key)");
        }
        int index = HashManager.hash(key.hashCode(), table.length, ht);
        Node<K,V> findRemove = table[index];
        if( findRemove.key.equals(key))
        {
            Node<K,V> temp =findRemove;
           table[index]=findRemove.next;
            return temp.value;
        }
       else {
            Node<K, V> node = getInChain(key, table[index]);
            Node<K,V> temp =node;
            node=node.next;
            return  temp.value;
        }
       // return null;
 */
    /**
     * Mixing
     */
    private void rehash() {
        HashMap<K, V> newMap = new HashMap<>(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++)
        {
            while (table[i] != null) {
                newMap.put(table[i].key, table[i].value);
                table[i] = table[i].next;
            }
        }
        table = newMap.table;
        maxChainSize = newMap.maxChainSize;
        chainsCounter = newMap.chainsCounter;
        lastUpdatedChain = newMap.lastUpdatedChain;
        rehashesCounter++;
    }

    /**
     * Search in a single chain
     *
     * @param key
     * @param node
     * @return
     */
    private Node<K, V> getInChain(K key, Node<K, V> node)
    {
        if (key == null)
        {
            throw new IllegalArgumentException("Key is null in getInChain(K key, Node node)");
        }
        int chainSize = 0;
        for (Node<K, V> n = node; n != null; n = n.next)
        {
            chainSize++;
            if ((n.key).equals(key))
            {
                return n;
            }
        }
        maxChainSize = Math.max(maxChainSize, chainSize + 1);
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Node<K, V> node : table) {
            if (node != null) {
                for (Node<K, V> n = node; n != null; n = n.next) {
                    result.append(n).append(System.lineSeparator());
                }
            }
        }
        return result.toString();
    }

    public boolean replace(K key, V oldValue, V newValue)
    {
        boolean found =false;
        for (Node<K, V> n : table)
        {
            while(n != null)
            {
                if(n.key==key &&  n.value.equals(oldValue))
                {
                        n.value=newValue;
                        found =true;
                }
                n=n.next;
            }
        }
         return found;
    }

    public boolean containsValue(Object value)
    {
        boolean found =false;
        for (Node<K, V> n : table)
        {
            while(n != null)
            {
                if(n.value== value)
                {
                    found =true;
                }
                n=n.next;
            }
        }
        return found;
    }

    /**
     * Returns the maximum chain length.
     *
     * @return Maximum chain length.
     */
    @Override
    public int getMaxChainSize() {
        return maxChainSize;
    }

    /**
     * Returns the amount of remixes that occurred during the formation of the hash table.
     *
     * @return Amount of mixing.
     */
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    /**
     * Returns the capacity of the hash table.
     *
     * @return Hash table capacity.
     */
    @Override
    public int getTableCapacity() {
        return table.length;
    }

    /**
     * Returns the index of the last chain added.
     *
     * @return Index of the last chain added.
     */
    @Override
    public int getLastUpdated() {
        return lastUpdatedChain;
    }

    /**
     * Returns the amount of chains.
     *
     * @return Number of chains.
     */
    @Override
    public int getNumberOfOccupied() {
        return chainsCounter;
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    public void replaceAll(V oldValue, V newValue)
    {
        for (Node<K, V> n : table)
        {
            while(n != null)
            {
                if(n.value.equals(oldValue))
                {
                    n.value=newValue;

                }
                n=n.next;
            }
        }

    }

    @Override
    public List<V> Values() {
        return null;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    protected static class Node<K, V> {

        // Key
        protected K key;
        // Value
        protected V value;
        // An arrow to the next node in the chain
        protected Node<K, V> next;

        protected Node()
        {
        }

        protected Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}

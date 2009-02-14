/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.util;

/**
 * HashMap is an implementation of Map. All optional operations are supported,
 * adding and removing. Keys and values can be any objects.
 */
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>
         {
    private static final long serialVersionUID = 362498820763181265L;

    transient int elementCount;

    transient Entry<K, V>[] elementData;

    final float loadFactor;

    int threshold;

    transient int modCount = 0;

    private static final int DEFAULT_SIZE = 16;

    static class Entry<K, V> extends MapEntry<K, V> {
        final int origKeyHash;

        Entry<K, V> next;

        Entry(K theKey, int hash) {
            super(theKey, null);
            this.origKeyHash = hash;
        }

        Entry(K theKey, V theValue) {
            super(theKey, theValue);
            origKeyHash = (theKey == null ? 0 : theKey.hashCode());
        }
    }

    static class HashMapIterator<E, KT, VT> implements Iterator<E> {
        private int position = 0;

        int expectedModCount;

        final MapEntry.Type<E, KT, VT> type;

        boolean canRemove = false;

        Entry<KT, VT> entry;

        Entry<KT, VT> lastEntry;

        final HashMap<KT, VT> associatedMap;

        HashMapIterator(MapEntry.Type<E, KT, VT> value, HashMap<KT, VT> hm) {
            associatedMap = hm;
            type = value;
            expectedModCount = hm.modCount;
        }

        public boolean hasNext() {
            if (entry != null) {
                return true;
            }
            while (position < associatedMap.elementData.length) {
                if (associatedMap.elementData[position] == null) {
                    position++;
                } else {
                    return true;
                }
            }
            return false;
        }

        void checkConcurrentMod() throws ConcurrentModificationException {
            if (expectedModCount != associatedMap.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        public E next() {
            checkConcurrentMod();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            MapEntry<KT, VT> result;
            if (entry == null) {
                result = lastEntry = associatedMap.elementData[position++];
                entry = lastEntry.next;
            } else {
                if (lastEntry.next != entry) {
                    lastEntry = lastEntry.next;
                }
                result = entry;
                entry = entry.next;
            }
            canRemove = true;
            return type.get(result);
        }

        public void remove() {
            checkConcurrentMod();
            if (!canRemove) {
                throw new IllegalStateException();
            }

            canRemove = false;
            associatedMap.modCount++;
            if (lastEntry.next == entry) {
                while (associatedMap.elementData[--position] == null) {
                    // Do nothing
                }
                associatedMap.elementData[position] = associatedMap.elementData[position].next;
                entry = null;
            } else {
                lastEntry.next = entry;
            }
            associatedMap.elementCount--;
            expectedModCount++;
        }
    }

    static class HashMapEntrySet<KT, VT> extends AbstractSet<Map.Entry<KT, VT>> {
        private final HashMap<KT, VT> associatedMap;

        public HashMapEntrySet(HashMap<KT, VT> hm) {
            associatedMap = hm;
        }

        HashMap<KT, VT> hashMap() {
            return associatedMap;
        }

        public int size() {
            return associatedMap.elementCount;
        }

        public void clear() {
            associatedMap.clear();
        }

        public boolean remove(Object object) {
            if (contains(object)) {
                associatedMap.remove(((Map.Entry<?, ?>) object).getKey());
                return true;
            }
            return false;
        }

        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Object key = ((Map.Entry<?, ?>) object).getKey();
                Entry<KT, VT> entry;
                if (key == null) {
                    entry = associatedMap.findNullKeyEntry();
                } else {
                    int hash = key.hashCode();
                    int index = hash & (associatedMap.elementData.length - 1);
                    entry = associatedMap.findNonNullKeyEntry(key, index, hash);
                }
                return entry == null ? false : entry.equals(object);
            }
            return false;
        }

        
        public Iterator<Map.Entry<KT, VT>> iterator() {
            return new HashMapIterator<Map.Entry<KT, VT>, KT, VT>(
                    new MapEntry.Type<Map.Entry<KT, VT>, KT, VT>() {
                        public Map.Entry<KT, VT> get(MapEntry<KT, VT> entry) {
                            return entry;
                        }
                    }, associatedMap);
        }
    }

    /**
     * Create a new element array
     * 
     * @param s
     * @return Reference to the element array
     */
    Entry<K, V>[] newElementArray(int s) {
        return new Entry[s];
    }

    /**
     * Constructs a new empty instance of HashMap.
     * 
     */
    public HashMap() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs a new instance of HashMap with the specified capacity.
     * 
     * @param capacity
     *            the initial capacity of this HashMap
     * 
     * @exception IllegalArgumentException
     *                when the capacity is less than zero
     */
    public HashMap(int capacity) {
        if (capacity >= 0) {
            capacity = calculateCapacity(capacity);
            elementCount = 0;
            elementData = newElementArray(capacity);
            loadFactor = 0.75f; // Default load factor of 0.75
            computeMaxSize();
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    private static final int calculateCapacity(int x) {
        if(x >= 1 << 30){
            return 1 << 30;
        }
        if(x == 0){
            return 16;
        }
        x = x -1;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x + 1;
    }

    /**
     * Constructs a new instance of HashMap with the specified capacity and load
     * factor.
     * 
     * 
     * @param capacity
     *            the initial capacity
     * @param loadFactor
     *            the initial load factor
     * 
     * @exception IllegalArgumentException
     *                when the capacity is less than zero or the load factor is
     *                less or equal to zero
     */
    public HashMap(int capacity, float loadFactor) {
        if (capacity >= 0 && loadFactor > 0) {
            capacity = calculateCapacity(capacity);
            elementCount = 0;
            elementData = newElementArray(capacity == 0 ? 1 : capacity);
            this.loadFactor = loadFactor;
            computeMaxSize();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Constructs a new instance of HashMap containing the mappings from the
     * specified Map.
     * 
     * @param map
     *            the mappings to add
     */
    public HashMap(Map<? extends K, ? extends V> map) {
        this(map.size() < 6 ? 11 : map.size() * 2);
        putAllImpl(map);
    }

    /**
     * Removes all mappings from this HashMap, leaving it empty.
     * 
     * @see #isEmpty
     * @see #size
     */
    
    public void clear() {
        if (elementCount > 0) {
            elementCount = 0;
            for (int i = 0; i < elementData.length; i++)
                elementData[i] = null;
//            Arrays.fill(elementData, null);
            modCount++;
        }
    }

    private void computeMaxSize() {
        threshold = (int) (elementData.length * loadFactor);
    }

    /**
     * Searches this HashMap for the specified key.
     * 
     * @param key
     *            the object to search for
     * @return true if <code>key</code> is a key of this HashMap, false
     *         otherwise
     */
    
    public boolean containsKey(Object key) {
        Entry<K, V> m;
        if (key == null) {
            m = findNullKeyEntry();
        } else {
            int hash = key.hashCode();
            int index = hash & (elementData.length - 1);
            m = findNonNullKeyEntry(key, index, hash);
        }
        return m != null;
    }

    /**
     * Searches this HashMap for the specified value.
     * 
     * @param value
     *            the object to search for
     * @return true if <code>value</code> is a value of this HashMap, false
     *         otherwise
     */
    
    public boolean containsValue(Object value) {
        if (value != null) {
            for (int i = elementData.length; --i >= 0;) {
                Entry<K, V> entry = elementData[i];
                while (entry != null) {
                    if (value.equals(entry.value)) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        } else {
            for (int i = elementData.length; --i >= 0;) {
                Entry<K, V> entry = elementData[i];
                while (entry != null) {
                    if (entry.value == null) {
                        return true;
                    }
                    entry = entry.next;
                }
            }
        }
        return false;
    }

    /**
     * Answers a Set of the mappings contained in this HashMap. Each element in
     * the set is a Map.Entry. The set is backed by this HashMap so changes to
     * one are reflected by the other. The set does not support adding.
     * 
     * @return a Set of the mappings
     */
    
    public Set<Map.Entry<K, V>> entrySet() {
        return new HashMapEntrySet<K, V>(this);
    }

    /**
     * Answers the value of the mapping with the specified key.
     * 
     * @param key
     *            the key
     * @return the value of the mapping with the specified key
     */
    
    public V get(Object key) {
        Entry<K, V> m;
        if (key == null) {
            m = findNullKeyEntry();
        } else {
            int hash = key.hashCode();
            int index = hash & (elementData.length - 1);
            m = findNonNullKeyEntry(key, index, hash);
        }
        if (m != null) {
            return m.value;
        }
        return null;
    }

    final Entry<K,V> findNonNullKeyEntry(Object key, int index, int keyHash) {
        Entry<K,V> m = elementData[index];
        while (m != null && (m.origKeyHash != keyHash || !key.equals(m.key))) {
            m = m.next;
        }
        return m;
    }
  
    final Entry<K,V> findNullKeyEntry() {
        Entry<K,V> m = elementData[0];
        while (m != null && m.key != null)
            m = m.next;
        return m;
    }

    /**
     * Answers if this HashMap has no elements, a size of zero.
     * 
     * @return true if this HashMap has no elements, false otherwise
     * 
     * @see #size
     */
    
    public boolean isEmpty() {
        return elementCount == 0;
    }

    /**
     * Answers a Set of the keys contained in this HashMap. The set is backed by
     * this HashMap so changes to one are reflected by the other. The set does
     * not support adding.
     * 
     * @return a Set of the keys
     */
    
    public Set<K> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<K>() {
                
                public boolean contains(Object object) {
                    return containsKey(object);
                }

                
                public int size() {
                    return HashMap.this.size();
                }

                
                public void clear() {
                    HashMap.this.clear();
                }

                
                public boolean remove(Object key) {
                    Entry<K, V> entry = HashMap.this.removeEntry(key);
                    return entry != null;
                }

                
                public Iterator<K> iterator() {
                    return new HashMapIterator<K, K, V>(
                            new MapEntry.Type<K, K, V>() {
                                public K get(MapEntry<K, V> entry) {
                                    return entry.key;
                                }
                            }, HashMap.this);
                }
            };
        }
        return keySet;
    }

    /**
     * Maps the specified key to the specified value.
     * 
     * @param key
     *            the key
     * @param value
     *            the value
     * @return the value of any previous mapping with the specified key or null
     *         if there was no mapping
     */
    
    public V put(K key, V value) {
        return putImpl(key, value);
    }

    V putImpl(K key, V value) {
        Entry<K,V> entry;
        if(key == null) {
            entry = findNullKeyEntry();
            if (entry == null) {
                modCount++;
                if (++elementCount > threshold) {
                    rehash();
                }
                entry = createHashedEntry(null, 0, 0);
            }
        } else {
            int hash = key.hashCode();
            int index = hash & (elementData.length - 1);
            entry = findNonNullKeyEntry(key, index, hash);
            if (entry == null) {
                modCount++;
                if (++elementCount > threshold) {
                    rehash();
                    index = hash & (elementData.length - 1);
                }
                entry = createHashedEntry(key, index, hash);
            }
        }

        V result = entry.value;
        entry.value = value;
        return result;
    }

    Entry<K, V> createEntry(K key, int index, V value) {
        Entry<K, V> entry = new Entry<K, V>(key, value);
        entry.next = elementData[index];
        elementData[index] = entry;
        return entry;
    }

    Entry<K,V> createHashedEntry(K key, int index, int hash) {
        Entry<K,V> entry = new Entry<K,V>(key,hash);
        entry.next = elementData[index];
        elementData[index] = entry;
        return entry;
    }

    /**
     * Copies all the mappings in the given map to this map. These mappings will
     * replace all mappings that this map had for any of the keys currently in
     * the given map.
     * 
     * @param map
     *            the Map to copy mappings from
     * @throws NullPointerException
     *             if the given map is null
     */
    
    public void putAll(Map<? extends K, ? extends V> map) {
        if (!map.isEmpty()) {
            putAllImpl(map);
        }
    }

    private void putAllImpl(Map<? extends K, ? extends V> map) {
        int capacity = elementCount + map.size();
        if (capacity > threshold) {
            rehash(capacity);
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            putImpl(entry.getKey(), entry.getValue());
        }
    }

    void rehash(int capacity) {
        int length = calculateCapacity((capacity == 0 ? 1 : capacity << 1));

        Entry<K, V>[] newData = newElementArray(length);
        for (int i = 0; i < elementData.length; i++) {
            Entry<K, V> entry = elementData[i];
            while (entry != null) {
                int index = entry.origKeyHash & (length - 1);
                Entry<K, V> next = entry.next;
                entry.next = newData[index];
                newData[index] = entry;
                entry = next;
            }
        }
        elementData = newData;
        computeMaxSize();
    }

    void rehash() {
        rehash(elementData.length);
    }

    /**
     * Removes a mapping with the specified key from this HashMap.
     * 
     * @param key
     *            the key of the mapping to remove
     * @return the value of the removed mapping or null if key is not a key in
     *         this HashMap
     */
    
    public V remove(Object key) {
        Entry<K, V> entry = removeEntry(key);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }

    Entry<K, V> removeEntry(Object key) {
        int index = 0;
        Entry<K, V> entry;
        Entry<K, V> last = null;
        if (key != null) {
            int hash = key.hashCode();
            index = hash & (elementData.length - 1);
            entry = elementData[index];
            while (entry != null && !(entry.origKeyHash == hash && key.equals(entry.key))) {
                last = entry;
                entry = entry.next;
            }
        } else {
            entry = elementData[0];
            while (entry != null && entry.key != null) {
                last = entry;
                entry = entry.next;
            }
        }
        if (entry == null) {
            return null;
        }
        if (last == null) {
            elementData[index] = entry.next;
        } else {
            last.next = entry.next;
        }
        modCount++;
        elementCount--;
        return entry;
    }

    /**
     * Answers the number of mappings in this HashMap.
     * 
     * @return the number of mappings in this HashMap
     */
    
    public int size() {
        return elementCount;
    }

    /**
     * Answers a Collection of the values contained in this HashMap. The
     * collection is backed by this HashMap so changes to one are reflected by
     * the other. The collection does not support adding.
     * 
     * @return a Collection of the values
     */
    
    public Collection<V> values() {
        if (valuesCollection == null) {
            valuesCollection = new AbstractCollection<V>() {
                
                public boolean contains(Object object) {
                    return containsValue(object);
                }

                
                public int size() {
                    return HashMap.this.size();
                }

                
                public void clear() {
                    HashMap.this.clear();
                }

                
                public Iterator<V> iterator() {
                    return new HashMapIterator<V, K, V>(
                            new MapEntry.Type<V, K, V>() {
                                public V get(MapEntry<K, V> entry) {
                                    return entry.value;
                                }
                            }, HashMap.this);
                }
            };
        }
        return valuesCollection;
    }
}

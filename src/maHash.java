import java.util.ArrayList;
import java.util.Objects;


public class maHash<K, V> {
    
    
    int includes;
    int capacity;
    maHashLinkedList<K, V>[] bigTable;
    public maHash(int initialCapacity) {
        bigTable = (maHashLinkedList<K, V>[]) new maHashLinkedList[initialCapacity];
        capacity = initialCapacity;
        for(int i = 0; i < initialCapacity; i++){
            bigTable[i] = null;
        }
    }
    public maHash() {

    }
    public int size() {
        return includes;
    }
    private int hash(K key) {
        return Math.abs(Objects.hashCode(key)) % capacity;
    }
    private int getIndex(K key){
        int index = hash(key);
        return index;
    }
    public void put(K key, V data) {
        int headIndex = getIndex(key);
        maHashLinkedList<K, V> head = (maHashLinkedList<K, V>) bigTable[headIndex];
        if(head == null){
            bigTable[headIndex] = new maHashLinkedList<>(key, data, headIndex);
            includes++;
        }
        else{
            maHashLinkedList<K, V> temp = head;
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = new maHashLinkedList<>(key, data, headIndex);
            includes++;
        }

    }
    public V get(K key){
        int index = getIndex(key);
        maHashLinkedList<K, V> head = (maHashLinkedList<K, V>) bigTable[index];
        if(head == null){
            return null;
        }
        else{
            maHashLinkedList<K, V> temp = head;
            while(temp != null){
                if(temp.key.equals(key)){
                    return temp.data;
                }
                temp = temp.next;
            }
            return null;
        }
    }
    public boolean containsKey(K key){
        int index = getIndex(key);
        maHashLinkedList<K, V> head = (maHashLinkedList<K, V>) bigTable[index];
        if(head == null){
            return false;
        }
        else{
            maHashLinkedList<K, V> temp = head;
            while(temp != null){
                if(temp.key.equals(key)){
                    return true;
                }
                temp = temp.next;
            }
            return false;
        }
    }
    public void remove(K key){
        int index = getIndex(key);
        maHashLinkedList<K, V> head = (maHashLinkedList<K, V>) bigTable[index];
        if(head == null){
            return;
        }
        else{
            maHashLinkedList<K, V> temp = head;
            maHashLinkedList<K, V> prev = null;
            while(temp != null){
                if(temp.key.equals(key)){
                    if(prev == null){
                        bigTable[index] = temp.next;
                    }
                    else{
                        prev.next = temp.next;
                    }
                    includes--;
                    return;
                }
                prev = temp;
                temp = temp.next;
            }
        }
    }
    public ArrayList<K> keySet(){
        ArrayList<K> keys = new ArrayList<>();
        for(int i = 0; i < capacity; i++){
            
            maHashLinkedList<K, V> head = (maHashLinkedList<K, V>) bigTable[i];
            if(head != null){
                maHashLinkedList<K, V> temp = head;
                while(temp != null){
                    keys.add(temp.key);
                    temp = temp.next;
                }
            }
        }
        return keys;
    }
    
}

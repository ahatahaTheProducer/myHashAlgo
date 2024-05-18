public class maHashLinkedList<K, T> {
    T data;
    K key;
    maHashLinkedList<K, T> next;
    public maHashLinkedList(K key, T data, int hashResult) {
        this.data = data;
        this.key = key;
        this.next = null;
    }
    
}

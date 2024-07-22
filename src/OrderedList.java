/*This program creates a linked list that is iterable and contains String data sorted in lexicographical order.
 */
import java.util.Iterator;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OrderedList implements Iterable<String> {
    private Node head;

    // This is the requirement of implementing Iterable. Provide
    // an iterator() method that returns an Iterator object.
    public Iterator<String> iterator() {
        return new LLIterator();
    }

    // This is what the iterator() returns to the caller.
    // This is the iterator object that processes the list.
    private class LLIterator implements Iterator<String> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public String next() {
            String item = current.data;
            current = current.next;
            return item;
        }
    }

    private class Node {
        private String data;
        private Node next;

        private Node(String item) {
            data = item;
            next = null;
        }
    }

    public OrderedList() {
        head = null;
    }

    public void insert(String item) {
        Node n = new Node(item);
        Node cur = head;
        Node back = null;
        int compare;
        if (head == null){
            //adds item to ol if ol is currently empty
            head = n;
            return;
        }

        while (cur != null){
            compare = cur.data.compareTo(item);

            if (compare < 0){
                back = cur;
                cur = cur.next;
            }
            else if (compare == 0){
                return;
            }
            else {
                if (back != null){
                    back.next = n;
                }
                else{
                    head = n;
                }
                n.next = cur;
                return;
            }
        }
        //adds item to the end of ol if the loop has run through the entire ordered list
        back.next = n;
    }

    public void delete(String item) {
        Node cur = head, back = null;

        while (cur != null) {
            if (cur.data.equals(item)) {//don't use "cur.data == item" here
                if (back == null)
                    head = cur.next;
                else
                    back.next = cur.next;
                break; //leave the loop
            }
            else {
                back = cur;
                cur = cur.next; // move to the next node
            }
        }
    }

    @Override
    public String toString() {
        String o = "[";
        Iterator iter = iterator();

        // first data value is alone.
        if ( iter.hasNext() )
            o += iter.next();
        // remaining data values have ", value"
        while ( iter.hasNext() )
            o += ", " + iter.next();

        return o + "]";
    }

    public static void main(String[] args) throws FileNotFoundException{
        //creates an ordered list iterable
        OrderedList ol = new OrderedList();
        //creates two input files and an output file.
        Scanner add_file = new Scanner(new FileReader(args[0]));
        Scanner del_file = new Scanner(new FileReader(args[1]));
        PrintWriter outfile = new PrintWriter(args[2]);
        //the contents of the first input file are added to the empty ordered list
        while (add_file.hasNext()){
            ol.insert(add_file.next());
        }
        //the contents of the second file are removed from the ordered list
        while (del_file.hasNext()){
            ol.delete(del_file.next());
        }
        //items in ordered list are printed to output file
        for (String n:ol){
            outfile.println(n);
        }

        add_file.close();
        outfile.close();
    }
}




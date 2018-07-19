//Samuel Akinmulero
//CS310 Riggins
//4-29-17
//Program 3: LatinDictionary

import java.util.Iterator;
import data_structures.*;

public class LatinDictionary {
    private DictionaryADT<String,String> dictionary;

    // constructor takes no arguments.  Size depends on the datafile.
    // creates an instance of the DictionaryADT. Use your HashTable
    // implementation in this class (though all four should work).
    // Methods that make modifications to the dictionary modify the
    // DictionaryADT object, not the datafile.
    public LatinDictionary() {
        //dictionary = new HashTable<String,String>(100000);
        //dictionary = new BinarySearchTree<String,String>();
       // dictionary = new OrderedArrayDictionary<String,String>(100000);
        //dictionary = new RedBlackTree<String,String>();
    }

    // reads the key=value pairs from the datafile and builds a dictionary structure
    public void loadDictionary(String fileName) {
        DictionaryEntry [] entries = DictionaryReader.getDictionaryArray(fileName);
        for(int i = 0; i< DictionaryReader.index; i++) {
            if (entries[i] != null) //Checks for no entries
                dictionary.add(entries[i].getKey(), entries[i].getValue()); //Loads up Dictionary keys and values
        }
    }

    // inserts a new Latin word and its definition
    public boolean insertWord(String word, String definition) {
        return dictionary.add(word,definition);
    }

    // removes the key value pair that is identified by the key from the dictionary
    public boolean deleteWord(String word) {
        return dictionary.delete(word);
    }

    // looks up the definition of the Latin word
    public String getDefinition(String word) {
        return dictionary.getValue(word); //Finds value of word
    }

    // returns true if the Latin word is already in the dictionary
    public boolean containsWord(String word) {
        return dictionary.contains(word); 
    }

    // returns all of the keys in the dictionary within the range start .. finish
    // inclusive, in sorted order. Neither value 'start' or 'finish' need be in the
    // dictionary.  Returns null if there are no keys in the range specified.
    public String[] getRange(String start, String finish) {
        Iterator<String> ite = dictionary.keys();
        UnorderedList<String> newList = new UnorderedList<String>();

        while(ite.hasNext()) {
            String speak = ite.next();
            if(speak.compareTo(start) >= 0 && speak.compareTo(finish) <= 0) //Checks from start to finish
                newList.add(speak);
        }
        String [] rList = new String[newList.size()];
        int i = 0;

        for(String w : newList)  //Uses enhanced for to check range
            rList[i++] = w;
        return rList;
    }

    // returns an Iterator of the latin words (the keys) in the dictionary,
    // in sorted order.
    public Iterator<String> words() {
        return dictionary.keys();
    }

    // returns the definitions in the dictionary, in exactly the same order
    // as the words() Iterator
    public Iterator<String> definitions() {
        return dictionary.values();
    }
}

package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix.isEmpty())
        {
            int n= new Random().nextInt(words.size()-1);
            return (words.get(n));
        }
        else
        {
            int beg=0;
            int last=words.size();
            String s= binarySearch(prefix,beg,last);
            return s;
        }
    }
    public String binarySearch(String prefix,int b,int l) {
         while(b < l) {
            int m = (b + l) / 2;
            if (words.get(m).startsWith(prefix))
                return (words.get(m));
            else if (prefix.compareTo(words.get(m)) > 0) {
                b = m + 1;
                Log.d("GREATER",words.get(m));
//                binarySearch(prefix, b, l);
            } else if (prefix.compareTo(words.get(m)) < 0) {
                l = m - 1;
                Log.d("LESSER",words.get(m));
//                binarySearch(prefix, b, l);
            }
        }
        return ("");
    }
    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}

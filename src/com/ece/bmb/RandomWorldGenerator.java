package com.ece.bmb;

import java.util.Iterator;
import java.util.Random;

public class RandomWorldGenerator implements Iterable<String> {
	private int counter=0;
	private final Random rand = new Random();
	@Override
	public Iterator iterator() {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}
	@Override
	public String next() {
		counter ++;
		int size = rand . nextInt ( MAX - MIN + 1) + MIN ;
		char [] word = new char [ size ];
		for (int i = 0; i < size ; i ++) {
			int c = Math . abs ( rand . nextInt ()) % 26 + ’a ’;
			word [ i ] = Character . toChars ( c )[0];
		}
		return new String ( word );


	}

}

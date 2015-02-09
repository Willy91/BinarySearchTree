package com.ece.bmb;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomWordGenerator implements Iterable<String> {

	private final Random rand = new Random();
	private static int MAX=5;
	private static int MIN=1;
	private final int bound;

	public RandomWordGenerator(int bound) {
		this.bound = bound;
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			private int counter=0;
			public String next () {
				counter ++;
				return rand . ints ( rand . nextInt ( MAX - MIN + 1) + MIN )
						. map ( i -> Math . abs ( i ) % 26 +'a')
						. mapToObj ( i -> new String ( Character . toChars ( i )))
						. collect ( Collectors . joining ());
			}

			@Override
			public boolean hasNext() {
				return counter < bound ? true : false ;
			}
		};
	}

}

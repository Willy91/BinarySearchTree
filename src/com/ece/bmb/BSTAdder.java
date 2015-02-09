package com.ece.bmb;

import java.util.concurrent.Callable;
import java.time.Duration;

public class BSTAdder implements Callable<Duration>{
	BinarySearchTree<String> bst;
	String word;
	
	public BSTAdder(BinarySearchTree<String> bst, String word) {
		this.bst = bst;
		this.word = word;
	}
	
	public Duration call() throws Exception {
		long timeStart = System.nanoTime();
		bst.add(word);
		long timeEnd = System.nanoTime();
		return Duration.ofNanos(timeEnd-timeStart);
	}

}

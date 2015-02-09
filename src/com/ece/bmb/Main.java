/* Copyright (c) 2015, Frédéric Fauberteau
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ece.bmb;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ece.bmb.View;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private View v;	
	ArrayList<Long> times = new ArrayList<Long>();


	public void start(Stage primaryStage) {

		v = new View(primaryStage);
		v.start(this);
	}

	public static final void main(String[] args) {
		launch(args);

	}


	public void doBST(int nbThread, int nbWord) throws IOException
	{
		RandomWordGenerator wordGen = new RandomWordGenerator(nbWord);
		BinarySearchTree<String> rbtree = new BinarySearchTree<>();
		
		for(int i=1; i<=nbThread;i++) {
			ExecutorService ex = Executors.newFixedThreadPool(i);
			long time = 0;
			
			for(String randString : wordGen) {
				Future<Duration> future = ex.submit(new BSTAdder(rbtree, randString));
				try {
					time += future.get().toNanos();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			times.add(time);
		}
		
		v.drawGraph(times);

		String name = "rbtree";

		PrintWriter writer = new PrintWriter(name + ".dot");
		writer.println(rbtree.toDOT(name));
		writer.close();
		ProcessBuilder builder = new ProcessBuilder("dot", "-Tpdf", "-o", name + ".pdf", name + ".dot");
		builder.start();
		System.out.println(rbtree.isCorrect(rbtree.getRoot()));
	}


}

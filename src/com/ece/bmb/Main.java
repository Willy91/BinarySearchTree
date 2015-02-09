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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ece.bmb.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	private View v;	
	
	public void start(Stage primaryStage) {

		v = new View(primaryStage);
		v.start();
	}
  
  public static final void main(String[] args) throws IOException {
	RandomWordGenerator wordGen = new RandomWordGenerator(100);
	ExecutorService ex = Executors.newFixedThreadPool(2);
	BinarySearchTree<String> rbtree = new BinarySearchTree<>();
	for(String randString : wordGen) {
		ex.submit(new BSTAdder(rbtree, randString));
	}
    String name = "rbtree";

    PrintWriter writer = new PrintWriter(name + ".dot");
    writer.println(rbtree.toDOT(name));
    writer.close();
    ProcessBuilder builder = new ProcessBuilder("C:/Program Files (x86)/Graphviz2.38/bin/dot.exe", "-Tpdf", "-o", name + ".pdf", name + ".dot");
    builder.start();
    System.out.println(rbtree.isCorrect(rbtree.getRoot()));

    
    launch(args);
    
  }
  
  
}

package com.ece.bmb;


import java.util.ArrayList;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {

	private Stage primaryStage;
	private Main ctrl;

	private NumberAxis x;
	private NumberAxis y;
	private LineChart < Number , Number > chart;
	private Scene vb;
	private Series<Number, Number> series;


	public void drawGraph(ArrayList<Long> times){

		System.out.println("Size time: " +times.size());
		series.getData().clear();
		for(int i=0; i<times.size();i++){
			series.getData().add(new XYChart.Data<Number,Number> (i+1 , times.get(i)));
		}
		
		x.setUpperBound(times.size());		
		
		chart.getData().clear();
		chart.getData().add(series);

	}

	public View(Stage primaryStage){
		this.primaryStage=primaryStage;

	}

	public void start(Main ctrl) {

		vb = new Scene(new VBox(),800,600);
		Label labelNbValue = new Label("Number of Value to Add");
		TextField nbValue = new TextField();
		Label labelMaxThread = new Label("Number of Thread");
		TextField maxThread = new TextField();	
		series = new XYChart.Series<Number, Number>();

		Button launchBenchmark = new Button("Launch Benchmark");
		launchBenchmark.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				if(!nbValue.getText().isEmpty() && !maxThread.getText().isEmpty()) {
					int nbVal = Integer.parseInt(nbValue.getText());
					int maxThr = Integer.parseInt(maxThread.getText());
					try {
						ctrl.doBST(maxThr, nbVal);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		HBox hbTop = new HBox();
		hbTop.getChildren().addAll(labelNbValue,nbValue,labelMaxThread, maxThread,launchBenchmark);

			
		x = new NumberAxis();
		x.setAutoRanging(false);
		x.setLowerBound(1);
		x.setTickUnit(1);
		
		y = new NumberAxis();
		y.setLabel("Time in ns");
		chart = new LineChart < >(x , y);
		
		chart.setTitle("Time according to thread number ");

		Label progressLabel = new Label("Progress");
		ProgressBar pb = new ProgressBar(0.6);
		ProgressIndicator pi = new ProgressIndicator(0.6);

		HBox hbBottom = new HBox();
		hbBottom.getChildren().addAll(progressLabel,pi,pb);

		((VBox) vb.getRoot()).getChildren().addAll(hbTop,chart,hbBottom);

		primaryStage.setScene(vb);
		primaryStage.show();
	}
}

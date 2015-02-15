package com.ece.bmb;


import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

	private NumberAxis x = new NumberAxis (1,4,1);
	private NumberAxis y = new NumberAxis ();
	private LineChart < Number , Number > chart;
	private Scene vb;

	private ObservableList<XYChart . Data < Number , Number >> data =FXCollections.observableArrayList();

	//private ObservableList < XYChart . Series < Number , Number > > series = FXCollections . observableArrayList ();

	XYChart.Series<Number, Number> series = new XYChart.Series();

	public void drawGraph(ArrayList<Long> times){

		series.getData().clear();
		
		System.out.println("Size time: " +times.size());
		data.clear();
		for(int i=0; i<times.size();i++){
			data.add(new XYChart . Data < >(i+1 , times.get(i)));
			series.getData().add(new XYChart.Data(i+1, times.get(i)));
		}

		x = new NumberAxis(1,times.size(),1);
		y.autoRangingProperty();
		chart.getData().remove(series);
		//series.getData().add(new XYChart . Series < >( data ));
		
					
		//((VBox) vb.getRoot()).getChildren().remove(1);
		//chart.setData(series);
		chart = new LineChart<>(x, y);
		chart.getData().add(series);
		((VBox) vb.getRoot()).getChildren().add(1, chart);
		((VBox) vb.getRoot()).getChildren().remove(2);

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

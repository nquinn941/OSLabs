/**
 * Controller for UI and Guassian/Poission distrubution of random numbers 
 * 
 * Name: LabController.java
 *  
 * Written by: Niall Quinn - September 2018 
 * 
 * Purpose: take input of amount of numbers and number of bins and generate random numbers
 * in various distributions 
 * 
 * usage: see Main.java 
 * 
 * Subroutines/Libraries: See import statements below 
 * 
 */

import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LabController {
	
	//The X axis of the chart
	private CategoryAxis xAxis= new CategoryAxis();
	
	//The Y axis of the chart 
	private NumberAxis yAxis = new NumberAxis();
	
	//A new bar chart using the x and y axis above called bc
	@FXML
	private BarChart<String,Number > bc = new BarChart<String,Number>(xAxis,yAxis);

	//The button for generating numbers between 0 and 1 
    @FXML
    private Button button01;
    
    //Text field where user inputs how many numbers to generate 
    @FXML
    private TextField generateTextBox;
    
    //Label that says "# to generate" 
    @FXML
    private Label toGenerateLabel;

    //Label that says "# of bins"
    @FXML
    private Label binsLabel;

    //Text field where user inputs how many bins they want 
    @FXML
    private TextField binTextBox;

    //A button that will generate numbers that are normally distributed
    @FXML
    private Button normalDistButton;

    //Label that says "Mean" for normal distribution function
    @FXML
    private Label normMeanLabel;
    
    //Text field that user inputs the mean for normal distribution function 
    @FXML
    private TextField meanTextBox;

    //Label that says "S.D" for normal distribution function
    @FXML
    private Label sdLabel;

    //Text field where user inputs the standard deviation for normal distribution function
    @FXML
    private TextField sdTextBox;

    //Label that says "Expected mean" for poisson function
    @FXML
    private Label poissonMeanLabel;

    //Text field that user inputs expected mean for poisson function
    @FXML
    private TextField expectedMeanTextBox;

    //Button that runs the poisson function
    @FXML
    private Button poissonButton;
    
    //Label that says "Actual Mean" 
    @FXML
    private Label actualMeanLabel;

    //Label that will contain the actual mean computed when functions run 
    @FXML
    private Label actualMeanOutputLabel;
    
    //Button that will run the dice function 
    @FXML
    private Button diceButton;

    /**
     * A method that will be run when a button is clicked 
     * @param event - the click on the button 
     */
    @FXML
    void actionPerformed(MouseEvent event) {
    	//Will clear the graph 
    	bc.getData().clear();
    	
    	//If source is button01, it will run the random method
    	if(event.getSource()==button01) {
    		//random will run with the number in # to generate text box as param
    		random(Integer.parseInt(generateTextBox.getText()));
    	}
    	
    	//If normaldistButton is pressed, the normalDist method will run 
    	if(event.getSource()==normalDistButton) {
    		normalDist(Integer.parseInt(generateTextBox.getText()));
    	}
    	
    	//if source is poissonButton, poission method is called 
    	if(event.getSource()==poissonButton) {
    		possion(Integer.parseInt(generateTextBox.getText()));
    	}
    	
    	//If diceButton is pressed, diceThrow method runs 
    	if(event.getSource()==diceButton) {
    		diceThrow(Integer.parseInt(generateTextBox.getText()));
    	}
    }
    
    /**
     * the random method that will generate random numbers between 0 and 1 
     * @param numbersToBeGenerated - the amount of numbers the user wants to be created
     */
    void random(int numbersToBeGenerated) {
    	//An array will be created of the size that the user has enterted 
    	int[] bins = new int[Integer.parseInt(binTextBox.getText())+1];
    	double actualMean=0;
    	
    	
    	for(int i=0;i<numbersToBeGenerated;i++) {
    		//Uses Math.random library function to create a random number 
    		double numberToBin = Math.random();
    		//gets the int of random* number of bins+1
    		int binNumber = (int)(numberToBin*Integer.parseInt(binTextBox.getText()))+1;
    		//adds the bin number to the actual mean 
    		actualMean+=binNumber;
    		//adds 1 to the binNumber in bins array 
    		bins[binNumber]++;
    	}//loop will to the same amount of times as the user has specified for numbers to be generated
    	//calls a method that will display visually the data in the bins
    	showOnGraph(bins);
    	//shows the actual mean on screen
    	showActualMean(numbersToBeGenerated,actualMean);	
    }
    
    /**
     * a method that will generate random dice throws 
     * @param numbersToBeGenerated - the amount of trails the user wants carried out 
     */
    void diceThrow(int numbersToBeGenerated) {
    	//creates 13 bins as there are only 12 possible binNos 
    	int[]bins = new int[13];
    	double actualMean=0;
    	
    	for(int i=0;i<numbersToBeGenerated;i++) {
    		//will get Bin #s for two rolls
    		int roll1 = getRandomDiceRoll();
    		int roll2 = getRandomDiceRoll();
    		//adds the two rolls to actual mean 
    		actualMean+=roll1+roll2;
    		//increments the bin 
    		bins[roll1+roll2]++;
    	}//loop will to the same amount of times as the user has specified for numbers to be generated
    	
    	//calls a method that will display visually the data in the bins
    	showOnGraph(bins);
    	//shows the actual mean on screen
    	showActualMean(numbersToBeGenerated,actualMean);
    }
    
    /**
     * Method that will generate a random dice roll
     * @return the bin number for that roll
     */
    int getRandomDiceRoll() {
    	//gets a random number between 0 and 1
    	double roll = Math.random();
    	//calculate the bin number for that random number
    	int binNo = (int) (roll*6)+1;
    	return binNo;
    }
    
    /**
     * Method that will create normal distributed data 
     * @param numbersToBeGenerated
     */
    void normalDist(int numbersToBeGenerated) {
    	//gets the number of bins the user wants created from the text box
    	int numberOfBins = Integer.parseInt(binTextBox.getText());
    	//Due to the long tail of a gaussian, i have hardcoded an extra 10 bins
    	int[] bins = new int[numberOfBins+10];
    	//creates a new random object 
    	Random rand = new Random();
    	double actualMean=0;
    	
    	//reads in the standard dev and mean from the respective text boxes
    	int sd = Integer.parseInt(sdTextBox.getText());
    	int mean = Integer.parseInt(meanTextBox.getText());
    	for(int i=0;i<numbersToBeGenerated;i++) {
    		//the random object will call the nextGaussian function
    		double newRandom = rand.nextGaussian();
    		//to make it normal distributed, the mean will be added
    		//to the random number and multiplied by sd 
    		newRandom = mean + newRandom *sd;
    		actualMean+=newRandom;
    		//bin for the next random will be calculated 
    		bins[(int) newRandom]++;
    	}//loop will to the same amount of times as the user has specified for numbers to be generated
    	
    	
    	showOnGraph(bins);
    	showActualMean(numbersToBeGenerated,actualMean);
    }
    
    /**
     * A method for creating a possopm distribution of numbers 
     * @param numbersToBeGenerated
     */
    void possion(int numbersToBeGenerated) {
    	//Reads in the expected mean from the text box
    	double lambda = Integer.parseInt(expectedMeanTextBox.getText());
    	int[] bins = new int[Integer.parseInt(binTextBox.getText())+1];
    	int actualMean=0;
    	
    	/**
    	 * from stackoverflow - https://stackoverflow.com/questions/1241555/algorithm-to-generate-poisson-and-binomial-random-numbers
    	 */
    	
    	for(int i=0;i<numbersToBeGenerated;i++) {
    		//will calculate the exponential of the negative expected mean
    		double l = Math.exp(-lambda);
    		double p = 1.0;
    		int k=0;
    		
    		do {
    			k++;
    			p*=Math.random();
    		}while(p>l);
    		actualMean+=k-1;
    		bins[k-1]++;
    	}//loop will to the same amount of times as the user has specified for numbers to be generated
    	showActualMean(numbersToBeGenerated,actualMean);
    	showOnGraph(bins);	
    }
    
    /**
     * will show the actual mean on screen 
     * @param numbersToBeGenerated
     * @param actualMean
     */
    void showActualMean(int numbersToBeGenerated,double actualMean) {
    	//will set the output string to the actual mean
    	String output = Double.toString(actualMean/numbersToBeGenerated);
    	//will set the actual mean label to the output string 
    	actualMeanOutputLabel.setText(output);
    }
    
    /**
     * show the data in graph form 
     * @param bins
     */
    void showOnGraph(int[]bins) {
    	for(int i=0;i<bins.length;i++) {
    		//create a new series of chart data 
    		XYChart.Series series1 = new XYChart.Series();
    		//adds the data from one bin to the series
    		series1.getData().add(new XYChart.Data("",bins[i]));
    		//sets the name of the series to the bin #
    		series1.setName("Bin "+ i);
    		//adds the series to the chart 
    		bc.getData().add(series1);
    		
    	}//loop will run for the number of bins there are 
    	
    }
    
    
    

   

}

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * The JavaFx Calculator extends the Application function from a prior source code
 * This is a basic calculator with working memory, backspace, decimal place, and 
 * square root, and power buttons.
 * 
 * @author Caden Cruise
 * @version 1.0
 * @since 2022.04.24
 *
 */
public class JavaFXCalculator extends Application {
	
	/**
	 * Field used to display stored memory.
	 */
	private Text memoryText;		// display memory text
	
	/**
	 * Field used to display the text on the screen.
	 */
	private TextField tfDisplay;    // display textfield
		
	
	// For computation
	
	/**
	 * Field to create and store the memory value. 
	 */
	private double memory = 0;		// memory.
	
	/**
	 * Field used to create and store the result of a calculation on screen.
	 */
	private double result = 0;   	// Result of computation
	
	/**
	 * Field used to input the current button pressed on the calculator. 
	 */
	private String inStr = "0";  	// Input number as String
	// Previous operator: ' '(nothing), '+', '-', '*', '\u00F7', '='
	
	/**
	 * Field used to store the last operator pressed on the calculator.
	 */
	private char lastOperator = ' ';

	// Event handler for all the 24 Buttons
	
	/**
	 * Handles the switch case for all working buttons on the calculator.
	 */
	EventHandler handler = evt -> {
		String currentBtnLabel = ((Button)evt.getSource()).getText();
		switch (currentBtnLabel) {
			// Number buttons
			case "0": case "1": case "2": case "3": case "4":
			case "5": case "6": case "7": case "8": case "9":
			case ".":
				if (inStr.equals("0")) {
					inStr = currentBtnLabel;  // no leading zero
				} else {
					inStr += currentBtnLabel; // append input digit
				}
				tfDisplay.setText(inStr);
				// Clear buffer if last operator is '='
				if (lastOperator == '=') {
					result = 0;
					lastOperator = ' ';
				}
				break;

			// Operator buttons: '+', '-', 'x', '\u00F7' = divide symbol, 
			//	'=', '^', '\u221a' = square root symbol, '\u2190' = backspace arrow
			case "+":
				compute();
				lastOperator = '+';
				break;
			case "-":
				compute();
				lastOperator = '-';
				break;
			case "x":
				compute();
				lastOperator = '*';
				break;
			case "\u00F7": //divide
				compute();
				lastOperator = '\u00F7';
				break;
			case "=":
				compute();
				lastOperator = '=';
				break;
				
			case "^": // power
				compute();
				lastOperator = '^';
				break;
				
			case "\u221a": // square root
				result = Math.sqrt(result);
				break;
				
			case "\u2190": // backspace arrow
				String backspace=null;
				if (tfDisplay.getText().length() > 1) {
					StringBuilder strB = new StringBuilder(tfDisplay.getText());
					strB.deleteCharAt(tfDisplay.getText().length() - 1);
					backspace = strB.toString();
					tfDisplay.setText(backspace);
					
				} else tfDisplay.setText("0");
									
				break;
	
			// Clear button
			case "C":
				result = 0;
				inStr = "0";
				lastOperator = ' ';
				tfDisplay.setText("0");
				break;
			
			// Memory Add Button
			case "M+":
				memory += result;
				memoryText.setText("Memory = " + memory);
				break;
				
			// Memory Subtract Button
			case "M-":
				memory -= result;
				memoryText.setText("Memory = " + memory);
				break;
				
			// Memory Recall Button  
			case "MR":
				inStr = String.valueOf(memory);
				tfDisplay.setText(memory + "");
				break;
				
			// Memory Clear Button
			case "MC":
				memory = 0;
				memoryText.setText("Memory = 0");
				break;
		}
	};

	// User pushes '+', '-', '*', '\u00F7', '^', or '=' button.
	// Perform computation on the previous result and the current input number,
	// based on the previous operator.
	
	/**
	 * Method that does the basic math function when an operator is pressed. 
	 */
	private void compute() {
		double inNum = Double.parseDouble(inStr);
		inStr = "0";
		if (lastOperator == ' ') {
			result = inNum;
		} else if (lastOperator == '+') {
			result += inNum;
		} else if (lastOperator == '-') {
			result -= inNum;
		} else if (lastOperator == '*') {
			result *= inNum;
		} else if (lastOperator == '\u00F7') {
			result /= inNum;
		} else if (lastOperator == '=') {
			// Keep the result for the next operation
		} else if (lastOperator == '^') {
			result = Math.pow(result, inNum);
		} tfDisplay.setText(result + "");
	}
	

	// Setup the UI
	/**
	 *Method to set up the working UI of the calculator.
	 */
	@Override
	public void start(Stage primaryStage) {
		Button[] btns;          // 20 buttons
		String[] btnLabels = {  // Labels of 24 buttons
				"7", "8", "9", "+",
				"4", "5", "6", "-",
				"1", "2", "3", "x",
				".", "0", "=", "\u00F7", // \u00F7 = divide symbol
				"C", "\u2190", "^", "\u221a", // \u2190 = backspace arrow, \u221a = square root 
				"M+", "M-", "MR", "MC"
		};
		
		// Setup the Display TextField
		tfDisplay = new TextField("0");
		tfDisplay.setEditable(false);
		tfDisplay.setAlignment(Pos.CENTER_RIGHT);
		
		//Setup the Display Memory Text
		memoryText = new Text(10, 50, "Memory = 0");

		// Setup a GridPane for 4x4 Buttons
		int numCols = 4;
		int numRows = 4;
		GridPane paneButton = new GridPane();
		paneButton.setPadding(new Insets(15, 0, 15, 0));  // top, right, bottom, left
		paneButton.setVgap(5);  // Vertical gap between nodes
		paneButton.setHgap(5);  // Horizontal gap between nodes
		// Setup 4 columns of equal width, fill parent
		ColumnConstraints[] columns = new ColumnConstraints[numCols];
		for (int i = 0; i < numCols; ++i) {
			columns[i] = new ColumnConstraints();
			columns[i].setHgrow(Priority.ALWAYS) ;  // Allow column to grow
			columns[i].setFillWidth(true);  // Ask nodes to fill space for column
			paneButton.getColumnConstraints().add(columns[i]);
		}

		// Setup 24 Buttons and add to GridPane; and event handler
		btns = new Button[24];
		for (int i = 0; i < btns.length; ++i) {
			btns[i] = new Button(btnLabels[i]);
			
			switch(btnLabels[i]) {
				case "C":
				case "\u2190": // backspace arrow
					btns[i].setStyle("-fx-color: orange");
					break;
					
				case "M+":
				case "M-":
				case "MR":
				case "MC":
					btns[i].setStyle("-fx-color: blue");
					break;
			}
			btns[i].setOnAction(handler);  // Register event handler
			btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // full-width
			paneButton.add(btns[i], i % numCols, i / numCols);  // control, col, row
		}

		// Setup up the scene graph rooted at a BorderPane (of 5 zones)
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(15, 15, 15, 15));  // top, right, bottom, left
		root.setTop(tfDisplay);     // Top zone contains the TextField
		root.setBottom(memoryText);     // Top zone contains the TextField
		root.setCenter(paneButton); // Center zone contains the GridPane of Buttons

		// Set up scene and stage
		primaryStage.setScene(new Scene(root, 300, 300));
		primaryStage.setTitle("JavaFX Calculator");
		primaryStage.show();
	}

	/**
	 * @param args No command line input args are used for this application
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
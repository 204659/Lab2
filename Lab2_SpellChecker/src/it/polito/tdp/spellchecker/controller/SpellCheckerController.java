 package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SpellCheckerController {
	
	private Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLingue;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextFlow txtOutput;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnClearText;

    @FXML
    private Label lblTime;

    
    @FXML
    void doClearText(ActionEvent event) {

    	txtOutput.getChildren().clear();
    	txtInput.clear();
    	lblTime.setText("");
    	lblErrors.setText("");
    	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	boolean isError = false;
    	
    	if(cmbLingue.getValue() == null){
    		txtInput.setText("You must choose a language!!");
    		return;
    	}
    	
    	if(cmbLingue.getValue().compareTo("English")==0)
    		model = new EnglishDictionary();
    	else
    		model = new ItalianDictionary();
    	
    	long t0 = System.currentTimeMillis();
    	//model.loadDictionary();
   
    	List<String> inputText = new ArrayList<String>();
    	//Set<String> inputText = new HashSet<String>();
		String input = txtInput.getText();
		StringTokenizer st = new StringTokenizer(input," ");
		while(st.hasMoreTokens()){
			String s = st.nextToken().toLowerCase().trim();
			inputText.add(s);
		}
		
		List<RichWord> results = model.spellCheckText(inputText);
		for(RichWord rw : results){
			Text t = new Text(rw.getParola()+" ");
			
			if(!rw.isCorretta()){
				t.setFill(Color.RED);
				isError = true;
			}
			txtOutput.getChildren().add(t);
		}
		long tf = System.currentTimeMillis();
		long tempo = tf-t0;
		
		if(isError)
			lblErrors.setText("Your text contains errors!!");
		
		lblTime.setText("Computed in "+tempo+" ms");
    }

    @FXML
    void initialize() {
        assert cmbLingue != null : "fx:id=\"cmbLingue\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        
        cmbLingue.getItems().addAll("English","Italian");
    }

	public void setModel(Dictionary model) {
		// TODO Auto-generated method stub
		
	}
}

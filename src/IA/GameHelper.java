package IA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameHelper {
	int points;
	int mistakes;
	public char getrandomSmallLetter(){
		String s="qwertyuiogpasdfghjklzxcvbnm";
		return s.charAt((int)Math.round(Math.random()*(s.length()-1)));
	}
        
        public char getrandomLetter(){
		String s="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		return s.charAt((int)Math.round(Math.random()*(s.length()-1)));
	}
        public char getrandomLetterOrMark(){
		String s="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM§'\"+!%/=(),.?:;";
		return s.charAt((int)Math.round(Math.random()*(s.length()-1)));
	}
    /*public String GetRandomWord(){
	String[] s={"apple", "Ben", "London"};
	return s[(int)Math.round(Math.random()*(s.length-1))];
    }
    public String GetRandomSentence(){
    	String[] s={"I like apple!", "Is Ben working?", "London doesn`t have a nice weather"};
    	return s[(int)Math.round(Math.random()*(s.length-1))];
        }
        */
    public void newGame(){
    	points = 0;
    	mistakes = 0;
    }
    public void newPointScored(){
    	points++;
    }
    public int getPoints(){
    	return points;
    }
    public void newMistake(){
    	mistakes++;
    }
    public int getMistakes(){
    	return mistakes;
    }
    
}

package IA;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ToplistManager {
BufferedWriter bw ;
FileWriter fw;
BufferedReader br;
FileReader fr;
File file;
Mainprog m;
int time =1;
public void writeNewScore(String name, int point){
	try {
		m = new Mainprog();
		file = new File("Toplist");
		file.createNewFile();
		fw = new FileWriter(file,true);
		bw = new BufferedWriter(fw);
		float f = point / (float)time;
		bw.write(Float.toString(f)+", "+name);
		bw.newLine();
		bw.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
	
	
}
public void setTime(int i){
	time = i ;
	
}
public ArrayList<String> readPoints(){
	ArrayList<String> Arrayl = new ArrayList<> () ;
	String line;
	file = new File("Toplist");
	try {
		br = new BufferedReader (new FileReader(file));
		while ((line=br.readLine())!=null){
                    //Arrayl.add(Float.valueOf(line));
                    Arrayl.add(line);
                }
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        Collections.sort(Arrayl,Collections.reverseOrder());
	return Arrayl;
        
}
}

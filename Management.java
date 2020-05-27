import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Management {

	String[] parseStory = null;// for split
	ArrayList<String> arrList = new ArrayList<>();// kelimeleri tutuyor
	ArrayList<Integer> arrListKey = new ArrayList<>();// key degeri icin
	char chr = ' ';// for key
	int ascii = 0; // for key
	ProbeHashMap myHash = new ProbeHashMap<>(5000);
	// dosya okuma
	BufferedReader br = null;
	{
		try {
			br = new BufferedReader(new FileReader("C:\\Users\\mustafa\\Desktop\\odevData\\story.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				parseStory = line.split(" ");
				for (int i = 0; i < parseStory.length; i++) {
					arrList.add(parseStory[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// key hesabi ve count ve hash'a elemanlari ekleme
		for (int i = 0; i < arrList.size(); i++) {
			ascii = 0;
			for (int j = 0; j < arrList.get(i).length(); j++) {
				chr = arrList.get(i).charAt(j);
				ascii += (int) chr;
			}
			arrListKey.add(ascii);
			myHash.put(arrListKey.get(i), arrList.get(i), 1);
		}
		myHash.print();
		boolean flag = true;
		Scanner scn = new Scanner(System.in);
		while (flag) {
			System.out.print("Enter value: ");
			String input = scn.next();
			myHash.control(input);
		}
	}
}

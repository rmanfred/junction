package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MessageChecker {
    final HashMap<Integer, String> badWords = new HashMap<>();
    //fill the hashMap w/ bad words
    public void fillMap() {
        String file = "badWordsList.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                badWords.put(i, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to check if the message contains a bad word
    public boolean isMessageBad(String message) {
        String[] arr = message.split(" ");
        boolean contains = false;
        for (int i = 0; i < arr.length; i++) {
            if (badWords.containsValue(arr[i])) {
                contains = true;
            }
        }
        return contains;
    }
}

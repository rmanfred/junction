package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MessageChecker {
    final HashMap<Integer, String> badWords = new HashMap<>();
    final List<String> niceWords = new ArrayList<>();

    // Меняем пути
    String fileOfBadWords = "/home/fara/chat/src/Client/badWordsList.txt";
    String fileOfNiceWords = "/home/fara/chat/src/Client/niceWordsList.txt";

    //fill the hashMap w/ bad words
    public void fillMap(boolean isBadFiller) {
        final String words = isBadFiller ? fileOfBadWords : fileOfNiceWords;

        try (BufferedReader br = new BufferedReader(new FileReader(words))) {
            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (isBadFiller)
                    badWords.put(i++, line);
                else
                    niceWords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to check if the message contains a bad word
    public List<String> isMessageBad(String message) {
        final List<String> usersBadWords = new ArrayList<>();

        String[] arr = message.split(" ");

        for (int i = 0; i < arr.length; i++) {
            if (badWords.containsValue(arr[i])) {
                usersBadWords.add(arr[i]);
            }
        }
        return usersBadWords;
    }

    public String replaceWords(List<String> usersWords, String message){
//        final String newMessage = "";
        for (String word : usersWords)
        {
            String toReplaceBy = niceWords.get(getRandomElement(niceWords));
            message = message.replace(word, toReplaceBy);
        }
        return message;
    }

    public int getRandomElement(List<String> list)
    {
        Random rand = new Random();
        return rand.nextInt(list.size());
    }
}

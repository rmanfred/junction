package Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MessageChecker {
    public static boolean filterMsg = true;
    final HashMap<Integer, String> badWords = new HashMap<>();
    final List<String> niceWords = new ArrayList<>();

    // Change the routs accordingly
    String fileOfBadWords = "C:\\Users\\mi\\Desktop\\max\\projects\\chat\\src\\Client\\badWordsList.txt";
    String fileOfNiceWords = "C:\\Users\\mi\\Desktop\\max\\projects\\chat\\src\\Client\\niceWordsList.txt";

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

    public List<String> isMessageBadFilter(String message) {
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

    public boolean isMessageBadNoFilter(String message) {
        String[] arr = message.split(" ");
        boolean contains = false;
        for (int i = 0; i < arr.length; i++) {
            if (badWords.containsValue(arr[i])) {
                contains = true;
            }
        }
        return contains;
    }
        public void reverseFilter() {
        filterMsg = !filterMsg;
    }

    public boolean getFilter() {
        return filterMsg;
    }
}

package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Anagram {

    public static void main(String[] args) throws IOException {
        String searchAnagramsFor = "сестра";
        List<String> arrayWords = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get("src", "task", "dictionary.txt").toFile()))) {
            bufferedReader.lines().forEach(arrayWords::add);
        }
        String[] dictionary = new String[arrayWords.size()];
        for (int i = 0; i < arrayWords.size(); i++) {
            dictionary[i] = arrayWords.get(i);
        }
        Collection collectAnagrams = collectAnagrams(dictionary, searchAnagramsFor);
        System.out.println("Список анаграмм к слову " + searchAnagramsFor + " - " + collectAnagrams);
    }

    private static Collection collectAnagrams(String[] dictionary, String searchAnagramsFor) {
        List<String> listWordsSameLength = getListWordsSameLength(dictionary, searchAnagramsFor);
        Collection<String> anagrams = new ArrayList<>();
        boolean oneWord = isOneWord(searchAnagramsFor);
        if (oneWord) {
            Map<Character, Integer> hashMapSearchAnagramFor = getHashMapWord(searchAnagramsFor);
            for (String element : listWordsSameLength) {
                Map<Character, Integer> hashMapSameLengthWord = getHashMapWord(element);
                if (isAnagram(hashMapSearchAnagramFor, hashMapSameLengthWord)) {
                    anagrams.add(element);
                }
            }
        } else {
            List<String> listSearchAnagramsFor = getListWords(searchAnagramsFor);
            for (String element : listWordsSameLength) {
                boolean result = false;
                List<String> listAnotherWords = getListWords(element);
                if (listSearchAnagramsFor.size() == listAnotherWords.size()) {
                    for (int i = 0; i < listSearchAnagramsFor.size(); i++) {
                        Map<Character, Integer> hashMapFirstWord = getHashMapWord(listSearchAnagramsFor.get(i));
                        Map<Character, Integer> hashMapSecondWord = getHashMapWord(listAnotherWords.get(i));
                        if (!isAnagram(hashMapFirstWord, hashMapSecondWord)) {
                            result = false;
                            break;
                        }
                        result = true;
                    }
                }
                if (result) {
                    anagrams.add(element);
                }
            }
        }
        return anagrams;
    }

    private static List<String> getListWordsSameLength(String[] dictionary, String searchAnagramsFor) {
        List<String> listWordsSameLength = new ArrayList<>();
        for (String element : dictionary) {
            if (element.length() == searchAnagramsFor.length()) {
                listWordsSameLength.add(element);
            }
        }
        return listWordsSameLength;
    }

    private static boolean isOneWord(String word) {
        return !(word.contains(" ") | word.contains("-"));
    }

    private static Map<Character, Integer> getHashMapWord(String word) {
        HashMap<Character, Integer> hashMapWord = new HashMap<>(word.length());
        String wordToLowerCase = word.toLowerCase();
        for (int i = 0; i < wordToLowerCase.length(); i++) {
            char c = wordToLowerCase.charAt(i);
            if (hashMapWord.containsKey(c)) {
                hashMapWord.put(c, hashMapWord.get(c) + 1);
            } else {
                hashMapWord.put(c, 1);
            }
        }
        return hashMapWord;
    }

    private static List<String> getListWords(String expression) {
        String[] arrayString = expression.split("[ -]+");
        return new ArrayList<>(Arrays.asList(arrayString));
    }

    private static boolean isAnagram(Map<Character, Integer> hashMapWord, Map<Character, Integer> hashMapSecondWord) {
        return hashMapWord.equals(hashMapSecondWord);
    }
}
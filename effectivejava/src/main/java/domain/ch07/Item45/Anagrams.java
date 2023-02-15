package domain.ch07.Item45;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Anagrams {

    final List<String> dictionary = Arrays.asList(
        "staple", "stplea", "petals", "ptleas", "abdec"
    );

    public Map<String, List<String>> getDictionaryMapNoStream() {
        Map<String, List<String>> groups = new HashMap<>();

        for (String word : dictionary) {
            groups.computeIfAbsent(alphabetize(word),
                (key) -> new ArrayList<>()).add(word);

        }

        return groups;
    }

    public Map<String, List<String>> getDictionaryMapWithBadStream() {
        Map<String, List<String>> collect = dictionary.stream().collect(
            Collectors.groupingBy(word -> word.chars().sorted()
                .collect(StringBuilder::new,
                    (sb, c) -> sb.append((char) c), StringBuilder::append).toString()));

        return collect;
    }

    public Map<String, List<String>> getDictionaryMapWithGoodStream() {
        HashMap<String, List<String>> collect = dictionary.stream()
            .collect(
                Collectors.groupingBy(word -> alphabetize(word), HashMap::new, Collectors.toList(
                ))
            );

        return collect;
    }

    private String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}

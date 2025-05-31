package ap.exercises.projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Comparator;

public class ObjectCounter {
    private List<String> items;
    private List<Integer> counts;

    public ObjectCounter() {
        items = new ArrayList<>();
        counts = new ArrayList<>();
    }

    public void add(String item) {
        int index = items.indexOf(item);
        if (index >= 0) {
            counts.set(index, counts.get(index) + 1);
        } else {
            items.add(item);
            counts.add(1);
        }
    }

    public List<Map.Entry<String, Integer>> getTop(int k) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            entries.add(new AbstractMap.SimpleEntry<>(items.get(i), counts.get(i)));
        }

        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return entries.subList(0, Math.min(k, entries.size()));
    }
}
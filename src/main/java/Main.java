import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(Main::findAndPutR).start();
        }
        sizeToFreq.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()).
                forEach(x -> System.out.println("Число " + x.getKey() + " повторилось " + x.getValue() + " раз"));

    }

    public synchronized static void findAndPutR() {
        String str = generateRoute("RLRFR", 100);
        int count = 0;
        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == 'R') {
                count++;
            }
        }
        if (!sizeToFreq.containsKey(count)) {
            sizeToFreq.put(count, 1);
        } else {
            sizeToFreq.computeIfPresent(count, (k, v) -> v + 1);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
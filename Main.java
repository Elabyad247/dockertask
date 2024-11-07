import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static final Map<Integer, Queue<String>> messageQueues = new ConcurrentHashMap<>();
    private static final int NUM_QUEUES = 3;
    public static void send(int queueId, String message) {
        messageQueues.get(queueId).offer(message);
        System.out.println("Sent message to queue " + queueId + ": " + message);
    }
    public static void receive(int queueId) throws InterruptedException {
        Queue<String> queue = messageQueues.get(queueId);
        while (true) {
            String message = queue.poll();
            if (message != null) {
                System.out.println("Received message from queue " + queueId + ": " + message);
            } else {
                Thread.sleep(200);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            messageQueues.put(i, new ConcurrentLinkedQueue<>());
        }
        List<String> randomMessages = Arrays.asList("Saif", "Ahmed", "Ali", "Mohamed", "Hassan", "Khaled", "Omar", "Amr", "Tamer", "Mahmoud");
        for (int queueId = 0; queueId < NUM_QUEUES; queueId++) {
            int finalQueueId = queueId;
            new Thread(() -> {
                try {
                    receive(finalQueueId);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        Random rand = new Random();
        for (String msg : randomMessages) {
            int randomQueue = rand.nextInt(NUM_QUEUES);
            send(randomQueue, msg);
            Thread.sleep(1000);
        }
    }
}

package org.zeroturnaround.jf.homework8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Producer class, will keep generating new random words to serve as the input for the hashing algorithm.
 * The method {@link #run()} should never return, until a proper shutdown is initiated.
 */
@SuppressWarnings("unused")
public class RandomStringProducer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RandomStringProducer.class);

    private static final char[] symbols;

    private final Random random = new Random();

    private char[] buf;

    LinkedBlockingQueue<String> queue;

    private ConcurrentHashMap<String, String> hashPassword;

    private AtomicBoolean finished;

    static {
        StringBuilder tempStringBuilder = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tempStringBuilder.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tempStringBuilder.append(ch);
        symbols = tempStringBuilder.toString().toCharArray();
    }

    public RandomStringProducer(LinkedBlockingQueue<String> queue, AtomicBoolean finished) {
        this.queue = queue;
        this.finished = finished;
    }

    /**
     * This should contain the actual String generation logic.
     */
    @Override
    public void run() {
        while (!finished.get()) {
            int length = random.nextInt(50);
            buf = new char[length];
            for (int i = 0; i < buf.length; ++i) {
                buf[i] = symbols[random.nextInt(symbols.length)];
            }
            try {
                this.queue.put(new String(buf));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

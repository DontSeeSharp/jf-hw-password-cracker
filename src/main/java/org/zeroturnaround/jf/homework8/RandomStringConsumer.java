package org.zeroturnaround.jf.homework8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.jf.homework8.data.Hash;
import org.zeroturnaround.jf.homework8.data.Salt;
import org.zeroturnaround.jf.homework8.util.BramHash;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Consumer class, will keep reading in new random words, feeding them to the hashing algorithm,
 * and checking the result for collisions with any of the given hashes.
 * The method {@link #run()} should never return, until a proper shutdown is initiated.
 */
@SuppressWarnings("unused")
public class RandomStringConsumer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RandomStringConsumer.class);

    private LinkedBlockingQueue<String> queue;

    private ConcurrentHashMap<String, String> hashPassword;

    private AtomicBoolean finished;

    public RandomStringConsumer(LinkedBlockingQueue<String> queue, ConcurrentHashMap<String, String> hashPassword, AtomicBoolean finished) {
        this.queue = queue;
        this.hashPassword = hashPassword;
        this.finished = finished;
    }

    /**
     * This should contain the actual hash testing + result recording logic.
     */
    @Override
    public void run() {
        int hashesSize = Hash.hashes.size();
        while (!finished.get()) {
            try {
                String password = queue.take();
                for (int i = 0; i < hashesSize; i++) {
                    String hash = BramHash.hash(Salt.salts.get(i) + password);
                    if (Hash.hashes.get(i).equals(hash)) {
                        if (!hashPassword.containsKey(hash)) {
                            hashPassword.put(hash, password);
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (hashesSize == hashPassword.size()) {
                this.finished.set(true);
            }
        }
    }
}

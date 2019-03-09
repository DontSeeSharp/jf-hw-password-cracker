package org.zeroturnaround.jf.homework8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.jf.homework8.data.Hash;
import org.zeroturnaround.jf.homework8.data.Password;
import org.zeroturnaround.jf.homework8.data.Salt;

/**
 * Sets up the required number of producer and consumer threads,
 * coordinates their information exchange,
 * keeps track of the discovered collisions (and only stores the first one found),
 * and takes care of a !!clean!! shutdown once all passwords have a found collision.
 */
public class CollisionFinder {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(CollisionFinder.class);

    /**
     * Read the data provided by the {@link Salt} and {@link Hash} classes, and for each find a collision.
     * A collision happens when 2 different inputs generate the same hash.
     * This is dangerous in password-validation systems that use hash-codes (and in many other applications),
     * as the system will wrongfully assume the user to have supplied the correct password, allowing an attacker to login.
     * <p>
     * N.B. 1: If by chance your randomly generate the actual original password, then this is fine too of course!
     * N.B. 2: As you by now, no doubt have seen, the elements in {@link Password}, {@link Salt} and {@link Hash} are all in the same order,
     * <t><t>meaning the first password with the first salt results in the first hash.
     * <t><t>When returning from this method, make sure your password guesses are in the same order.
     */
    public List<String> findCollidingPasswords() {

        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        ConcurrentHashMap<String, String> hashPassword = new ConcurrentHashMap<>();

        AtomicBoolean finished = new AtomicBoolean();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        executorService.submit(new RandomStringProducer(queue, finished));
        executorService.submit(new RandomStringProducer(queue, finished));

        executorService.submit(new RandomStringConsumer(queue, hashPassword, finished));
        executorService.submit(new RandomStringConsumer(queue, hashPassword, finished));
        executorService.submit(new RandomStringConsumer(queue, hashPassword, finished));
        executorService.submit(new RandomStringConsumer(queue, hashPassword, finished));

        executorService.shutdown();

        try {
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<String> passwords = new ArrayList<>();

        Hash.hashes.forEach(i -> passwords.add(hashPassword.get(i)));

        return passwords; // Temporary placeholder
    }
}

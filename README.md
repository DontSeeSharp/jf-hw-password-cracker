Java Fundamentals - Concurrency API Homework
===========

Description
----------

Imagine I own a web-store. 
I am a smart guy, so I do not store my passwords plaintext, rather I salt them first, then store the salt and the hash. 
However, I am not _that_ smart, and I use a very sad 16-bit hashing function. 

Tragedy strikes, and my full DB of salts and hashes leaks to you. 

Your assignment is to brute-force your own valid passwords that, using the same salt, result in the same hash as my original passwords. 

Of course you are also smart, so you will solve this using multiple processors, for faster cracking. 
To do this, you will design a system where producer threads will keep on generating random Strings to serve as inputs for the hashing function. 
Consumer threads will now continuously read these Strings, and test whether, using String + Salt, it results in the same hash that you want to break / find a collision for. 
When all hashes are broken, the system will shut down, as its work is now over. 
At the very end you will return a list of equally valid passwords (Note, passwords, not hashes, not salted passwords, just passwords). 

To score the maximum points: 
* Follow all requirements below
* Make the single test pass, it verifies if you indeed found hash collisions

Requirements
----------

1. Use a fixed nr of producer threads: 2 to be precise
2. Use a fixed nr of consumer threads: 4 of them
3. A producer's job is to keep producing, in a **Thread-Safe** way, **Random** inputs, ad infinitum, until asked to shutdown
4. A consumer's job is to keep taking in random inputs, checking if they produce a hash collision, and if so, record which input triggered the collision 
5. Do not manually manage your threads, recall Executors from previous lectures
6. Keep the concurrent collections, and the synchronisers in mind discussed this lecture, and use them appropriately
7. Absolutely do not use the following: any form of `Lock`, `synchronized`, `volatile` or any other form of creative low-level locking (e.g. using `Semaphore(1)`)
8. Unless really needed, try to avoid using the `Atomic\*` classes
9. Implement a clean shutdown mechanism, after all 10 passwords are cracked, *cleanly* signal all consumers and producers to end their jobs, then *cleanly* wait for the executor(s) to properly shut down
10. Use the `Salt` and `Hash` classes to find the data you need to break
11. Return a list of passwords, in the same order, so they correspond to the data in `Salt` and `Hash`
12. Do not add any new libraries, the ones in `pom.xml` will certainly suffice to solve this
13. Be **absolutely completely positively** Thread Safe in **all** your operations (and look up the definition in doubt)
14. Only store the very first valid password guess: if another consumer finds another valid hash, discard it
15. Make an efficient program, at least make sure your implementation is faster than a single-threaded implementation
16. Read up yourself on how to properly do consumer-producer design, and handle clean shutdowns, if needed. You are all University students, and do not need handholding for every single detail. "It was not covered in the lecture" is **not** a valid excuse

Lecture Code
------------

In the package `org.zeroturnaround.jf.lecture8` all code examples shown in class can be found. 
A lot (most?) of this code originates from the excellent read _Java Concurrency in Practice_ by _Brian Goetz_, read this book. 

Submitting your assignment
--------------------------

1. Run `./mvnw clean deploy`
2. Send your solution to *jf@zeroturnaround.com*

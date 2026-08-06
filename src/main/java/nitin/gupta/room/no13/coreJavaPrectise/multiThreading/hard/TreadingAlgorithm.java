package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author nitingupta2
 * There is a object state which is represented by two variables. How would
 * you write a high throughput non-blocking algorithm to update the state from multiple
 * threads?
 * <br>
 * ANS <BR>
 *     Non-blocking algorithms provide better throughput at low thread contention compared to the locking
 * counterpart. This can only be achieved in Java using CAS1
 *  (compare and swap) utilities provided in atomic
 * package. AtomicReference along with Immutable object can be used to write a non-blocking algorithm
 * maintaining a current state of Trade Volume.
 *
 * <ui>
 *     There are key points to be noted while writing non-blocking algorithm2
 *  are:<li>Immutability of TradeVolume as in below example is must to ensure proper initialization at it's assignment
 * time. Immutability is achieved by making all fields final and providing constructor initialization.</li>
 * <li> compareAndSet operation must be called repetitively in a while loop till the time it returns true.</li>
 * </ui>
 * This TradeVolume Class must be Immutable otherwise it may create Java Memory Model Problems
 * while using with Atomic Reference. All the fields must be final to guarantee the initialization
 * and assignment at the same time from other thread.
 */
public class TreadingAlgorithm {
    private static class TradeVolume {
        final long quantity;
        final long price;
        private TradeVolume(long quantity, long price) {
            this.quantity = quantity;
            this.price = price;
        }
    }
    private final AtomicReference<TradeVolume> tradeVol =
            new AtomicReference<>(new TradeVolume(100, 200));
    public long getQuantity() {
        return tradeVol.get().quantity;
    }
    public long getPrice() {
        return tradeVol.get().price;
    }

    /**
     * A non-blocking update method which updates the TradeVolume Object using AtomicReference.
     * This method is likely to perform better under multi-core environment with low thread contention.
     * @param quantity Quantity of the Trade
     * @param price Price of the Trade
     */
    public void update(long quantity, long price) {
        while (true) {
            TradeVolume oldValue = tradeVol.get();
            TradeVolume newValue = new TradeVolume(quantity+ oldValue.quantity,
                    price+ oldValue.price);
            if (tradeVol.compareAndSet(oldValue, newValue))
                return;
        }
    }

   // The update method can be written concisely using the updateAndGet() method introduced in Java 8
   public void update1(long quantity, long price) {
       tradeVol.updateAndGet(x ->
               new TradeVolume(quantity + x.quantity, price + x.price));
   }

    /**
     *     Atomically updates the current value with the results of applying the given function,
     *     returning the updated value. The function should be side-effect-free, since it may be
     *     re-applied when attempted updates fail due to contention among threads.
     */

}

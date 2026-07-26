# CopyOnWriteArrayList

## Internal Write Mechanism

Each write operation:

1. Locks (using an internal `ReentrantLock`)
2. Copies the existing array into a new, larger array
3. Applies the change to the new array
4. Swaps the internal reference to point to the new array

Reads never lock — they just read whatever array reference is currently set.

## Key Characteristics

| Aspect | Behavior |
|---|---|
| Thread safety | Fully thread-safe for concurrent reads/writes |
| Read performance | Fast, no locking, no blocking |
| Write performance | Slow — O(n) copy on every mutation |
| Iterator | Snapshot-based, never throws `ConcurrentModificationException` |
| Iterator mutation | `iterator.remove()`, `add()`, `set()` throw `UnsupportedOperationException` |
| Memory | Higher — each write allocates a new array |

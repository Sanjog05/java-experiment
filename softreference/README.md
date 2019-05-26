1. No strong (direct) reference to an object o
2. If GC sees there's not enough memory and if the last time the softReference object s is referenced was earlier than -XX:SoftRefLRUPolicyMSPerMB
3. GC can now call clear() on the softReference object s, which contains the referent object o
4. s.get() will return null after clear() has been called on s
5. GC will put the softReference object s in its ReferenceQueue if the queue was specified at softReference construction time.
6. GC determines which instances should be removed by removing objects from that queue mentioned above.
7. Only when there's no more strong, softReference, GC will call clear() on the weakReference object s
8. Only when there's no more strong, softReference, weakReference, GC will call clear() on the phantomReference object f


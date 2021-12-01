package com.guang.rxjava.frame.rxjava.connect.observable;

import com.guang.rxjava.frame.rxjava.disposable.FrameDisposable;
import com.guang.rxjava.frame.rxjava.observable.FrameObservableSource;
import com.guang.rxjava.frame.rxjava.observer.FrameObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author guangyong.deng
 * @date 2021-11-29 10:34
 */
public class FrameObservablePublish<T> extends FrameConnectableObservable<T> {

    /** The source observable. */
    final FrameObservableSource<T> source;

    /** Holds the current subscriber that is, will be or just was subscribed to the source observable. */
    final AtomicReference<FramePublishObserver<T>> current;

    final FrameObservableSource<T> onSubscribe;

    private FrameObservablePublish(FrameObservableSource<T> onSubscribe, FrameObservableSource<T> source,
                              final AtomicReference<FramePublishObserver<T>> current) {
        this.onSubscribe = onSubscribe;
        this.source = source;
        this.current = current;
    }

    /**
     * Creates a OperatorPublish instance to publish values of the given source observable.
     * @param <T> the source value type
     * @param source the source observable
     * @return the connectable observable
     */
    public static <T> FrameConnectableObservable<T> create(FrameObservableSource<T> source) {
        // the current connection to source needs to be shared between the operator and its onSubscribe call
        final AtomicReference<FramePublishObserver<T>> curr = new AtomicReference<>();
        FrameObservableSource<T> onSubscribe = new FramePublishSource<T>(curr);
        return new FrameObservablePublish<T>(onSubscribe, source, curr);
    }

    @Override
    public void connect(Consumer<? super FrameDisposable> connection) {

    }

    @Override
    protected void subscribeActual(FrameObserver<? super T> observer) {
        // 此时 onSubscribe: PublishSource
        // observer : LS
        // 注意此时没有消费元素的动作，只是将订阅者保存好
        // 消费的动作需要在 connect 之后发出
        onSubscribe.subscribe(observer);
    }


    static final class FramePublishObserver<T> implements FrameObserver<T>, FrameDisposable {

        /** Holds onto the current connected PublishObserver. */
        final AtomicReference<FramePublishObserver<T>> current;

        /** Indicates a terminated PublishObserver. */
        static final FrameInnerDisposable[] TERMINATED = new FrameInnerDisposable[0];

        /** Indicates an empty array of inner observers. */
        static final FrameInnerDisposable[] EMPTY = new FrameInnerDisposable[0];

        /** Tracks the subscribed observers. */
        final AtomicReference<FrameInnerDisposable<T>[]> observers;

        /**
         * Atomically changed from false to true by connect to make sure the
         * connection is only performed by one thread.
         */
        final AtomicBoolean shouldConnect;

        final AtomicReference<Disposable> upstream = new AtomicReference<>();

        FramePublishObserver(AtomicReference<FramePublishObserver<T>> current) {
            this.observers = new AtomicReference<FrameInnerDisposable<T>[]>(EMPTY);
            this.current = current;
            this.shouldConnect = new AtomicBoolean();
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public void onSubscribe(Disposable var1) {

        }

        @Override
        public void onNext(T var1) {

        }

        @Override
        public void onError(Throwable var1) {

        }

        @Override
        public void onComplete() {

        }

        /**
         * 1.将订阅者封装为 InnerDisposable
         * 2.将订阅者加入数组中，保存
         *
         * Atomically try adding a new InnerDisposable to this Observer or return false if this
         * Observer was terminated.
         * @param producer the producer to add
         * @return true if succeeded, false otherwise
         */
        boolean add(FrameInnerDisposable<T> producer) {
            // the state can change so we do a CAS loop to achieve atomicity
            for (;;) {
                // get the current producer array
                FrameInnerDisposable<T>[] c = observers.get();
                // if this subscriber-to-source reached a terminal state by receiving
                // an onError or onComplete, just refuse to add the new producer
                if (c == TERMINATED) {
                    return false;
                }
                // we perform a copy-on-write logic
                int len = c.length;
                @SuppressWarnings("unchecked")
                FrameInnerDisposable<T>[] u = new FrameInnerDisposable[len + 1];
                System.arraycopy(c, 0, u, 0, len);
                u[len] = producer;
                // try setting the observers array
                if (observers.compareAndSet(c, u)) {
                    return true;
                }
                // if failed, some other operation succeeded (another add, remove or termination)
                // so retry
            }
        }

        void remove( FrameInnerDisposable<T> producer) {
            // the state can change so we do a CAS loop to achieve atomicity
            for (;;) {
                // let's read the current observers array
                FrameInnerDisposable<T>[] c = observers.get();
                // if it is either empty or terminated, there is nothing to remove so we quit
                int len = c.length;
                if (len == 0) {
                    return;
                }
                // let's find the supplied producer in the array
                // although this is O(n), we don't expect too many child observers in general
                int j = -1;
                for (int i = 0; i < len; i++) {
                    if (c[i].equals(producer)) {
                        j = i;
                        break;
                    }
                }
                // we didn't find it so just quit
                if (j < 0) {
                    return;
                }
                // we do copy-on-write logic here
                FrameInnerDisposable<T>[] u;
                // we don't create a new empty array if producer was the single inhabitant
                // but rather reuse an empty array
                if (len == 1) {
                    u = EMPTY;
                } else {
                    // otherwise, create a new array one less in size
                    u = new  FrameInnerDisposable[len - 1];
                    // copy elements being before the given producer
                    System.arraycopy(c, 0, u, 0, j);
                    // copy elements being after the given producer
                    System.arraycopy(c, j + 1, u, j, len - j - 1);
                }
                // try setting this new array as
                if (observers.compareAndSet(c, u)) {
                    return;
                }
                // if we failed, it means something else happened
                // (a concurrent add/remove or termination), we need to retry
            }
        }
    }

    static final class FrameInnerDisposable<T> extends AtomicReference<Object> implements Disposable {

        private static final long serialVersionUID = -1100270633763673112L;

        /** The actual child subscriber. */
        final FrameObserver<? super T> child;

        FrameInnerDisposable(FrameObserver<? super T> child) {
            this.child = child;
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        void setParent(FramePublishObserver<T> p) {
            if (!compareAndSet(null, p)) {
                p.remove(this);
            }
        }
    }


    static final class FramePublishSource<T> implements FrameObservableSource<T> {

        private final AtomicReference<FramePublishObserver<T>> curr;

        FramePublishSource(AtomicReference<FramePublishObserver<T>> curr) {
            this.curr = curr;
        }

        @Override
        public void subscribe(FrameObserver<? super T> child) {
            // create the backpressure-managing producer for this child
            FrameInnerDisposable<T> inner = new FrameInnerDisposable<T>(child);

            // 暂时不清楚，作用
           // child.onSubscribe(inner);

            // concurrent connection/disconnection may change the state,
            // we loop to be atomic while the child subscribes
            for (;;) {
                // get the current subscriber-to-source
                FramePublishObserver<T> r = curr.get();
                // if there isn't one or it is disposed
                if (r == null || r.isDisposed()) {
                    // create a new subscriber to source
                    FramePublishObserver<T> u = new FramePublishObserver<T>(curr);
                    // let's try setting it as the current subscriber-to-source
                    if (!curr.compareAndSet(r, u)) {
                        // didn't work, maybe someone else did it or the current subscriber
                        // to source has just finished
                        continue;
                    }
                    // we won, let's use it going onwards
                    r = u;
                }

                /*
                 * Try adding it to the current subscriber-to-source, add is atomic in respect
                 * to other adds and the termination of the subscriber-to-source.
                 */
                if (r.add(inner)) {
                    inner.setParent(r);
                    break; // NOPMD
                }
                /*
                 * The current PublishObserver has been terminated, try with a newer one.
                 */
                /*
                 * Note: although technically correct, concurrent disconnects can cause
                 * unexpected behavior such as child observers never receiving anything
                 * (unless connected again). An alternative approach, similar to
                 * PublishSubject would be to immediately terminate such child
                 * observers as well:
                 *
                 * Object term = r.terminalEvent;
                 * if (r.nl.isCompleted(term)) {
                 *     child.onComplete();
                 * } else {
                 *     child.onError(r.nl.getError(term));
                 * }
                 * return;
                 *
                 * The original concurrent behavior was non-deterministic in this regard as well.
                 * Allowing this behavior, however, may introduce another unexpected behavior:
                 * after disconnecting a previous connection, one might not be able to prepare
                 * a new connection right after a previous termination by subscribing new child
                 * observers asynchronously before a connect call.
                 */
            }
        }
    }
}

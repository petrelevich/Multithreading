package ru.pingpong.open2;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


/**
 * @author sergey
 */
@Warmup(iterations = 1)
@State(value = Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ComparativeTest {


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ComparativeTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void pingPongSynch() {
        new PingPongSynch(false);
    }

    @Benchmark
    public void pingPongAtomicTest() {
        new PingPongAtomic(false);
    }

    @Benchmark
    public void pingPongPark() {
        new PingPongPark(false);
    }

    @Benchmark
    public void pingPongLock() {
        new PingPongLock(false);
    }

}

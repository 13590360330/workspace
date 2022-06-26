package webui;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class test {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        config.setInteger(RestOptions.PORT,9998);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config);
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        env.setParallelism(1);
        DataStream<Long> someIntegers = env.generateSequence(0, 4);

        IterativeStream<Long> iteration = someIntegers.iterate();

        DataStream<Long> minusOne = iteration.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                System.out.println("map:" + value);
                return value - 1;
            }
        });

        DataStream<Long> stillGreaterThanZero = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                System.out.println("filter:" + value);
                return (value > 0);
            }
        });
        iteration.closeWith(stillGreaterThanZero);
        env.execute("123");
    }
}


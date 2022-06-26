package webui;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WebUIEnv {

    private static StreamExecutionEnvironment env;

    public static StreamExecutionEnvironment getEnv() {
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("windows")) {
            if (env == null) {
                Configuration config = new Configuration();
                config.setInteger(RestOptions.PORT, 9998);
                env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(config);
            }
            return env;
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            if (env == null) {
                env = StreamExecutionEnvironment.getExecutionEnvironment();
            }
            return env;
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
            throw new RuntimeException("暂时只支持window和linux系统");
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = WebUIEnv.getEnv();
        env.setParallelism(1);
        DataStream<Long> someIntegers = env.generateSequence(0, 4);

        IterativeStream<Long> iteration = someIntegers.iterate();

        DataStream<Long> minusOne = iteration.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                System.out.println("map:" +value);
                return value - 1 ;
            }
        });

        DataStream<Long> stillGreaterThanZero = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                System.out.println("filter:" +value);
                return (value > 0);
            }
        });
        iteration.closeWith(stillGreaterThanZero);
        env.execute("123");
    }
}

package com.atguigu.streamx;

import com.streamxhub.streamx.flink.core.StreamEnvConfig;
import com.streamxhub.streamx.flink.core.java.function.SQLFromFunction;
import com.streamxhub.streamx.flink.core.java.sink.JdbcSink;
import com.streamxhub.streamx.flink.core.java.source.KafkaSource;
import com.streamxhub.streamx.flink.core.scala.StreamingContext;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;


/**
 * @Author lizhenchao@atguigu.cn
 * @Date 2022/2/9 18:51
 */
public class StreamxMysqlDemo {
    public static void main(String[] args) {
        
        // 配置
        StreamEnvConfig javaConfig = new StreamEnvConfig(args, null);
        // 创建StreamingContext对象, 是一个核心类
        StreamingContext ctx = new StreamingContext(javaConfig);
        
        SingleOutputStreamOperator<WaterSensor> source = new KafkaSource<String>(ctx)
            .alias("kafka1")
            .getDataStream()
            .map(record -> {
                String[] data = record.value().split(",");
                return new WaterSensor(data[0], Long.valueOf(data[1]), Integer.valueOf(data[2]));
            });
        
        new JdbcSink<WaterSensor>(ctx)
            .sql(new SQLFromFunction<WaterSensor>() {
                // 抽取sql语句
                @Override
                public String from(WaterSensor ws) {
                    return String.format("insert into sensor(id, ts, vc)values('%s', %d, %d)",
                                         ws.getId(),
                                         ws.getTs(),
                                         ws.getVc()
                    );
                }
            })
            .sink(source);
        
        
        ctx.start();
        
    }
}

package com.atguigu.streamx;

import com.streamxhub.streamx.flink.core.StreamEnvConfig;
import com.streamxhub.streamx.flink.core.java.source.KafkaSource;
import com.streamxhub.streamx.flink.core.scala.StreamingContext;
import com.streamxhub.streamx.flink.core.scala.source.KafkaRecord;

/**
 * @Author lizhenchao@atguigu.cn
 * @Date 2022/2/9 18:51
 */
public class StreamxKafkaDemo {
    public static void main(String[] args) {
        
        // 配置
        StreamEnvConfig javaConfig = new StreamEnvConfig(args, null);
        // 创建StreamingContext对象, 是一个核心类
        StreamingContext ctx = new StreamingContext(javaConfig);
        /*// 消费kafka数据
        new KafkaSource<String>(ctx)
            .getDataStream()
            .map(new MapFunction<KafkaRecord<String>, String>() {
                @Override
                public String map(KafkaRecord<String> value) throws Exception {
                    return value.value();
                }
            })
            .print();*/
    
        /*new KafkaSource<String>(ctx)
            .topic("s1") // 消费一个topic
            .getDataStream()
            .map(record -> record.value())
            .print("one");
        
        new KafkaSource<String>(ctx)
            .topic("s1","s2") // 消费一组topic
            .getDataStream()
            .map(record -> record.value())
            .print("two");*/
    
        new KafkaSource<String>(ctx)
            .alias("kafka1") // 指定要消费的Kafka集群
            .getDataStream()
            .map(KafkaRecord::value)
            .print("one");
        new KafkaSource<String>(ctx)
            .alias("kafka2") // 指定要消费的Kafka集群
            .getDataStream()
            .map(KafkaRecord::value)
            .print("two");
        
        // 启动任务
        ctx.start();
        
    }
}

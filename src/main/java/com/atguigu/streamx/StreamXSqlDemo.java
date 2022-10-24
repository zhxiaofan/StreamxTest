package com.atguigu.streamx;

import com.streamxhub.streamx.flink.core.TableContext;
import com.streamxhub.streamx.flink.core.TableEnvConfig;

/**
 * @Author lzc
 * @Date 2022/3/18 20:00
 */
public class StreamXSqlDemo {
    public static void main(String[] args) {
        TableEnvConfig tableEnvConfig = new TableEnvConfig(args, null);
        for (String arg : args) {
            System.out.println(arg);
        }

        TableContext ctx = new TableContext(tableEnvConfig);

        ctx.sql("first");
    }
}

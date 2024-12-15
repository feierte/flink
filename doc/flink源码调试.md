



启动master StandaloneSessionClusterEntrypoint



```java
-Dlog.file=./logs/flink-jobmanager-1.local.log 
-Dlog4j.configuration=./conf/log4j.properties 
-Dlog4j.configurationFile=./conf/log4j.properties 
-Dlogback.configurationFile=./conf/logback.xml
```



启动 taskmanager TaskManagerRunner



```java
-Dlog.file=./logs/flink-taskmanager-1.local.log 
-Dlog4j.configuration=./conf/log4j.properties 
-Dlog4j.configurationFile=./conf/log4j.properties 
-Dlogback.configurationFile=./conf/logback.xml
```





```java
taskmanager.resource-id: tm_hitao
taskmanager.cpu.cores: 1
taskmanager.memory.task.heap.size: 512m
taskmanager.memory.managed.size: 512m
taskmanager.memory.network.min: 128m
taskmanager.memory.network.max: 128m
taskmanager.memory.task.off-heap.size: 0m
taskmanager.memory.framework.heap.size: 256m
taskmanager.memory.framework.off-heap.size: 128m
taskmanager.memory.jvm-metaspace.size: 128m
taskmanager.memory.jvm-overhead.max: 128m
taskmanager.memory.jvm-overhead.min: 128m
```







CliFronted 



```java
-Dlog.file=./logs/flink-hunter-client-hunter.log 
-Dlog4j.configuration=./conf/log4j-cli.properties 
-Dlog4j.configurationFile=./conf/log4j-cli.properties 
-Dlogback.configurationFile=./conf/logback.xml 
```



```java
run -c org.apache.flink.streaming.examples.hitao.WorkCount ./demo.jar
```



```java
FLINK_CONF_DIR=./conf
```


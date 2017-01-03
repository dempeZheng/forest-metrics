namespace java com.zhizus.forest.metrics.gen

struct MetaConfig{
    1:optional string serviceName;
    2:optional string ip;
    3:optional string roomId;
    4:optional string version;
    5:optional string type;
}

struct MetaReq{
    1:optional string configId;
    2:string uri;
    3:i32 count;
    4:i64 time;
    5:i32 maxTime;
    6:i32 minTime;
    7:list<i32> codes;
}
struct Ack{
    1:i16 code;
    2:optional string configId;
}

service MetricService{
    Ack sendMeta(1:MetaReq metaReq);
    Ack getConfigId(1:MetaConfig config);
    bool ping();
}
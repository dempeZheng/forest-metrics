namespace java com.zhizus.forest.metrics.gen
struct Meta{
    1:string uri;
    2:i32 code;
    3:i32 count;
    4:i64 time;
    5:list<i32> codeArr;
}
struct Ack{
    1:optional i32 configId;
}


service Metrics{
    Ack sendMeta(1:Meta meta)
    bool ping()
}
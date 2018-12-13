# cloud-func
云函数



> 啥意思

通过socket连接的方式调用java的方法，实现远程调用，是不是还挺有意思的！

> 如何使用

```java
// 先写一个供远程调用的函数，然后使用注解标识它，比如这样
@Register("fun")
public class RemoteFunc {
    @Register
    public String add(String a, String b) {
        return a + b;
    }
}
// 实例化云函数服务，然后在云函数服务类中注册这个函数
public class ServerTest {
    public static void main(String[] args) {
        RemoteFunc remoteFunc = new RemoteFunc();
        CftpServer cftpServer = new CftpServer();
        // 注册
        cftpServer.getCftpContext().registerObj(remoteFunc);
        // 启动云函数
        cftpServer.start();
    }
}
```

> 怎么远程调用呢

1. 每个类会有自己的groupName与methodName标识，调用遵循

```bash
groupName methodName param1 param2
```

2. 测试一下

```bash
# 启动云函数服务 见单元测试ServerTest.java
# telnet连接云函数服务 默认端口8080
telnet localhost 8080
# 输入指令
cftp help
```


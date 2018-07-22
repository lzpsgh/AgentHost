## 简介

AgentHost 是一款后台主机仿真工具。监听指定端口，使用 TCP 协议接收前置系统转发的 XML 报文，通过报文中的唯一标志码匹配对应的响应报文并返回给前置系统。

## 功能

1. 跨平台

+ 基于 Java 开发，支持 Windows 与 Linux 系统，同时提供的相关启动脚本方便自行修改。

+ 目前已在 Win7、Win10、Redhat6上测试过，shell 脚本与 bat 脚本均可用。后续可能支持 Solaris 与 AIX。

2. 响应报文智能加载机制

+ 响应报文直接加载到内存中，当响应报文有新增或修改时，通过WatchTask监控文件改动，同时在返回响应报文前预加载到内存中待命。

+ 在性能测试场景下表现有待测试

3. 自动计算报文头长度字节数，因此无论是 GBK 编码或 UTF-8 编码都通用，且无需在响应报文中手动计算添加

4. 配置项丰富，包括端口 port、超时断开时间 timeOut、响应延迟时间 delay、响应报文路径 rspPath 等

## 使用

1. 准备好主机响应报文，文件后缀用xml，统一放到rsp目录下
2. 将 config.ini 配置文件、script 中的启动脚本、、含有响应报文的rsp文件夹以及 AgentHost.jar 置于同一目录下
3. 自行修改config配置文件，执行启动脚本即可

## 其他

1. 响应报文单双匹配码，二期实现

2. 传输协议：目前支持 TCP，后续可能支持 HTTP，HTTPS。

3. 报文格式：目前支持 XML，后续可能支持银联ISO8583，结构体，分隔符，JSON，WebService。

## FAQ

1. 直接下载后导入到IDE中会提示找不到dom4j的库，因为我没有上传dom4j-1.6.1.jar。自行下载一个导入即可
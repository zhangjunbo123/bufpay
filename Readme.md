> 编译打包命令：mvn clean install -Dmaven.test.skip=true
> 上传到Linux： rz
> 启动项目：nohup java -jar bufpay-0.0.1-SNAPSHOT.jar &
> 关闭项目进程：ps -ef|grep bufpay kill port
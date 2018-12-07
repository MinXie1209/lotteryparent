> 本项目是一个抽奖系统
1. 对服务业务进行拆分，以分布式部署提高连接数
2. 具体部署部署步骤请看[docker部署抽奖系统](https://github.com/xyj1209/docker-compose-lottery)

> 打包前，需要注意的几个地方
1. 本项目划分为三个模块，lottery-core(抽奖)、lottery-login(登录)、lottery-exposer(暴露接口)
2. lottery-core需要配置奖池，在application.yml下有奖池的参数配置，目前是划分为特等奖、一等奖、二等奖、三等奖几个奖项，对应po下面的Lottery类，奖项有区别的可以重写这个类。

> 此项目是Maven工程，打包的过程只需要一个命令
1. 在根目录下打开命令窗口，输入**mvn clean package**

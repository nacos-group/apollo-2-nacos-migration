# apollo-2-nacos-migration
该工程为Apollo迁移Nacos的迁移脚本
脚本使用步骤：
* 在Apollo控制台导出需要迁移的配置，并且使用配套的迁移脚本将配置内容发布至Nacos侧。
![image](https://user-images.githubusercontent.com/58767027/197932429-8f23a82a-8d59-4aae-ae06-bfbcd489c155.png)

* 将导出的配置文件放入迁移脚本同目录下，使用迁移脚本重新发布至MSE。（迁移脚本将读取配置文件中的配置内容，并重新发布至MSE Nacos）

* 在发布之前，请使用迁移脚本将Apollo导出的配置发布至MSE Nacos。
![image](https://user-images.githubusercontent.com/58767027/197932257-4f32cbe4-6e36-486a-b180-030228e4bae0.png)

其中，Apollo和Nacos的数据结构的推荐对应关系如下：
Apollo数据结构	Nacos数据结构
环境	Namespace
集群	group
Namespace	dataId
在发布之前，请按照上述Apollo和Nacos数据结构的对应关系，将本地的配置的存储结构布局为：
![image](https://user-images.githubusercontent.com/58767027/197932505-49a27774-ea79-4c63-8a47-f78718b04f8c.png)

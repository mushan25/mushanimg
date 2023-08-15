# 木杉图床（Mushan Image Hosting）

## 简介

木杉图床是一个基于Cola架构和Spring Boot的Web应用，用于快速上传和管理图片资源。它集成了Nacos作为配置中心，Redis用于缓存支持，Minio作为对象存储，JWT实现身份验证，以及NGINX作为反向代理。此外，项目还使用gRPC进行服务间通信，便于扩展和异步任务处理。技术栈还包括Arthas、Sentinel、RocketMQ、MyBatis、MyBatis Plus和Druid连接池，为项目提供更多功能和性能支持。为了保障图片内容的安全和合规性，我们还接入了百度审核API，对上传的图片进行内容审核。

## Cola架构

Cola是一种基于领域驱动设计（DDD）和面向对象设计（OOD）思想的架构模式，它鼓励代码组织为独立的领域模块，每个模块都有自己的领域模型、领域服务和数据访问层。Cola架构以其清晰的模块划分、高内聚低耦合的特性，适用于复杂业务场景下的系统开发。在木杉图床中，我们充分运用Cola架构，以提高项目的可维护性和扩展性，同时降低代码复杂度。

## 项目导航
- 快速体验地址：[MuShan图床](https://mushanimg.top/)
- 前端项目地址：[mushanimg-web](https://github.com/mushan25/mushanimg-web)

## 功能特点

- 快速上传图片并获取图片链接。
- 图片资源管理，支持查看、删除图片。
- 身份验证和访问控制，保护图片资源安全。
- 配置中心支持，方便项目配置管理。
- 使用缓存加速图片加载，提高访问性能。
- 扩展性强，支持gRPC接口，可扩展更多功能。

## 技术选型

- Spring Boot：提供快速开发和易于扩展的框架支持。
- Nacos：用于集中管理配置信息，方便动态调整配置参数。
- Redis：作为缓存数据库，加速图片访问，减轻后端负载。
- Minio：作为对象存储，存放上传的图片资源。
- JWT：实现用户身份验证和授权，保护图片资源安全。
- NGINX：作为反向代理服务器，提供负载均衡和缓存支持。
- gRPC：用于服务间通信，支持异步任务处理和扩展接口。
- Arthas：开发者工具，用于在线诊断和调试。
- Sentinel：流量控制和熔断降级，保护服务稳定性。
- RocketMQ：可靠消息队列，用于异步消息处理，主要用于异步审核图片。
- MyBatis和MyBatis Plus：持久层框架，用于数据库操作。
- Druid连接池：高性能数据库连接池，提供连接管理和监控功能。

## 项目演示
![2fb8664d589903b71364e8e5548f87d.png.jpg](https://m.mushanimg.top/img/admin/2023/07/28/fa769627af97a71ff7002560e69363e0.png)
![6677e251e680bfc0105942dfd089c38.png.jpg](https://m.mushanimg.top/img/admin/2023/07/28/1ae24107104c9a846e7bebfa1041d51d.png)
![ce8f614589955b37c0e595886457b33.png.jpg](https://m.mushanimg.top/img/admin/2023/07/28/11fc68e67ba31db1e876ee6ced978df0.png)
![77280137c610b759745b444f5eabba5.png.jpg](https://m.mushanimg.top/img/admin/2023/08/15/ba4d531e6044d05b624168e260430500.png)

## 未来计划

木杉图床有着许多激动人心的未来计划，计划逐步增强功能并扩展使用范围。一些可能的未来改进和功能包括：
- 添加共享图片社区功能，允许用户创建相册、分享图片，并进行交流互动。
- 引入更多的存储后端选项，比如Amazon S3、Google Cloud Storage等，提供更多选择。
- 增强安全性，添加更多身份验证方式，并优化访问控制策略。
- 优化性能，包括图片上传和加载速度的提升，以及更高并发的支持。
- 提供RESTful API和GraphQL接口，方便外部应用和服务的集成。

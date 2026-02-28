# 旅小秘系统（Trig-Genie）需求分析与详细设计文档

## 1. 引言

### 1.1 背景
旅小秘系统（Trig-Genie）旨在为用户提供一站式智能旅行解决方案，涵盖目的地探索、行程规划、社交互动、智能助手及客户服务等核心场景，解决用户行前攻略繁琐、行中规划不便、行后分享困难的痛点。

### 1.2 目标
构建一个高并发、高可用、体验流畅的旅行服务平台，依托现有微服务架构（Spring Cloud Alibaba）进行功能扩展，集成 AI 能力与社交互动特性。

---

## 2. 技术架构与框架扩展

基于现有框架（Spring Boot + Spring Cloud Alibaba + Mybatis Plus + MySQL + Redis），根据业务需求进行如下扩展：

### 2.1 核心框架扩展
1.  **全文检索引擎 (Elasticsearch)**
    *   **用途**：用于目的地、攻略、游记的高性能搜索与聚合。
    *   **理由**：MySQL 模糊查询在大数据量下性能不足，ES 支持分词与相关性排序。
2.  **即时通讯 (WebSocket / Netty)**
    *   **用途**：用于“客服与帮助中心”的在线咨询、“互动社交”的消息通知。
    *   **方案**：Spring Boot WebSocket 或 Netty + Redis (Session共享)。
3.  **对象存储 (MinIO / OSS)**
    *   **用途**：存储攻略图片、游记视频、用户头像等非结构化数据。
    *   **理由**：分布式存储，减轻应用服务器压力，支持CDN加速。
4.  **AI 大模型集成 (Spring AI / LangChain4j)**
    *   **用途**：驱动“旅行助手”进行智能问答、行程推荐。
    *   **方案**：对接 OpenAI/通义千问等大模型 API。
5.  **定时任务 (XXL-JOB)**
    *   **用途**：定时更新热门榜单、清理过期行程、发送提醒。

### 2.2 数据存储设计原则
*   **MySQL**: 核心业务数据（用户、行程、订单、基础攻略）。
*   **Redis**: 缓存（热点攻略、用户信息）、计数器（点赞数、浏览量）、分布式锁。
*   **Elasticsearch**: 搜索索引（攻略标题、内容、标签）。
*   **MongoDB (可选)**: 复杂的社交评论嵌套结构、聊天记录（海量写入）。

---

## 3. 功能模块详细设计

### 3.1 目的地与攻略模块 (Destination & Strategy)

#### 3.1.1 功能描述
*   **目的地管理**：维护全球国家、城市、景点层级数据。
*   **攻略管理**：官方/UGC 攻略发布、审核、分类（美食、住宿、交通）。
*   **搜索与推荐**：基于标签和热度的推荐算法。

#### 3.1.2 数据库设计 (MySQL)

**表名：`tg_destination` (目的地)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| name | varchar | 名称（如：杭州） |
| parent_id | bigint | 父级ID（如：浙江省） |
| type | tinyint | 类型（1:国家 2:省份 3:城市 4:景点） |
| cover_url | varchar | 封面图 |
| desc | text | 简介 |

**表名：`tg_strategy` (攻略)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| dest_id | bigint | 关联目的地ID |
| user_id | bigint | 作者ID |
| title | varchar | 标题 |
| content | longtext | 内容（富文本/Markdown） |
| category | tinyint | 分类（1:美食 2:景点 3:路线） |
| status | tinyint | 状态（0:草稿 1:发布 2:下架） |
| view_count | int | 浏览量 |

#### 3.1.3 关键 API
*   `GET /tg/strategy/search?keyword={kw}`: 搜索攻略
*   `GET /tg/destination/{id}/strategies`: 获取某目的地下攻略列表

---

### 3.2 行程规划模块 (Trip Planning)

#### 3.2.1 功能描述
*   **智能规划**：用户输入天数、偏好，系统生成初步行程。
*   **行程编辑**：拖拽调整每日景点顺序、添加备注。
*   **行程分享**：生成分享链接或海报，允许他人复制行程。

#### 3.2.2 数据库设计

**表名：`tg_trip_plan` (行程单)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| user_id | bigint | 用户ID |
| title | varchar | 行程标题 |
| start_date | date | 开始日期 |
| end_date | date | 结束日期 |
| day_count | int | 天数 |
| privacy | tinyint | 隐私（0:公开 1:私密） |

**表名：`tg_trip_node` (行程节点)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| plan_id | bigint | 行程ID |
| day_index | int | 第几天（1, 2...） |
| sort_order | int | 当日排序 |
| dest_id | bigint | 关联地点ID |
| memo | varchar | 备注 |
| start_time | time | 建议开始时间 |

#### 3.2.3 关键 API
*   `POST /trip/generate`: AI 自动生成行程建议
*   `PUT /trip/{id}/nodes`: 批量更新行程节点（排序/增删）

---

### 3.3 互动社交模块 (Interaction & Social)

#### 3.3.1 功能描述
*   **游记分享**：图片/视频流形式分享旅行见闻（类似小红书）。
*   **互动**：点赞、收藏、评论、关注用户。
*   **圈子**：特定主题的讨论组（如“穷游党”、“自驾圈”）。

#### 3.3.2 数据库设计

**表名：`tg_travel_log` (游记/动态)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| user_id | bigint | 发布者 |
| content | varchar | 文本内容 |
| media_urls | json | 图片/视频地址列表 |
| location | varchar | 定位信息 |

**表名：`tg_interaction` (互动记录)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| target_id | bigint | 目标ID（攻略/游记/评论） |
| target_type | tinyint | 类型 |
| user_id | bigint | 操作用户 |
| action | tinyint | 动作（1:点赞 2:收藏 3:关注） |

#### 3.3.3 关键 API
*   `POST /social/feed`: 发布动态
*   `POST /social/action`: 点赞/收藏/关注

---

### 3.4 旅行助手模块 (Travel Assistant)

#### 3.4.1 功能描述
*   **AI 问答**：基于大模型的实时问答（如“杭州明天穿什么？”“推荐西湖附近美食”）。
*   **工具箱**：汇率换算、天气查询、翻译助手。

#### 3.4.2 实现方案
*   **后端**：Spring AI 调用 LLM 接口，预置 Prompt 模板（Role: 专业导游）。
*   **前端**：流式输出（SSE）展示 AI 回答。

#### 3.4.3 关键 API
*   `POST /assistant/chat`: 发送问题，返回流式答案
*   `GET /assistant/weather?city={city}`: 查询天气

---

### 3.5 客服与帮助中心 (Customer Service)

#### 3.5.1 功能描述
*   **常见问题 (FAQ)**：自助查询。
*   **工单系统**：复杂问题提交工单。
*   **在线客服**：实时 IM 沟通。

#### 3.5.2 数据库设计

**表名：`tg_faq` (常见问题)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| question | varchar | 问题 |
| answer | text | 回答 |
| category | varchar | 分类 |

**表名：`tg_ticket` (工单)**
| 字段名 | 类型 | 描述 |
| :--- | :--- | :--- |
| id | bigint | 主键 |
| user_id | bigint | 用户 |
| content | text | 问题描述 |
| status | tinyint | 状态（0:待处理 1:处理中 2:已解决） |

---

## 4. 模块依赖规划 (Maven)

建议将 `trig-genie` 作为一个业务聚合层，内部包含多个子模块（微服务化）或作为一个单体大服务（模块化）。鉴于项目规模，若初期流量不大，推荐 **单体大服务 + 逻辑分包** 模式，减少运维成本。

**推荐 `trig-genie` 内部包结构：**
```
com.east.sea.genie
├── strategy    // 攻略模块
├── trip        // 行程模块
├── social      // 社交模块
├── assistant   // 助手模块
└── support     // 客服模块
```

**Maven 依赖扩展：**
```xml
<!-- 搜索 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
<!-- 消息/IM -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
<!-- 对象存储 (以 MinIO 为例) -->
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
</dependency>
<!-- AI 支持 (示例) -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

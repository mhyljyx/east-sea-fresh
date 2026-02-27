---
## 一、总体目标

1. **一致性优先**：AI 生成的代码风格、命名、结构必须与 QOrder 现有代码保持一致
2. **可维护性优先**：可读性 > 炫技；明确 > 晦涩；稳定 > 复杂
3. **工程化思维**：默认代码用于生产系统，而非 Demo
4. **最小侵入原则**：在已有架构下补充，而不是推倒重来
5. **安全第一**：默认考虑权限、校验、异常、边界条件
6. 思考前先同步一下本地文件


---

## 二、技术栈约束（AI 必须遵守）

### 后端技术栈

- **语言**：Java 8+
- **框架**：Spring Boot
- **ORM**：MyBatis-Plus
- **数据库**：MySQL8/kingbaseV8R6
- **缓存**：Redis
- **鉴权**：JWT
- **构建工具**：Maven

### 禁止行为

- ❌ 引入与现有架构冲突的框架（如 JPA、Hibernate）
- ❌ 随意升级 JDK 大版本
- ❌ 引入“看起来高级但无实际收益”的新技术

---

## 三、代码风格规范

### 3.1 命名规范

#### 包名

- 全小写,按业务域拆分,基础包名controller,service,service/impl,pojo,pojo/entity,pojo/vo,pojo/dto,pojo/response,mapper
- 解释：
- ```
  entity（数据库实体）：和数据库表一一对应
  dto（数据传输对象）：服务层之间传输数据
  vo（视图对象）：返回给前端的数据结构
  response（统一返回结构）
  ```

- 示例：

  ```
  com.xinran.pam
  com.xinran.pam.service
  com.xinran.pam.alarm
  ```

#### 类名

- 使用 **模块缩写+业务 + 职责** 命名
- 示例：
    - `AcAccessService`
    - `VisDeviceApiService`
    - `AcDoorStatusEnum`
    - `CmUserEntity`
    - `CmUserQueryDTO`
    - `CmUserVO`

#### 方法名

- 动词开头，表达“做什么”
- 禁止模糊动词：`do`, `handle`, `process`
- 推荐示例：
    - `addAlarmHost`
    - `delAlarmHost`
    - `updateAlarmHost`
    - `getAlarmHost`

#### 变量名

- 语义明确，禁止缩写
- ❌ `cnt`, `tmp`, `obj`
- ✅ `orderCount`, `orderEntity`

---

### 3.2 注释规范

#### 类注释（必写）

```java
/**
 * 订单创建服务
 *
 * 负责订单创建、校验及初始化逻辑
 * @author tztang
 * 日期
 */
```

#### 方法注释（对外方法必写）

```java
/**
 * 创建订单
 *
 * @param command 创建订单参数
 * @return 订单ID
 */
```

#### 行内注释

```
//创建订单
```

- 只解释 **“为什么这么做”**
- 不解释显而易见的代码

### 3.3 代码书写规范
```
1.每个大方法之间都要空一行。
2.每个对象、接口、类、方法、参数首尾各要空一行。
3.@RequestMapping要提取接口公共部分。
4.各层之间的引用在没有错误的情况下都用@Resource。
5.xxxMapper.xml的sql语句要有以下规范,需要使用标签,入参必须先判断是否为空
 - 示例
```
    <select id="selectPermsByUserId" resultType="java.lang.String">
        SELECT
            DISTINCT m.perms
        FROM sys_menu m
        LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id
        LEFT JOIN sys_role r ON rm.role_id = r.id
        LEFT JOIN sys_user_role ur ON r.id = ur.role_id
        <where>
            m.is_del = '0'
            AND r.is_del = '0'
            AND m.perms IS NOT NULL
            AND m.perms != ''
            <if test="userId != null and userId != ''">
                AND ur.user_id = #{userId}
            </if>
        </where>
    </select>
```
6.xxxDTO对象要加上@ApiModelProperty注解,描述字段的含义，加了这个注解后可以不需要注释
 - 示例
```
    @ApiModelProperty(value = "当前页码，默认值为1", example = "1L")
    private Long pageIndex = 1L;
```
7.xxxVO对象要加上@ApiModelProperty注解,描述字段的含义，加了这个注解后可以不需要注释。
 - 示例
```
    @ApiModelProperty(value = "当前页码，默认值为1", example = "1L")
    private Long pageIndex = 1L;
```
8.xxxVO用于新增或者修改时,根据业务参数使用@EditType注解
 - 示例
 - 新增时
```
    @EditType(EditType.INSERT)
    private String username;
```
 - 修改时
```
    @EditType(EditType.UPDATE)
    private String username;
```
 - 都要时
```
    @EditType({EditType.INSERT, EditType.UPDATE})
    private String username;
```

9.注解首先更具功能优先排序,其次根据长度排序

---

## 四、分层与职责边界

### 4.1 标准分层

```
Controller
   ↓
Service
   ↓
Entity
   ↓
Mapper
```

### 4.2 各层职责

#### Controller

- 只做：
    - 参数接收
    - 参数校验
    - 权限校验
    - 调用 Service
- ❌ 禁止写业务逻辑

#### Service

- 编排业务流程
- 事务控制
- 调用多个

#### Entity

- 核心业务规则
- 状态流转校验
- 不感知 HTTP、JSON

#### Mapper

- 只做数据库操作
- 禁止拼装业务对象

---

## 五、DTO / Entity / VO 使用规范

### 5.1 对象职责

| 类型   | 用途       |
| ------ | ---------- |
| DTO | 接口入参   |
| Entity | 数据库映射 |
| VO   | 接口出参   |

### 5.2 强制规则

- ❌ Entity 不允许直接作为 Controller 出参
- ❌ DTO/View不允许直接落库
- ✅ 明确对象转换层（Converter）

---

## 六、异常与返回规范

### 6.1 异常处理

- 使用 **业务异常 + 错误码**
- 禁止直接 `throw RuntimeException`

```java
throw new CustomException(OrderErrorCode.ORDER_STATUS_INVALID);
```

### 6.2 错误码规范

- 模块级前缀
- 示例：
    - `QO-ORDER-001` 订单不存在
    - `QO-ORDER-002` 订单状态非法

---

## 七、数据库与 SQL 规范

### 表设计

- 必须包含：
    - `id`
    - `create_time`
    - `update_time`
    - `is_del`

### SQL 规范

- 禁止 `SELECT *`
- 明确字段
- 大批量操作必须分页

---

## 八、AI 编码行为约束（核心）

AI 在 QOrder 项目中 **必须遵守以下行为准则**：

1. **先理解业务，再写代码**
2. **不确定时，优先给设计建议，而不是直接拍代码**
3. **新增代码前，优先复用已有结构**
4. **任何“看起来很高级”的设计，必须说明必要性**
5. **默认写单元可测试的代码**

### AI 禁止行为

- ❌ 臆测数据库结构
- ❌ 编造不存在的公共工具类
- ❌ 擅自重构用户未要求的模块

---

## 九、示例：标准 Service 模板

```java
public interface AlarmHostService extends IService<AlarmHostEntity> {
    public Long createOrder(CreateOrderCommand command);
}

@Service
public class AlarmHostServiceImpl extends ServiceImpl<AlarmHostMapper, AlarmHostEntity> implements AlarmHostService  {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addAlarmHost(AlarmHostAddParams params) {
        AlarmHostEntity order = AlarmHostEntity();
        orderMapper.insert(order);
        BeanUtil.copyProperties(params, alarmHost);
        this.baseMapper.insert(alarmHost);
        return Result.success();
    }
    
}
```

---

## 十、适用说明

- 本规范适用于：
    - AI 生成代码
    - AI 重构代码
    - AI 代码评审
- 当规范与实际项目冲突时：
  **以 现有代码风格为最高优先级**


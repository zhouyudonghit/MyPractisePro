#技能名称
ai sse 开发
#技能描述
ai sse 开发，开发一个流式对话demo

#步骤
1、在AiSSETestActivity中，实现一个流式对话demo，实现一个流式对话demo；
2、整个流失对话基于SSE协议，采用智谱大模型的SSE接口，https://open.bigmodel.cn/api/paas/v4/chat/completions
3、可以使用OKHttp来解析SSE的数据，工程上标准做法：**OkHttp + Response.body ().source () 逐行读取字节流，解析 `data:` 行，遇到 `data: [DONE]` 结束
4、编程语言使用Kotlin
5、开发UI，实现类似于豆包界面的交互

#注意事项
1. 依赖：`okhttp` + `gson`，不要用第三方 SSE 库（面试一般问原生解析）
2. 必须子线程读取流，**禁止阻塞主线程**，用`viewModelScope + flow`把文字抛到 UI 层
3. 坑：流是按字节分片，有可能一条 json 被拆在两个 buffer 里，必须做缓冲区拼接（很多人 demo 卡在这里）
4. 支持手动停止对话：调用 `call.cancel()` 关闭 okhttp 请求
5. 多轮对话：自己维护 `messages` 列表，每次把历史一起上传，message需要在数据库里面保存，数据库采用Room
6. 多轮对话：每次对话都需要重新生成一个 `completion_id`，否则会导致对话不流畅
7. 坑：Room 的 Flow 查询（如 `dao.getAllMessages()`）**不能在 init 中持续 collect 来刷新 UI 状态**——insert 消息入库后 Room Flow 会立刻发射，把流式过程中内存里维护的 `_messages`（含 assistant 占位）覆盖掉，导致 `appendDelta` 拼接失败、界面无流式输出。正确做法：历史只在进入页面时用 `first()` **加载一次**，流式状态由 ViewModel 内存管理，回复结束后再写库
8. 坑：项目中同时存在 Retrofit 旧版本（传递依赖 okhttp 3.x）时，`toRequestBody()`/`toMediaType()` 这类 OkHttp 4.x Kotlin 扩展函数可能编译报 `Unresolved reference`。兼容写法：`MediaType.parse(...)` + `RequestBody.create(...)`（3.x/4.x 通用），或用 `resolutionStrategy.force` 强制统一 okhttp 版本
9. 坑：**HTTP 响应流只能读一次**。调试时在 `onResponse` 里用 `resp.body?.string()` 打印响应体会把整个流消费掉，后面 `source()` 读到的全是 null，导致流式解析失败、界面只显示占位符（表象：日志能看到完整数据，界面却没内容）。排查技巧：在 SSE 解析层/ViewModel/UI 层分别加同 tag 的日志（如 `AiSSE--`），看断在哪一层
10. 原理：为什么必须逐行读、不能用 `body.string()` 一次性读完？——SSE 的价值在于**实时性**：大模型逐 token 生成、服务器逐 chunk 推送，客户端逐行读才能实现打字机效果（首字 <1s）；一次性读完要等整个回答生成完（长回答 30s+），界面长时间空白，且超时风险高（流式需设 `readTimeout(0)`）。如果产品不需要流式效果，直接把请求参数 `stream` 设为 `false`，接口返回完整 JSON，用 `string()` 一次读取即可，无需 SSE 解析
11. 原理：大模型**无法回答实时信息**（如"今天天气"）——模型知识来自训练数据（有截止日期），推理阶段不会联网，纯聊天模式下回答"无法访问实时数据"是正确行为。想让 demo 具备实时能力需要给模型"外挂"数据源：① **Function Calling（工具调用）**：请求带 `tools` 参数 → 模型返回 `tool_call` → 客户端调真实 API（如天气接口）→ 结果塞回对话 → 模型组织语言回答；② **RAG（检索增强）**：先搜索/查库 → 结果拼进 prompt → 模型基于资料回答。模型选择：改 `AiSSEViewModel` 中的模型名即可切换，常用模型对比：

| 模型 | 特点 | 费用 |
|------|------|------|
| `glm-4-flash` | 速度快，demo 首选 | 免费 |
| `glm-4-air` | 能力更强 | 低价 |
| `glm-4-plus` | 旗舰能力 | 较贵 |
| `glm-4v-flash` | 支持图片理解（多模态） | 免费 |
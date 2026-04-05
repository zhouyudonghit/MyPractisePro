# 技能文档目录

欢迎查看本项目中的所有可用技能文档。每个技能都包含完整的文档、代码模板和示例，帮助你快速掌握特定技术领域的开发知识。

---

## 📚 可用技能列表

### 1. [Retrofit 网络请求](./retrofit-network/README.md)

**版本**: 1.0.0  
**分类**: android-network  
**标签**: android, network, retrofit, okhttp, rxjava, http, api

#### 技能描述
Android Retrofit 网络请求技能，提供标准化的网络请求开发流程、接口定义、数据解析和错误处理。适用于所有需要进行 HTTP 网络请求的 Android 项目。

#### 何时使用
- 用户需要实现 HTTP 网络请求
- 用户提到 Retrofit、OkHttp、网络 API
- 用户需要与 RESTful API 交互
- 用户需要实现登录、数据获取等网络功能

#### 内容概览
- ✅ 基本使用流程（5个步骤）
- ✅ 数据模型定义
- ✅ API 接口定义
- ✅ Retrofit 客户端管理
- ✅ 与 RxJava 结合使用
- ✅ 高级功能（拦截器、文件上传下载）
- ✅ 最佳实践和错误处理

#### 快速链接
- 📖 [完整文档](./retrofit-network/README.md)
- 🚀 [快速开始](./retrofit-network/docs/QUICKSTART.md)
- 🔧 [进阶指南](./retrofit-network/docs/ADVANCED.md)
- 💻 [基本模板](./retrofit-network/templates/BasicNetworkRequest.java)
- ⚡ [RxJava 模板](./retrofit-network/templates/RxJavaNetworkRequest.java)
- 📝 [GET 请求示例](./retrofit-network/examples/GetRequestExample.java)
- 📝 [RxJava 请求示例](./retrofit-network/examples/RxJavaRequestExample.java)

---

### 2. [示例技能](./demo-skill/demo.md)

**版本**: N/A  
**分类**: demo  
**标签**: demo, example

#### 技能描述
这是一个示例技能文档模板，用于演示技能文档的标准结构和格式。

#### 快速链接
- 📖 [完整文档](./demo-skill/demo.md)

---

## 🎯 如何使用技能文档

### 选择合适的技能

1. 根据你的开发需求选择对应的技能
2. 阅读 README.md 了解技能的完整功能
3. 查看快速开始指南（QUICKSTART.md）快速上手
4. 参考代码模板进行开发

### 学习路径

```
技能文档目录
    ↓
选择技能
    ↓
阅读 README.md（了解完整功能）
    ↓
查看 QUICKSTART.md（快速上手）
    ↓
使用代码模板（templates/）
    ↓
参考示例代码（examples/）
    ↓
深入学习（ADVANCED.md）
```

---

## 📂 技能文档结构规范

每个技能文档都遵循统一的结构规范：

```
skill-name/
├── README.md                    # 技能主文档（必选）
├── skill-config.json            # 技能配置文件（必选）
├── templates/                   # 代码模板（可选）
│   ├── Template1.java
│   └── Template2.java
├── examples/                    # 完整示例（可选）
│   ├── Example1.java
│   └── Example2.java
└── docs/                        # 详细文档（可选）
    ├── QUICKSTART.md           # 快速开始
    └── ADVANCED.md             # 进阶指南
```

### 文件说明

| 文件 | 必选 | 说明 |
|------|------|------|
| `README.md` | ✅ | 技能主文档，包含完整的技能说明、使用流程、代码示例 |
| `skill-config.json` | ✅ | 技能配置文件，包含技能元数据、触发关键词、依赖信息 |
| `templates/` | ❌ | 代码模板目录，包含可复用的代码模板 |
| `examples/` | ❌ | 完整示例目录，包含实际可运行的示例代码 |
| `docs/` | ❌ | 详细文档目录，包含快速开始、进阶指南等 |

---

## 🏗️ 创建新技能

### 1. 创建技能目录

```bash
mkdir skills/your-skill-name
cd skills/your-skill-name
```

### 2. 创建主文档

创建 `README.md`，包含以下内容：
- 技能描述
- 使用场景
- 完整使用流程
- 代码示例
- 最佳实践
- 常见问题

### 3. 创建配置文件

创建 `skill-config.json`，参考 [Retrofit 技能配置](./retrofit-network/skill-config.json)。

### 4. 创建模板和示例（可选）

根据需要创建：
- `templates/` 目录：存放代码模板
- `examples/` 目录：存放完整示例
- `docs/` 目录：存放详细文档

### 5. 更新本目录

将新技能添加到本文件的"可用技能列表"中。

---

## 🔍 搜索技能

### 按分类查找

- **android-network**: Android 网络相关
- **android-ui**: Android UI 相关
- **kotlin**: Kotlin 语言相关
- **java**: Java 语言相关

### 按标签查找

常用标签：
- android
- network
- retrofit
- okhttp
- rxjava
- http
- api

---

## 💡 技能使用建议

### 新手建议
1. 从 [Retrofit 网络请求](./retrofit-network/README.md) 开始学习
2. 阅读快速开始指南
3. 使用代码模板进行练习
4. 参考示例代码进行修改

### 进阶开发者
1. 直接查看进阶指南
2. 研究示例代码中的高级用法
3. 根据需要自定义代码模板

---

## 📝 技能文档更新日志

| 日期 | 技能 | 更新内容 |
|------|------|----------|
| 2026-03-28 | retrofit-network | 创建 Retrofit 网络请求技能文档 v1.0.0 |

---

## 🤝 贡献指南

欢迎为技能文档库贡献新技能或改进现有技能：

1. 创建新技能文档
2. 更新本目录文件（`SKILLS_INDEX.md`）
3. 确保遵循技能文档结构规范
4. 提供清晰的代码示例和文档说明

---

## 📮 反馈与建议

如果你有任何问题或建议，欢迎提出反馈：

- 发现错误：请指出具体的错误位置
- 功能建议：请详细描述需求
- 新技能建议：请说明技能的应用场景和目标

---

**最后更新时间**: 2026-03-28  
**技能总数**: 2  
**技能文档版本**: 1.0.0

# leetcode-practice

LeetCode 算法刷题笔记，使用 Java 实现。

## 技术栈

- **语言**: Java
- **JDK**: 21
- **构建工具**: Maven 3.9+
- **测试框架**: JUnit 5

## 项目结构

```
leetcode-practice/
├── pom.xml
├── src/main/java/
│   ├── array/              # 数组
│   ├── string/             # 字符串
│   ├── linkedlist/         # 链表
│   ├── tree/               # 树
│   ├── graph/              # 图
│   ├── dynamicprogramming/ # 动态规划
│   ├── backtracking/       # 回溯
│   ├── stack/              # 栈 & 单调栈
│   ├── binarysearch/       # 二分查找
│   ├── slidingwindow/      # 滑动窗口
│   ├── sorting/            # 排序
│   └── math/               # 数学
└── src/test/java/          # 单元测试
```

每个题目文件以 LeetCode 题号 + 题目名命名，如 `0001_TwoSum.java`。

## 使用方式

```bash
# 编译
mvn compile

# 运行测试
mvn test
```

也可用 IntelliJ IDEA 直接打开 `pom.xml` 作为 Maven 项目导入。

## 分类速查

| 分类 | 说明 |
|------|------|
| array | 双指针、前缀和、差分数组、螺旋矩阵 |
| string | 子串、回文、KMP、字符串匹配 |
| linkedlist | 反转、快慢指针、环形链表、LRU |
| tree | 遍历（前中后序/层序）、BST、最近公共祖先 |
| graph | DFS/BFS、拓扑排序、并查集、最短路径 |
| dynamicprogramming | 背包、子序列、区间 DP、状态压缩 |
| backtracking | 组合、排列、子集、N 皇后 |
| stack | 括号匹配、单调栈、计算器 |
| binarysearch | 二分模板、旋转数组、峰值查找 |
| slidingwindow | 定长/变长窗口、哈希表辅助 |
| sorting | 快排、归并、堆排、桶排、TopK |
| math | 素数、最大公约数、快速幂、位运算 |
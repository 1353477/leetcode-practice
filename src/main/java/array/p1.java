package array;

import java.util.*;

public class p1 {
    public int subarraySum1(int[] nums, int k) {

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    count++;
                }
            }

        }

        return count;
    }

    public int subarraySum(int[] nums, int k) {
        // key: 前缀和的值, value: 该前缀和在之前出现过的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();

        // --- 关键初始化 ---
        // 定义 preSum[0] = 0，表示前0个元素的和为0，出现次数为1
        // 这是为了处理「从数组开头到当前位置的和正好等于k」的情况
        // 例如：nums = [3,4], k=7，当 i=1 时 preSum=7，preSum-k=0，需要能找到这个0
        prefixSumCount.put(0, 1);

        int preSum = 0;  // 当前的前缀和（从数组开头到当前元素的和）
        int count = 0;   // 统计和为k的子数组总数

        // 遍历数组中的每个元素
        for (int num : nums) {
            // 步骤1：累加当前元素，更新前缀和
            preSum += num;

            // 步骤2：核心逻辑 - 查找之前有多少个前缀和等于 preSum - k
            // 原理：如果 preSum[i] - preSum[j] = k，那么子数组 nums[j+1..i] 的和就是k
            // 这里用 getOrDefault 是为了处理 preSum - k 从未出现过的情况（返回0）
            count += prefixSumCount.getOrDefault(preSum - k, 0);

            // 步骤3：更新哈希表，将当前前缀和记录下来（供后面的元素使用）
            // 注意：必须先查再更新！因为我们要找的是 j < i 的情况，不能包含当前的 preSum
            prefixSumCount.put(preSum, prefixSumCount.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }


    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];

        int nLength = nums.length;

        for (int i = 0; i <= (nLength - k); i++) {

            int max = nums[i];

            for (int j = i; j < (i + k); j++) {
                if (nums[j] >= max) {
                    max = nums[j];
                }
            }
            result[i] = max;
        }

        return result;
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;

        // 双端队列：存的是【下标】，保证对应数值【从大到小】
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && deque.peekFirst() < (i - k + 1)) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= (k - 1)) {
                result[index++] = nums[deque.peekFirst()];

            }
        }

        return result;
    }


    // --- 方案1: 大顶堆 PriorityQueue，O(n log k) ---
    // 直观好理解：堆顶始终是最大值，懒惰删除过期元素
    public int[] maxSlidingWindowHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;

        // 大顶堆：int[] {值, 下标}，按值降序，值相同按下标降序
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> b[0] != a[0] ? b[0] - a[0] : b[1] - a[1]);

        for (int i = 0; i < n; i++) {
            maxHeap.offer(new int[]{nums[i], i});

            // 堆顶过期（下标超出窗口左边界）就弹掉；可能连续弹多个
            while (maxHeap.peek()[1] <= i - k) {
                maxHeap.poll();
            }

            if (i >= k - 1) {
                result[index++] = maxHeap.peek()[0];
            }
        }
        return result;
    }

    // --- 方案2: 分块法（Block），O(n) ---
    // 把数组按每 k 个切成块，预处理每个块的「向左扫描最大值」和「向右扫描最大值」
    // 窗口 [i, i+k-1] 跨两个块时，最大值 = max(右块向左扫到 i, 左块向右扫到 i+k-1)
    public int[] maxSlidingWindowBlock(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;

        // leftMax[j]: 从所在块的左边界扫到 j 的最大值
        int[] leftMax = new int[n];
        // rightMax[j]: 从所在块的右边界扫到 j 的最大值
        int[] rightMax = new int[n];

        for (int i = 0; i < n; i++) {
            if (i % k == 0) {
                leftMax[i] = nums[i];                     // 块起点，重置
            } else {
                leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || (i + 1) % k == 0) {
                rightMax[i] = nums[i];                    // 块终点，重置
            } else {
                rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
            }
        }

        // 窗口 [i, i+k-1] 的最大值 = max(rightMax[i], leftMax[i+k-1])
        for (int i = 0; i <= n - k; i++) {
            result[index++] = Math.max(rightMax[i], leftMax[i + k - 1]);
        }
        return result;
    }

    public int[] maxSlidingWindowBlock1(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;

        // leftMax[j]: 从所在块的左边界扫到 j 的最大值
        int[] leftMax = new int[n];
        // rightMax[j]: 从所在块的右边界扫到 j 的最大值
        int[] rightMax = new int[n];

        for (int i = 0; i < n; i++) {
            if (i % k == 0) {
                leftMax[i] = nums[i];                     // 块起点，重置
            } else {
                leftMax[i] = Math.max(leftMax[i - 1], nums[i]);
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || (i + 1) % k == 0) {
                rightMax[i] = nums[i];                    // 块终点，重置
            } else {
                rightMax[i] = Math.max(rightMax[i + 1], nums[i]);
            }
        }

        // 窗口 [i, i+k-1] 的最大值 = max(rightMax[i], leftMax[i+k-1])
        for (int i = 0; i <= n - k; i++) {
            result[index++] = Math.max(rightMax[i], leftMax[i + k - 1]);
        }
        return result;
    }


    // --- 方案3: 数组模拟双端队列，O(n)，极简 + 无包装类开销 ---
    // 不需要 Deque/LinkedList，纯 int[] + 头尾指针，最快
    public int[] maxSlidingWindowArray(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[0];
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int index = 0;

        int[] deque = new int[n];   // 存下标，最多 n 个
        int head = 0, tail = 0;     // head 在队头取，tail 在队尾放

        for (int i = 0; i < n; i++) {
            // 1. 队头过期
            if (head < tail && deque[head] < i - k + 1) head++;

            // 2. 队尾弱的淘汰
            while (head < tail && nums[deque[tail - 1]] <= nums[i]) tail--;

            // 3. 入队
            deque[tail++] = i;

            // 4. 记录结果
            if (i >= k - 1) {
                result[index++] = nums[deque[head]];
            }
        }
        return result;
    }

    public int[] maxSlidingWindow2(int[] nums, int k) {
        // 边界判空
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int len = nums.length;
        // 结果数组：一共 len - k + 1 个窗口
        int[] res = new int[len - k + 1];
        int resIdx = 0;

        // 数组模拟双端队列：只存 nums 的下标
        int[] queue = new int[len];
        int head = 0;
        int tail = 0;

        // 遍历每个元素的下标 i
        for (int i = 0; i < len; i++) {
            // 第一步：把已经滑出窗口的队头干掉
            // 窗口左边界：left = i - k + 1
            // 如果队头下标 < 左边界，说明不在窗口里了，head右移
            int windowLeft = i - k + 1;
            // 队列有元素 且 队头下标过期
            if (head < tail && queue[head] < windowLeft) {
                head++;
            }

            // 第二步：维护单调递减：队尾凡是比当前数小的，全部踢出
            // 队列有元素 且 队尾对应数值 <= 当前数值
            while (head < tail && nums[queue[tail - 1]] <= nums[i]) {
                tail--; // 队尾指针左移，等于删掉队尾
            }

            // 第三步：当前下标入队
            queue[tail] = i;
            tail++;

            // 第四步：窗口形成了，开始记录最大值
            // i >= k-1 说明第一个窗口已经凑齐
            if (i >= k - 1) {
                // 队头下标对应的数，就是当前窗口最大值
                res[resIdx] = nums[queue[head]];
                resIdx++;
            }
        }

        return res;
    }

    public String minWindow(String s, String t) {
        // 边界处理
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        //



        return "";

    }

    public static void main(String[] args) {

    }
}

package com.guanhuan.test;

import java.util.*;
import java.util.stream.Stream;

class Solution {

    public static void main(String[] args){
        System.out.println(countBinarySubstrings("00110"));
        StringBuilder sb = new StringBuilder();
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getKey));

    }


    public static int countBinarySubstrings(String s) {
        List<Integer> list = new ArrayList<>(s.length());
        char[] c = s.toCharArray();
        int i = 1;
        char temp = c[0];
        int sum = 1;
        while(i < c.length){
            if(c[i] == temp){
                sum++;
            } else {
                list.add(sum);
                temp = c[i];
                sum = 1;
            }
            i++;
        }
        list.add(sum);
        sum = 0;
        for(int j=0; j<list.size()-1; j++){
            sum += (list.get(j) > list.get(j+1) ? list.get(j+1) : list.get(j));
        }
        return sum;
    }

    public static boolean detectCapitalUse(String word) {
        char[] words = word.toCharArray();
        boolean first = words[0] > 'a' && words[0] < 'z';
        if(word.length() == 1)
            return true;
        if(first){
            for(char c : words){
                if(c > 'A' && c < 'Z')
                    return false;
            }
        } else {
            boolean flag = (words[1] > 'a' && words[1] < 'z');
            for(int i=2; i<words.length; i++){
                if(words[i] > 'a' && words[i] < 'z' && !flag){
                    return false;
                }
                if(words[i] > 'A' && words[i] < 'Z' && flag){
                    return false;
                }
            }
        }
        return true;

        //相比传统方法慢了一倍
        // return word.matches("[a-z]*|[A-Z]*|[A-Z][a-z]*");
    }
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] max = new int[nums2.length];
        if(nums1.length == 0)
            return nums1;

        boolean flag;
        int[] result = new int[nums1.length];
        max[nums2.length-1] = -1;
        for(int i=nums2.length-2; i>=0; i--){
            if(nums2[i+1] > nums2[i]) {
                max[i] = nums2[i+1];
                continue;
            }
            for(int j=i+1; j<nums2.length; j++){
                if(max[j] > nums2[i]){
                    max[i] = max[j];
                    break;
                }
            }
            max[i] = -1;
        }
        for(int i=0; i<nums1.length; i++){
            for(int j=0; i<nums2.length; j++){
                if(nums1[i] == nums2[j]){
                    result[i] = max[j];
                    break;
                }
            }
        }
        return result;
    }

    public static String[] findWords(String[] words) {
        return Stream.of(words)
                .filter(s -> s.toLowerCase().matches("[qwertyuiop]*|[sadfghjkl]*|[xzcvbnm]*"))
                .toArray(String[] :: new);
    }

    public static int findComplement(int num) {
        int i;
        i = Integer.toBinaryString(num).length();
        int temp = (1 << i) - 1;
        return num ^ temp;
    }

    public static List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> list = new ArrayList<>();
        int temp;
        int n;
        boolean flag;
        for(int i=left; i <= right; i++){
            n = i;
            temp = n % 10;
            flag = true;
            if(i >= 10 && temp == 0)
                flag = false;
            while(n > 0){
                if(i % temp != 0)
                    flag = false;
                n = n / 10;
                temp = n % 10;
            }
            if(flag){
                list.add(i);
            }
        }
        return list;
    }

    public static int hammingDistance(int x, int y) {
        char[] charx = Integer.toBinaryString(x).toCharArray();
        char[] chary = Integer.toBinaryString(y).toCharArray();
        int i = 0;
        int count = 0;
        while(i<charx.length && i < chary.length){
            if(charx[charx.length-i-1] != chary[chary.length-i-1]){
                count++;
            }
            i++;
        }
        if(i < charx.length){
            for(; i<charx.length; i++){
                if(charx[charx.length-i-1] == '1')
                    count++;
            }
        }
        if(i < chary.length){
            for(; i<chary.length; i++){
                if(chary[chary.length-i-1] == '1')
                    count++;
            }
        }
        return count;
    }

    public static int numJewelsInStones(String J, String S) {
        char[] Ss = S.toCharArray();
        int count = 0;
        for(char c : Ss){
            if(J.indexOf(c)!= -1){
                count++;
            }
        }
        return count;
    }

    public static int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for(int i=0; i < grid.length; i++){
            for(int j=0; j < grid[i].length; j++){
                int temp = Solution.count(grid,i,j);
                max = max > temp ? max : temp;
            }
        }
        return max;
    }

    public static int count(int[][] grid, int i, int j){
        int sum = 0;
        if(grid[i][j] == 0){
            return 0;
        }
        else {
            grid[i][j] = 0;
            sum ++;
        }
        int x = grid.length;
        int y = grid[0].length;
        if(0 <= i-1) sum += count(grid, i-1, j);
        if(j+1 < y) sum += count(grid, i, j+1);
        if(i+1 < x) sum += count(grid, i+1, j);
        if(0 <= j-1) sum += count(grid, i, j-1);
        return sum;
    }



    public static boolean checkPossibility(int[] nums) {
        if(nums.length < 3){
            return true;
        }
        boolean first = true;
        for(int i=0; i<nums.length-1; i++){
            if(i-1 >= 0 && nums[i-1] > nums[i+1]){
                return false;
            }
            if(nums[i] > nums[i+1]){
                if(first){
                    nums[i] = nums[i+1];
                    first = false;
                } else {
                    return false;
                }
            }

        }
        return true;

    }

    public static int thirdMax(int[] nums) {
        if (nums.length == 0)
            return -1;
        if (nums.length == 1)
            return nums[0];
        if (nums.length == 2)
            return nums[0] > nums[1] ? nums[0] : nums[1];
        PriorityQueue<Integer> queue = new PriorityQueue<>((Comparator.reverseOrder()));
        for (int i : nums) {
            if (!queue.contains(i)) {
                queue.add(i);
            }
        }
        int temp = queue.poll();
        if (queue.poll() == null) {
            return temp;
        }
        if (queue.peek() == null) {
            return temp;
        }
        return queue.poll();
    }

    public static int findPairs(int[] nums, int k) {
        int count = 0;
        if(k == 0){
            Map<Integer,Integer> map = new HashMap<>();
            for(int i=0; i<nums.length; i++){
                if(!map.containsKey(nums[i])){
                    map.put(nums[i],1);
                }
                map.put(nums[i],map.get(nums[i])+1);
            }
            for(int i : map.values()){
                count = count + i - 1;
            }
            return count;
        }
        Set<Integer> set = new HashSet<>();
        for(int i=0; i<nums.length; i++){
            set.add(nums[i]+k);
        }
        for(int i=0; i<nums.length; i++){
            if(set.contains(nums[i])){
                count++;
            }
        }
        return count;
    }

    public static int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        for(int i=0; i<nums.length; i++){
            boolean flag = true;
            for(int j=i+1; j<nums.length; j++){
                if(nums[i] > nums[j]){
                    flag = false;
                    break;
                }
            }
            if(!flag)
                break;
            n--;
        }
        int length = nums.length-n;
        for(int i=nums.length-1; i >= length; i--){
            boolean flag = true;
            for(int j=i-1; j>= length; j--){
                if(nums[i] < nums[j]){
                    flag = false;
                    break;
                }
            }
            if(!flag)
                break;
            n--;
        }
        return n;
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> list = new ArrayList<Integer>();
        int x=0,y=0;
        for(int i=0; i< m+n; i++){
            if(x == m && y < n){
                list.add(nums2[y++]);
                continue;
            }
            if(y == n && x < m){
                list.add(nums1[x++]);
                continue;
            }
            if(x == m && y == n){
                break;
            }
            if( nums1[x] < nums2[y]){
                list.add(nums1[x++]);
            }
            else {
                list.add(nums2[y++]);
            }
        }
        for(int j=0; j<list.size(); j++){
            nums1[j] = list.get(j);
        }
    }

    public static int removeDuplicates(int[] nums) {
        if(nums.length == 0){
            return 0;
        }

        int dupli = 1;
        int current = 1;
        boolean flag;
        while(current <= nums.length-1){
            flag = false;
            for(int i=0; i<dupli; i++){
                if(nums[i] == nums[current]){
                    current++;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                if(current != dupli){
                    nums[current] = nums[dupli] ^ nums[current];
                    nums[dupli] = nums[dupli] ^ nums[current];
                    nums[current] = nums[dupli] ^ nums[current];
                }
                dupli++;
                current++;
            }
        }
        return dupli;
    }

    public static int pivotIndex(int[] nums) {
        if(nums.length==0)
            return -1;
        int i=1,j=nums.length-1;
        int right=nums[0],left=Integer.MIN_VALUE;
        while(true){
            if(i == j && right == left){
                break;
            }
            if(right > left){
                if(left == Integer.MIN_VALUE){
                    left = 0;
                }
                left += nums[j--];
            } else if(right <= left){
                right += nums[i++];
            }
            if(i>j)
                return -1;
        }
        return i;
    }

    public int[] plusOne(int[] digits) {
        int next = 0;
        List<Integer> result = new ArrayList<>();
        for(int i=digits.length-1; i>=0; i--){
            int sum = digits[i] + next + 1;
            if(sum == 10){
                next = 1;
                result.add(0);
            } else {
                next = 0;
                result.add(sum);
            }
        }
        if(next == 1){
            result.add(1);
        }
        Collections.reverse(result);
        int[] temp = new int[result.size()];
        int j=0;
        for(int i : result){
            temp[j++] = i;
        }
        return temp;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> temp;
        temp = new ArrayList<>(1);
        temp.add(1);
        list.add(temp);
        int sum = 0;
        for(int i=1; i < numRows; i++){
            temp = new ArrayList<>(i+1);
            for(int j=0; j<i+1; j++){
                if(j-1 > 0){
                    sum = sum + list.get(i).get(j-1);
                }
                if(j < list.get(i-1).size()){
                    sum = sum + list.get(i).get(j);
                }
                temp.add(sum);
            }
            list.add(temp);
        }
        return list;
    }

    public static int searchInsert(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length-1;
        int mid = (lo+hi)/2;
        while(hi > lo){
            if(nums[mid] == target ){
                break;
            } else if(nums[mid] < target){
                lo = mid + 1;
                mid = (lo+hi)/2;
            } else{
                hi = mid - 1;
                mid = (lo+hi)/2;
            }
        }
        if(target > nums[mid]){
            return mid+1;
        }
        return mid;
    }

    public static int removeElement(int[] nums, int val) {
        int sum = 0;
        int j=nums.length-1;
        for(int i=0; i<=j; i++){
            if(nums[i] == val){
                sum++;
                while(j > i){
                    if(nums[j] != val){
                        nums[i] = nums[j] ^ nums[i];
                        nums[j] = nums[j] ^ nums[i];
                        nums[i] = nums[j] ^ nums[i];
                        break;
                    }
                    j--;
                }
            }
        }
        return nums.length - sum;
    }


    public static int minCostClimbingStairs(int[] cost) {
        int costs = 0;
        int site;
        if(cost[0] > cost[1]){
            costs += cost[1];
            site = 1;
        }else {
            costs += cost[0];
            site = 0;
        }
        while(site < cost.length - 2){
            if(cost[site+1] > cost[site+2]){
                site += 2;
                costs += cost[site];
            }else{
                site += 1;
                costs += cost[site];
            }
        }
        return costs;
    }

    public static int maximumProduct(int[] nums) {
        int h1=nums[0],h2=Integer.MIN_VALUE,h3=Integer.MIN_VALUE;
        int l1=nums[0],l2=Integer.MAX_VALUE,l3=Integer.MAX_VALUE;
        for(int i=1; i<nums.length; i++){
            int temp = nums[i];
            if(temp > h1){
                h1 = temp ^ h1;
                temp = temp ^ h1;
                h1 = temp ^ h1;
            }
            if(temp > h2){
                h2 = temp ^ h2;
                temp = temp ^ h2;
                h2 = temp ^ h2;
            }
            if(temp > h3){
                h3 = temp ^ h3;
                temp = temp ^ h3;
                h3 = temp ^ h3;
            }

            temp = nums[i];
            if(temp < l1){
                l1 = temp ^ l1;
                temp = temp ^ l1;
                l1 = temp ^ l1;
            }
            if(temp < l2){
                l2 = temp ^ l2;
                temp = temp ^ l2;
                l2 = temp ^ l2;
            }
            if(temp < l3){
                l3 = temp ^ l3;
                temp = temp ^ l3;
                l3 = temp ^ l3;
            }
        }

        if(h3 <= 0 && h2 > 0){
            return l1 * l2 * h1;
        }
        if(h2 <= 0 && h1 > 0){
            return l1 * l2 * h1;
        }
        if(h1 <= 0){
            return h1 * h2 * h3;
        }
        return Math.max(h1 * h2 * h3, l1 * l2 * h1);

    }

    public static int[][] imageSmoother(int[][] M) {
        int[][] m = new int[M.length][M[0].length];
        for(int i=0; i < M.length; i++){
            for(int j=0; j < M[i].length; j++){
                m[i][j] = count1(M, i, j);
            }
        }
        return m;
    }

    public static int count1(int[][] M, int i, int j){
        int pointSum = 0;
        int count = 0;
        for(int n=i-1; n < i+2; n++){
            for(int m=j-1; m < j+2; m++){
                if(m > 0 && n > 0 && n < M.length && m < M[n].length){
                    pointSum += M[n][m];
                    count++;
                }
            }
        }
        return (int)(Math.floor(pointSum / count));
    }

    public static boolean containsDuplicate(int[] nums) {
        if(nums.length == 0 || nums.length == 1)
            return false;
        int sum = nums[0];
        int temp ;
        for(int i=1; i<nums.length; i++){
            temp = sum & nums[i];
            if(temp == sum){
                return true;
            }
            else{
                sum = sum | nums[i];
            }
        }
        return false;
    }

    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        int temp;
        for(int i=0; i<numbers.length; i++){
            int left = i+1,right = numbers.length-1,mid = (left + right)/2;
            while(true){
                temp = numbers[i]+numbers[mid];
                if(temp==target){
                    result[0] = i + 1;
                    result[1] = mid+1;
                    return result;
                }else if(temp > target){
                    right = mid - 1;
                }else if(temp < target){
                    left = mid + 1;
                }
                if(left > right){
                    break;
                }
                mid = (right+left)/2;
            }
        }
        return result;
    }

    public static int maxProfit(int[] prices) {
        if(prices.length == 0 || prices.length == 1)
            return 0;
        List<Integer> list = new ArrayList<>();
        if(prices[0] > prices[1] && prices[0] > prices[prices.length-1]){
            list.add(prices[0]);
        }else if(prices[0] < prices[1] && prices[0] < prices[prices.length-1]){
            list.add(prices[0]);
        }

        for(int i=1; i<prices.length; i++){
            if(prices[i] > prices[i-1] && (i==prices.length-1 || prices[i] > prices[i+1])){
                list.add(prices[i]);
            }
            if(prices[i] < prices[i-1] && (i==prices.length-1 || prices[i] < prices[i+1])){
                // down.add(prices[i]);
                list.add(prices[i]);
            }
        }

        if(prices[0] > prices[1] && prices[0] > prices[prices.length-1]){
            list.add(prices[0]);
        }else if(prices[0] < prices[1] && prices[0] < prices[prices.length-1]){
            list.add(prices[0]);
        }

        if(list.isEmpty()){
            return 0;
        }

        int min = list.get(0);
        int max = list.get(0);
        int sum = 0;
        int maxPrices = 0;
        for(int i=1; i < list.size(); i++){
            if(max < list.get(i)){
                max = list.get(i);
            }
            if(min > list.get(i) || i == list.size()-1){
                sum = max - min;
                maxPrices = maxPrices > sum ? maxPrices : sum;
                min = list.get(i);
                max = 0;
            }
        }
        return maxPrices;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        int[] temp = new int[nums.length];
        for(int i=0; i < nums.length; i++){
            temp[nums[i]]++;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i < temp.length; i++){
            if(temp[i] == 0){
                list.add(temp[i]);
            }
        }
        return list;
    }
}

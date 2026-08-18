class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;

        if (k == n) {
            int res = nums[0];

            for (int i = 1; i < n; i++) {
                res = Math.max(res, nums[i]);
            }

            return res;
        }

        int res = -1;

        for (int i = 0; i < n; i++) {
            int first = -1, last = -1;
            int occurrences = 0;

            for (int j = 0; j < n; j++) {
                if (nums[j] == nums[i]) {
                    if (first == -1) {
                        first = j;
                    }

                    last = j;
                    occurrences++;
                }
            }

            if (occurrences == 1) {
                int left = Math.max(0, first - k + 1);
                int right = Math.min(first, n - k);

                if (left == right) {
                    res = Math.max(res, nums[i]);
                }
            }
        }

        return res;
    }
}
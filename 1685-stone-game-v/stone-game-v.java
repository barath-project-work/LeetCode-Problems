class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[] pre = new int[n+1];
        for(int i = 1; i <= n; i++) {
            pre[i] = pre[i-1] + stoneValue[i-1];
        }
        int[][] dp = new int[n][n];
        for(int l = 1; l < n; l++) {
            for(int i = 0; i < n-l; i++) {
                int j = i+l, res = 0;
                for(int k = i; k < j; k++) {
                    int left = pre[k+1] - pre[i], right = pre[j+1] - pre[k+1];
                    if(left < right) {
                        res = Math.max(res, left + dp[i][k]);
                    } else if(left > right) {
                        res = Math.max(res, right + dp[k+1][j]);
                    } else {
                        res = Math.max(res, left + dp[i][k]);
                        res = Math.max(res, right + dp[k+1][j]);
                    }
                }
                dp[i][j] = res;
            }
        }
        return dp[0][n-1];
    }
}
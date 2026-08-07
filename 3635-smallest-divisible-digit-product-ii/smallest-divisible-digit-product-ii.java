class Solution {

    private int[][] dp;

    private int minDigits23(int a, int b) {

        if (a == 0 && b == 0) {
            return 0;
        }

        if (dp[a][b] != -1) {
            return dp[a][b];
        }

        int ans = Integer.MAX_VALUE / 2;

        if (a >= 1)
            ans = Math.min(ans, 1 + minDigits23(a - 1, b));

        if (b >= 1)
            ans = Math.min(ans, 1 + minDigits23(a, b - 1));

        if (a >= 2)
            ans = Math.min(ans, 1 + minDigits23(a - 2, b));

        if (a >= 1 && b >= 1)
            ans = Math.min(ans, 1 + minDigits23(a - 1, b - 1));

        if (a >= 3)
            ans = Math.min(ans, 1 + minDigits23(a - 3, b));

        if (b >= 2)
            ans = Math.min(ans, 1 + minDigits23(a, b - 2));

        return dp[a][b] = ans;
    }

    private void addFactors(int digit, int[] cnt) {

        while (digit % 2 == 0) {
            cnt[0]++;
            digit /= 2;
        }

        while (digit % 3 == 0) {
            cnt[1]++;
            digit /= 3;
        }

        while (digit % 5 == 0) {
            cnt[2]++;
            digit /= 5;
        }

        while (digit % 7 == 0) {
            cnt[3]++;
            digit /= 7;
        }
    }

    private int minDigits(int[] cnt) {

        return minDigits23(cnt[0], cnt[1])
                + cnt[2]
                + cnt[3];
    }

    private String buildSmallest(int[] need, int len) {

        if (minDigits(need) > len) {
            return "";
        }

        StringBuilder ans = new StringBuilder();

        int[] rem = need.clone();

        for (int pos = 0; pos < len; pos++) {

            int remainingSlots = len - pos - 1;

            for (int digit = 1; digit <= 9; digit++) {

                int[] next = rem.clone();

                addFactors(digit, next);

                for (int j = 0; j < 4; j++) {
                    next[j] = Math.max(0, rem[j] -
                            (rem[j] - next[j]));
                }

                int[] candidate = rem.clone();

                int x = digit;

                while (x % 2 == 0) {
                    candidate[0]--;
                    x /= 2;
                }

                while (x % 3 == 0) {
                    candidate[1]--;
                    x /= 3;
                }

                while (x % 5 == 0) {
                    candidate[2]--;
                    x /= 5;
                }

                while (x % 7 == 0) {
                    candidate[3]--;
                    x /= 7;
                }

                for (int j = 0; j < 4; j++) {
                    candidate[j] = Math.max(0, candidate[j]);
                }

                if (minDigits(candidate) <= remainingSlots) {

                    ans.append((char) ('0' + digit));
                    rem = candidate;
                    break;
                }
            }
        }

        return ans.toString();
    }

    public String smallestNumber(String num, long t) {

        int n = num.length();

        int[] need = new int[4];

        long temp = t;

        while (temp % 2 == 0) {
            need[0]++;
            temp /= 2;
        }

        while (temp % 3 == 0) {
            need[1]++;
            temp /= 3;
        }

        while (temp % 5 == 0) {
            need[2]++;
            temp /= 5;
        }

        while (temp % 7 == 0) {
            need[3]++;
            temp /= 7;
        }

        if (temp != 1) {
            return "-1";
        }

        dp = new int[64][64];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, -1);
        }

        int[][] prefix = new int[n + 1][4];

        boolean[] hasZero = new boolean[n + 1];

        for (int i = 0; i < n; i++) {

            prefix[i + 1] = prefix[i].clone();

            int digit = num.charAt(i) - '0';

            hasZero[i + 1] =
                    hasZero[i] || digit == 0;

            if (digit != 0) {
                addFactors(digit, prefix[i + 1]);
            }
        }

        if (!hasZero[n]) {

            boolean valid = true;

            for (int j = 0; j < 4; j++) {
                if (prefix[n][j] < need[j]) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return num;
            }
        }

        for (int i = n - 1; i >= 0; i--) {

            if (hasZero[i]) {
                continue;
            }

            int original =
                    num.charAt(i) - '0';

            for (int digit = original + 1;
                 digit <= 9;
                 digit++) {

                int[] remaining = new int[4];

                for (int j = 0; j < 4; j++) {
                    remaining[j] =
                            Math.max(
                                    0,
                                    need[j] - prefix[i][j]
                            );
                }

                int x = digit;

                while (x % 2 == 0) {
                    remaining[0]--;
                    x /= 2;
                }

                while (x % 3 == 0) {
                    remaining[1]--;
                    x /= 3;
                }

                while (x % 5 == 0) {
                    remaining[2]--;
                    x /= 5;
                }

                while (x % 7 == 0) {
                    remaining[3]--;
                    x /= 7;
                }

                for (int j = 0; j < 4; j++) {
                    remaining[j] =
                            Math.max(0, remaining[j]);
                }

                int freeSlots = n - i - 1;

                /*
                 * Can the remaining factors fit?
                 */
                if (minDigits(remaining) <= freeSlots) {

                    String suffix =
                            buildSmallest(
                                    remaining,
                                    freeSlots
                            );

                    if (!suffix.isEmpty()
                            || freeSlots == 0) {

                        StringBuilder ans =
                                new StringBuilder();

                        ans.append(num, 0, i);

                        ans.append(
                                (char) ('0' + digit)
                        );

                        ans.append(suffix);

                        return ans.toString();
                    }
                }
            }
        }

        int minLen = minDigits(need);

        int answerLen =
                Math.max(n + 1, minLen);

        return buildSmallest(need, answerLen);
    }
}
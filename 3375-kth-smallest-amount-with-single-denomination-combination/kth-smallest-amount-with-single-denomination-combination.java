class Solution {
    HashMap<Long,ArrayList<Long>> hm = new HashMap<>();
    public void genSubSets(int nums[],int n){
        
         for(long i = 0;i<=n;i++){
            hm.put(i,new ArrayList<>());
         }
        
        for(int i = 0;i<(1<<n);i++){
             long count = 0;
             long gcd = 1;
             long den = 1;
             int j = 0;
             long lcm = 1;
            for(int bit = 0;bit<=31;bit++){
                    if(((i>>bit)&1)==1){
                        lcm = lcm(lcm,nums[j]);
                        count++;
                    }
                    j++;
            }
            // System.out.println("count = "+count +"i = "+i + " gcd = "+gcd +" den = "+den);
            hm.get(count).add(lcm);
         }
    }
    long lcm(long a,long b){
        if(a==0 || b==0) return 0;
        return (a/(gcd(a,b))) * b;
    }
    long gcd(long a,long b){
        if(b==0) return a;
        return gcd(b,a%b);
    }
    public long findKthSmallest(int[] coins, int k) {
        Arrays.sort(coins);
        int n = coins.length;
        int Neg = 0;
        for(int i = 0;i<n;i++){
            if(coins[i]!=-1){
                for(int j = i+1;j<n;j++){
                    if(coins[j]!=-1){
                        if(coins[j]%coins[i] == 0) {
                            coins[j] = -1;
                            Neg++;
                        }
                    }
                }
            }
        }
        int nums[] = new int[n-Neg];
        int len = nums.length;
       
        Neg = 0;
        for(int num: coins){
            if(num!=-1){
                nums[Neg++] = num;
                // System.out.print(num+" ");
            }
        }
        genSubSets(nums,len);
        // System.out.println(hm);
        long l = nums[0];
        long r = 1L*nums[0]*k;
        long ans = 0;
        while(l<=r){
            long m = (l+(r-l)/2);
            long count = getCount(m,nums);
            
            if(count<k){
                 l = m +1;
            }else{
                 ans = m;
                 r = m -1;
            }
        }
        return ans;
    }
    public long getCount(long target ,int nums[]){
        long count = 0;
        int sign = 1;
        for(long fre = 1;fre<=nums.length;fre++){
            if(hm.get(fre)==null) continue;
            for(long g: hm.get(fre)){
                count = (count + sign*(target/g));
            }
            sign = -sign;
        }
        return count;
    }
}
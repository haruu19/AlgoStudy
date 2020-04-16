package dp;
/**
 * 
 * 계단은 한 번에 한 계단씩 또는 두 계단씩 오를 수 있다. 즉, 한 계단을 밟으면서 이어서 다음 계단이나, 다음 다음 계단으로 오를 수 있다.
 * 연속된 세 개의 계단을 모두 밟아서는 안 된다. 단, 시작점은 계단에 포함되지 않는다.
 * 마지막 도착 계단은 반드시 밟아야 한다.
 * n 번 째 계단 : n-1 에서 오르거나, n-2 에서 오르는 방법이 있다.
 * n-2 에서 오를 경우, 제약사항 없음. d(n) = d(n-2) + ~~
 * n-1 에서 오를 경우, 세 계단 연속해서 밟는 경우가 발생한다. n-2번 째 계단은 밟지 않은 상태여야 한다. 
 * 10 20 15 25 10 20
 * d(1) = s(1) = 10
 * d(2) = s(1) + s(2) = 30
 * d(3) = s(1) + s(3) = 35
 * d(n) = s(n) + max(d(n-1)
 * 
 * d[n][c] c: n-1층과 붙은 상태? c==1 : 붙지않음   c==2 : 붙음
 * d[1][1] = 10 d[1][2] = 10
 * d[2][1] = 20 d[2][2] = 30
 * d[3][1] = 25 d[3][2] = 35
 * d[4][1] = max(d[2][1] d[2][2]) + 25 = 55   d[4][2] = d[3][1] + 25 = 50
 * d[5][1] = 35 + 10 = 45   d[5][2] = 55 + 10 = 65
 * d[6][1] = 55 + 20 = 75   d[6][2] = 45 + 20 = 65
 * 
 * d[n][1] = max(d[n-2][1], d[n-2][2]) + s[n], d[n][2] = d[n-1][1] + s[n]
 */
/**
 * 
 * 마이구미 블로그 참조 점화식 : 
 * 2가지 케이스가 있다.
 * n층 가려면 하나는 n-1층에서 오고, 하나는 n-2에서 오는데
 * n-1층에서 오는 경우 n-2층은 밟지 않은 상태여야 한다.
 * 따라서 dp[n] = dp[n-3] + arr[n-1] + arr[n] 인 케이스 하나가 도출되었다.
 * n-2층에서 오는 경우는
 * dp[n] = dp[n-2] + arr[n] 이다.
 * 따라서 dp[n] = max(dp[n-3] + arr[n-1] + arr[n], dp[n-2] + arr[n]) 인 점화식을 도출할 수 있다.
 * 
 * 그리고 N의 값이 1이거나 2인 경우 처리를 해 주지 않으면 런타임 에러가 날 수 있으니 조심하자!!!
 */
import java.util.Scanner;

public class boj_2579 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] s = new int[N+1];
		for(int i=1;i<=N;i++) {
			s[i] = sc.nextInt();
		}
		int[][] d = new int[N+1][2+1];
		d[1][1] = s[1];
		d[1][2] = s[1];
		if(N!=1) {
			d[2][1] = s[2];
			d[2][2] = s[1]+s[2];
		}
		for(int i=3;i<=N;i++) {
			d[i][1] = Math.max(d[i-2][1], d[i-2][2]) + s[i];
			d[i][2] = d[i-1][1] + s[i];
		}
		int result = Math.max(d[N][1], d[N][2]);
		System.out.println(result);
	}
}
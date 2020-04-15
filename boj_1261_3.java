package baekjoon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class boj_1261_3 {
	static int N,M;
	static int[][] map;
	static int[][] dp;
	static int[] dx = {-1,1,0,0};// 상,하,좌,우
	static int[] dy = {0,0,-1,1};
	static class Pos{
		int x;
		int y;
		public Pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		M = sc.nextInt();
		N = sc.nextInt();
		sc.nextLine();
		map = new int[N][M];
		for(int i=0;i<N;i++) {
			String s = sc.nextLine();
			for(int j=0;j<M;j++) {
				map[i][j] = s.charAt(j)-48;
			}
		}//end input
		
		dp = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				dp[i][j] = Integer.MAX_VALUE;
			}
		}
		
		dp[0][0] = 0;
		Queue<Pos> q = new LinkedList<>();
		q.add(new Pos(0,0));
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			for(int i=0;i<4;i++) {
				int nx = cur.x+dx[i];
				int ny = cur.y+dy[i];
				if(nx>=0&&ny>=0&&nx<N&&ny<M) {
					if(map[nx][ny]==0) {
						if(dp[nx][ny]>dp[cur.x][cur.y]) {
							dp[nx][ny] = dp[cur.x][cur.y];
							q.add(new Pos(nx,ny));
						}
					}else {
						if(dp[nx][ny]>dp[cur.x][cur.y]+1) {
							dp[nx][ny] = dp[cur.x][cur.y]+1;
							q.add(new Pos(nx,ny));
						}
					}
				}
			}
		}
		System.out.println(dp[N-1][M-1]);
	}
}

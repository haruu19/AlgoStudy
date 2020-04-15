package baekjoon;

import java.util.PriorityQueue;
import java.util.Scanner;

public class boj_1261_4 {
	static int N,M;
	static int[][] map;
	static int[][] D;
	static int[] dx = {-1,1,0,0};// 상,하,좌,우
	static int[] dy = {0,0,-1,1};
	static class Pos implements Comparable<Pos>{
		int x;
		int y;
		int weight;
		public Pos(int x, int y, int weight) {
			super();
			this.x = x;
			this.y = y;
			this.weight=weight;
		}
		@Override
		public int compareTo(Pos o) {
			// TODO Auto-generated method stub
			return this.weight - o.weight;
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
		
		D = new int[N][M];
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				D[i][j] = Integer.MAX_VALUE;
			}
		}
		
		D[0][0] = 0;
		PriorityQueue<Pos> pq = new PriorityQueue<>();
		pq.add(new Pos(0,0,0));
		while(!pq.isEmpty()) {
			Pos cur = pq.poll();
			for(int i=0;i<4;i++) {
				int nx = cur.x+dx[i];
				int ny = cur.y+dy[i];
				if(nx>=0&&ny>=0&&nx<N&&ny<M) {
					if(D[nx][ny]>D[cur.x][cur.y]+map[nx][ny]) {
						D[nx][ny] = D[cur.x][cur.y]+map[nx][ny];
						pq.add(new Pos(nx,ny,D[nx][ny]));
					}
				}
			}
		}
		System.out.println(D[N-1][M-1]);
	}
}

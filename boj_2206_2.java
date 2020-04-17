package baekjoon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class boj_2206_2 {
	static int result;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Pos{
		int x;
		int y;
		int dist;
		boolean hasBreaked;
		public Pos(int x, int y, int dist, boolean hasBreaked) {
			super();
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.hasBreaked = hasBreaked;
		}
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		sc.nextLine();
		int[][] map = new int[N][M];
		int[][] D = new int[N][M];
		int[][] visited = new int[N][M];
		for(int i=0;i<N;i++) {
			String s = sc.nextLine();
			for(int j=0;j<M;j++) {
				map[i][j] = s.charAt(j)-'0';
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				D[i][j] = Integer.MAX_VALUE;
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				visited[i][j] = Integer.MAX_VALUE;
			}
		}
		D[0][0] = 0;
		visited[0][0] = 1;
		boolean[][] busum = new boolean[N][M]; // 해당 좌표에 위치했을때 이미 찬스 쓴 경우. true 이고 dist 같으면 false로 갱신하면서 큐에 add할 수 있게 만들자
		Queue<Pos> q = new LinkedList<>();
		q.add(new Pos(0,0,1,false));
		result = Integer.MAX_VALUE;
		/**
		 * bfs로 탐색을 한다.
		 * 갈 수 있는 곳이면 일단 큐에 넣어!
		 * 방문배열은 int 배열로 선언, 최솟값을 갱신하는 형태로.
		 * hasbreaked false 인데 map[nx][ny]==1 이면 true로
		 * hasbreaked true 인데 map[nx][ny]==1 이면 continue
		 * 
		 * 
		 */
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			
			for(int i=0;i<4;i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				if(nx>=0&&ny>=0&&nx<N&&ny<M) {
					if(cur.hasBreaked==false && map[nx][ny]==1) {
						if(visited[nx][ny] > cur.dist+1) {
							visited[nx][ny] = cur.dist+1;
//							visited 출력해보자
//							for(int a=0;a<N;a++) {
//								for(int b=0;b<M;b++) {
//									System.out.print(visited[a][b]==Integer.MAX_VALUE?"INF ":" "+visited[a][b] + "  ");
//								}
//								System.out.println();
//							}
//							System.out.println();
							busum[nx][ny] = true;
							q.add(new Pos(nx,ny,cur.dist+1,true));
						}
						continue;
					}
					if(cur.hasBreaked==true && map[nx][ny]==1)
						continue;
					if(visited[nx][ny] > cur.dist+1) {
						visited[nx][ny] = cur.dist+1;
//						visited 출력해보자
//						for(int a=0;a<N;a++) {
//							for(int b=0;b<M;b++) {
//								System.out.print(visited[a][b]==Integer.MAX_VALUE?"INF ":" "+visited[a][b] + "  ");
//							}
//							System.out.println();
//						}
//						System.out.println();
						busum[nx][ny] = cur.hasBreaked;
						q.add(new Pos(nx,ny,cur.dist+1,cur.hasBreaked));
						continue;
					}
					if(busum[nx][ny] && !cur.hasBreaked) {
						busum[nx][ny] = false;
						q.add(new Pos(nx,ny,cur.dist+1,cur.hasBreaked));
					}
				}
			}
		}
		
		if(visited[N-1][M-1]==Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(visited[N-1][M-1]);
		}
	}
}

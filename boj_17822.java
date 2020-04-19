package samsungProbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_17822 {
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean[][] visited;
	static boolean[][] colored;
	static int N,M,T;
	static int[][] board;
	static int[] X,D,K;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		visited = new boolean[N][M];
		colored = new boolean[N][M];
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<M;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		X = new int[T];
		D = new int[T];
		K = new int[T];
		for(int i=0; i<T;i++) {
			st = new StringTokenizer(br.readLine());
			X[i] = Integer.parseInt(st.nextToken());
			D[i] = Integer.parseInt(st.nextToken());
			K[i] = Integer.parseInt(st.nextToken());
			rotate(X[i],D[i],K[i]);
//			{
//				for(int a=0;a<N;a++) {
//					for(int b=0;b<M;b++) {
//						System.out.print(board[a][b]+" ");
//					}
//					System.out.println();
//				}
//				System.out.println();
//			}
			boolean check = delete();
//			{
//				for(int a=0;a<N;a++) {
//					for(int b=0;b<M;b++) {
//						System.out.print(board[a][b]+" ");
//					}
//					System.out.println();
//				}
//				System.out.println();
//			}
			if(check==false) {
				int cnt = 0;
				int sum = 0;
				float avg = 0;
				for(int a=0;a<N;a++) {
					for(int b=0;b<M;b++) {
						if(board[a][b]!=0) {
							sum += board[a][b];
							cnt++;
						}
					}
				}
				avg = (float)sum / cnt;
//				System.out.println("avg: "+avg);
				for(int a=0;a<N;a++) {
					for(int b=0;b<M;b++) {
						if(board[a][b]!=0) {
							if(board[a][b]>avg) {
								board[a][b]--;
								continue;
							}
							if(board[a][b]<avg) {
								board[a][b]++;
							}
						}
					}
				}
			}
//			{
//				for(int a=0;a<N;a++) {
//					for(int b=0;b<M;b++) {
//						System.out.print(board[a][b]+" ");
//					}
//					System.out.println();
//				}
//				System.out.println();
//			}
			clear();
		} //end input
		int result = 0;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				result+=board[i][j];
			}
		}
		System.out.println(result);
	}// end main
	static void rotate(int x, int d, int k) {
		for(int i=0;i<N;i++) {
			if( (i+1)%x==0 ) {
				int[] temp = new int[M];
				if(d==0) {
					for(int j=0;j<M;j++) {
						temp[(j+k)%M] = board[i][j];
					}
					for(int j=0;j<M;j++) {
						board[i][j] = temp[j];
					}
				}else {
					for(int j=0;j<M;j++) {
						temp[j] = board[i][(j+k)%M];
					}
					for(int j=0;j<M;j++) {
						board[i][j] = temp[j];
					}
				}
			}
		}
	}
	static boolean delete() {
		boolean result = false;
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				colored[i][j] = false;
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(board[i][j]==0)
					continue;
				for(int k=0;k<4;k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					if(nx>=0&&ny>=0&&nx<N&&ny<M&&!visited[nx][ny]) {
						if(board[i][j]==board[nx][ny]) {
							if(!result) {
								result = true;
							}
							colored[i][j] = true;
							colored[nx][ny] = true;
						}
					}else {
						if(ny<0) {
							if(board[i][j]==board[i][M-1]) {
								if(!result) {
									result = true;
								}
								colored[i][j] = true;
								colored[i][M-1] = true;
							}
						}
						if(ny>=M) {
							if(board[i][j]==board[i][0]) {
								if(!result) {
									result = true;
								}
								colored[i][j] = true;
								colored[i][0] = true;
							}
						}
					}
				}
				visited[i][j] = true;
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				if(colored[i][j])
					board[i][j] = 0;
			}
		}
		return result;
	}
	static void clear() {
		for(int i=0;i<N;i++) {
			for(int j=0;j<M;j++) {
				visited[i][j] = false;
			}
		}
	}
}// end class

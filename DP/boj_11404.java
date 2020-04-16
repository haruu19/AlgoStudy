package dp;

import java.util.Scanner;

public class boj_11404 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		final int INF = Integer.MAX_VALUE;
		int N = sc.nextInt();
		int[][] D = new int[N][N];
		int M = sc.nextInt();
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				D[i][j] = INF;
			}
		}
		for(int i=0;i<M;i++) {
			int a = sc.nextInt()-1;
			int b = sc.nextInt()-1;
			int c = sc.nextInt();
			D[a][b] = Math.min(D[a][b], c);
		}
		for(int k=0;k<D.length;k++) { // 경유 정점
			for(int i=0;i<D.length;i++) { // 출발 정점
				if(k == i) continue;
				for(int j=0;j<D.length;j++) { // 도착 정점
					if( k == j || i == j ) continue;
					if(D[i][k] != INF && D[k][j] != INF &&D[i][j] > D[i][k] + D[k][j])
						D[i][j] = D[i][k] + D[k][j];
				}
			}
		}
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(D[i][j]==INF?0+" ":D[i][j]+" ");
			}
			System.out.println();
		}
	}
}

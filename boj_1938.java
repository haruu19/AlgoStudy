package baekjoon;

/**
 * 
 * 일단 bfs를 돌리자
 * 근데 정보를 좀 디테일하게 줄 필요가 있을 것 같애
 * Log 클래스에
 * 중심의 좌표
 * 가로,세로상태
 * 이동횟수
 * 요종도 넣으면 되겠지? 근데 가로세로 돌리면 무한뤂에 빠질 수 있는데 이 경우는 어떻하누/... <-- visited를 3차원 배열로 만들어야 한다 ***
 * -> 로그 클래스에 canChange 넣어서 한번 돌리면 false로 바꿔주자. 4방탐색 하러 떠나는 경우는 다시 true로 바꿔주면 될거같음
 * -> 일단 4방탐색 하고 다음에 한번만 가로세로 바꾸는 전략으로 가자. 총 5가지 케이스로 탐색한다고 생각하면 될듯 !!
 * 당연히 가지 못하는 케이스는 continue 해줘야겟지? 바운더리 넘는거랑 중심점 말고 끄트머리가 1이랑 겹치는 경우, 
 * 가로세로 바꾸는 경우는 주위 3*3 겹치는거 체크만 해주면 개꿀
 * 그리고 마지막 도착점에 다다랐을 경우를 찾는 법에 대해 생각해 봐야되는데..
 * 일딴 B를 먼저 찾았을 때 가로세로 판별을 해야겟지? 이건 완탐으로 해야 하나.. i,j이중포문으로 돌릴 시 어쨋든 가로 세로방향이든 첫번째 B가 걸릴거야
 * 그 다음 B가 나오는게 같은 열이면 가로고, 행이면 세로겠지.
 * E도 마찬가지로 찾아서 중심점과 가로세로 여부를 저장해놓으면 문제해결 거의 다 한거같은데?
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class boj_1938 {
	static class Log{
		int c_x;//센터좌표
		int c_y;
		boolean shape; // 가로세로 여부. true : 가로 , false : 세로로 하자!
		boolean canChange;
		int cnt;
		public Log(int c_x, int c_y, boolean shape, boolean canChange, int cnt) {
			super();
			this.c_x = c_x;
			this.c_y = c_y;
			this.shape = shape;
			this.canChange = canChange;
			this.cnt = cnt;
		}
	}
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static char[][] map;
	static int[][][] visited; 
	static int bCount;//b갯수
	static boolean bShape;//b 가로세로 여부
	static int bC_x;//b센터좌표
	static int bC_y;
	static int eCount;
	static boolean eShape;
	static int eC_x;
	static int eC_y;
	static final int INF = Integer.MAX_VALUE;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		for(int i=0;i<N;i++) {
			map[i] = br.readLine().toCharArray();
		}// end input

		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				if(bCount<2 && map[i][j]=='B') { // B정보 찾기
					bCount++;
					if(bCount==2) {
						bC_x = i;
						bC_y = j;
						if(j-1>=0 && map[i][j-1]=='B') { // 가로인 경우
							bShape = true;
						}
					}
				}
				if(eCount<2 && map[i][j]=='E') { // E정보 찾기
					eCount++;
					if(eCount==2) {
						eC_x = i;
						eC_y = j;
						if(j-1>=0 && map[i][j-1]=='E') { // 가로인 경우
							eShape = true;
						}
					}
				}
			}
		} //end findInfo
		int result = INF;
		Queue<Log> q = new LinkedList<>();
		visited = new int[N][N][2]; // [][][0] : 가로 , [][][1] : 세로
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				for(int k=0;k<2;k++) {
					visited[i][j][k] = INF;
				}
			}
		}
		q.add(new Log(bC_x,bC_y,bShape,true,0));
		if(bShape) {
			visited[bC_x][bC_y][0] = 0;
		}else {
			visited[bC_x][bC_y][1] = 0;
		}
		while(!q.isEmpty()) {
			Log cur = q.poll();
			//정답 갱신
			if(cur.c_x==eC_x && cur.c_y==eC_y && cur.shape==eShape) {
				if(result>cur.cnt) {
					result = cur.cnt;
				}
			}
			
			//4방탐색
			LOOP1:
			for(int i=0;i<4;i++) {
				int nC_x = cur.c_x + dx[i];
				int nC_y = cur.c_y + dy[i];
				if(cur.shape) {
					if(!(nC_x>=0&&nC_y>=1&&nC_x<N&&nC_y<N-1))
						continue;
				}else {
					if(!(nC_x>=1&&nC_y>=0&&nC_x<N-1&&nC_y<N))
						continue;
				}//boundary Check
				
				// 중복제거, 최소값 유지. cnt같으면서 모양 반대일 경우 큐에 추가 가능
				if(cur.shape) { // 현재모양 가로일 때
					if(visited[nC_x][nC_y][0] <= cur.cnt+1) 
						continue;
					
					for(int idx=nC_y-1; idx<=nC_y+1; idx++) { // 방해물 체크
						if(map[nC_x][idx] == '1')
							continue LOOP1;
					}
					
					q.add(new Log(nC_x,nC_y,cur.shape,true,cur.cnt+1));
					visited[nC_x][nC_y][0] = cur.cnt+1;
				}else { // 현재모양 세로일 때
					if(visited[nC_x][nC_y][1] <= cur.cnt+1) 
						continue;
					
					for(int idx=nC_x-1; idx<=nC_x+1; idx++) { // 방해물 체크
						if(map[idx][nC_y] == '1')
							continue LOOP1;
					}
					
					q.add(new Log(nC_x,nC_y,cur.shape,true,cur.cnt+1));
					visited[nC_x][nC_y][1] = cur.cnt+1;
				}
			}
			//가로세로 변환
			if(cur.canChange) {
				boolean nShape = !cur.shape;
				LOOP2:
				{
					if(!(cur.c_x-1>=0&&cur.c_y-1>=0&&cur.c_x+1<N&&cur.c_y+1<N))
						break LOOP2;
					for(int i=cur.c_x-1;i<=cur.c_x+1;i++) {
						for(int j=cur.c_y-1;j<=cur.c_y+1;j++) {
							if(map[i][j] == '1') {
								break LOOP2;
							}
						}
					}
					q.add(new Log(cur.c_x,cur.c_y,nShape,false,cur.cnt+1));	
					if(cur.shape) { // 현재모양 가로일 때
						visited[cur.c_x][cur.c_y][0] = visited[cur.c_x][cur.c_y][1]+1;
					}else { // 현재모양 세로일 때
						visited[cur.c_x][cur.c_y][1] = visited[cur.c_x][cur.c_y][0]+1;
					}
				}
			}
		}
		
		System.out.println(result==INF?0:result);
	}
}



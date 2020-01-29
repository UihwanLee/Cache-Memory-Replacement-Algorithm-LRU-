package cache.algorithm;

public class CacheBlock {
	protected String[] cacheblock = new String[3]; // ĳ�ú�� (ĳ�� ��� �� = 3)
	private int[] rank = new int[3]; // �켱 ���� �Ǵ�
	private String[][] progress = new String[31][3]; // ĳ�ú�� �����Ȳ
	private String[][] progressRank = new String[31][3]; // ĳ�ú�� ��ũ �����Ȳ
	private int progressIndex = 0;
	private boolean HIT = false;  
	private int HITcount = 0;
	private int i = 0;
	
	public void LFU(String block) {
		if(cacheblock[0] != null) { // ĳ�� ����� �ϳ��� ä�������� �� LFU �˰��� ����� ���� ��ü
			for(i=0; i<3; i++) {
				if(block.equals(cacheblock[i])) {
					HIT = true;
					HITcount++;
					RankUpdate(i);
				}
			}
			if(HIT == false && cacheblock[2] != null) {
				int j = 0;
				for(i=0; i<3; i++) { if(rank[i]==1) { cacheblock[i] = block; j = i;} }
				RankUpdate(j); 
			}
		}
		// ĳ�� ����� �ٿ����� �� ���ʴ�� ��� �߰�
		if(cacheblock[0] == null) { cacheblock[0] = block; rank[0] = 1; }
		else if(cacheblock[1] == null && HIT != true) { cacheblock[1] = block; rank[1] = 2; }
		else if(cacheblock[2] == null && HIT != true) { cacheblock[2] = block; rank[2] = 3; }
	}
	
	public void RankUpdate(int i) {
		if(rank[1]== 0) { rank[i] = 1; } // ĳ�� �޸� ��Ͽ� �����Ͱ� 1�� ������� �� 
		else if(rank[2]==0) { rank[i] = 2; rank[(i+3)%2] = 1; } // ĳ�� �޸� ��Ͽ� �����Ͱ� 2�� ������� �� 
		else { // ĳ�� �޸� ��Ͽ� �����Ͱ� 3�� ������� �� 
			rank[i] = 3;
			int p = (i+4)%3; int q = (i+2)%3;
			if(rank[p] > rank[q]) { rank[p] = 2; rank[q] = 1; }
			else { rank[p] = 1; rank[q] = 2; }
		}
	}
	
	public void ProgressUpdate() {
		String cacheblock1 = (cacheblock[0]==null) ? "  " : cacheblock[0];
		String cacheblock2 = (cacheblock[1]==null) ? "  " : cacheblock[1];
		String cacheblock3 = (cacheblock[2]==null) ? "  " : cacheblock[2];
		progress[progressIndex][0] = (HIT==true) ? " H" : cacheblock1;
		progress[progressIndex][1] = (HIT==true) ? " I" : cacheblock2;
		progress[progressIndex++][2] = (HIT==true) ? " T" : cacheblock3;
		HIT=false;
	}
	
	public void ProgressRankUpdate() {
		String rank1 = (rank[0]==0) ? " " : Integer.toString((rank[0]));
		String rank2 = (rank[1]==0) ? " " : Integer.toString((rank[1]));
		String rank3 = (rank[2]==0) ? " " : Integer.toString((rank[2]));
		progressRank[progressIndex-1][0] = rank1;
		progressRank[progressIndex-1][1] = rank2;
		progressRank[progressIndex-1][2] = rank3;
	}
	
	public void showProgress(String[] block) {
		String HITrate = HITcount + "/" + Integer.toString(progressIndex);
		System.out.println("[CacheBlock Progress]");
		for(i=0; i<progressIndex; i++) 
			System.out.print(block[i] + "| ");
		System.out.print(" ���߷�| ");
		System.out.println();
		for(i=0; i<progressIndex; i++) 
			System.out.print("----");
		for(int j=0; j<3; j++) {
			System.out.println(); 
			for(i=0; i<progressIndex; i++) {
				System.out.print(progress[i][j] + "| ");
				if(i==progressIndex-1 && j!=1) System.out.print("     |");
				if(i==progressIndex-1 && j==1) System.out.print(HITrate + "|");
			}
		}
		System.out.println("\n");
	}
	
	public void showProgressRank(String[] block) {
		System.out.println("[Rank Progress]");
		for(i=0; i<progressIndex; i++) 
			System.out.print(block[i] + "| ");
		System.out.println();
		for(i=0; i<progressIndex; i++) 
			System.out.print("----");
		for(int j=0; j<3; j++) {
			System.out.println(); 
			for(i=0; i<progressIndex; i++) {
				System.out.print(" " + progressRank[i][j] + "| ");
			}
		}
		System.out.println("\n");
	}
	
	public void showLastCacheBlock() { 
		double HITrate = (Math.round(((double)HITcount/(double)progressIndex)*1000)/1000.0)*100; // �Ҽ��� ��°�ڸ����� �ݿø�
		if(cacheblock[0]==null) cacheblock[0] = "  ";
		if(cacheblock[1]==null) cacheblock[1] = "  ";
		if(cacheblock[2]==null) cacheblock[2] = "  ";
		System.out.println("[CacheBlock]        [ ���߷� ]");
		System.out.println("| CacheBlcok1 | " + cacheblock[0] + "|         |");
		System.out.println("| CacheBlcok2 | " + cacheblock[1] + "|   " + HITrate + "% |");
		System.out.println("| CacheBlcok3 | " + cacheblock[2] + "|         |");
	}
}


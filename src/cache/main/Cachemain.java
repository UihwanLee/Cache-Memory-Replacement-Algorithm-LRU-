package cache.main;
import java.io.*;
import java.nio.file.*;
import cache.algorithm.CacheBlock;
import java.util.Scanner;

public class Cachemain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		String[] block = new String[30];
		CacheBlock cacheBlock = new CacheBlock();
		int index = 0;
		
		try {
			File file = new File("input-txt.txt");
			for (String line : Files.readAllLines(file.toPath())) {
			    for (String part : line.split("\\s+")) {
			    	if(Integer.parseInt(part) >= 99 || index>=30) break;
			    	block[index++] = part;
			    	cacheBlock.LFU(part);
			    	cacheBlock.ProgressUpdate();
			    	cacheBlock.ProgressRankUpdate();
			    }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cacheBlock.showProgress(block);
		cacheBlock.showProgressRank(block);
		cacheBlock.showLastCacheBlock();
		System.out.println();
		System.out.print("종료하려면 1을 입력하세요: ");
		while(true) {
			String str = scan.nextLine();
			if(str.equals("1")) {
				break;
			}
		}
		scan.close();
	}
}

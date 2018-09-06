package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileTest {

	public static void main(String[] args) throws IOException {
		
		//来一条写文件
		//存缓存
		//处理好了，删除文件
		/*List<String> lines=new ArrayList<String>();
		lines.add("6");
		lines.add("7");
		lines.add("8");
		lines.add("9");
		lines.add("10");
		FileUtils.writeLines(file, lines, true);*/
		//FileUtils.r
		
		//处理好了，删除缓存
		File file=new File("D://logs//logs1.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		FileUtils.writeStringToFile(file, "hello11112");
		String message=FileUtils.readFileToString(file);
		System.out.println(message);

	}

}

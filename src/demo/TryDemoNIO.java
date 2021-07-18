package demo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TryDemoNIO {

	public static void main(String[] args) {
		Path path = Paths.get("sample.txt");
		System.out.println(path.toAbsolutePath().getFileName());
		boolean exists = Files.exists(path);
		System.out.println(exists);
		
		
		List<Runnable> list = new ArrayList<Runnable>();
		list.add(TryDemoNIO::tryFileToPath);
		list.add(TryDemoNIO::tryPathGetRoot);
		for (var r : list) {
			r.run();
		}
	}
	
	private static void tryFileToPath() {
		System.out.println("******* TryFileToPath *******");
		File file = new File("sample.txt");
		Path path = file.toPath();
		System.out.println(path.getFileName());
	}

	private static void tryPathGetRoot() {
		System.out.println("******* TryPathGetRoot *******");
		Path path = Paths.get("sample.txt");
		
		System.out.println(path.getRoot());
	}
	
}

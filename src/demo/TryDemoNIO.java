package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
		list.add(TryDemoNIO::tryReadFileNotFound);
		list.add(TryDemoNIO::tryReadFile);
		list.add(TryDemoNIO::tryReadFileAll);
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
	
	private static void tryReadFileNotFound() {
		System.out.println("******* TryReadFileNotFound *******");
		
		Path path = Paths.get("notfound.txt");
		try (InputStream in = Files.newInputStream(path); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (NoSuchFileException  e) {
		    System.err.println("File does not exist. File: " + e.getFile());
		} catch (IOException  e) {
		    System.err.println(e);
		}

	}
	
	private static void tryReadFileAll() {
		System.out.println("******* TryReadFileAll *******");
		
		try {
			Path path = Paths.get("sample.txt");
			String contents = Files.readString(path, Charset.forName("UTF-8"));
			System.out.println(contents);
		} catch (IOException  e) {
		    System.err.println(e);
		}
	}
	
	private static void tryReadFile() {
		System.out.println("******* TryReadFile *******");
		
		Path path = Paths.get("sample.txt");
		try (InputStream in = Files.newInputStream(path); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		} catch (NoSuchFileException  e) {
		    System.err.println("File does not exist. File: " + e.getFile());
		} catch (IOException  e) {
		    System.err.println(e);
		}

	}
	
}

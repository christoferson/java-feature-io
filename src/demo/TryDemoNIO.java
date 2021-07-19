package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TryDemoNIO {

	public static void main(String[] args) {

		List<Runnable> list = new ArrayList<Runnable>();
		list.add(TryDemoNIO::tryFileExists);
		list.add(TryDemoNIO::tryFileToPath);
		list.add(TryDemoNIO::tryPathGetRoot);
		list.add(TryDemoNIO::tryPathAttributes);
		list.add(TryDemoNIO::tryPathSibling);
		list.add(TryDemoNIO::tryPathNormalize);
		list.add(TryDemoNIO::tryPathResolve);
		list.add(TryDemoNIO::tryPathFactory);
		list.add(TryDemoNIO::tryReadFileNotFound);
		list.add(TryDemoNIO::tryReadFileLineByLine);
		list.add(TryDemoNIO::tryReadFileUnbufferedLineByLine);
		list.add(TryDemoNIO::tryReadFileAll);
		list.add(TryDemoNIO::tryWriteFileLineByLine);
		list.add(TryDemoNIO::tryCopyFile);
		list.add(TryDemoNIO::tryMoveFile);
		list.add(TryDemoNIO::tryFileCreateTemporaryFile);
		list.add(TryDemoNIO::tryDirectoryExists);
		list.add(TryDemoNIO::tryDirectoryStream);
		list.add(TryDemoNIO::tryStreamFilesFind);
		for (var r : list) {
			r.run();
		}
		
	}
	
	private static void tryFileExists() {
		System.out.println("******* TryFileExists *******");
		Path path = Paths.get("sample.txt");
		boolean exists = Files.exists(path);
		System.out.println(exists);
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

	private static void tryPathAttributes() {
		System.out.println("******* TryPathAttributes *******");
		Path path = Paths.get("sample-dir\\dummy-2.txt");
		System.out.println("Exists   : " + Files.exists(path));
		System.out.println("Parent   : " + path.getParent());
		System.out.println("Root     : " + path.getRoot());
		System.out.println("FileName : " + path.getFileName());
		System.out.println("NameCount: " + path.getNameCount());
		System.out.println("Name(0): " + path.getName(0));
		System.out.println("Name(1): " + path.getName(1));
		System.out.println("SubPath(0,2): " + path.subpath(0, 2));
		Path sibling = path.resolveSibling("dummy-1.txt");
		System.out.println(sibling);
	}
	
	private static void tryPathSibling() {
		System.out.println("******* TryPathSibling *******");
		Path path = Paths.get("sample-dir\\dummy-2.txt");
		Path sibling = path.resolveSibling("dummy-1.txt");
		System.out.println(sibling);
		System.out.println(Files.exists(sibling));
	}

	private static void tryPathNormalize() {
		System.out.println("******* TryPathNormalize *******");
		Path path = Paths.get("sample-dir\\.\\dummy-2.txt");
		Path npath = path.normalize();
		System.out.println("Original : " + path);
		System.out.println("New      : " + npath);
	}
	
	private static void tryPathResolve() {
		System.out.println("******* TryPathResolve *******");
		Path path = Paths.get("sample-dir");
		Path npath = path.resolve("dummy-2.txt");
		System.out.println(npath);
	}
	
	private static void tryPathFactory() {
		System.out.println("******* TryPathFactory *******");
		{
			Path path = Path.of("sample-dir", "dummy-2.txt");
			System.out.println(path);
			System.out.println(Files.exists(path));
		}
		{
			URI uri = URI.create("file:///unknown.txt");
			Path path = Path.of(uri);
			System.out.println(path);
			System.out.println(Files.exists(path));		
		}
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
	
	private static void tryReadFileLineByLine() {
		System.out.println("******* TryReadFileLineByLine *******");
		
		Path path = Paths.get("sample.txt");
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
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
	
	
	private static void tryReadFileUnbufferedLineByLine() {
		System.out.println("******* TryReadFileUnbufferedLineByLine *******");
		
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
	
	
	private static void tryWriteFileLineByLine() {
		System.out.println("******* TryWriteFileLineByLine *******");
		
		Path path = Paths.get("sample-out.txt");
		
		if (Files.exists(path)) { try { Files.delete(path); } catch (IOException e) { System.err.println(e); } }
		
		List<String> lines = List.of(String.format("Written at %s", LocalDate.now()), "Line1", "Line2", "Line3");
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
		    for (String line : lines) {
		    	writer.write(line);
		    	writer.newLine();
		    }
		    System.out.println("Wrote file " + path);
		} catch (IOException  e) {
		    System.err.println(e);
		}

	}
	
	private static void tryCopyFile() {
		System.out.println("******* TryCopyFile *******");
		
		Path src = Paths.get("sample-out.txt");
		Path dst = Paths.get("sample-out-copy.txt");
		
		//if (Files.exists(dst)) { try { Files.delete(dst); } catch (IOException e) { System.err.println(e); } }
		
		try {
		    Files.copy(src,  dst, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
		    System.out.println(String.format("Copied file %s to %s.", src, dst));
		} catch (IOException  e) {
		    System.err.println(e);
		}

	}
	
	private static void tryMoveFile() {
		System.out.println("******* TryMoveFile *******");
		
		Path src = Paths.get("sample-out-move-1.txt");
		Path dst = Paths.get("sample-out-move-2.txt");
		
		try {
		    Files.copy(Paths.get("sample-out.txt"),  src, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
		    Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
		    System.out.println(String.format("Moved file %s to %s.", src, dst));
		} catch (IOException  e) {
		    System.err.println(e);
		}

	}

	// File.deleteOnExit()
	// Runtime.getRuntime().addShutdownHook(new Thread() { ... });
	// StandardOpenOption.DELETE_ON_CLOSE
	private static void tryFileCreateTemporaryFile() {
		System.out.println("******* TryFileCreateTemporaryFile *******");
		Path path = Paths.get("sample-dir");
		Path file;
		try {
			file = Files.createTempFile(path, "out-", ".tmp");
			System.out.println(file);
			Files.writeString(file,String.valueOf(LocalDate.now()), Charset.forName("UTF-8"), StandardOpenOption.DELETE_ON_CLOSE);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println();
	}
	
	private static void tryDirectoryExists() {
		System.out.println("******* TryDirectoryExists *******");
		Path path = Paths.get("sample-dir");
		boolean exists = Files.exists(path);
		System.out.println(exists);
	}
	
	private static void tryDirectoryStream() {
		System.out.println("******* TryDirectoryStream *******");
		Path path = Paths.get("sample-dir");
		DirectoryStream<Path> stream;
		try {
			stream = Files.newDirectoryStream(path, "dummy-1*.{txt}");
			stream.forEach(System.out::println);
		} catch (IOException e) {
			System.err.println(e);
		}
		
		System.out.println();
	}
	
	private static void tryStreamFilesFind() {
		System.out.println("******* TryStreamFilesFind *******");
		
		Path rootpath = Paths.get(".");
		
		int maxDepth = 5;
	    try (Stream<Path> stream = Files.find(rootpath, maxDepth, (path, attr) -> String.valueOf(path).endsWith(".txt"))) {
	        String joined = stream
	                .sorted()
	                .map(String::valueOf)
	                .collect(Collectors.joining("; "));
	        System.out.println("Found: " + joined);
	    } catch (Exception e) {
	    	System.err.println(e);
	    }

	}
	

}

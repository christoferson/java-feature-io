package demo.model;

public class AutoCloseableFaultyResource implements AutoCloseable {

	private String name;

    public AutoCloseableFaultyResource(String name) {
		this.name = name;
	}

	@Override
    public void close() throws Exception {
        System.out.println("Closing Faulty Resource " + name);
        throw new InterruptedException("Resource Crashed!");
    }

    public void connect() throws Exception {
        System.out.println("Connect to Faulty Resource " + name);
        throw new InterruptedException("Server Refused Connection!");
    }

}

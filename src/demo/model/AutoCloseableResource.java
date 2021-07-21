package demo.model;

public class AutoCloseableResource implements AutoCloseable {

	private String name;

    public AutoCloseableResource(String name) {
		this.name = name;
	}

	@Override
    public void close() throws Exception {
        System.out.println("Closing Resource " + name);
    }

    public void connect() throws Exception {
        System.out.println("Connect to Resource " + name);
    }
    
    public void query() throws Exception {
        System.out.println("Run Query " + name);
        throw new InterruptedException("Query Timedout!");
    }

}

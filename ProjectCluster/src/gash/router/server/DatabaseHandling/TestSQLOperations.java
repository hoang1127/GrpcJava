/**
 * TestSQLOperations.java
 */
package gash.router.server.DatabaseHandling;


public class TestSQLOperations {
    private SaveDB SaveDB;

    public TestSQLOperations() {
        this.SaveDB = new SaveDB();
    }

    public void createTable() throws Exception {
        SaveDB.createTable();
    }

    public void dropTable() throws Exception {
        SaveDB.dropTable();
    }
    
    public void insertRecordFileChunk(int chunkId) throws Exception {
        String temp = "Hello every.\n" +
                "How are you?\n" +
                "\n" +
                "Test drop table.";
        String fileName = "files/input.txt";
        int chunkID = chunkId;
        byte[] data = temp.getBytes();
        int TotalChunks = 10;
        String file_id = "Just for testing.";
        SaveDB.insertRecordFileChunk(fileName, chunkID, data, TotalChunks, file_id);

    }

    public void selectRecordFileChunk() throws Exception {
        String fileName = "test.txt";
        int chunkID = 0;
        ChunkFileClass result = SaveDB.selectRecordFileChunk(fileName, chunkID);
        System.out.println("Selecting results: " + result);
    }

    public void tearDown() throws Exception {
        this.SaveDB.dropTable();
    }
    
    public void checkFileExist(String fname) {
    	boolean answer = this.SaveDB.checkFileExist("files/simplefile.txt");
    	System.out.println("checkFileExist(\"" + fname + "\") = " + answer);
    }
}

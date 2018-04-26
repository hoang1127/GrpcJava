/**
 * ChunkFileClass.java
 */

package gash.router.server.storage;

import java.util.Arrays;


public class ChunkFileClass {
   
    private int chunkID;
    private int TotalChunks;
    private String file_name;
    private String file_id;
    private byte[] data;

    public ChunkFileClass(String file_name, int chunkID, byte[] data, int TotalChunks, String file_id) {
        this.data = data;
        this.chunkID = chunkID;
        this.file_id = file_id;
        this.file_name = file_name;
        this.TotalChunks = TotalChunks;
        
    }

    public ChunkFileClass(String file_name, int chunkID, byte[] data, int TotalChunks) {
        this.data = data;
        this.chunkID = chunkID;
        this.file_name = file_name;
        this.TotalChunks = TotalChunks;
    }

    public ChunkFileClass(String file_name, int chunkID, byte[] data) {
        this.data = data;
        this.file_name = file_name;
        this.chunkID = chunkID;
        
    }

    public ChunkFileClass(String file_name, int chunkID) {
        this.chunkID = chunkID;
        this.file_name = file_name;
        
    }

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String file_name) {
        this.file_name = file_name;
    }

    public int getChunkID() {
        return chunkID;
    }

    public void setChunkID(int chunkID) {
        this.chunkID = chunkID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getTotalNoOfChunks() {
        return TotalChunks;
    }

    public void setTotalNoOfChunks(int TotalChunks) {
        this.TotalChunks = TotalChunks;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    @Override
    public String toString() {
        return "FileChunk {" +
                "fileName='" + file_name + '\'' +
                ", chunkID=" + chunkID +
                ", data=" + Arrays.toString(data) +
                ", TotalChunks=" + TotalChunks +
                ", file_id='" + file_id + '\'' + '}';
    }
}

package gash.router.server.messages;

import gash.router.container.RoutingConf;
import gash.router.server.CommandHandler;
import gash.router.server.ServerState;
import gash.router.server.edges.EdgeInfo;
import gash.router.server.raft.MessageUtil;
import gash.router.server.raft.RaftHandler;
import gash.router.server.storage.ClassFileChunkRecord;
import gash.router.server.storage.MySQLStorage;
import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pipe.common.Common;
import pipe.common.Common.Header;
import pipe.common.Common.Response;
import pipe.common.Common.TaskType;
import pipe.common.Common.WriteBody;
import pipe.work.Work.WorkMessage;
import routing.Pipe.CommandMessage;

public class CommandSession implements Session, Runnable{
    protected static Logger logger = LoggerFactory.getLogger("server");
    private RoutingConf conf;
    private CommandMessage message;
    private Channel channel;

    public CommandSession(RoutingConf conf, CommandMessage msg, Channel channel) {
        this.conf = conf;
        this.message = msg;
        this.channel = channel;
    }

    //Handling for command session from server
    @Override
    public void handleMessage() {
    	TaskType type = message.getRequest().getRequestType();
        try {
            // When curretn id == message destination. Accept or transfer the message to new node
           	if (message.hasPing()) {
                //return ping ack
                Header.Builder hb = Header.newBuilder();
                hb.setNodeId(conf.getNodeId());
                hb.setTime(System.currentTimeMillis());

                CommandMessage.Builder cm = CommandMessage.newBuilder();
                cm.setHeader(hb);
                cm.setPing(true);
                channel.writeAndFlush(cm); //respond back to client
                
        	}else if (message.hasRequest()) {
        		
            	logger.info("CommendSession handleMessage RequestType is " + type);

    			Hashtable<Integer, String> location = new Hashtable<Integer, String>();
        		String fileName = message.getRequest().getRrb().getFilename();
        		
            	if (type == TaskType.REQUESTREADFILE) {
            	
            		logger.info("read fileName is " + fileName);
            		MySQLStorage mySQLStorage = MySQLStorage.getInstance();
            		
            		// When fileName exists on server.
            		if (fileName.equals("ls_all_the_files_and_chunks")) {
            			
            			ArrayList<ClassFileChunkRecord> fileList = mySQLStorage.selectAllRecordsFileChunk();
                		//HashMap of locations
            			CommandMessage cm = MessageUtil.buildCommandMessage(
            					MessageUtil.buildHeader(conf.getNodeId(), System.currentTimeMillis()),
            					null,
            					null,
            					MessageUtil.buildResponse(TaskType.RESPONSEREADFILE, fileName, null , null, 
            								MessageUtil.buildReadResponseAllFiles(-1, fileName, fileList.size(), 
            										fileList, null)));

                		channel.writeAndFlush(cm);
                		
            		} else if (mySQLStorage.checkFileExist(fileName)) {
            			// First message from the client. 
            			// The client says "I want to read the file whose name is fileName." chunkID in the request will be -1.
            			int chunkId = message.getRequest().getRrb().getChunkId();
            			logger.info("chunkID is " + chunkId);
            			
            			// By default to be 0;
            			if (!message.getRequest().getRrb().hasChunkId()) {

                			// Get chunkIDs of the file from the database on the leader. 
                			// All the nodes have same data and we just return the records on the leader.
                			ArrayList<Integer> chunkIDArray = mySQLStorage.selectRecordFilenameChunkID(fileName);
                    		logger.info("The first time to receive the read request from client, and server will return a location String");
                			String host = "";
                			try {
    							Enumeration enm = NetworkInterface.getNetworkInterfaces();
    							String pattern1 = "169.254.*.*";
    							String pattern2 = "192.168.*.*";
    							while(enm.hasMoreElements())
    							{
    								boolean isMatch1 = false, isMatch2 = false;
    							    NetworkInterface netw = (NetworkInterface) enm.nextElement();
    							    Enumeration enu = netw.getInetAddresses();
    							    while (enu.hasMoreElements())
    							    {
    							        InetAddress ine = (InetAddress) enu.nextElement();
    							        String ipAddress = ine.getHostAddress();

    							        isMatch1 = Pattern.matches(pattern1, ipAddress);
    							        isMatch2 = Pattern.matches(pattern2, ipAddress);
    							        
    							        if (isMatch1 || isMatch2) {
    							        	host = ipAddress;
    							        	break;
    							        }
    							        else {
    							        	host = "localhost";
    							        }						        
    							    }
    							    if (isMatch1 || isMatch2) break;
    							}
                			} catch (Exception err) {
    							err.printStackTrace();
    						}
                			int port = conf.getCommandPort();
                			int nodeID = conf.getNodeId();
                			String locationAddress = nodeID + ";" + host + ";" + port;
                			for (Integer i : chunkIDArray) {
                				location.put(i, locationAddress);
                			}

                    		//return to client a HashMap of locations
                			CommandMessage cm = MessageUtil.buildCommandMessage(
                					MessageUtil.buildHeader(conf.getNodeId(), System.currentTimeMillis()),
                					null,
                					null,
                					MessageUtil.buildResponse(TaskType.RESPONSEREADFILE, fileName, null , null, 
                								MessageUtil.buildReadResponse(-1, fileName, null, location.size(), 
                										location, null)));

                    		channel.writeAndFlush(cm);
                    	//chunk data request
                    	} else {
                    		// Second time to receive the message from the client which has received the HashMap<chunkID, location> from the server (Only one location of each chunk).
                    		// The client: "I want to get the data of chunkId from the location."
                			mySQLStorage = MySQLStorage.getInstance();
                			String fileName = message.getRequest().getRrb().getFilename();
                			int chunkID = message.getRequest().getRrb().getChunkId();
                			ClassFileChunkRecord record = mySQLStorage.selectRecordFileChunk(fileName, chunkID);
                			byte[] chunkData = record.getData();
                			int chunkSize = record.getTotalNoOfChunks();

                			CommandMessage cm = MessageUtil.buildCommandMessage(
                					MessageUtil.buildHeader(conf.getNodeId(), System.currentTimeMillis()),
                					null,
                					null,
                					MessageUtil.buildResponse(TaskType.RESPONSEREADFILE, fileName, null , null, 
                								MessageUtil.buildReadResponse(-1, fileName, null, location.size(), 
                										location, MessageUtil.buildChunk(chunkID, chunkData, chunkSize))));
              
                			channel.writeAndFlush(cm);
                    	}
                	}
            		else {
            			// File isn't in the database and return to client FAIL to read
            			CommandMessage cm = MessageUtil.buildCommandMessage(
            					MessageUtil.buildHeader(conf.getNodeId(), System.currentTimeMillis()),
            					null,
            					null,
            					MessageUtil.buildResponse(TaskType.RESPONSEREADFILE, fileName, Response.Status.FILENOTFOUND , null, null));
                        logger.info("File " + fileName + " isn't in the database. Read failed on the server.");
                        channel.writeAndFlush(cm);
                	}
            	}
            	
            	/* WRITE */
            	else if (type.equals(TaskType.REQUESTWRITEFILE)) {
            		
            		MySQLStorage mySQLStorage = MySQLStorage.getInstance();
            		WriteBody wb = message.getRequest().getRwb();
            		fileName = wb.getFilename();
    	    		int chunkId = wb.getChunk().getChunkId();
    	    		int nodeId = message.getHeader().getNodeId();
    	    		
            		if(conf.getNodeId() == RaftHandler.getInstance().getLeaderNodeId()) {
            			//Add into channels table
    					if (!mySQLStorage.checkFileChunkExist(fileName, chunkId)) {
    						//if it is not write before
	    					if ((nodeId % 10) == RoutingConf.clusterId) { 
	    						if (!ServerState.channelsTable.containsKey(nodeId)) {
	    							CommandHandler.handleClientRequest(channel, nodeId);
	    						} else {
		    						//stop here
		    						Hashtable<Channel, Integer> client = ServerState.channelsTable.get(nodeId);
		    						Channel firstChannel = client.keys().nextElement();
		    						
		    						//build successful response cm
		    						CommandMessage cm = MessageUtil.buildCommandMessage(
		    								MessageUtil.buildHeader(RoutingConf.clusterId, System.currentTimeMillis()),
		    								null,
		    								null,
		    								MessageUtil.buildResponse(TaskType.RESPONSEWRITEFILE,
                                            message.getRequest().getRwb().getFilename(),
		    										Response.Status.SUCCESS,
		    										null, 
		    										null));
		    									
		    						firstChannel.writeAndFlush(cm);
		    						CommandHandler.updateChannelsTable(client, firstChannel, nodeId);
		    						return;
	    						}
	    						
	        					ServerState.nextCluster.writeAndFlush(message);
	        					logger.info("Forwarding a WRITE message get from client id " + nodeId);
	    					
	    					} else { //from your neighbor
	    						//forward anyway
	    						ServerState.nextCluster.writeAndFlush(message);
	    						logger.info("Forwarding a WRITE message get from the neighbor cluster...");
	    					}
    					} else {
    						logger.info("The file already in the system. Ignore and not forward the message");
    					}
            		}
            		
    	    		byte[] data = wb.getChunk().getChunkData().toByteArray();
    	    		int numOfChunk = wb.getChunk().getChunkSize();
    	    		String fileId = "";
    	    		if (wb.hasFileId()) {
    	    			fileId = wb.getFileId();
    	    		}
    	    		
            		// If the file and chunkid already in server.
            		if (!mySQLStorage.checkFileChunkExist(fileName, chunkId)) {
            			
	    	    		boolean result = mySQLStorage.insertRecordFileChunk(fileName, chunkId, data, numOfChunk, fileId);
	    	    		logger.info("Writing the file <" + fileName + "> with its chunk_id <"+ chunkId+ "> out of <"+numOfChunk+"> into DB.");
	    	    		
	    	    		if (result) {
	    	    			// Replication
		    	    		for(EdgeInfo ei:RaftHandler.getInstance().getEdgeMonitor().getOutboundEdges().getMap().values()){
		    					if (ei.isActive() && ei.getChannel().isActive()) {
		    						int internalNodeId = ei.getRef();
		    						String nodehost = ei.getHost();
		    						int port = ei.getPort();
		    						
		    						WorkMessage wm = MessageUtil.replicateData(internalNodeId, nodehost, port, msg);
		    						ei.getChannel().writeAndFlush(wm);
		    					}
		    	    		}
	    	    		}
            		}
    	    	} else { }//Update and Delete:
    	    }
        } catch (Exception e) {
            // TODO add logging
            Common.Failure.Builder eb = Common.Failure.newBuilder();
            eb.setId(conf.getNodeId());
            eb.setRefId(message.getHeader().getNodeId());
            eb.setMessage(e.getMessage());
            CommandMessage.Builder rb = CommandMessage.newBuilder(message);
            rb.setErr(eb);
            channel.write(rb.build());
            e.printStackTrace();
        }
        System.out.flush();
    }

    @Override
    public void run() {
        handleMessage();
    }
    // Get message command
    public CommandMessage getMsg() {
        return message;
    }
    // Set message
    public void setMsg(CommandMessage msg) {
        this.message = msg;
    }
}

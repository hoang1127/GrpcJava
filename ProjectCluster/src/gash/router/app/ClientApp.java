/**
 * Copyright 2016 Gash.
 *
 * This file and intellectual content is protected under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package gash.router.app;

import gash.router.client.CommConnection;
import gash.router.client.CommListener;
import gash.router.client.MessageClient;
import gash.router.routingConfiguration.RoutingConf;
import routing.Pipe.CommandMessage;
import gash.router.redis.RedisDatabaseServer;

import java.util.Scanner;

public class ClientApp implements CommListener {
	private MessageClient mc;
	public static String connectedClusterId;
	public static String host = null;
	public static int port;

	public ClientApp(MessageClient mc) {
		init(mc);
	}
	private void init(MessageClient mc) {
		this.mc = mc;
		this.mc.addListener(this);
	}

	@Override
	public String getListenerID() {
		return "Client";
	}

	@Override
	public void onMessage(CommandMessage msg) {
    
    }	
	private void menu() {
		Scanner scanner = new Scanner(System.in);	 
		String command = null;
		String[] commands;
	
		do {
			System.out.flush();
	        System.out.print("\n\n-------------------------------------\n" +
	        				"Menu: Host: " + host + ":" + port 
	        				+ "\n--------------------------------------\n" +
	        				"* ping <node_id>\n" +
	        				"* write <file_name>\n" +
	                        "* read <file_name>\n" + 
	                        "* quit\n\n\n" +
	                        "> ");
	        System.out.flush();
	
			command = scanner.nextLine();
			
			commands = command.split("\\s+");
			// Set selection
	        switch(commands[0]) {
	          	case "ping":
	          		if(commands.length > 1)
	          			mc.ping(Integer.valueOf(commands[1]));
	          		System.out.println("\nSyntax: ping <cluster_id>\n");
	        	  	break;
				case "leader":
				
					RedisDatabaseServer.getInstance().getjedis().select(0);
					String leader = RedisDatabaseServer.getInstance().getjedis().get(""+RoutingConf.clusterId);
					System.out.println("Cluster <" + ClientApp.connectedClusterId + "> has leader node <" + leader + ">");
					break;
	          	case "read" :
	                  if(commands.length > 1)
	                	  mc.sendReadRequest(commands[1]);
	                  break;
	          	case "write" :
	                  if(commands.length > 1)
	                	  mc.splitFileAndProcess(commands[1]);
	                  break;
	          	case "ls" :
	                	  mc.lsFiles();
	                  break;
	          	default:
	                  break;
	        }
	      } while(!commands[0].equals("quit"));
		
		//close scanner command line
		scanner.close();
  }

	/**
	 * sample application (client) use of our messaging service
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length > 0) {
			connectedClusterId = args[0];
			RedisDatabaseServer.getInstance().getjedis().select(0);
			String leader = RedisDatabaseServer.getInstance().getjedis().get(connectedClusterId);
			
			if(leader != null) {

				host = leader.split(":")[0];
				port = Integer.parseInt(leader.split(":")[1]);
			} else{
				host = "localhost";
				port = 5510;
			}

			try {
				MessageClient mc = new MessageClient(host, port);
				ClientApp ca = new ClientApp(mc);

				ca.menu();

				System.out.println("\n Exiting in 20 seconds.");

				System.out.flush();
				Thread.sleep(20000);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommConnection.getInstance().release();
			}
		} 
	else {
			System.out.println("\nSyntax: client.sh <CLUSTER_ID>\n");
		}
	}
}

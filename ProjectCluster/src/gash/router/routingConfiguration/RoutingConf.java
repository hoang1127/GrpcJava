/**
 * Copyright 2012 Gash.
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
package gash.router.routingConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Routing information for the server - internal use only
 * 
 * @author gash
 * 
 */
@XmlRootElement(name = "conf")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoutingConf {
	private int nodeId;
	private int workPort;
	private int commandPort;
	private int heartbeatDt = 2000;
	private boolean internalNode = true;
	
	private List<RoutingEntry> routing;
	
	public static String redis = "localhost";
	public static String dbUser = "root";
	public static String dbPassword = "root" ;

	public static int maxHops = 10;
	public static int clusterId = 1; 
	public static int clientId = clusterId * 10 + clusterId;

	
	public HashMap<String, Integer> asHashMap() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		if (routing != null) {
			for (RoutingEntry entry : routing) {
				System.out.println("RoutingConf asHashMap(): host = " + entry.host + " port = " + entry.port);
				map.put(entry.host, entry.port);
			}
		}
		return map;
	}
    // Add new Entry
	public void addEntry(RoutingEntry entry) {
		if (entry == null)
			return;

		if (routing == null)
			routing = new ArrayList<RoutingEntry>();

		System.out.println("RoutingConf addEntry(): host = " + entry.host + " port = " + entry.port);
		routing.add(entry);
	}
    // Get node id
	public int getNodeId() {
		return nodeId;
	}
    // Set node id
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
    // return port
	public int getCommandPort() {
		return commandPort;
	}
    // set port
	public void setCommandPort(int commandPort) {
		this.commandPort = commandPort;
	}
    // retrieve working port
	public int getWorkPort() {
		return workPort;
	}
    // set working port
	public void setWorkPort(int workPort) {
		this.workPort = workPort;
	}
    // Check inter node
	public boolean isInternalNode() {
		return internalNode;
	}

	public void setInternalNode(boolean internalNode) {
		this.internalNode = internalNode;
	}
    // Get heartbeat
	public int getHeartbeatDt() {
		return heartbeatDt;
	}

	public void setHeartbeatDt(int heartbeatDt) {
		this.heartbeatDt = heartbeatDt;
	}
    // List for routing
	public List<RoutingEntry> getRouting() {
		return routing;
	}

	public void setRouting(List<RoutingEntry> conf) {
		this.routing = conf;
	}

	@XmlRootElement(name = "entry")
	@XmlAccessorType(XmlAccessType.PROPERTY)
	public static final class RoutingEntry {
		private String host;
		private int port;
		private int id;

		public RoutingEntry() {
		}

		public RoutingEntry(int id, String host, int port) {
			this.id = id;
			this.host = host;
			this.port = port;
		}
        // Host
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
        }
        // Port
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
        }
        // ID
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	}
}

package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public abstract class Node extends Thread {
	public Node rgt;

	public int id_nodo;
	private static final Lock lock = new ReentrantLock();
	private static Condition[] conditions;
	
	public boolean finished = false;
	public boolean rgtbool = false;
	public boolean rgtSent = false;
	public Node lft;
	public boolean lftbool = false;
	public boolean lftSent = false;
	public Node pnt; // Parent node
	
	protected static Map<String, ArrayList<String>> trazas = new HashMap<>();
	protected static BidiMap<String, String> event_type = new DualHashBidiMap<>();
	protected List<String> timestamps = new ArrayList<>();

	public Node(int id,int total, Node parent) {

		this.id_nodo = id;
		this.pnt = parent;
		if(pnt == null) {
			conditions = new Condition[total];
		}
		conditions[id_nodo] = lock.newCondition();
	}

	public void setSons(Node node1, Node node2) {
		this.lft = node1;
		this.rgt = node2;
	}
	
	protected String getEvent(String e) {
		return event_type.getKey(e);
	}

	public abstract boolean evaluarCondicion();

	@Override
	public void run() {
		lock.lock();
		try {
			if (lft != null) {
				lft.start();
				while(!lftSent) {
					conditions[id_nodo].await();
				}
			}
			if (rgt != null) {
				rgt.start();
				while(!rgtSent) {
					conditions[id_nodo].await();
				}
			}
			if (pnt != null && (lft == null || lftSent || rgtSent)) {
				if (pnt.lft.equals(this)) {
					pnt.lftbool = evaluarCondicion();
					pnt.lftSent = true;
					if(pnt != null)
						conditions[pnt.id_nodo].signal();
	
				} else {
					pnt.rgtbool = evaluarCondicion();
					pnt.rgtSent = true;
					if(pnt != null)
						conditions[pnt.id_nodo].signal();
				}
			}		
			if(pnt == null) {
				finished = true;
			}
			
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); 
		} finally {
			lock.unlock();
		}
	}

	public void setTimestamp(String ts1, String ts2) {
		timestamps.add(ts1);
		if (!ts2.equals(""))
			timestamps.add(ts2);
	}
}

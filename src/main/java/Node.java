import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node extends Thread {
	public String formula;
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

	public Node(String f,int id,int total, Node parent) {
		formula = f;
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

	public boolean evaluarCondicion() {
		switch (formula.toUpperCase()) {
		case "TRUE":
			return true;
		case "FALSE":
			return false;
		case "NOT":
			System.out.print("Soy Nodo " + id_nodo);
			System.out.println(" tengo NOT y devuelvo " + !lftbool);
			return !lftbool;
		case "":
			return lftbool;
		case "AND":
			System.out.print("Soy Nodo " + id_nodo);
			System.out.println(" tengo AND y devuelvo " + (lftbool && rgtbool));
			return lftbool && rgtbool;
		case "OR":
			System.out.print("Soy Nodo " + id_nodo);
			System.out.println(" tengo OR y devuelvo " + (lftbool || rgtbool));
			return lftbool || rgtbool;
		default:
			return false;
		}
	}

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
}
package org.hacker.engine.war.replay;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hacker.engine.war.WarModel;
import org.hacker.engine.war.order.BuildOrder;
import org.hacker.engine.war.order.MoveOrder;
import org.hacker.engine.war.order.Order;
import org.hacker.engine.war.order.StopOrder;

public class ReplayReader {
	private DataInputStream dinp;
	private double curTime;
	private int baseTime;
	private Map<Integer, Order> orders = new HashMap<Integer, Order>();
	private Order nextOrder;
	private int nextUnitID;
	private double nextOrderTime;
	private int bytesInId = 1;

	public ReplayReader(DataInputStream dinp) {
		this.dinp = dinp;
		
		readOrders();
	}

	private void readOrders() {
		while (nextOrder == null) {
			try {
				int orderType = dinp.readUnsignedByte();
				if (orderType == ReplayOutputStream.OVERFLOW_MARKER) {
					bytesInId++;
					orderType = dinp.readByte();
				}

				int deltaTime = dinp.readByte();
				baseTime += deltaTime;
				int bt = (baseTime * WarModel.THINK_EVERY);
				double time = bt / 10.0;

				int unitID = 0;
				for (int i = bytesInId - 1; i >= 0; i--) {
					unitID |= dinp.readUnsignedByte() << (8 * i);
				}

				// System.out.println("order at " + time + " for " + unitID + "
				// of " + orderType);
				// System.out.println("rd tm: " + bt + " " + time + ", ord: " +
				// orderType + ", unit: " + unitID);
				Order order;
				switch (orderType) {
				case Order.BUILD:
					int buildType = dinp.readUnsignedByte();
					order = BuildOrder.fromType(buildType);
					break;
				case Order.MOVE:
					short sx = dinp.readShort();
					short sy = dinp.readShort();
					float x = sx / 10.0f;
					float y = sy / 10.0f;
					order = new MoveOrder(x, y);
					break;
				case Order.STOP:
					order = new StopOrder();
					break;
				default:
					throw new RuntimeException("illegal order: " + orderType);
				}
				if (orders.size() == 0 || deltaTime == 0) {
					orders.put(unitID, order);
					curTime = time;
				}
				else {
					nextOrder = order;
					nextUnitID = unitID;
					nextOrderTime = time;
				}
			} catch (IOException e) {
				System.out.println("end of replay stream");
				break;
			}
		}
	}

	public double getTime() {
		return curTime;
	}

	public boolean hasUnitID(int id) {
		return orders.containsKey(id);
	}

	/**
	 * @param id
	 * @return orders until none left, then throws an exception 
	 */
	public Order getOrder(int id) {
		if (!orders.containsKey(id)) throw new RuntimeException("no such order");
		Order o = orders.remove(id);
		// finished this bunch?
		if (orders.size() == 0) {
			if (nextOrder != null) {
				// move next order into play
				orders.put(nextUnitID, nextOrder);
				curTime = nextOrderTime;
				nextOrder = null;
			}
		}
		readOrders();
		return o;
	}
}

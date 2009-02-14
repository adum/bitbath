package org.hacker.engine.war.jme;

import com.jme.math.Quaternion;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

public class UnitRecord {
	public Node node, groupNode;
    public Quaternion rotQuat = new Quaternion();
//    public Line damageLine;
    public Spatial damage;
}

package org.hacker.engine.war.jme;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SharedNode;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.collada.ColladaImporter;
import com.jmex.model.collada.ColladaToJme;
import com.jmex.model.converters.FormatConverter;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.ObjToJme;

public class ModelCache {
        // types of models we know about
    public enum ModelType {
        CITY,
        UNIT,
        ARTIL,
        HOVER
    }

    // keep a record of models we've loaded so we can quickly bang out copies
    private Map<ModelType, Node> baseNodes = new HashMap<ModelType, Node>();

    public Node load(ModelType mt) throws Exception {
        Node n = baseNodes.get(mt);
        if (n != null) return new SharedNode(n);

        Matrix3f m = new Matrix3f();
        switch (mt) {
            case CITY:
//                fl = WarJme.class.getClassLoader().getResource("img/models/AdjustableTurret.dae");
//              URL colladaFile = WarJme.class.getClassLoader().getResource("img/models/SpiderBase.dae");
                n = loadModel("img/models/spider2.dae", 0.008f);
                        m.fromAngleAxis((float) Math.PI, new Vector3f(0, 1, 1));
                        n.setLocalRotation(m);
//                URL colladaFile = WarJme.class.getClassLoader().getResource("img/models/AdjustableTurret.dae");
//                  URL colladaFile = WarJme.class.getClassLoader().getResource("img/models/ndafscout.dae");
//                URL city_model=HelloModelLoading.class.getClassLoader().getResource("jmetest/data/model/maggie.obj");
                break;
            case UNIT:
//                fl = Lesson9.class.getClassLoader().getResource("jmetest/data/model/bike.jme");
//                fl = Lesson9.class.getClassLoader().getResource("jmetest/data/model/bike.3ds");
//                fl = WarJme.class.getClassLoader().getResource("img/models/funnybox/box.3ds");
//                 fl = WarJme.class.getClassLoader().getResource("img/models/AdjustableTurret.dae");
                Node nn = loadModel("img/models/boxtank.dae", 0.00185f);
                        m.fromAngleAxis((float) Math.PI, new Vector3f(0, 1, 0));
                        nn.setLocalRotation(m);
                 n = new Node("m");
                 n.attachChild(nn);
                 
//                fl = WarJme.class.getClassLoader().getResource("img/models/al.3ds");
//                scale = 0.0185f;

//                 fl = WarJme.class.getClassLoader().getResource("img/models/nilhoverl.3ds");
//                scale = 0.0585f;
                break;
            case HOVER:
                n = loadModel("img/models/hovercraft.dae", 0.00585f);
               break;
            case ARTIL:
                n = loadModel("img/models/artil2.dae", 0.00585f);
//              n = loadModel("img/models/artil.dae", 0.00585f);
               break;
            default:
                throw new IllegalArgumentException("no such model");
        }
                
        
        baseNodes.put(mt, n);
        
        return new SharedNode(n);
    }

    // load according to extension
    private Node loadModel(String path, float scale) throws Exception {
        URL fl = WarJme.class.getClassLoader().getResource(path);
        Node n = null;

        // new one
        if (fl.toString().toLowerCase().endsWith(".dae"))
            n = loadCollada(fl);
        else if (fl.toString().toLowerCase().endsWith(".obj"))
            n = loadObj(fl);
        else if (fl.toString().toLowerCase().endsWith(".jme"))
            n = loadJME(fl);
        else if (fl.toString().toLowerCase().endsWith(".3ds"))
            n = load3DS(fl);
        else
            throw new IllegalArgumentException("no such file type handled: " + fl);

        n.setLocalScale(scale);
                return n;
        }

        private Node loadJME(URL fl) throws IOException {
        BinaryImporter importer = new BinaryImporter();
        Node node = (Node) importer.load(fl.openStream());
        return node;
    }

    private Node loadCollada(URL fl) throws Exception {
        ColladaToJme c2j = new ColladaToJme();
        ColladaImporter.load(new BufferedInputStream(fl.openStream()), "cmodel");
        return ColladaImporter.getModel();
    }

    private Node loadObj(URL fl) throws Exception {
        // Create something to convert .obj format to .jme
        FormatConverter converter=new ObjToJme();
        // Point the converter to where it will find the .mtl file from
        converter.setProperty("mtllib",fl);
        // This byte array will hold my .jme file
        ByteArrayOutputStream BO=new ByteArrayOutputStream();

        // Use the format converter to convert .obj to .jme
        
        converter.convert(fl.openStream(), BO);
        Node node=(Node)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
        return node;
    }
    
    private Node load3DS(URL fl) throws Exception {
        MaxToJme C1 = new MaxToJme();
        ByteArrayOutputStream BO = new ByteArrayOutputStream();
        C1.convert(new BufferedInputStream(fl.openStream()), BO);
        return (Node) BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
    }
}

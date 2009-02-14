package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.CPEntry;
import ojvm.loading.CPIntEntry;
import ojvm.loading.CPFloatEntry;
import ojvm.loading.CPStringEntry;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an ldc_w instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ldc_w extends Instruction {
  public final static int INT_TYPE = -1;
  public final static int FLOAT_TYPE = 0;
  public final static int STRING_TYPE = 1;
  public int ctype;
  private int intValue;
  private float floatValue;
  private String stringValue;

  public Ins_ldc_w (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_ldc_w);
      int index = classFile.readU2();
      CPEntry ce = cp.get(index);
      if (ce instanceof CPIntEntry) {
          ctype = INT_TYPE;
          intValue = ((CPIntEntry)ce).getInt();
      }
      else if (ce instanceof CPFloatEntry) {
          ctype = FLOAT_TYPE;
          floatValue = ((CPFloatEntry)ce).getFloat();
      }
      else if (ce instanceof CPStringEntry) {
          ctype = STRING_TYPE;
          stringValue = ((CPStringEntry)ce).getString();
      }
      else throw new ConstantPoolE("Bad ldc_w instruction");
  }

  public int getIntValue() { return intValue; }
  public float getFloatValue() { return floatValue; }
  public String getStringValue() { return stringValue; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_ldc_w (this);
  }

  public String toString () {
    String res = opcodeName + " ";
    if (ctype == INT_TYPE) res += intValue;
    else if (ctype == FLOAT_TYPE) res += floatValue + "f";
    else res += "\"" + stringValue + "\"";
    return res;
  }
}

package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.CPEntry;
import ojvm.loading.CPLongEntry;
import ojvm.loading.CPDoubleEntry;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an ldc2_w instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ldc2_w extends Instruction {
  public final static int LONG_TYPE = -1;
  public final static int DOUBLE_TYPE = 0;
  public int ctype;
  private long longValue;
  private double doubleValue;

  public Ins_ldc2_w (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_ldc2_w);
      int index = classFile.readU2();
      CPEntry ce = cp.get(index);
      if (ce instanceof CPLongEntry) {
          ctype = LONG_TYPE;
          longValue = ((CPLongEntry)ce).getLong();
      }
      else if (ce instanceof CPDoubleEntry) {
          ctype = DOUBLE_TYPE;
          doubleValue = ((CPDoubleEntry)ce).getDouble();
      }
      else throw new ConstantPoolE("Bad ldc2 instruction");
  }

  public long getLongValue () { return longValue; }
  public double getDoubleValue () { return doubleValue; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_ldc2_w (this);
  }

  public String toString () {
    String res = opcodeName + " ";
    if (ctype == LONG_TYPE) res += longValue + "L";
    else res += doubleValue;
    return res;
  }
}

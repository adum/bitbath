package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an tableswitch instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_tableswitch extends Instruction {
  private int def, low, high;
  private int[] offsets;

  public Ins_tableswitch (InstructionInputStream classFile) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_tableswitch);
      int numPadding = classFile.skipPadding(); 
      this.length = 1; // the opcode itself
      this.length += numPadding;
    
      this.def = classFile.readInt(); 
      this.length += 4;
      this.low = classFile.readInt(); 
      this.length += 4;
      this.high = classFile.readInt(); 
      this.length += 4;
      if (this.low > this.high) throw new ConstantPoolE("Illegal tableswitch instruction");

      int size = this.high - this.low + 1;
      this.offsets = new int[size];
      for (int i=0; i<size; i++) {
          this.offsets[i] = classFile.readInt(); 
          this.length += 4;
      }
  }

  public int getOffset (int index) {
    if (index < this.low || index > this.high) return this.def;
    else return this.offsets[index-low];
  }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_tableswitch (this);
  }

  public String toString () {
    String res = opcodeName + "\n";
    res += "\tdefault = " + def + ", low = " + low + ", high = " + high + "\n";
    res += "\toffsets = ";
    for (int i=0; i<offsets.length; i++) 
      res += offsets[i] + " ";
    return res;
  }
}

package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lookupswitch instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lookupswitch extends Instruction {
  private int def, npairs;
  private int[] matches, offsets;

  public Ins_lookupswitch (InstructionInputStream classFile) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_lookupswitch);
      length = 1; // the opcode itself
      int numPadding = classFile.skipPadding(); length += numPadding;
      def = classFile.readInt(); length += 4;

      npairs = classFile.readInt(); length += 4;
      if (npairs < 0) throw new ConstantPoolE("Bad lookupswitch instruction: npairs can't be less than 0");

      matches = new int[npairs];
      offsets = new int[npairs];
      for (int i=0; i<npairs; i++) { 
	matches[i] = classFile.readInt(); length += 4;
	offsets[i] = classFile.readInt(); length += 4;
      }
  }

  public int lookup (int key) {
    for (int i=0; i<npairs; i++) {
      if (key == matches[i]) return offsets[i];
    }
    return def;
  }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_lookupswitch (this);
  }

  public String toString () {
    String result = opcodeName + "\n";
    for (int i=1; i<npairs; i++) {
      result += "\t" + matches[i] + ": " + offsets[i] + "\n";
    }
    result += "\tdefault: " + def;
    return result;
  }
}

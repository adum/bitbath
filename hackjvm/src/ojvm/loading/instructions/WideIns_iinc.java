package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iinc WideInstruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class WideIns_iinc extends Ins_iinc {

  public WideIns_iinc (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_wide, classFile.readU2(), classFile.readShort());
  }

  public String toString () {
    return opcodeName + " " + super.toString();
  }
}

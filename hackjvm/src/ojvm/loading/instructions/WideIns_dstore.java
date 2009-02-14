package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore WideInstruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class WideIns_dstore extends Ins_dstore {
  public WideIns_dstore (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_wide, classFile.readU2());
  }

  public String toString () {
    return opcodeName + " " + super.toString();
  }
}

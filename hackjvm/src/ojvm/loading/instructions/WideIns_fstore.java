package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore WideInstruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class WideIns_fstore extends Ins_fstore {
  public WideIns_fstore (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_wide, classFile.readU2());
  }

  public String toString () {
    return opcodeName + " " + super.toString();
  }
}

package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an ret WideInstruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class WideIns_ret extends Ins_ret {

  public WideIns_ret (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_wide, classFile.readU2());
  }

  public String toString () {
    return opcodeName + " " + super.toString();
  }
}

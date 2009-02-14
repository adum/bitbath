package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aload WideInstruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class WideIns_aload extends Ins_aload {
  public WideIns_aload (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_wide, classFile.readU2());
  }

  public String toString () {
    return opcodeName + " " + super.toString();
  }
}

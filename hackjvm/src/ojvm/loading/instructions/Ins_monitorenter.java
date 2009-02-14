package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an monitorenter instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_monitorenter extends Instruction {

  public Ins_monitorenter (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_monitorenter);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_monitorenter (this);
  }

  public String toString () {
    return opcodeName;
  }
}

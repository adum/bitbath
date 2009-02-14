package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2c instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2c extends Instruction {

  public Ins_i2c (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2c);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2c (this);
  }

  public String toString () {
    return opcodeName;
  }
}

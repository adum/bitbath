 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an astore_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_astore_3 extends Ins_astore {
  public Ins_astore_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_astore_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}

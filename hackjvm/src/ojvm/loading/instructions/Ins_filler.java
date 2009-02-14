package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

/**
 * The encapsulation of an filler instruction. Instructions that take
 * more than one word have all the information in the first word and
 * the rest is fillers. 
 *
 * @author Amr Sabry
 * @version jdk-1.1 */

public class Ins_filler extends Instruction {
  public final static byte OPCODE = (byte)254; // reserved for implementations

  public Ins_filler () {
    super(1); // length
  }

  public boolean isActualInstruction () { return false; }

  public void accept (InstructionVisitor iv) throws JavaException { 
      iv.visit_filler(this);
  }

  public String toString () {
    return ".";
  }
}

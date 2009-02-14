package ojvm.loading;
                         
import ojvm.util.RuntimeConstants;
import ojvm.loading.instructions.*;

/**
 * The code attribute of a method.
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class CodeAttribute extends AbsynAttribute {
    private int maxStack;
    private int maxLocals;
    private Instruction[] instructions;
    private ExceptionHandler[] exceptionHandlers;

    public String getName () { return "Code"; }
    public int getMaxStack () { return maxStack; }
    public int getMaxLocals () { return maxLocals; }
    public Instruction[] getInstructions () { return instructions; }
    public ExceptionHandler[] getExceptionHandlers () { return exceptionHandlers; }

    CodeAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ClassFileInputStreamE, ConstantPoolE {
        maxStack = classFile.readU2();
        maxLocals = classFile.readU2();

        int codeLength = classFile.readU4();
        byte[] instructionArray = classFile.readAttribute(codeLength);
        InstructionInputStream inp = new InstructionInputStream(instructionArray, classFile.getFilename());
        readInstructions(inp,cp); // initializes this.instructions

        int exceptionTable_length = classFile.readU2();
        exceptionHandlers = new ExceptionHandler[exceptionTable_length];
        for (int i=0; i<exceptionTable_length; i++) exceptionHandlers[i] = new ExceptionHandler(classFile, cp);

        // Read and ignore code attributes ...
        int attributes_count = classFile.readU2();
        for (int i=0; i<attributes_count; i++) AbsynAttribute.read(classFile, cp);
    }

    private void readInstructions (InstructionInputStream inp, ConstantPool cp) 
        throws ClassFileInputStreamE, ConstantPoolE {

        int byteArrayLength = inp.getByteArrayLength();
        if (byteArrayLength == 0) throw new ConstantPoolE("Code array can't have length 0");

        instructions = new Instruction [byteArrayLength];
        for (int i=0; i<byteArrayLength; i++) instructions[i] = new Ins_filler();

        int i = 0;
        while ( i < byteArrayLength ) {
            int opcode = inp.readU1();
            switch (opcode) {
            case RuntimeConstants.opc_aaload:
                instructions[i] = new Ins_aaload(inp);
                break;
            case RuntimeConstants.opc_aastore:
                instructions[i] = new Ins_aastore(inp);
                break;
            case RuntimeConstants.opc_aconst_null:
                instructions[i] = new Ins_aconst_null(inp);
                break;
            case RuntimeConstants.opc_aload:
                instructions[i] = new Ins_aload(inp);
                break;
            case RuntimeConstants.opc_aload_0:
                instructions[i] = new Ins_aload_0(inp);
                break;
            case RuntimeConstants.opc_aload_1:
                instructions[i] = new Ins_aload_1(inp);
                break;
            case RuntimeConstants.opc_aload_2:
                instructions[i] = new Ins_aload_2(inp);
                break;
            case RuntimeConstants.opc_aload_3:
                instructions[i] = new Ins_aload_3(inp);
                break;
            case RuntimeConstants.opc_anewarray:
                instructions[i] = new Ins_anewarray(inp,cp);
                break;
            case RuntimeConstants.opc_areturn:
                instructions[i] = new Ins_areturn(inp);
                break;
            case RuntimeConstants.opc_arraylength:
                instructions[i] = new Ins_arraylength(inp);
                break;
            case RuntimeConstants.opc_astore:
                instructions[i] = new Ins_astore(inp);
                break;
            case RuntimeConstants.opc_astore_0:
                instructions[i] = new Ins_astore_0(inp);
                break;
            case RuntimeConstants.opc_astore_1:
                instructions[i] = new Ins_astore_1(inp);
                break;
            case RuntimeConstants.opc_astore_2:
                instructions[i] = new Ins_astore_2(inp);
                break;
            case RuntimeConstants.opc_astore_3:
                instructions[i] = new Ins_astore_3(inp);
                break;
            case RuntimeConstants.opc_athrow:
                instructions[i] = new Ins_athrow(inp);
                break;
            case RuntimeConstants.opc_baload:
                instructions[i] = new Ins_baload(inp);
                break;
            case RuntimeConstants.opc_bastore:
                instructions[i] = new Ins_bastore(inp);
                break;
            case RuntimeConstants.opc_bipush:
                instructions[i] = new Ins_bipush(inp);
                break;
            case RuntimeConstants.opc_caload:
                instructions[i] = new Ins_caload(inp);
                break;
            case RuntimeConstants.opc_castore:
                instructions[i] = new Ins_castore(inp);
                break;
            case RuntimeConstants.opc_checkcast:
                instructions[i] = new Ins_checkcast(inp,cp);
                break;
            case RuntimeConstants.opc_d2f:
                instructions[i] = new Ins_d2f(inp);
                break;
            case RuntimeConstants.opc_d2i:
                instructions[i] = new Ins_d2i(inp);
                break;
            case RuntimeConstants.opc_d2l:
                instructions[i] = new Ins_d2l(inp);
                break;
            case RuntimeConstants.opc_dadd:
                instructions[i] = new Ins_dadd(inp);
                break;
            case RuntimeConstants.opc_daload:
                instructions[i] = new Ins_daload(inp);
                break;
            case RuntimeConstants.opc_dastore:
                instructions[i] = new Ins_dastore(inp);
                break;
            case RuntimeConstants.opc_dcmpg:
                instructions[i] = new Ins_dcmpg(inp);
                break;
            case RuntimeConstants.opc_dcmpl:
                instructions[i] = new Ins_dcmpl(inp);
                break;
            case RuntimeConstants.opc_dconst_0:
                instructions[i] = new Ins_dconst_0(inp);
                break;
            case RuntimeConstants.opc_dconst_1:
                instructions[i] = new Ins_dconst_1(inp);
                break;
            case RuntimeConstants.opc_ddiv:
                instructions[i] = new Ins_ddiv(inp);
                break;
            case RuntimeConstants.opc_dload:
                instructions[i] = new Ins_dload(inp);
                break;
            case RuntimeConstants.opc_dload_0:
                instructions[i] = new Ins_dload_0(inp);
                break;
            case RuntimeConstants.opc_dload_1:
                instructions[i] = new Ins_dload_1(inp);
                break;
            case RuntimeConstants.opc_dload_2:
                instructions[i] = new Ins_dload_2(inp);
                break;
            case RuntimeConstants.opc_dload_3:
                instructions[i] = new Ins_dload_3(inp);
                break;
            case RuntimeConstants.opc_dmul:
                instructions[i] = new Ins_dmul(inp);
                break;
            case RuntimeConstants.opc_dneg:
                instructions[i] = new Ins_dneg(inp);
                break;
            case RuntimeConstants.opc_drem:
                instructions[i] = new Ins_drem(inp);
                break;
            case RuntimeConstants.opc_dreturn:
                instructions[i] = new Ins_dreturn(inp);
                break;
            case RuntimeConstants.opc_dstore:
                instructions[i] = new Ins_dstore(inp);
                break;
            case RuntimeConstants.opc_dstore_0:
                instructions[i] = new Ins_dstore_0(inp);
                break;
            case RuntimeConstants.opc_dstore_1:
                instructions[i] = new Ins_dstore_1(inp);
                break;
            case RuntimeConstants.opc_dstore_2:
                instructions[i] = new Ins_dstore_2(inp);
                break;
            case RuntimeConstants.opc_dstore_3:
                instructions[i] = new Ins_dstore_3(inp);
                break;
            case RuntimeConstants.opc_dsub:
                instructions[i] = new Ins_dsub(inp);
                break;
            case RuntimeConstants.opc_dup2:
                instructions[i] = new Ins_dup2(inp);
                break;
            case RuntimeConstants.opc_dup2_x1:
                instructions[i] = new Ins_dup2_x1(inp);
                break;
            case RuntimeConstants.opc_dup2_x2:
                instructions[i] = new Ins_dup2_x2(inp);
                break;
            case RuntimeConstants.opc_dup:
                instructions[i] = new Ins_dup(inp);
                break;
            case RuntimeConstants.opc_dup_x1:
                instructions[i] = new Ins_dup_x1(inp);
                break;
            case RuntimeConstants.opc_dup_x2:
                instructions[i] = new Ins_dup_x2(inp);
                break;
            case RuntimeConstants.opc_f2d:
                instructions[i] = new Ins_f2d(inp);
                break;
            case RuntimeConstants.opc_f2i:
                instructions[i] = new Ins_f2i(inp);
                break;
            case RuntimeConstants.opc_f2l:
                instructions[i] = new Ins_f2l(inp);
                break;
            case RuntimeConstants.opc_fadd:
                instructions[i] = new Ins_fadd(inp);
                break;
            case RuntimeConstants.opc_faload:
                instructions[i] = new Ins_faload(inp);
                break;
            case RuntimeConstants.opc_fastore:
                instructions[i] = new Ins_fastore(inp);
                break;
            case RuntimeConstants.opc_fcmpg:
                instructions[i] = new Ins_fcmpg(inp);
                break;
            case RuntimeConstants.opc_fcmpl:
                instructions[i] = new Ins_fcmpl(inp);
                break;
            case RuntimeConstants.opc_fconst_0:
                instructions[i] = new Ins_fconst_0(inp);
                break;
            case RuntimeConstants.opc_fconst_1:
                instructions[i] = new Ins_fconst_1(inp);
                break;
            case RuntimeConstants.opc_fconst_2:
                instructions[i] = new Ins_fconst_2(inp);
                break;
            case RuntimeConstants.opc_fdiv:
                instructions[i] = new Ins_fdiv(inp);
                break;
            case RuntimeConstants.opc_fload:
                instructions[i] = new Ins_fload(inp);
                break;
            case RuntimeConstants.opc_fload_0:
                instructions[i] = new Ins_fload_0(inp);
                break;
            case RuntimeConstants.opc_fload_1:
                instructions[i] = new Ins_fload_1(inp);
                break;
            case RuntimeConstants.opc_fload_2:
                instructions[i] = new Ins_fload_2(inp);
                break;
            case RuntimeConstants.opc_fload_3:
                instructions[i] = new Ins_fload_3(inp);
                break;
            case RuntimeConstants.opc_fmul:
                instructions[i] = new Ins_fmul(inp);
                break;
            case RuntimeConstants.opc_fneg:
                instructions[i] = new Ins_fneg(inp);
                break;
            case RuntimeConstants.opc_frem:
                instructions[i] = new Ins_frem(inp);
                break;
            case RuntimeConstants.opc_freturn:
                instructions[i] = new Ins_freturn(inp);
                break;
            case RuntimeConstants.opc_fstore:
                instructions[i] = new Ins_fstore(inp);
                break;
            case RuntimeConstants.opc_fstore_0:
                instructions[i] = new Ins_fstore_0(inp);
                break;
            case RuntimeConstants.opc_fstore_1:
                instructions[i] = new Ins_fstore_1(inp);
                break;
            case RuntimeConstants.opc_fstore_2:
                instructions[i] = new Ins_fstore_2(inp);
                break;
            case RuntimeConstants.opc_fstore_3:
                instructions[i] = new Ins_fstore_3(inp);
                break;
            case RuntimeConstants.opc_fsub:
                instructions[i] = new Ins_fsub(inp);
                break;
            case RuntimeConstants.opc_getfield:
                instructions[i] = new Ins_getfield(inp,cp);
                break;
            case RuntimeConstants.opc_getstatic:
                instructions[i] = new Ins_getstatic(inp,cp);
                break;
            case RuntimeConstants.opc_goto:
                instructions[i] = new Ins_goto(inp);
                break;
            case RuntimeConstants.opc_goto_w:
                instructions[i] = new Ins_goto_w(inp);
                break;
            case RuntimeConstants.opc_i2b:
                instructions[i] = new Ins_i2b(inp);
                break;
            case RuntimeConstants.opc_i2c:
                instructions[i] = new Ins_i2c(inp);
                break;
            case RuntimeConstants.opc_i2d:
                instructions[i] = new Ins_i2d(inp);
                break;
            case RuntimeConstants.opc_i2f:
                instructions[i] = new Ins_i2f(inp);
                break;
            case RuntimeConstants.opc_i2l:
                instructions[i] = new Ins_i2l(inp);
                break;
            case RuntimeConstants.opc_i2s:
                instructions[i] = new Ins_i2s(inp);
                break;
            case RuntimeConstants.opc_iadd:
                instructions[i] = new Ins_iadd(inp);
                break;
            case RuntimeConstants.opc_iaload:
                instructions[i] = new Ins_iaload(inp);
                break;
            case RuntimeConstants.opc_iand:
                instructions[i] = new Ins_iand(inp);
                break;
            case RuntimeConstants.opc_iastore:
                instructions[i] = new Ins_iastore(inp);
                break;
            case RuntimeConstants.opc_iconst_0:
                instructions[i] = new Ins_iconst_0(inp);
                break;
            case RuntimeConstants.opc_iconst_1:
                instructions[i] = new Ins_iconst_1(inp);
                break;
            case RuntimeConstants.opc_iconst_2:
                instructions[i] = new Ins_iconst_2(inp);
                break;
            case RuntimeConstants.opc_iconst_3:
                instructions[i] = new Ins_iconst_3(inp);
                break;
            case RuntimeConstants.opc_iconst_4:
                instructions[i] = new Ins_iconst_4(inp);
                break;
            case RuntimeConstants.opc_iconst_5:
                instructions[i] = new Ins_iconst_5(inp);
                break;
            case RuntimeConstants.opc_iconst_m1:
                instructions[i] = new Ins_iconst_m1(inp);
                break;
            case RuntimeConstants.opc_idiv:
                instructions[i] = new Ins_idiv(inp);
                break;
            case RuntimeConstants.opc_if_acmpeq:
                instructions[i] = new Ins_if_acmpeq(inp);
                break;
            case RuntimeConstants.opc_if_acmpne:
                instructions[i] = new Ins_if_acmpne(inp);
                break;
            case RuntimeConstants.opc_if_icmpeq:
                instructions[i] = new Ins_if_icmpeq(inp);
                break;
            case RuntimeConstants.opc_if_icmpge:
                instructions[i] = new Ins_if_icmpge(inp);
                break;
            case RuntimeConstants.opc_if_icmpgt:
                instructions[i] = new Ins_if_icmpgt(inp);
                break;
            case RuntimeConstants.opc_if_icmple:
                instructions[i] = new Ins_if_icmple(inp);
                break;
            case RuntimeConstants.opc_if_icmplt:
                instructions[i] = new Ins_if_icmplt(inp);
                break;
            case RuntimeConstants.opc_if_icmpne:
                instructions[i] = new Ins_if_icmpne(inp);
                break;
            case RuntimeConstants.opc_ifeq:
                instructions[i] = new Ins_ifeq(inp);
                break;
            case RuntimeConstants.opc_ifge:
                instructions[i] = new Ins_ifge(inp);
                break;
            case RuntimeConstants.opc_ifgt:
                instructions[i] = new Ins_ifgt(inp);
                break;
            case RuntimeConstants.opc_ifle:
                instructions[i] = new Ins_ifle(inp);
                break;
            case RuntimeConstants.opc_iflt:
                instructions[i] = new Ins_iflt(inp);
                break;
            case RuntimeConstants.opc_ifne:
                instructions[i] = new Ins_ifne(inp);
                break;
            case RuntimeConstants.opc_ifnonnull:
                instructions[i] = new Ins_ifnonnull(inp);
                break;
            case RuntimeConstants.opc_ifnull:
                instructions[i] = new Ins_ifnull(inp);
                break;
            case RuntimeConstants.opc_iinc:
                instructions[i] = new Ins_iinc(inp);
                break;
            case RuntimeConstants.opc_iload:
                instructions[i] = new Ins_iload(inp);
                break;
            case RuntimeConstants.opc_iload_0:
                instructions[i] = new Ins_iload_0(inp);
                break;
            case RuntimeConstants.opc_iload_1:
                instructions[i] = new Ins_iload_1(inp);
                break;
            case RuntimeConstants.opc_iload_2:
                instructions[i] = new Ins_iload_2(inp);
                break;
            case RuntimeConstants.opc_iload_3:
                instructions[i] = new Ins_iload_3(inp);
                break;
            case RuntimeConstants.opc_imul:
                instructions[i] = new Ins_imul(inp);
                break;
            case RuntimeConstants.opc_ineg:
                instructions[i] = new Ins_ineg(inp);
                break;
            case RuntimeConstants.opc_instanceof:
                instructions[i] = new Ins_instanceof(inp,cp);
                break;
            case RuntimeConstants.opc_invokeinterface:
                instructions[i] = new Ins_invokeinterface(inp,cp);
                break;
            case RuntimeConstants.opc_invokespecial:
                instructions[i] = new Ins_invokespecial(inp,cp);
                break;
            case RuntimeConstants.opc_invokestatic:
                instructions[i] = new Ins_invokestatic(inp,cp);
                break;
            case RuntimeConstants.opc_invokevirtual:
                instructions[i] = new Ins_invokevirtual(inp,cp);
                break;
            case RuntimeConstants.opc_ior:
                instructions[i] = new Ins_ior(inp);
                break;
            case RuntimeConstants.opc_irem:
                instructions[i] = new Ins_irem(inp);
                break;
            case RuntimeConstants.opc_ireturn:
                instructions[i] = new Ins_ireturn(inp);
                break;
            case RuntimeConstants.opc_ishl:
                instructions[i] = new Ins_ishl(inp);
                break;
            case RuntimeConstants.opc_ishr:
                instructions[i] = new Ins_ishr(inp);
                break;
            case RuntimeConstants.opc_istore:
                instructions[i] = new Ins_istore(inp);
                break;
            case RuntimeConstants.opc_istore_0:
                instructions[i] = new Ins_istore_0(inp);
                break;
            case RuntimeConstants.opc_istore_1:
                instructions[i] = new Ins_istore_1(inp);
                break;
            case RuntimeConstants.opc_istore_2:
                instructions[i] = new Ins_istore_2(inp);
                break;
            case RuntimeConstants.opc_istore_3:
                instructions[i] = new Ins_istore_3(inp);
                break;
            case RuntimeConstants.opc_isub:
                instructions[i] = new Ins_isub(inp);
                break;
            case RuntimeConstants.opc_iushr:
                instructions[i] = new Ins_iushr(inp);
                break;
            case RuntimeConstants.opc_ixor:
                instructions[i] = new Ins_ixor(inp);
                break;
            case RuntimeConstants.opc_jsr:
                instructions[i] = new Ins_jsr(inp);
                break;
            case RuntimeConstants.opc_jsr_w:
                instructions[i] = new Ins_jsr_w(inp);
                break;
            case RuntimeConstants.opc_l2d:
                instructions[i] = new Ins_l2d(inp);
                break;
            case RuntimeConstants.opc_l2f:
                instructions[i] = new Ins_l2f(inp);
                break;
            case RuntimeConstants.opc_l2i:
                instructions[i] = new Ins_l2i(inp);
                break;
            case RuntimeConstants.opc_ladd:
                instructions[i] = new Ins_ladd(inp);
                break;
            case RuntimeConstants.opc_laload:
                instructions[i] = new Ins_laload(inp);
                break;
            case RuntimeConstants.opc_land:
                instructions[i] = new Ins_land(inp);
                break;
            case RuntimeConstants.opc_lastore:
                instructions[i] = new Ins_lastore(inp);
                break;
            case RuntimeConstants.opc_lcmp:
                instructions[i] = new Ins_lcmp(inp);
                break;
            case RuntimeConstants.opc_lconst_0:
                instructions[i] = new Ins_lconst_0(inp);
                break;
            case RuntimeConstants.opc_lconst_1:
                instructions[i] = new Ins_lconst_1(inp);
                break;
            case RuntimeConstants.opc_ldc2_w:
                instructions[i] = new Ins_ldc2_w(inp,cp);
                break;
            case RuntimeConstants.opc_ldc:
                instructions[i] = new Ins_ldc(inp,cp);
                break;
            case RuntimeConstants.opc_ldc_w:
                instructions[i] = new Ins_ldc_w(inp,cp);
                break;
            case RuntimeConstants.opc_ldiv:
                instructions[i] = new Ins_ldiv(inp);
                break;
            case RuntimeConstants.opc_lload:
                instructions[i] = new Ins_lload(inp);
                break;
            case RuntimeConstants.opc_lload_0:
                instructions[i] = new Ins_lload_0(inp);
                break;
            case RuntimeConstants.opc_lload_1:
                instructions[i] = new Ins_lload_1(inp);
                break;
            case RuntimeConstants.opc_lload_2:
                instructions[i] = new Ins_lload_2(inp);
                break;
            case RuntimeConstants.opc_lload_3:
                instructions[i] = new Ins_lload_3(inp);
                break;
            case RuntimeConstants.opc_lmul:
                instructions[i] = new Ins_lmul(inp);
                break;
            case RuntimeConstants.opc_lneg:
                instructions[i] = new Ins_lneg(inp);
                break;
            case RuntimeConstants.opc_lookupswitch:
                instructions[i] = new Ins_lookupswitch(inp);
                break;
            case RuntimeConstants.opc_lor:
                instructions[i] = new Ins_lor(inp);
                break;
            case RuntimeConstants.opc_lrem:
                instructions[i] = new Ins_lrem(inp);
                break;
            case RuntimeConstants.opc_lreturn:
                instructions[i] = new Ins_lreturn(inp);
                break;
            case RuntimeConstants.opc_lshl:
                instructions[i] = new Ins_lshl(inp);
                break;
            case RuntimeConstants.opc_lshr:
                instructions[i] = new Ins_lshr(inp);
                break;
            case RuntimeConstants.opc_lstore:
                instructions[i] = new Ins_lstore(inp);
                break;
            case RuntimeConstants.opc_lstore_0:
                instructions[i] = new Ins_lstore_0(inp);
                break;
            case RuntimeConstants.opc_lstore_1:
                instructions[i] = new Ins_lstore_1(inp);
                break;
            case RuntimeConstants.opc_lstore_2:
                instructions[i] = new Ins_lstore_2(inp);
                break;
            case RuntimeConstants.opc_lstore_3:
                instructions[i] = new Ins_lstore_3(inp);
                break;
            case RuntimeConstants.opc_lsub:
                instructions[i] = new Ins_lsub(inp);
                break;
            case RuntimeConstants.opc_lushr:
                instructions[i] = new Ins_lushr(inp);
                break;
            case RuntimeConstants.opc_lxor:
                instructions[i] = new Ins_lxor(inp);
                break;
            case RuntimeConstants.opc_monitorenter:
                instructions[i] = new Ins_monitorenter(inp);
                break;
            case RuntimeConstants.opc_monitorexit:
                instructions[i] = new Ins_monitorexit(inp);
                break;
            case RuntimeConstants.opc_multianewarray:
                instructions[i] = new Ins_multianewarray(inp,cp);
                break;
            case RuntimeConstants.opc_new:
                instructions[i] = new Ins_new(inp,cp);
                break;
            case RuntimeConstants.opc_newarray:
                instructions[i] = new Ins_newarray(inp);
                break;
            case RuntimeConstants.opc_nop:
                instructions[i] = new Ins_nop(inp);
                break;
            case RuntimeConstants.opc_pop2:
                instructions[i] = new Ins_pop2(inp);
                break;
            case RuntimeConstants.opc_pop:
                instructions[i] = new Ins_pop(inp);
                break;
            case RuntimeConstants.opc_putfield:
                instructions[i] = new Ins_putfield(inp,cp);
                break;
            case RuntimeConstants.opc_putstatic:
                instructions[i] = new Ins_putstatic(inp,cp);
                break;
            case RuntimeConstants.opc_ret:
                instructions[i] = new Ins_ret(inp);
                break;
            case RuntimeConstants.opc_return:
                instructions[i] = new Ins_return(inp);
                break;
            case RuntimeConstants.opc_saload:
                instructions[i] = new Ins_saload(inp);
                break;
            case RuntimeConstants.opc_sastore:
                instructions[i] = new Ins_sastore(inp);
                break;
            case RuntimeConstants.opc_sipush:
                instructions[i] = new Ins_sipush(inp);
                break;
            case RuntimeConstants.opc_swap:
                instructions[i] = new Ins_swap(inp);
                break;
            case RuntimeConstants.opc_tableswitch:
                instructions[i] = new Ins_tableswitch(inp);
                break;
            case RuntimeConstants.opc_wide: {
                int nextopcode = inp.readU1();
                switch (nextopcode) {
                case RuntimeConstants.opc_iload:
                    instructions[i] = new WideIns_iload(inp);
                    break;
                case RuntimeConstants.opc_fload:
                    instructions[i] = new WideIns_fload(inp);
                    break;
                case RuntimeConstants.opc_aload:
                    instructions[i] = new WideIns_aload(inp);
                    break;
                case RuntimeConstants.opc_lload:
                    instructions[i] = new WideIns_lload(inp);
                    break;
                case RuntimeConstants.opc_dload:
                    instructions[i] = new WideIns_dload(inp);
                    break;
                case RuntimeConstants.opc_istore:
                    instructions[i] = new WideIns_istore(inp);
                    break;
                case RuntimeConstants.opc_fstore:
                    instructions[i] = new WideIns_fstore(inp);
                    break;
                case RuntimeConstants.opc_astore:
                    instructions[i] = new WideIns_astore(inp);
                    break;
                case RuntimeConstants.opc_lstore:
                    instructions[i] = new WideIns_lstore(inp);
                    break;
                case RuntimeConstants.opc_dstore:
                    instructions[i] = new WideIns_dstore(inp);
                    break;
                case RuntimeConstants.opc_ret:
                    instructions[i] = new WideIns_ret(inp);
                    break;
                case RuntimeConstants.opc_iinc:
                    instructions[i] = new WideIns_iinc(inp);
                    break;
                default:
                    throw new ConstantPoolE("Illegal opcode argument to wide: " + opcode);
                }
            }
            default:
                throw new ConstantPoolE("Unknown opcode: " + opcode);
            }
            i += instructions [i].getLength ();
        }
    }
    
    public String toString () { 
        String res = "Code Attribute:\n";
        res += "maxStack = " + maxStack + "\n";
        res += "maxLocals = " + maxLocals + "\n";
        res += "Instructions:\n";
        for (int i=0; i<instructions.length; i++)
            res += i + "\t" + instructions[i] + "\n";
        res += "Exception handlers:\n";
        for (int i=0; i<exceptionHandlers.length; i++)
            res += exceptionHandlers[i] + "\n";
        return res;
    }
}

 package ojvm.loading.instructions;

import ojvm.loading.ClassFileInputStream;
import ojvm.loading.ClassFileInputStreamE;

/**
 * A specialized input stream for reading byte arrays of
 * instructions. Compared to ClassInputStream, the new functionality
 * is to keep track of how many bytes were read so far so that we can
 * conveniently deal with instructions that must be aligned on a
 * 4-byte boundary. 
 * 
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class InstructionInputStream extends ClassFileInputStream {
  private int byteArrayLength;
  private int bytesRead;

  public InstructionInputStream (byte[] instructionArray, String filename) {
    super(instructionArray, filename);
    this.byteArrayLength = instructionArray.length;
    this.bytesRead = 0;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////

  public int getByteArrayLength () { return byteArrayLength; }

  int skipPadding () throws ClassFileInputStreamE { 
    int bytesSkipped = 3 - ((bytesRead - 1) % 4); // skip 0, 1, 2, or 3 bytes to align next entry
    skipBytes (bytesSkipped);
    return bytesSkipped;
  }

  public void skipBytes (int len) throws ClassFileInputStreamE {
    bytesRead += len;
    super.skipBytes(len);
  }

  public int readU1 () throws ClassFileInputStreamE {
    bytesRead += 1;
    return super.readU1();
  }

  public int readU2 () throws ClassFileInputStreamE {
    bytesRead += 2;
    return super.readU2();
  }

  public int readU4 () throws ClassFileInputStreamE {
    bytesRead += 4;
    return super.readU4();
  }

  public byte readByte () throws ClassFileInputStreamE {
    bytesRead += 1;
    return super.readByte();
  }

  public short readShort () throws ClassFileInputStreamE {
    bytesRead += 2;
    return super.readShort();
  }

  public int readInt () throws ClassFileInputStreamE {
    bytesRead += 4;
    return super.readInt();
  }
}

package com.progressive.code.cos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.output.CountingOutputStream;

public class CoutingOutputStreamDemo {

    private CountingOutputStream countingOutputStream;
    private OutputStreamWriter outputStreamWriter;

    /**
     * Constructor
     * @param file the path to the file to write
     * @param charset the {@link Charset) to use for writing
     * @throws FileNotFoundException
     */
    public CoutingOutputStreamDemo(String file, Charset charset) throws FileNotFoundException {
        // We store a reference to the CountingOutputStream to be able to count the bytes later
        this.countingOutputStream = new CountingOutputStream(new FileOutputStream(file));
        this.outputStreamWriter = new OutputStreamWriter(this.countingOutputStream, charset);
    }

    /**
     * Write a line to the output stream
     * @param content the content to write
     * @throws IOException
     */
    public void writeLine(String content) throws IOException {
        outputStreamWriter.write(content + System.lineSeparator());
    }

    /**
     * Determine the number of bytes written so far
     * @return the number of bytes written so far
     * @throws IOException
     */
    public Long getBytesWritten() throws IOException {
        // We first have to flush the buffer to ensure all bytes were actually written
        this.outputStreamWriter.flush();
        return countingOutputStream.getByteCount();
    }
    
    /**
     * Closes the output stream and returns the number of bytes written
     * @return the number of bytes written
     * @throws IOException
     */
    public void close() throws IOException {
        this.outputStreamWriter.flush();
        this.outputStreamWriter.close();
    }
}

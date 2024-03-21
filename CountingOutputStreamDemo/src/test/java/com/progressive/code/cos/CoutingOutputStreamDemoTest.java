package com.progressive.code.cos;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class CoutingOutputStreamDemoTest {

    private static final String OUTPUT_FILE_UTF_8 = "./data/output-utf-8.txt";
    private static final String OUTPUT_FILE_UTF_16 = "./data/output-utf-16.txt";

    private static final Charset CHARSET_UTF_8= Charset.forName("UTF-8");
    private static final Charset CHARSET_UTF_16= Charset.forName("UTF-16");
    
    @Test
    public void testWriterUtf8() throws IOException {
        CoutingOutputStreamDemo cos = new CoutingOutputStreamDemo(OUTPUT_FILE_UTF_8, CHARSET_UTF_8);
        cos.writeLine("A simple test");
        cos.writeLine("And another line");
        long bytestWritten = cos.getBytesWritten();
        cos.close();
        long actualFileSize = new File(OUTPUT_FILE_UTF_8).length();
        System.out.println(String.format("Actual file size UTF-8: %s bytes", actualFileSize));
        assertEquals(actualFileSize, bytestWritten);
    }

    @Test
    public void testWriterUtf16() throws IOException {
        CoutingOutputStreamDemo cos = new CoutingOutputStreamDemo(OUTPUT_FILE_UTF_16, CHARSET_UTF_16);
        cos.writeLine("A simple test");
        cos.writeLine("And another line");
        long bytestWritten = cos.getBytesWritten();
        cos.close();
        long actualFileSize = new File(OUTPUT_FILE_UTF_16).length();
        System.out.println(String.format("Actual file size UTF-16: %s bytes", actualFileSize));
        assertEquals(actualFileSize, bytestWritten);
    }

}

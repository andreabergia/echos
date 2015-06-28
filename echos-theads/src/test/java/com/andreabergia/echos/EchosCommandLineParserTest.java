package com.andreabergia.echos;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EchosCommandLineParserTest {
    @Test
    public void testEmptyCommandLineHasDefaults() throws Exception {
        EchosSocketServerOptions commandLine = doParse();
        assertEquals(commandLine.getPort(), EchosSocketServerOptions.DEFAULT_PORT);
        assertEquals(commandLine.getNumThreads(), EchosSocketServerOptions.NUM_THREADS);
    }

    @Test
    public void testPortCanBeSpecified() throws Exception {
        EchosSocketServerOptions commandLine = doParse("-p", "42");
        assertEquals(commandLine.getPort(), 42);
        assertEquals(commandLine.getNumThreads(), EchosSocketServerOptions.NUM_THREADS);
    }

    @Test
    public void testPortCanBeSpecifiedLongOption() throws Exception {
        EchosSocketServerOptions commandLine = doParse("--port", "43");
        assertEquals(commandLine.getPort(), 43);
        assertEquals(commandLine.getNumThreads(), EchosSocketServerOptions.NUM_THREADS);
    }

    @Test
    public void testNumThreadsCanBeSpecified() throws Exception {
        EchosSocketServerOptions commandLine = doParse("-t", "12");
        assertEquals(commandLine.getPort(), EchosSocketServerOptions.DEFAULT_PORT);
        assertEquals(commandLine.getNumThreads(), 12);
    }

    @Test
    public void testNumThreadsCanBeSpecifiedLongOption() throws Exception {
        EchosSocketServerOptions commandLine = doParse("--threads", "13");
        assertEquals(commandLine.getPort(), EchosSocketServerOptions.DEFAULT_PORT);
        assertEquals(commandLine.getNumThreads(), 13);
    }

    @Test
    public void testBothArgumentsCanBeSpecified() throws Exception {
        EchosSocketServerOptions commandLine = doParse("--port", "43", "--threads", "12");
        assertEquals(commandLine.getPort(), 43);
        assertEquals(commandLine.getNumThreads(), 12);
    }

    @Test(expected = InvalidCommandLineException.class)
    public void testInvalidCommandLineThrowsException() throws Exception {
        doParse("foo");
    }

    private EchosSocketServerOptions doParse(String... args) {
        return new EchosCommandLineParser().parseCommandLine(args);
    }
}

package com.sezgk.tractor.census.parser;

/**
 * Wrapper for execeptions that take place during the lifecycle of a census tract parser object.
 * 
 * @author Ennis Golaszewski
 */
public class CensusTractParserException extends RuntimeException
{
    private static final long serialVersionUID = 2132L;

    /**
     * Creates an exception with both a message and a cause. All of the execeptions thrown by the parser should use this
     * constructor.
     * 
     * @param msg, information regarding the exception.
     * @param cause, the internal exception that triggered a parser exception.
     */
    public CensusTractParserException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}

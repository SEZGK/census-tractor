package com.sezgk.tractor.census.parser;

/**
 * Wrapper for any exceptions thrown internally during the life cycle of a TractCoordinateParser object.
 * 
 * @author Ennis Golaszewski
 */
public class TractCoordinateParserException extends RuntimeException
{
    private static final long serialVersionUID = 2132L;
    
    /**
     * Creates an exception with both a message and a cause. All of the execeptions thrown by the parser should use this
     * constructor.
     * 
     * @param msg, information regarding the exception.
     * @param cause, the internal exception that triggered a parser exception.
     */
    public TractCoordinateParserException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}

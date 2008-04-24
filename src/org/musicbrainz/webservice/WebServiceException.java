package org.musicbrainz.webservice;

public class WebServiceException extends Exception {
    public WebServiceException() {}
    public WebServiceException(String message) {super(message);}
    public WebServiceException(Throwable cause) {super(cause);}
}

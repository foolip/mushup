package org.musicbrainz.webservice;

public class ConnectionException extends WebServiceException {
    public ConnectionException() {}
    public ConnectionException(String message) {super(message);}
    public ConnectionException(Throwable cause) {super(cause);}
}

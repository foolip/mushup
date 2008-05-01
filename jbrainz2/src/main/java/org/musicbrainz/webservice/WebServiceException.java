package org.musicbrainz.webservice;

public class WebServiceException extends Exception {
    WebServiceException() {}
    WebServiceException(String message) {super(message);}
    WebServiceException(Throwable cause) {super(cause);}
}

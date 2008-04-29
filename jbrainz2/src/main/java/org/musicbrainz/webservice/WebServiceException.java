package org.musicbrainz.webservice;

class WebServiceException extends Exception {
    WebServiceException() {}
    WebServiceException(String message) {super(message);}
    WebServiceException(Throwable cause) {super(cause);}
}

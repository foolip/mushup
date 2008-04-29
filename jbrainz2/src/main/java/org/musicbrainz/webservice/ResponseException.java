package org.musicbrainz.webservice;

class ResponseException extends WebServiceException {
    ResponseException() {}
    ResponseException(String message) {super(message);}
    ResponseException(Throwable cause) {super(cause);}
}

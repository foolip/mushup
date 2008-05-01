package org.musicbrainz.webservice;

public class ResourceNotFoundException extends WebServiceException {
    ResourceNotFoundException() {}
    ResourceNotFoundException(String message) {super(message);}
    ResourceNotFoundException(Throwable cause) {super(cause);}
}

package my.spring.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class RestContollerSample {

    /**
     * Данные метод, получит тело в массиве байтов,
     */
    public ResponseEntity< String > handle( HttpEntity< byte[] > requestEntity ) {
        String requestHeader = requestEntity.getHeaders().getFirst( "myRequestHeader" );
        byte[] requestEntityBody = requestEntity.getBody();

        //do something with body
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( "myRequestHeader", "newValue" );
        return new ResponseEntity<>( "Hello, World", httpHeaders, CREATED );
    }
}

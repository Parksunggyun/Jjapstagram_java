package com.example.jjapstagram_java.login;


@FunctionalInterface
public interface LoginRequest {

    /**
     *
     * @param type if type == GOOGLE  => call google loginRequest
     *              if type == FACEBOOK => call facebook loginRequest
     */
    void request(int type);


}

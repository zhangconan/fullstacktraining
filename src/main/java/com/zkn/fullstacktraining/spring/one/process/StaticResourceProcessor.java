package com.zkn.fullstacktraining.spring.one.process;


import com.zkn.fullstacktraining.spring.one.requestresponse.Request;
import com.zkn.fullstacktraining.spring.one.requestresponse.Response;

/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response) {

        response.sendStaticResource(request.getUri());
    }
}

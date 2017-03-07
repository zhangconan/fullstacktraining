package com.zkn.fullstacktraining.ninth.fileupload;

import java.io.IOException;
import com.zkn.fullstacktraining.ninth.fileupload2.Request;
/**
 * Created by wb-zhangkenan on 2016/12/29.
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response) {

        try {
            response.sendStaticResource(request.getUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

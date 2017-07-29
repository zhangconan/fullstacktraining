package com.zkn.fullstacktraining.spring.one.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zkn on 2017/7/28.
 */

public class CustomClasspathResource implements CustomInputStreamSource {

    private String location;

    public CustomClasspathResource(String location) {
        this.location = location;
    }

    /**
     * 获取文件输入流
     *
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = null;
        is = this.getClass().getResourceAsStream(location);
        if (is == null) {
            is = this.getClass().getClassLoader().getResourceAsStream(location);
        }
        return is;
    }
}

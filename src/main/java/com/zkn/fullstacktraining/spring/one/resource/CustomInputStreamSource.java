package com.zkn.fullstacktraining.spring.one.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zkn on 2017/7/28.
 */
public interface CustomInputStreamSource {
    /**
     * 获取文件输入流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}

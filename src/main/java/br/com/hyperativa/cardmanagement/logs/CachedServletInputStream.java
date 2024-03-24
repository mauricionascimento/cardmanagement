package br.com.hyperativa.cardmanagement.logs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CachedServletInputStream extends ServletInputStream {

    private final static Logger LOGGER = LoggerFactory.getLogger(CachedServletInputStream.class);
    private InputStream cachedInputStream;

    public CachedServletInputStream(byte[] cachedBody) {
        this.cachedInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return this.cachedInputStream.read();
    }
}

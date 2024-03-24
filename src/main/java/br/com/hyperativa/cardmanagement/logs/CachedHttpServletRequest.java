package br.com.hyperativa.cardmanagement.logs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.util.StreamUtils;


public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedPayload;

    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedPayload = StreamUtils.copyToByteArray(requestInputStream);
    }

    public byte[] getCachedPayload() {
		return cachedPayload;
	}

	@Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(this.cachedPayload);
    }

    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}
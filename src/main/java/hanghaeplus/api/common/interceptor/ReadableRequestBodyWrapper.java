package hanghaeplus.api.common.interceptor;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadableRequestBodyWrapper extends HttpServletRequestWrapper {

    static class ServletInputStreamImpl extends ServletInputStream {
        private final InputStream inputStream;

        public ServletInputStreamImpl(final InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isReady() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }

        @Override
        public int read(final byte[] b) throws IOException {
            return this.inputStream.read(b);
        }

        @Override
        public void setReadListener(final ReadListener listener) {
            // TODO Auto-generated method stub
        }
    }

    private final byte[] bytes;
    private final String requestBody;

    public ReadableRequestBodyWrapper(final HttpServletRequest request) throws IOException {
        super(request);

        InputStream in = super.getInputStream();
        this.bytes = IOUtils.toByteArray(in);
        this.requestBody = new String(this.bytes);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.bytes);
        return new ServletInputStreamImpl(byteArrayInputStream);
    }

    public String getRequestBody() {
        return requestBody;
    }
}
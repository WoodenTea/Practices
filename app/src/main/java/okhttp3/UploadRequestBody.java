package okhttp3;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadRequestBody extends RequestBody {
    private RequestBody requestBody;
    private UploadListener listener;

    public UploadRequestBody(RequestBody requestBody, UploadListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink bufferedSink = Okio.buffer(wrapSink(sink));
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink wrapSink(Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }

                bytesWritten += byteCount;
                if (listener != null) {
                    listener.onRequestProgress(bytesWritten, contentLength);
                }
            }
        };
    }

    public interface UploadListener {
        void onRequestProgress(long bytesWritten, long contentLength);
    }
}

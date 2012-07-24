package org.nuxeo.ecm.eval.qna;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

@Provider
public class QnaListBodyReader implements MessageBodyReader<QnaList> {

    public static final MediaType targetMediaType = new MediaType("text",
            "json");

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType) {
        return (targetMediaType.isCompatible(mediaType) && QnaList.class.isAssignableFrom(type));
    }

    @Override
    public QnaList readFrom(Class<QnaList> type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {

        throw new UnsupportedOperationException(
                "QnaListBodyReader the MessageBodyReader for QnaList type is not implemented, you should fix this, otherwise you can not know if your answers are correct !!!");
    }

}

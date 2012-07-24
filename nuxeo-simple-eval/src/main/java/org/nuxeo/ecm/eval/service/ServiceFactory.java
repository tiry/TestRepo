package org.nuxeo.ecm.eval.service;


/**
 * Simple factory used to access the services singleton
 *
 * @author Tiry (tdelprat@nuxeo.com)
 *
 */
public class ServiceFactory {

    public static <T> T getService(Class<T> serviceClass) {

        if (MessageService.class.isAssignableFrom(serviceClass)) {
            return  serviceClass.cast(MessageServiceImpl.instance());
        }
        else if (ChatService.class.isAssignableFrom(serviceClass)) {
            return  serviceClass.cast(ChatServiceImpl.instance());
        }
        return null;
    }

}

package org.nuxeo.ecm.eval.webengine;

import java.util.HashSet;
import java.util.Set;

import org.nuxeo.ecm.eval.qna.QnaListBodyReader;
import org.nuxeo.ecm.webengine.app.WebEngineModule;

public class EvalWebEngineApp extends WebEngineModule {

    protected static final Set<Object> providers = setupProviders();

    protected static Set<Object> setupProviders() {
        Set<Object> result = new HashSet<Object>();
        result.add(new QnaListBodyReader());
        return result;
    }

    @Override
    public Set<Object> getSingletons() {
        return providers;
    }

}

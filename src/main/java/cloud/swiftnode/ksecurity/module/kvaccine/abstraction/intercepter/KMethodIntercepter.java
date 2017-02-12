package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class KMethodIntercepter implements MethodInterceptor {
    private Object delegate;
    private Field delegateField;

    public KMethodIntercepter(Object delegate, String fieldName, boolean pub) throws NoSuchFieldException {
        this.delegate = delegate;
        if (pub) {
            this.delegateField = delegate.getClass().getField(fieldName);
        } else {
            this.delegateField = delegate.getClass().getDeclaredField(fieldName);
            this.delegateField.setAccessible(true);
        }
    }

    /**
     * 객체의 참조값이 변경될 수 있어
     * 리플렉션 사용
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return proxy.invoke(delegateField.get(delegate), args);
    }

    public static class KMethodIntercepterBuilder {
        private Object delegate;
        private String fieldName;
        private boolean pub;

        public KMethodIntercepterBuilder(Object delegate) {
            setDelegate(delegate);
        }

        public KMethodIntercepterBuilder setDelegate(Object delegate) {
            this.delegate = delegate;
            return this;
        }

        public KMethodIntercepterBuilder setField(String fieldName, boolean pub) {
            this.fieldName = fieldName;
            this.pub = pub;
            return this;
        }

        public KMethodIntercepterBuilder setField(String fieldName) {
            return setField(fieldName, false);
        }

        public KMethodIntercepter build() throws NoSuchFieldException {
            return new KMethodIntercepter(delegate, fieldName, pub);
        }

        public KMethodIntercepter build(String fieldName) throws NoSuchFieldException {
            return setField(fieldName).build();
        }
    }
}

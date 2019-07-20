package steffan.javafxdemo.core.models.util;

import javafx.beans.value.WritableValue;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static steffan.javafxdemo.core.control.PlatformHelper.callLater;

public class WritableValueHelper {

    @SuppressWarnings("unchecked")
    public static <V, W extends WritableValue<V>> W writableValueWithSetInPlatformThread(W writableValue) {
        Class<? extends WritableValue> clazz = writableValue.getClass();
        try {
            return (W) new ByteBuddy(ClassFileVersion.JAVA_V11)
                    .subclass(clazz, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
                    .method(ElementMatchers.any())
                    .intercept(InvocationHandlerAdapter.of(new SetterInterceptor(writableValue)))
                    .make()
                    .load(writableValue.getClass().getClassLoader())
                    .getLoaded()
                    .getConstructor().newInstance();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Cannot create property proxy");
    }

    private static class SetterInterceptor<T> implements InvocationHandler {

        private final T obj;

        private SetterInterceptor(T obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("set")) {
                return callLater(() -> method.invoke(obj, args));
            } else {
                return method.invoke(obj, args);
            }

        }

    }

}

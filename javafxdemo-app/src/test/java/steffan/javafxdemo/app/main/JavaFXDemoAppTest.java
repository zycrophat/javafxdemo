package steffan.javafxdemo.app.main;


import org.junit.Test;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.UIViewManager;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JavaFXDemoAppTest {

    @Test
    public void testAppIsInitialized() {
        UIViewManager UIViewManager = mock(UIViewManager.class);
        PersistenceContext ctx = mock(PersistenceContext.class);

        JavaFXAppControl app = new JavaFXAppControl(UIViewManager, ctx);

        assertThat(app.isInitialized(), is(false));

        app.initialize();

        assertThat(app.isInitialized(), is(true));
        assertThat(app.getUIViewManager(), is(notNullValue()));
    }
}

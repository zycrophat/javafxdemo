package steffan.javafxdemo.app.main;


import org.junit.Test;
import steffan.javafxdemo.persistence.api.PersistenceContext;
import steffan.javafxdemo.view.api.ViewManager;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JavaFXDemoAppTest {

    @Test
    public void testAppIsInitialized() {
        ViewManager viewManager = mock(ViewManager.class);
        PersistenceContext ctx = mock(PersistenceContext.class);

        JavaFXAppControl app = new JavaFXAppControl(viewManager, ctx);

        assertThat(app.isInitialized(), is(false));

        app.initialize();

        assertThat(app.isInitialized(), is(true));
        assertThat(app.getViewManager(), is(notNullValue()));
    }
}

package steffan.javafxdemo.app.main;


import org.junit.Test;
import steffan.javafxdemo.app.api.view.View;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JavaFXDemoAppTest {

    @Test
    public void testAppIsInitialized() {
        View view = mock(View.class);

        JavaFXDemoApp app = new JavaFXDemoApp(() -> view);

        assertThat(app.isInitialized(), is(false));
        assertThat(app.view, is(nullValue()));

        app.initialize();

        assertThat(app.isInitialized(), is(true));
        assertThat(app.view, is(notNullValue()));
    }
}

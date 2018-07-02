module steffan.javafxdemo.core {

    requires transitive javafx.base;

    exports steffan.javafxdemo.core.view.api;
    exports steffan.javafxdemo.core.models.domainmodel;
    exports steffan.javafxdemo.core.persistence.api;
    exports steffan.javafxdemo.core.control;
    exports steffan.javafxdemo.core.models.viewmodel;
    exports steffan.javafxdemo.core.commands.base;
    exports steffan.javafxdemo.core.commands.contactcommands;
}
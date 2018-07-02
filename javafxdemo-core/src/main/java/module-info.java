module steffan.javafxdemo.core {

    requires transitive javafx.base;

    exports steffan.javafxdemo.view.api;
    exports steffan.javafxdemo.models.domainmodel;
    exports steffan.javafxdemo.persistence.api;
    exports steffan.javafxdemo.control;
    exports steffan.javafxdemo.models.viewmodel;
    exports steffan.javafxdemo.commands.base;
    exports steffan.javafxdemo.commands.contactcommands;
}
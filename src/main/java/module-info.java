module org.example.dungeonrunner {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    exports dungeonrunner;
    exports dungeonrunner.constants;
    exports dungeonrunner.infoPanes;
    exports dungeonrunner.figures;
    exports dungeonrunner.creation;
}
TEMPLATE = app
TARGET = christmastree

QT += quick quickcontrols2 sql multimedia purchasing
CONFIG += c++17

DEFINES += QT_DEPRECATED_WARNINGS QT_NO_CAST_FROM_ASCII QT_NO_CAST_TO_ASCII

SOURCES += \
    src/admobhelper.cpp \
    src/androidgw.cpp \
    src/gifcreator.cpp \
    src/main.cpp \
    src/sharehelper.cpp \
    src/uihelper.cpp

HEADERS += \
    src/admobhelper.h \
    src/androidgw.h \
    src/gif.h \
    src/gifcreator.h \
    src/sharehelper.h \
    src/uihelper.h

RESOURCES += \
    qml.qrc \
    resources.qrc \
    translations.qrc

TRANSLATIONS += \
    translations/christmastree_de.ts \
    translations/christmastree_es.ts \
    translations/christmastree_fr.ts \
    translations/christmastree_it.ts \
    translations/christmastree_ru.ts \
    translations/christmastree_zh.ts

# Additional import path used to resolve QML modules in Qt Creator's code model
QML_IMPORT_PATH =

# Additional import path used to resolve QML modules just for Qt Quick Designer
QML_DESIGNER_IMPORT_PATH =

android {
    QT += androidextras

    CONFIG(release, debug|release) {
        CONFIG += qtquickcompiler
    }

    OTHER_FILES += \
        android/source/AndroidManifest.xml \
        android/source/build.gradle \
        android/source/gradle.properties \
        android/source/gradlew \
        android/source/gradlew.bat \
        android/source/gradle/wrapper/gradle-wrapper.jar \
        android/source/gradle/wrapper/gradle-wrapper.properties \
        android/source/res/drawable/splash_qt.xml \
        android/source/res/drawable/splash_theme.xml \
        android/source/res/drawable-hdpi/ic_launcher_background.png \
        android/source/res/drawable-hdpi/ic_launcher_foreground.png \
        android/source/res/drawable-hdpi/ic_splash_qt.png \
        android/source/res/drawable-hdpi/ic_splash_theme.png \
        android/source/res/drawable-mdpi/ic_launcher_background.png \
        android/source/res/drawable-mdpi/ic_launcher_foreground.png \
        android/source/res/drawable-mdpi/ic_splash_qt.png \
        android/source/res/drawable-mdpi/ic_splash_theme.png \
        android/source/res/drawable-xhdpi/ic_launcher_background.png \
        android/source/res/drawable-xhdpi/ic_launcher_foreground.png \
        android/source/res/drawable-xhdpi/ic_splash_qt.png \
        android/source/res/drawable-xhdpi/ic_splash_theme.png \
        android/source/res/drawable-xxhdpi/ic_launcher_background.png \
        android/source/res/drawable-xxhdpi/ic_launcher_foreground.png \
        android/source/res/drawable-xxhdpi/ic_splash_qt.png \
        android/source/res/drawable-xxhdpi/ic_splash_theme.png \
        android/source/res/drawable-xxxhdpi/ic_launcher_background.png \
        android/source/res/drawable-xxxhdpi/ic_launcher_foreground.png \
        android/source/res/drawable-xxxhdpi/ic_splash_qt.png \
        android/source/res/drawable-xxxhdpi/ic_splash_theme.png \
        android/source/res/mipmap-anydpi-v26/ic_launcher.xml \
        android/source/res/mipmap-hdpi/ic_launcher.png \
        android/source/res/mipmap-mdpi/ic_launcher.png \
        android/source/res/mipmap-xhdpi/ic_launcher.png \
        android/source/res/mipmap-xxhdpi/ic_launcher.png \
        android/source/res/mipmap-xxxhdpi/ic_launcher.png \
        android/source/res/values/colors.xml \
        android/source/res/values/libs.xml \
        android/source/res/values/strings.xml \
        android/source/res/values/themes.xml \
        android/source/res/values-de/strings.xml \
        android/source/res/values-es/strings.xml \
        android/source/res/values-fr/strings.xml \
        android/source/res/values-it/strings.xml \
        android/source/res/values-ru/strings.xml \
        android/source/res/values-zh/strings.xml \
        android/source/res/xml/backup_content.xml \
        android/source/res/xml/provider_paths.xml \
        android/source/src/com/derevenetz/oleg/christmastree/TreeActivity.java

    ANDROID_PACKAGE_SOURCE_DIR = $$PWD/android/source
}

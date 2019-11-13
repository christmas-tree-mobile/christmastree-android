#include <QtCore/QString>
#include <QtCore/QLocale>
#include <QtCore/QTranslator>
#include <QtGui/QGuiApplication>
#include <QtQml/QQmlApplicationEngine>
#include <QtQml/QQmlContext>
#include <QtQuickControls2/QQuickStyle>
#include <QtAndroidExtras/QtAndroid>

#include "admobhelper.h"
#include "androidgw.h"
#include "gifcreator.h"
#include "sharehelper.h"
#include "uihelper.h"

int main(int argc, char *argv[])
{
    QTranslator     translator;
    QGuiApplication app(argc, argv);

    if (translator.load(QStringLiteral(":/tr/christmastree_%1").arg(QLocale::system().name()))) {
        QGuiApplication::installTranslator(&translator);
    }

    QObject::connect(&AndroidGW::GetInstance(), &AndroidGW::interstitialActiveUpdated, &AdMobHelper::GetInstance(), &AdMobHelper::setInterstitialActive);
    QObject::connect(&AndroidGW::GetInstance(), &AndroidGW::bannerViewHeightUpdated,   &AdMobHelper::GetInstance(), &AdMobHelper::setBannerViewHeight);

    QQmlApplicationEngine engine;

    engine.rootContext()->setContextProperty(QStringLiteral("AdMobHelper"), &AdMobHelper::GetInstance());
    engine.rootContext()->setContextProperty(QStringLiteral("GIFCreator"), &GIFCreator::GetInstance());
    engine.rootContext()->setContextProperty(QStringLiteral("ShareHelper"), &ShareHelper::GetInstance());
    engine.rootContext()->setContextProperty(QStringLiteral("UIHelper"), &UIHelper::GetInstance());

    QQuickStyle::setStyle(QStringLiteral("Default"));

    engine.load(QUrl(QStringLiteral("qrc:/qml/main.qml")));

    QtAndroid::hideSplashScreen();

    if (engine.rootObjects().isEmpty()) {
        return -1;
    } else {
        return QGuiApplication::exec();
    }
}

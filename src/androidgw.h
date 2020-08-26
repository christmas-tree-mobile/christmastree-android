#ifndef ANDROIDGW_H
#define ANDROIDGW_H

#include <QtCore/QObject>

class AndroidGW : public QObject
{
    Q_OBJECT

private:
    explicit AndroidGW(QObject *parent = nullptr);
    ~AndroidGW() noexcept override = default;

public:
    AndroidGW(const AndroidGW &) = delete;
    AndroidGW(AndroidGW &&) noexcept = delete;

    AndroidGW &operator=(const AndroidGW &) = delete;
    AndroidGW &operator=(AndroidGW &&) noexcept = delete;

    static AndroidGW &GetInstance();

signals:
    void interstitialActiveUpdated(bool active);
    void bannerViewHeightUpdated(int bannerViewHeight);
    void shareImageCompleted();
};

#endif // ANDROIDGW_H

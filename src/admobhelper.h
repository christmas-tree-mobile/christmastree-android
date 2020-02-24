#ifndef ADMOBHELPER_H
#define ADMOBHELPER_H

#include <QtCore/QObject>
#include <QtCore/QString>

class AdMobHelper : public QObject
{
    Q_OBJECT

    Q_PROPERTY(bool interstitialReady  READ interstitialReady)
    Q_PROPERTY(bool interstitialActive READ interstitialActive NOTIFY interstitialActiveChanged)
    Q_PROPERTY(int  bannerViewHeight   READ bannerViewHeight   NOTIFY bannerViewHeightChanged)

private:
    explicit AdMobHelper(QObject *parent = nullptr);
    ~AdMobHelper() noexcept override = default;

public:
    static const QString ADMOB_BANNERVIEW_UNIT_ID,
                         ADMOB_INTERSTITIAL_UNIT_ID;

    AdMobHelper(const AdMobHelper &) = delete;
    AdMobHelper(AdMobHelper &&) noexcept = delete;

    AdMobHelper &operator=(const AdMobHelper &) = delete;
    AdMobHelper &operator=(AdMobHelper &&) noexcept = delete;

    static AdMobHelper &GetInstance();

    bool interstitialReady() const;
    bool interstitialActive() const;
    int bannerViewHeight() const;

    Q_INVOKABLE void initAds();

    Q_INVOKABLE void setPersonalization(bool personalized) const;

    Q_INVOKABLE void showBannerView() const;
    Q_INVOKABLE void hideBannerView() const;

    Q_INVOKABLE void showInterstitial() const;

public slots:
    void setInterstitialActive(bool active);
    void setBannerViewHeight(int height);

signals:
    void interstitialActiveChanged(bool interstitialActive);
    void bannerViewHeightChanged(int bannerViewHeight);

private:
    bool Initialized, InterstitialActive;
    int  BannerViewHeight;
};

#endif // ADMOBHELPER_H

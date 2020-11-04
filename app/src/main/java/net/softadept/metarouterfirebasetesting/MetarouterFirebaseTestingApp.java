package net.softadept.metarouterfirebasetesting;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import com.segment.analytics.Analytics;
import com.segment.analytics.ConnectionFactory;
import com.segment.analytics.android.integrations.firebase.FirebaseIntegration;

import java.io.IOException;
import java.net.HttpURLConnection;

public class MetarouterFirebaseTestingApp extends Application {
    private static final String ANALYTICS_WRITE_KEY = "RSQMngto3iHCQ3qEKm5Qx";

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize a new instance of the Analytics client.
        Analytics.Builder builder =
                new Analytics.Builder(this, ANALYTICS_WRITE_KEY)
                        .use(FirebaseIntegration.FACTORY)
                        .trackApplicationLifecycleEvents()
                        .connectionFactory(new ConnectionFactory() {
                            @Override protected HttpURLConnection openConnection(String url) throws IOException {
                                Uri parsedUri = Uri.parse(url);
                                String path = parsedUri.getPath();
                                String host = parsedUri.getHost();
                                if (host.equals("cdn-settings.segment.com")) {
                                    return super.openConnection("https://cdn.metarouter.io" + path);
                                } else if (host.equals("api.segment.io")) {
                                    return super.openConnection("https://e.metarouter.io" + path);
                                }
                                return super.openConnection(url);
                            }
                        })
                        .trackAttributionInformation()
                        .recordScreenViews();

        // Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(builder.build());

        // Now anytime you call Analytics.with, the custom instance will be returned.
        Analytics analytics = Analytics.with(this);

        // If you need to know when integrations have been initialized, use the onIntegrationReady
        // listener.
        analytics.onIntegrationReady(
                "Segment.io",
                new Analytics.Callback() {
                    @Override
                    public void onReady(Object instance) {
                        Log.d("Segment Sample", "Metarouter integration ready.");
                    }
                });
    }
}

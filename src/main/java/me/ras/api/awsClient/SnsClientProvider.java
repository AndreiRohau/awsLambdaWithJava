package me.ras.api.awsClient;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.util.logging.Logger;

import static me.ras.api.Constant.getAwsRegion;
import static me.ras.api.Constant.getCredentialProvider;

public class SnsClientProvider {
    private static Logger log = Logger.getLogger(SnsClientProvider.class.getName());

    private SnsClient snsClient;

    public SnsClientProvider() {
        final Region region = Region.of(getAwsRegion());

        snsClient = SnsClient.builder()
                .credentialsProvider(getCredentialProvider())
                .region(region)
                .build();

        log.info("SnsClient is built. Region=" + region);
    }

    public static SnsClientProvider init() {
        return new SnsClientProvider();
    }

    public SnsClient getSnsClient() {
        return snsClient;
    }
}

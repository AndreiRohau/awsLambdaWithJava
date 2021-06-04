package me.ras.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import me.ras.api.awsClient.SnsClientProvider;
import me.ras.api.awsClient.SqsClientProvider;
import me.ras.api.service.NotificationService;
import me.ras.api.service.NotificationServiceImpl;

import java.util.logging.Logger;

public class LambdaSqsToSns implements RequestHandler<LambdaSqsToSns.Request, LambdaSqsToSns.Response> {
    private static Logger log = Logger.getLogger(LambdaSqsToSns.class.getName());

    @Override
    public Response handleRequest(Request request, Context context) {
        final String logMsg = "Called awsLambdaSqsToSns#LambdaSqsToSns#handleRequest(). Request={" + request + "}, Context={" + context + "}";
        log.info(logMsg);

        Constant.initConstants(request);
        log.info(Constant.concatAllValues());

        final SnsClientProvider snsClientProvider = SnsClientProvider.init();
        final SqsClientProvider sqsClientProvider = SqsClientProvider.init();
        final NotificationService notificationService = NotificationServiceImpl.init(snsClientProvider, sqsClientProvider);
        notificationService.sendNotifications();

        return Response.createResponse("Response msg is=[" + request.msg + "].");
    }

    static class Request {
        public String msg;
        public String awsAccessKeyId;
        public String awsSecretKey;
        public String awsRegion;
        public String awsSnsTopicArn;
        public String awsSqsUrl;
        public String awsSqsQueueName;
    }

    static class Response {
        public String msg;

        public Response(String msg) {
            this.msg = msg;
        }

        public static Response createResponse(String msg) {
            return new Response(msg);
        }
    }
}

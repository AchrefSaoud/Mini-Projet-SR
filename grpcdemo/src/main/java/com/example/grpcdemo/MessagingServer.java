package com.example.grpcdemo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class MessagingServer {
    private int port = 50051;
    private Server server;

    private void start() throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(new MessagingServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            MessagingServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        final MessagingServer server = new MessagingServer();
        server.start();
        server.blockUntilShutdown();
    }

    static class MessagingServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {
        private Map<String, List<MessageResponse>> userMessages = new HashMap<>();
        @Override
        public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
            String messageId = UUID.randomUUID().toString();
            MessageResponse response = MessageResponse.newBuilder()
                .setMessageId(messageId)
                .setStatus("Message sent successfully")
                .build();

            userMessages.computeIfAbsent(request.getRecipientId(), k -> new ArrayList<>())
                    .add(response);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getReceivedMessages(UserRequest request, StreamObserver<MessagesResponse> responseObserver) {
        List<MessageResponse> messages = userMessages.getOrDefault(request.getUserId(), Collections.emptyList());
            MessagesResponse.Builder messagesResponseBuilder = MessagesResponse.newBuilder();
            messagesResponseBuilder.addAllMessages(messages);
            
            responseObserver.onNext(messagesResponseBuilder.build());
            responseObserver.onCompleted();
        }
    }
}


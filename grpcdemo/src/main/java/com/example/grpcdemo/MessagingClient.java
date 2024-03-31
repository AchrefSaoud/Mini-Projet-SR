package com.example.grpcdemo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class MessagingClient {
    private final ManagedChannel channel;
    private final MessagingServiceGrpc.MessagingServiceBlockingStub blockingStub;

    public MessagingClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = MessagingServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    public void sendMessage(String senderId, String recipientId, String text) {
        MessageRequest request = MessageRequest.newBuilder()
                .setSenderId(senderId)
                .setRecipientId(recipientId)
                .setText(text)
                .build();

        MessageResponse response = blockingStub.sendMessage(request);
        System.out.println("Message sent, message ID: " + response.getMessageId());
    }

    public void getReceivedMessages(String userId) {
        UserRequest request = UserRequest.newBuilder()
                .setUserId(userId)
                .build();

        MessagesResponse response = blockingStub.getReceivedMessages(request);
        for (MessageResponse message : response.getMessagesList()) {
            System.out.println("Received message, message ID: " + message.getMessageId());
        }
    }

    public static void main(String[] args) {
        MessagingClient client = new MessagingClient("localhost", 50051);
        try {
            client.sendMessage("sender123", "recipient456", "Hello, world!");
            client.getReceivedMessages("user789");
        } finally {
            try {
                client.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

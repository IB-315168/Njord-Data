package com.sep3yg9.njorddata.grpc.protobuf.user;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: UserService.proto")
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final String SERVICE_NAME = "com.sep3yg9.njorddata.grpc.protobuf.user.UserService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser,
      com.sep3yg9.njorddata.grpc.protobuf.user.User> METHOD_CREATE_USER =
      io.grpc.MethodDescriptor.<com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser, com.sep3yg9.njorddata.grpc.protobuf.user.User>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "CreateUser"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.User.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser,
      com.google.protobuf.Empty> METHOD_UPDATE_USER =
      io.grpc.MethodDescriptor.<com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser, com.google.protobuf.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "UpdateUser"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int32Value,
      com.google.protobuf.Empty> METHOD_DELETE_USER =
      io.grpc.MethodDescriptor.<com.google.protobuf.Int32Value, com.google.protobuf.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "DeleteUser"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Int32Value.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.sep3yg9.njorddata.grpc.protobuf.user.User> METHOD_GET_BY_EMAIL =
      io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, com.sep3yg9.njorddata.grpc.protobuf.user.User>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "GetByEmail"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.StringValue.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.User.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.protobuf.Int32Value,
      com.sep3yg9.njorddata.grpc.protobuf.user.User> METHOD_GET_BY_ID =
      io.grpc.MethodDescriptor.<com.google.protobuf.Int32Value, com.sep3yg9.njorddata.grpc.protobuf.user.User>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "GetById"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.google.protobuf.Int32Value.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.User.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser,
      com.sep3yg9.njorddata.grpc.protobuf.user.UserList> METHOD_SEARCH_USER =
      io.grpc.MethodDescriptor.<com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser, com.sep3yg9.njorddata.grpc.protobuf.user.UserList>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.sep3yg9.njorddata.grpc.protobuf.user.UserService", "SearchUser"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.sep3yg9.njorddata.grpc.protobuf.user.UserList.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    return new UserServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UserServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UserServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class UserServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createUser(com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_USER, responseObserver);
    }

    /**
     */
    public void updateUser(com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_USER, responseObserver);
    }

    /**
     */
    public void deleteUser(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_USER, responseObserver);
    }

    /**
     */
    public void getByEmail(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_EMAIL, responseObserver);
    }

    /**
     */
    public void getById(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BY_ID, responseObserver);
    }

    /**
     */
    public void searchUser(com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.UserList> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEARCH_USER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_USER,
            asyncUnaryCall(
              new MethodHandlers<
                com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser,
                com.sep3yg9.njorddata.grpc.protobuf.user.User>(
                  this, METHODID_CREATE_USER)))
          .addMethod(
            METHOD_UPDATE_USER,
            asyncUnaryCall(
              new MethodHandlers<
                com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser,
                com.google.protobuf.Empty>(
                  this, METHODID_UPDATE_USER)))
          .addMethod(
            METHOD_DELETE_USER,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int32Value,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_USER)))
          .addMethod(
            METHOD_GET_BY_EMAIL,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                com.sep3yg9.njorddata.grpc.protobuf.user.User>(
                  this, METHODID_GET_BY_EMAIL)))
          .addMethod(
            METHOD_GET_BY_ID,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Int32Value,
                com.sep3yg9.njorddata.grpc.protobuf.user.User>(
                  this, METHODID_GET_BY_ID)))
          .addMethod(
            METHOD_SEARCH_USER,
            asyncUnaryCall(
              new MethodHandlers<
                com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser,
                com.sep3yg9.njorddata.grpc.protobuf.user.UserList>(
                  this, METHODID_SEARCH_USER)))
          .build();
    }
  }

  /**
   */
  public static final class UserServiceStub extends io.grpc.stub.AbstractStub<UserServiceStub> {
    private UserServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     */
    public void createUser(com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_USER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateUser(com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_USER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteUser(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_USER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getByEmail(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_EMAIL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getById(com.google.protobuf.Int32Value request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BY_ID, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchUser(com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser request,
        io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.UserList> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEARCH_USER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserServiceBlockingStub extends io.grpc.stub.AbstractStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.sep3yg9.njorddata.grpc.protobuf.user.User createUser(com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_USER, getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateUser(com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_USER, getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty deleteUser(com.google.protobuf.Int32Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_USER, getCallOptions(), request);
    }

    /**
     */
    public com.sep3yg9.njorddata.grpc.protobuf.user.User getByEmail(com.google.protobuf.StringValue request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_EMAIL, getCallOptions(), request);
    }

    /**
     */
    public com.sep3yg9.njorddata.grpc.protobuf.user.User getById(com.google.protobuf.Int32Value request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BY_ID, getCallOptions(), request);
    }

    /**
     */
    public com.sep3yg9.njorddata.grpc.protobuf.user.UserList searchUser(com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEARCH_USER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserServiceFutureStub extends io.grpc.stub.AbstractStub<UserServiceFutureStub> {
    private UserServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sep3yg9.njorddata.grpc.protobuf.user.User> createUser(
        com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_USER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateUser(
        com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_USER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteUser(
        com.google.protobuf.Int32Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_USER, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sep3yg9.njorddata.grpc.protobuf.user.User> getByEmail(
        com.google.protobuf.StringValue request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_EMAIL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sep3yg9.njorddata.grpc.protobuf.user.User> getById(
        com.google.protobuf.Int32Value request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BY_ID, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.sep3yg9.njorddata.grpc.protobuf.user.UserList> searchUser(
        com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEARCH_USER, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_USER = 0;
  private static final int METHODID_UPDATE_USER = 1;
  private static final int METHODID_DELETE_USER = 2;
  private static final int METHODID_GET_BY_EMAIL = 3;
  private static final int METHODID_GET_BY_ID = 4;
  private static final int METHODID_SEARCH_USER = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_USER:
          serviceImpl.createUser((com.sep3yg9.njorddata.grpc.protobuf.user.CreatingUser) request,
              (io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User>) responseObserver);
          break;
        case METHODID_UPDATE_USER:
          serviceImpl.updateUser((com.sep3yg9.njorddata.grpc.protobuf.user.UpdatingUser) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_DELETE_USER:
          serviceImpl.deleteUser((com.google.protobuf.Int32Value) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_BY_EMAIL:
          serviceImpl.getByEmail((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User>) responseObserver);
          break;
        case METHODID_GET_BY_ID:
          serviceImpl.getById((com.google.protobuf.Int32Value) request,
              (io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.User>) responseObserver);
          break;
        case METHODID_SEARCH_USER:
          serviceImpl.searchUser((com.sep3yg9.njorddata.grpc.protobuf.user.SearchingUser) request,
              (io.grpc.stub.StreamObserver<com.sep3yg9.njorddata.grpc.protobuf.user.UserList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class UserServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.sep3yg9.njorddata.grpc.protobuf.user.UserServiceOuterClass.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceDescriptorSupplier())
              .addMethod(METHOD_CREATE_USER)
              .addMethod(METHOD_UPDATE_USER)
              .addMethod(METHOD_DELETE_USER)
              .addMethod(METHOD_GET_BY_EMAIL)
              .addMethod(METHOD_GET_BY_ID)
              .addMethod(METHOD_SEARCH_USER)
              .build();
        }
      }
    }
    return result;
  }
}

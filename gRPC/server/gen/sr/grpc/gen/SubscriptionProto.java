// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: subscription.proto

package sr.grpc.gen;

public final class SubscriptionProto {
  private SubscriptionProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_subscription_SubscriptionRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_subscription_SubscriptionRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_subscription_Event_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_subscription_Event_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_subscription_PingMsg_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_subscription_PingMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022subscription.proto\022\014subscription\"?\n\023Su" +
      "bscriptionRequest\022(\n\010category\030\001 \001(\0162\026.su" +
      "bscription.Category\"]\n\005Event\022\014\n\004name\030\001 \001" +
      "(\t\022(\n\010category\030\002 \001(\0162\026.subscription.Cate" +
      "gory\022\014\n\004date\030\003 \001(\t\022\016\n\006cities\030\004 \003(\t\"\t\n\007Pi" +
      "ngMsg*C\n\010Category\022\014\n\010BUSINESS\020\000\022\013\n\007FASHI" +
      "ON\020\001\022\006\n\002IT\020\002\022\t\n\005MUSIC\020\003\022\t\n\005SPORT\020\0042\224\001\n\014S" +
      "ubscription\022N\n\022InformAboutMeeting\022!.subs" +
      "cription.SubscriptionRequest\032\023.subscript" +
      "ion.Event0\001\0224\n\004Ping\022\025.subscription.PingM" +
      "sg\032\025.subscription.PingMsgB\"\n\013sr.grpc.gen" +
      "B\021SubscriptionProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_subscription_SubscriptionRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_subscription_SubscriptionRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_subscription_SubscriptionRequest_descriptor,
        new java.lang.String[] { "Category", });
    internal_static_subscription_Event_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_subscription_Event_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_subscription_Event_descriptor,
        new java.lang.String[] { "Name", "Category", "Date", "Cities", });
    internal_static_subscription_PingMsg_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_subscription_PingMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_subscription_PingMsg_descriptor,
        new java.lang.String[] { });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

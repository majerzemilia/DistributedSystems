// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: subscription.proto

package sr.grpc.gen;

/**
 * Protobuf type {@code subscription.Event}
 */
public  final class Event extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:subscription.Event)
    EventOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Event.newBuilder() to construct.
  private Event(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Event() {
    name_ = "";
    category_ = 0;
    date_ = "";
    cities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Event();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Event(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            category_ = rawValue;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            date_ = s;
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              cities_ = new com.google.protobuf.LazyStringArrayList();
              mutable_bitField0_ |= 0x00000001;
            }
            cities_.add(s);
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        cities_ = cities_.getUnmodifiableView();
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return sr.grpc.gen.SubscriptionProto.internal_static_subscription_Event_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return sr.grpc.gen.SubscriptionProto.internal_static_subscription_Event_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            sr.grpc.gen.Event.class, sr.grpc.gen.Event.Builder.class);
  }

  public static final int NAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object name_;
  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CATEGORY_FIELD_NUMBER = 2;
  private int category_;
  /**
   * <code>.subscription.Category category = 2;</code>
   * @return The enum numeric value on the wire for category.
   */
  public int getCategoryValue() {
    return category_;
  }
  /**
   * <code>.subscription.Category category = 2;</code>
   * @return The category.
   */
  public sr.grpc.gen.Category getCategory() {
    @SuppressWarnings("deprecation")
    sr.grpc.gen.Category result = sr.grpc.gen.Category.valueOf(category_);
    return result == null ? sr.grpc.gen.Category.UNRECOGNIZED : result;
  }

  public static final int DATE_FIELD_NUMBER = 3;
  private volatile java.lang.Object date_;
  /**
   * <code>string date = 3;</code>
   * @return The date.
   */
  public java.lang.String getDate() {
    java.lang.Object ref = date_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      date_ = s;
      return s;
    }
  }
  /**
   * <code>string date = 3;</code>
   * @return The bytes for date.
   */
  public com.google.protobuf.ByteString
      getDateBytes() {
    java.lang.Object ref = date_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      date_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CITIES_FIELD_NUMBER = 4;
  private com.google.protobuf.LazyStringList cities_;
  /**
   * <code>repeated string cities = 4;</code>
   * @return A list containing the cities.
   */
  public com.google.protobuf.ProtocolStringList
      getCitiesList() {
    return cities_;
  }
  /**
   * <code>repeated string cities = 4;</code>
   * @return The count of cities.
   */
  public int getCitiesCount() {
    return cities_.size();
  }
  /**
   * <code>repeated string cities = 4;</code>
   * @param index The index of the element to return.
   * @return The cities at the given index.
   */
  public java.lang.String getCities(int index) {
    return cities_.get(index);
  }
  /**
   * <code>repeated string cities = 4;</code>
   * @param index The index of the value to return.
   * @return The bytes of the cities at the given index.
   */
  public com.google.protobuf.ByteString
      getCitiesBytes(int index) {
    return cities_.getByteString(index);
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    if (category_ != sr.grpc.gen.Category.BUSINESS.getNumber()) {
      output.writeEnum(2, category_);
    }
    if (!getDateBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, date_);
    }
    for (int i = 0; i < cities_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, cities_.getRaw(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    if (category_ != sr.grpc.gen.Category.BUSINESS.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, category_);
    }
    if (!getDateBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, date_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < cities_.size(); i++) {
        dataSize += computeStringSizeNoTag(cities_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getCitiesList().size();
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof sr.grpc.gen.Event)) {
      return super.equals(obj);
    }
    sr.grpc.gen.Event other = (sr.grpc.gen.Event) obj;

    if (!getName()
        .equals(other.getName())) return false;
    if (category_ != other.category_) return false;
    if (!getDate()
        .equals(other.getDate())) return false;
    if (!getCitiesList()
        .equals(other.getCitiesList())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + CATEGORY_FIELD_NUMBER;
    hash = (53 * hash) + category_;
    hash = (37 * hash) + DATE_FIELD_NUMBER;
    hash = (53 * hash) + getDate().hashCode();
    if (getCitiesCount() > 0) {
      hash = (37 * hash) + CITIES_FIELD_NUMBER;
      hash = (53 * hash) + getCitiesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static sr.grpc.gen.Event parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sr.grpc.gen.Event parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sr.grpc.gen.Event parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sr.grpc.gen.Event parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sr.grpc.gen.Event parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static sr.grpc.gen.Event parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static sr.grpc.gen.Event parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sr.grpc.gen.Event parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static sr.grpc.gen.Event parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static sr.grpc.gen.Event parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static sr.grpc.gen.Event parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static sr.grpc.gen.Event parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(sr.grpc.gen.Event prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code subscription.Event}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:subscription.Event)
      sr.grpc.gen.EventOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return sr.grpc.gen.SubscriptionProto.internal_static_subscription_Event_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return sr.grpc.gen.SubscriptionProto.internal_static_subscription_Event_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              sr.grpc.gen.Event.class, sr.grpc.gen.Event.Builder.class);
    }

    // Construct using sr.grpc.gen.Event.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      name_ = "";

      category_ = 0;

      date_ = "";

      cities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return sr.grpc.gen.SubscriptionProto.internal_static_subscription_Event_descriptor;
    }

    @java.lang.Override
    public sr.grpc.gen.Event getDefaultInstanceForType() {
      return sr.grpc.gen.Event.getDefaultInstance();
    }

    @java.lang.Override
    public sr.grpc.gen.Event build() {
      sr.grpc.gen.Event result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public sr.grpc.gen.Event buildPartial() {
      sr.grpc.gen.Event result = new sr.grpc.gen.Event(this);
      int from_bitField0_ = bitField0_;
      result.name_ = name_;
      result.category_ = category_;
      result.date_ = date_;
      if (((bitField0_ & 0x00000001) != 0)) {
        cities_ = cities_.getUnmodifiableView();
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.cities_ = cities_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof sr.grpc.gen.Event) {
        return mergeFrom((sr.grpc.gen.Event)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(sr.grpc.gen.Event other) {
      if (other == sr.grpc.gen.Event.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.category_ != 0) {
        setCategoryValue(other.getCategoryValue());
      }
      if (!other.getDate().isEmpty()) {
        date_ = other.date_;
        onChanged();
      }
      if (!other.cities_.isEmpty()) {
        if (cities_.isEmpty()) {
          cities_ = other.cities_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureCitiesIsMutable();
          cities_.addAll(other.cities_);
        }
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      sr.grpc.gen.Event parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (sr.grpc.gen.Event) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.lang.Object name_ = "";
    /**
     * <code>string name = 1;</code>
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string name = 1;</code>
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <code>string name = 1;</code>
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
    }

    private int category_ = 0;
    /**
     * <code>.subscription.Category category = 2;</code>
     * @return The enum numeric value on the wire for category.
     */
    public int getCategoryValue() {
      return category_;
    }
    /**
     * <code>.subscription.Category category = 2;</code>
     * @param value The enum numeric value on the wire for category to set.
     * @return This builder for chaining.
     */
    public Builder setCategoryValue(int value) {
      category_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.subscription.Category category = 2;</code>
     * @return The category.
     */
    public sr.grpc.gen.Category getCategory() {
      @SuppressWarnings("deprecation")
      sr.grpc.gen.Category result = sr.grpc.gen.Category.valueOf(category_);
      return result == null ? sr.grpc.gen.Category.UNRECOGNIZED : result;
    }
    /**
     * <code>.subscription.Category category = 2;</code>
     * @param value The category to set.
     * @return This builder for chaining.
     */
    public Builder setCategory(sr.grpc.gen.Category value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      category_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.subscription.Category category = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearCategory() {
      
      category_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object date_ = "";
    /**
     * <code>string date = 3;</code>
     * @return The date.
     */
    public java.lang.String getDate() {
      java.lang.Object ref = date_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        date_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string date = 3;</code>
     * @return The bytes for date.
     */
    public com.google.protobuf.ByteString
        getDateBytes() {
      java.lang.Object ref = date_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        date_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string date = 3;</code>
     * @param value The date to set.
     * @return This builder for chaining.
     */
    public Builder setDate(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      date_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string date = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDate() {
      
      date_ = getDefaultInstance().getDate();
      onChanged();
      return this;
    }
    /**
     * <code>string date = 3;</code>
     * @param value The bytes for date to set.
     * @return This builder for chaining.
     */
    public Builder setDateBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      date_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringList cities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    private void ensureCitiesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        cities_ = new com.google.protobuf.LazyStringArrayList(cities_);
        bitField0_ |= 0x00000001;
       }
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @return A list containing the cities.
     */
    public com.google.protobuf.ProtocolStringList
        getCitiesList() {
      return cities_.getUnmodifiableView();
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @return The count of cities.
     */
    public int getCitiesCount() {
      return cities_.size();
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param index The index of the element to return.
     * @return The cities at the given index.
     */
    public java.lang.String getCities(int index) {
      return cities_.get(index);
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param index The index of the value to return.
     * @return The bytes of the cities at the given index.
     */
    public com.google.protobuf.ByteString
        getCitiesBytes(int index) {
      return cities_.getByteString(index);
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param index The index to set the value at.
     * @param value The cities to set.
     * @return This builder for chaining.
     */
    public Builder setCities(
        int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureCitiesIsMutable();
      cities_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param value The cities to add.
     * @return This builder for chaining.
     */
    public Builder addCities(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  ensureCitiesIsMutable();
      cities_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param values The cities to add.
     * @return This builder for chaining.
     */
    public Builder addAllCities(
        java.lang.Iterable<java.lang.String> values) {
      ensureCitiesIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, cities_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearCities() {
      cities_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated string cities = 4;</code>
     * @param value The bytes of the cities to add.
     * @return This builder for chaining.
     */
    public Builder addCitiesBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      ensureCitiesIsMutable();
      cities_.add(value);
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:subscription.Event)
  }

  // @@protoc_insertion_point(class_scope:subscription.Event)
  private static final sr.grpc.gen.Event DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new sr.grpc.gen.Event();
  }

  public static sr.grpc.gen.Event getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Event>
      PARSER = new com.google.protobuf.AbstractParser<Event>() {
    @java.lang.Override
    public Event parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Event(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Event> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Event> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public sr.grpc.gen.Event getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}


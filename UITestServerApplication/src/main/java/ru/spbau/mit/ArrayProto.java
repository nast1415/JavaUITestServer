// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: array.proto

package ru.spbau.mit;

public final class ArrayProto {
  private ArrayProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ArrayOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Array)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated int32 element = 1;</code>
     */
    java.util.List<Integer> getElementList();
    /**
     * <code>repeated int32 element = 1;</code>
     */
    int getElementCount();
    /**
     * <code>repeated int32 element = 1;</code>
     */
    int getElement(int index);
  }
  /**
   * Protobuf type {@code Array}
   */
  public static final class Array extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Array)
      ArrayOrBuilder {
    // Use Array.newBuilder() to construct.
    private Array(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Array(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Array defaultInstance;
    public static Array getDefaultInstance() {
      return defaultInstance;
    }

    public Array getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Array(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                element_ = new java.util.ArrayList<Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              element_.add(input.readInt32());
              break;
            }
            case 10: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001) && input.getBytesUntilLimit() > 0) {
                element_ = new java.util.ArrayList<Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              while (input.getBytesUntilLimit() > 0) {
                element_.add(input.readInt32());
              }
              input.popLimit(limit);
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          element_ = java.util.Collections.unmodifiableList(element_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ArrayProto.internal_static_Array_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ArrayProto.internal_static_Array_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Array.class, Builder.class);
    }

    public static com.google.protobuf.Parser<Array> PARSER =
        new com.google.protobuf.AbstractParser<Array>() {
      public Array parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Array(input, extensionRegistry);
      }
    };

    @Override
    public com.google.protobuf.Parser<Array> getParserForType() {
      return PARSER;
    }

    public static final int ELEMENT_FIELD_NUMBER = 1;
    private java.util.List<Integer> element_;
    /**
     * <code>repeated int32 element = 1;</code>
     */
    public java.util.List<Integer>
        getElementList() {
      return element_;
    }
    /**
     * <code>repeated int32 element = 1;</code>
     */
    public int getElementCount() {
      return element_.size();
    }
    /**
     * <code>repeated int32 element = 1;</code>
     */
    public int getElement(int index) {
      return element_.get(index);
    }

    private void initFields() {
      element_ = java.util.Collections.emptyList();
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      for (int i = 0; i < element_.size(); i++) {
        output.writeInt32(1, element_.get(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < element_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(element_.get(i));
        }
        size += dataSize;
        size += 1 * getElementList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    protected Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static Array parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Array parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Array parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Array parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Array parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Array parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static Array parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static Array parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static Array parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static Array parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(Array prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Array}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Array)
        ArrayOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ArrayProto.internal_static_Array_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ArrayProto.internal_static_Array_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Array.class, Builder.class);
      }

      // Construct using ru.spbau.mit.ArrayProto.Array.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        element_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ArrayProto.internal_static_Array_descriptor;
      }

      public Array getDefaultInstanceForType() {
        return Array.getDefaultInstance();
      }

      public Array build() {
        Array result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Array buildPartial() {
        Array result = new Array(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          element_ = java.util.Collections.unmodifiableList(element_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.element_ = element_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Array) {
          return mergeFrom((Array)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Array other) {
        if (other == Array.getDefaultInstance()) return this;
        if (!other.element_.isEmpty()) {
          if (element_.isEmpty()) {
            element_ = other.element_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureElementIsMutable();
            element_.addAll(other.element_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Array parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Array) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<Integer> element_ = java.util.Collections.emptyList();
      private void ensureElementIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          element_ = new java.util.ArrayList<Integer>(element_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public java.util.List<Integer>
          getElementList() {
        return java.util.Collections.unmodifiableList(element_);
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public int getElementCount() {
        return element_.size();
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public int getElement(int index) {
        return element_.get(index);
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public Builder setElement(
          int index, int value) {
        ensureElementIsMutable();
        element_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public Builder addElement(int value) {
        ensureElementIsMutable();
        element_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public Builder addAllElement(
          Iterable<? extends Integer> values) {
        ensureElementIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, element_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated int32 element = 1;</code>
       */
      public Builder clearElement() {
        element_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Array)
    }

    static {
      defaultInstance = new Array(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Array)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Array_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Array_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\013array.proto\"\030\n\005Array\022\017\n\007element\030\001 \003(\005B" +
      "\032\n\014ru.spbau.mitB\nArrayProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Array_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Array_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Array_descriptor,
        new String[] { "Element", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.blob;

import java.io.Closeable;

/**
 * A simple store and retrieve binary large objects (BLOBs).
 *
 * @apiNote BlobService 是用于管理和存储 Flink 作业所需的各种资源文件的服务。这些资源文件可以是用户上传的 JAR 文件、类库、配置文件等。
 * BlobService 是 C/S 模式，BlobServer 是服务端，负责接收客户端的上传 Blob 请求，并根据请求将 Blob 存储到相应的 BlobStore 中。
 * BlobServer 还负责处理客户端下载 Blob 的请求。BlobClient 是客户端，用于上传和下载 Blob，BlobClient 向 BlobServer 发送上传请求，
 * 并根据 BlobKey 下载 Blob。BlobClient 和 BlobServer 之间的通信采用的是 BIO Socket。
 */
public interface BlobService extends Closeable {

    /**
     * Returns a BLOB service for accessing permanent BLOBs.
     *
     * @return BLOB service
     */
    PermanentBlobService getPermanentBlobService();

    /**
     * Returns a BLOB service for accessing transient BLOBs.
     *
     * @return BLOB service
     */
    TransientBlobService getTransientBlobService();

    /**
     * Returns the port of the BLOB server that this BLOB service is working with.
     *
     * @return the port of the blob server.
     */
    int getPort();
}

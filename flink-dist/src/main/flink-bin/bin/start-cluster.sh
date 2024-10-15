#!/usr/bin/env bash
################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

. "$bin"/config.sh

# Start the JobManager instance(s)
shopt -s nocasematch
if [[ $HIGH_AVAILABILITY == "zookeeper" ]]; then
    # HA Mode
    # 读取 masters 配置文件，获取主节点的配置信息
    readMasters

    echo "Starting HA cluster with ${#MASTERS[@]} masters."
    # 遍历启动多个主节点
    for ((i=0;i<${#MASTERS[@]};++i)); do
        # 读取到的其中一个主节点
        master=${MASTERS[i]}
        webuiport=${WEBUIPORTS[i]}

        if [ ${MASTERS_ALL_LOCALHOST} = true ] ; then
            # 启动主节点（JobManager）的命令
            "${FLINK_BIN_DIR}"/jobmanager.sh start "${master}" "${webuiport}"
        else
            ssh -n $FLINK_SSH_OPTS $master -- "nohup /bin/bash -l \"${FLINK_BIN_DIR}/jobmanager.sh\" start ${master} ${webuiport} &"
        fi
    done

else
    echo "Starting cluster."

    # Start single JobManager on this machine
    "$FLINK_BIN_DIR"/jobmanager.sh start
fi
shopt -u nocasematch

# Start TaskManager instance(s)
# 启动从节点实例（TaskManager）
TMWorkers start

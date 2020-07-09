package com.handdubbo.register;

import com.handdubbo.constant.ZooKeeperConst;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author: sxx
 * @Date 2020/6/26 16:05
 * @Version 1.0
 **/
public class RegisterCenter {

    private ZooKeeper zk;


    /**
     * 连接ZooKeeper, 创建dubbo根节点
     */
    public RegisterCenter() {
        try {
            final CountDownLatch connectedSignal = new CountDownLatch(1);
            zk = new ZooKeeper(ZooKeeperConst.host, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        connectedSignal.countDown();
                    }
                }
            });
            //因为监听器是异步操作, 要保证监听器操作先完成, 即要确保先连接上ZooKeeper再返回实例.
            connectedSignal.await();

            //创建dubbo注册中心的根节点(持久节点)
            if (zk.exists(ZooKeeperConst.rootNode, false) == null) {
                zk.create(ZooKeeperConst.rootNode, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 将服务和服务提供者URL注册到注册中心
     * @param serviceName 服务名称
     * @param serviceProviderAddr 服务所在TCP地址
     */
    public void register(String serviceName, String serviceProviderAddr) {
        try {
            //创建服务节点
            String servicePath = ZooKeeperConst.rootNode + "/" + serviceName;
            if (zk.exists(servicePath, false) == null) {
                zk.create(servicePath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            //创建服务提供者节点
            String serviceProviderPath = servicePath + "/" + serviceProviderAddr;
            if (zk.exists(serviceProviderPath, false) == null) {
                zk.create(serviceProviderPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
        } catch (Exception e) {
        }
    }
}

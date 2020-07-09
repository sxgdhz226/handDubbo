package com.handdubbo.customer;

import com.handdubbo.constant.ZooKeeperConst;
import com.handdubbo.loadBalance.RandomLoadBalance;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author: sxx
 * @Date 2020/6/26 15:55
 * @Version 1.0
 **/
public class ServiceSubscribe {

    private ZooKeeper zk;
    private List<String> providerList;

    /**
     * 连接ZooKeeper, 创建dubbo根节点
     */
    public ServiceSubscribe() {
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
     * 在注册中心订阅服务, 返回对应的服务url
     * 只要第一次获取到了服务的RPC地址, 后面注册中心挂掉之后, 仍然可以继续通信.
     * @param serviceName 服务名称
     * @return 服务host
     */
    public String subscribe(String serviceName) {
        //服务节点路径
        final String servicePath = ZooKeeperConst.rootNode + "/" + serviceName;
        try {
            //获取服务节点下的所有子节点, 即服务的RPC地址
            providerList = zk.getChildren(servicePath, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        try {
                            //循环监听
                            providerList = zk.getChildren(servicePath, true);
                        } catch (KeeperException | InterruptedException e) {
                        }
                    }
                }
            });
        } catch (Exception e) {
        }

        //负载均衡
        RandomLoadBalance randomLoadBalance = new RandomLoadBalance();
        return randomLoadBalance.doSelect(providerList);
    }

}

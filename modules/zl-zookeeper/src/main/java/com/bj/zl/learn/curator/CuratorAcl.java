/**
 * create by Administrator
 * 2019-09-18
 */
package com.bj.zl.learn.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.AuthenticationProvider;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * 为zookeeper增加权限
 * 具有创建权限的可以创建子节点,创建时可以指定权限,如不指定则是anyone
 * 不具有创建权限的不可以创建子节点,但是可以创建非该节点 子节点的其他节点
 *
 * 创建用户ID的时候 注意加密
 */
public class CuratorAcl {

    private CuratorFramework client;
    private static final String ACL_Path = "/acl1";
    private static final String ZK_ADRESS = "127.0.0.1";
    public CuratorAcl() {
        //client = CuratorFrameworkFactory.newClient(ZK_ADRESS,new RetryNTimes(3,10));
        client = CuratorFrameworkFactory.builder()
                //.authorization("digest","zhangsan:123".getBytes())
                //.authorization("digest","wangwu:123".getBytes())
                .authorization("digest","lisi:123".getBytes())
                .connectString(ZK_ADRESS)
                .retryPolicy(new RetryNTimes(3,200))
                .sessionTimeoutMs(100)
                .connectionTimeoutMs(1000000)
                .build();
        client.start();
    }

    public static void main(String[] args) throws Exception {
        CuratorAcl curatorAcl = new CuratorAcl();
        if (CuratorFrameworkState.STARTED.name().equals(curatorAcl.client.getState().name())){
            System.out.println("curator 连接成功");
            /*List<ACL> aclList = new ArrayList<>();
            Id zhangsan = new Id("digest", DigestAuthenticationProvider.generateDigest("zhangsan:123"));
            Id lisi = new Id("digest", DigestAuthenticationProvider.generateDigest("lisi:123"));
            aclList.add(new ACL(ZooDefs.Perms.ALL,zhangsan));
            aclList.add(new ACL(ZooDefs.Perms.READ,lisi));*/

            //创建节点
          /*  curatorAcl.client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)//指定节点类型
                    .withACL(aclList)//指定权限
                    .forPath(ACL_Path,"acl".getBytes());*/
            //修改数据
            //curatorAcl.client.setData().withVersion(-1).forPath(ACL_Path,"acl6".getBytes());
            //System.out.println(new String(curatorAcl.client.getData().forPath(ACL_Path)));
            /*List<ACL> acls = curatorAcl.client.getACL().forPath(ACL_Path);
            acls.add(new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.CREATE |ZooDefs.Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("wangwu:123"))));
            curatorAcl.client.setACL()
                    .withACL(acls)
                    .forPath(ACL_Path);*/
            Stat stat = new Stat();
            System.out.println(new String(curatorAcl.client.getData().storingStatIn(stat).forPath(ACL_Path+"/acl")));
            curatorAcl.client.setData().withVersion(stat.getVersion()).forPath(ACL_Path+"/acl","s1f".getBytes());
            List<ACL> acls = curatorAcl.client.getACL().forPath(ACL_Path + "/acl");
            System.out.println(new String(curatorAcl.client.getData().storingStatIn(stat).forPath(ACL_Path+"/acl")));

            /*curatorAcl.client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)//指定节点类型
                   // .withACL(aclList)//指定权限
                    .forPath(ACL_Path + "/acl1","acl".getBytes());*/


        }
    }
}

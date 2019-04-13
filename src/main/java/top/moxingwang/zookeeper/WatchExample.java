package top.moxingwang.zookeeper;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WatchExample {


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() ->
        {
            ZooKeeper zk = null;
            try {
                zk = new ZooKeeper("localhost:2181", 3000, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        System.out.println(watchedEvent.getPath());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Stat stat = new Stat();
                byte[] data = zk.getData("/a", true, stat);
                System.out.println(new String(data));

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();


        countDownLatch.await();

    }


}

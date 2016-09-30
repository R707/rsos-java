import com.abcs.finance.rsos.Master;
import com.abcs.finance.rsos.Rsos;
import com.abcs.finance.rsos.callback.IWatchDirCallback;
import com.abcs.finance.rsos.callback.IWatchFileCallback;
import com.abcs.finance.rsos.utils.ZkCli;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * Created by cavasblack on 16/9/30.
 */
public class WatchFileTest {

    public void watchFile() {
        try {
            ZkCli zk = new ZkCli("localhost:2181");

            zk.watchDir("/tr-java", new IWatchDirCallback() {
                public void change(List<String> list) {
                    System.out.println("====>" + list);
                }
            });
            zk.watchFile("/tr-java/aaaa", new IWatchFileCallback() {
                public void change(String data) {
                    System.out.println("====<" + data);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new WatchFileTest().watchFile();
    }

}

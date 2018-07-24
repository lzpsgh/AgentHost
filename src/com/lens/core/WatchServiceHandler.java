package com.lens.core;

import com.lens.util.LogUtil;

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceHandler {
    // 只在Linux的大部分常规目录下 正确计数，windows下修改会产生多个事件
    private String filePath = "./rspPath";
    public WatchService watchService;
    public WatchKey key;

    // 要监听事件的文件夹，目前只能监听1个
    public void watch(String path){
        this.filePath = path;
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(filePath).register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            LogUtil.i("注册目录事件监听器："+filePath);

            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if(event.count() == 1){
                        LogUtil.i(event.kind() + "---> " + event.context() );
                    }
                }
                if(!key.reset()){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}

package com.paojiao.user;

import com.paojiao.user.task.UserTaskService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

//@Component
public class UserInfoCacheTest {
    @Inject
    private UserTaskService userTaskService;
    int userId = 0;

    @PostConstruct
    public void test() {
//		Thread thread = new Thread(() -> {
//			while (true) {
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				try {
//					userService.getUserInfo(1);
//					userService.getUserInfo(1);
//				} catch (Exception err) {
//					err.printStackTrace();
//				}
//			}
//		});
//		thread.start();
    }
}

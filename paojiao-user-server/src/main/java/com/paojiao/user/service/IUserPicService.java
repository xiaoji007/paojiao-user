package com.paojiao.user.service;

import com.paojiao.user.service.bean.UserPicInfo;

import java.util.List;

public interface IUserPicService {

    /**
     * 添加首页图片
     *
     * @param userId 用户id
     * @param index  图片位置
     * @param pic    图片地址
     * @return
     */
    void addUserPicInfo(int userId, int index, String pic);

    /**
     * 首页图片移动
     *
     * @param userId  用户id
     * @param index   图片位置
     * @param toIndex 图片移动位置
     * @return
     */
    void moveUserPic(int userId, int index, int toIndex);

    /**
     * 删除用户图片
     *
     * @param userId     用户id
     * @param userPicIds 图片id
     * @return
     */
    void subUserIndexPicInfo(int userId, List<Integer> userPicIds);


    /**
     * 获取用户首页所有图片
     *
     * @param userId 用户id
     * @return
     */
    List<UserPicInfo> listUserAllPicInfo(int userId,boolean reset);

    UserPicInfo getUserHeadPicInfo(int userId);
}

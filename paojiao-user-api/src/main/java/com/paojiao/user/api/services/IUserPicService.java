package com.paojiao.user.api.services;

import com.fission.motan.spring.Rpc;
import com.fission.next.common.bean.ClientContext;
import com.fission.next.common.constant.ServiceName;
import com.fission.utils.bean.ResultUtil;
import com.paojiao.user.api.bean.UserPicInfoBean;
import com.weibo.api.motan.transport.async.MotanAsync;

import java.util.List;
import java.util.Map;

@MotanAsync
@Rpc(ServiceName.PAOJIAO_USER)
public interface IUserPicService {
    /**
     * 添加首页图片
     *
     * @param userId        用户id
     * @param index         图片位置
     * @param pic           图片地址
     * @param clientContext 客户端信息
     * @return
     */
    ResultUtil<Void> addUserPicInfo(int userId, int index, String pic, ClientContext clientContext);

    /**
     * 首页图片移动
     *
     * @param userId        用户id
     * @param index         图片位置
     * @param toIndex       图片移动位置
     * @param clientContext 客户端信息
     * @return
     */
    ResultUtil<Void> moveUserPic(int userId, int index, int toIndex, ClientContext clientContext);

    /**
     * 删除用户图片
     *
     * @param userId        用户id
     * @param userPicIds    图片id
     * @param clientContext 客户端信息
     * @return
     */
    ResultUtil<Void> subUserIndexPicInfo(int userId, List<Integer> userPicIds, ClientContext clientContext);


    /**
     * 获取用户首页所有图片
     *
     * @param userId        用户id
     * @param clientContext 客户端信息
     * @return
     */
    ResultUtil<List<UserPicInfoBean>> listUserAllPicInfo(int userId, ClientContext clientContext);

    /**
     * 获取用户首页所有图片
     *
     * @param userIds        用户id
     * @param clientContext 客户端信息
     * @return
     */
    ResultUtil<Map<Integer,List<UserPicInfoBean>>> listUsersAllPicInfo(List<Integer> userIds, ClientContext clientContext);
}
